package cl.skyscraper.clients.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cl.skyscraper.clients.clients.AerolineClientRest;
import cl.skyscraper.clients.dto.AerolineDTO;
import cl.skyscraper.clients.dto.ReviewRequestDTO;
import cl.skyscraper.clients.dto.ReviewResponseDTO;
import cl.skyscraper.clients.model.Review;
import cl.skyscraper.clients.model.User;
import cl.skyscraper.clients.repository.ReviewRepository;
import cl.skyscraper.clients.repository.UserRepository;
import cl.skyscraper.clients.util.Messages;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AerolineClientRest aerolineClient;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser() {
    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();
    
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado en sesión"));
    }

    public List<ReviewResponseDTO> findAll() {
        return reviewRepository.findAll().stream()
            .map(review -> mappingManual(review, null)) // Pasamos null en aerolinea
            .toList();
    }

    public ReviewResponseDTO findById(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        ReviewResponseDTO response = mappingManual(review, null);  
        return response;
    }

    public List<ReviewResponseDTO> findAllResolve() {
        // 1. Obtenemos los datos brutos
        List<Review> reviews = reviewRepository.findAll();
        List<AerolineDTO> aerolines = aerolineClient.getAll();

        // 2. Preparamos el mapa para evitar búsquedas lentas (N+1)
        Map<Long, AerolineDTO> aerolineMap = aerolines.stream()
                .collect(Collectors.toMap(AerolineDTO::getId, aeroline -> aeroline));

        // 3. Delegamos la transformación al nuevo método
        return mapReviewsToResponseDTOs(reviews, aerolineMap);
    }

    public ReviewResponseDTO getReviewByIdResolve(Long id){
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        return transformToDTO(review);
    }

    public List<ReviewResponseDTO> findByIdAerolineRecentResolve(Long idAerolinea) {
        List<Review> reviews = reviewRepository.findByIdAerolineOrderByDateDesc(idAerolinea);
        AerolineDTO aerolinea = aerolineClient.getAerolineById(idAerolinea);

        return reviews.stream()
            .map(resena -> mappingManual(resena, aerolinea))
            .toList();
    }

    public void addReview(Review review) { 
        User currentUser = getAuthenticatedUser();
        review.setUser(currentUser); // Forzamos que el dueño sea el usuario del token
        review.setDate(new Date(System.currentTimeMillis())); 
        reviewRepository.save(review);
    }

    public ReviewResponseDTO editReview(Long id, ReviewRequestDTO updateData) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review no encontrada"));

        User currentUser = getAuthenticatedUser();

        // Verificamos si el ID del dueño coincide con el del usuario autenticado
        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException(Messages.NO_PERMISSION_EDIT_REVIEW);
        }

        review.setDescription(updateData.description());
        review.setStars(updateData.stars());
        review.setDate(new Date(System.currentTimeMillis()));

        return findById(review.getId());
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        
        User currentUser = getAuthenticatedUser();

        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException(Messages.NO_PERMISSION_DELETE_REVIEW);
        }

        reviewRepository.delete(review);
    }

    // --- Métodos de apoyo (Private Helpers) ---

    private ReviewResponseDTO transformToDTO(Review review) {
        AerolineDTO aeroline = aerolineClient.getAerolineById(review.getIdAeroline());
        return mappingManual(review, aeroline);
    }

    // 2. Mapeador Manual Unificado
    private ReviewResponseDTO mappingManual(Review review, AerolineDTO aeroline) {
        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(review.getId());
        response.setDescription(review.getDescription());
        response.setStars(review.getStars());
        response.setUser(userService.mapToUserDTO(review.getUser()));
        response.setIdAeroline(review.getIdAeroline());
        response.setAerolinea(aeroline); // Puede ser null si no se requiere resolver
        return response;
}

    private List<ReviewResponseDTO> mapReviewsToResponseDTOs(List<Review> reviews, Map<Long, AerolineDTO> aerolineMap) {
    return reviews.stream()
            .map(review -> {
                AerolineDTO aeroline = aerolineMap.get(review.getIdAeroline());
                return mappingManual(review, aeroline);
            })
            .toList();
    }


}
