package cl.skyscraper.clients.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import cl.skyscraper.clients.clients.AerolineClientRest;
import cl.skyscraper.clients.dto.AerolineDTO;
import cl.skyscraper.clients.dto.ReviewRequestDTO;
import cl.skyscraper.clients.dto.ReviewResponseDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.model.Review;
import cl.skyscraper.clients.model.User;
import cl.skyscraper.clients.repository.ReviewRepository;
import cl.skyscraper.clients.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AerolineClientRest aerolineClient;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("user@example.com");
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void findAll_shouldReturnMappedResponseDTOs() {
        Review review = buildReview(1L, 1L, buildUser(1L, "user@example.com"));
        when(reviewRepository.findAll()).thenReturn(List.of(review));
        when(userService.mapToUserDTO(any(User.class))).thenReturn(buildUserResponseDTO(1L));

        List<ReviewResponseDTO> responses = reviewService.findAll();

        assertEquals(1, responses.size());
        assertEquals(review.getId(), responses.get(0).getId());
        assertNull(responses.get(0).getAerolinea());
    }

    @Test
    void findById_shouldReturnReviewResponseDTO_whenFound() {
        Review review = buildReview(1L, 2L, buildUser(1L, "user@example.com"));
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(userService.mapToUserDTO(any(User.class))).thenReturn(buildUserResponseDTO(1L));

        ReviewResponseDTO response = reviewService.findById(1L);

        assertEquals(1L, response.getId());
        assertEquals(review.getDescription(), response.getDescription());
    }

    @Test
    void findAllResolve_shouldReturnAerolineResolvedReviews() {
        Review review = buildReview(1L, 3L, buildUser(1L, "user@example.com"));
        AerolineDTO aeroline = new AerolineDTO();
        aeroline.setId(3L);
        aeroline.setName("TestAir");

        when(reviewRepository.findAll()).thenReturn(List.of(review));
        when(aerolineClient.getAll()).thenReturn(List.of(aeroline));
        when(userService.mapToUserDTO(any(User.class))).thenReturn(buildUserResponseDTO(1L));

        List<ReviewResponseDTO> responseList = reviewService.findAllResolve();

        assertEquals(1, responseList.size());
        assertNotNull(responseList.get(0).getAerolinea());
        assertEquals(3L, responseList.get(0).getAerolinea().getId());
    }

    @Test
    void getReviewByIdResolve_shouldResolveAeroline() {
        Review review = buildReview(1L, 4L, buildUser(1L, "user@example.com"));
        AerolineDTO aeroline = new AerolineDTO();
        aeroline.setId(4L);
        aeroline.setName("BestAir");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(aerolineClient.getAerolineById(4L)).thenReturn(aeroline);
        when(userService.mapToUserDTO(any(User.class))).thenReturn(buildUserResponseDTO(1L));

        ReviewResponseDTO response = reviewService.getReviewByIdResolve(1L);

        assertNotNull(response.getAerolinea());
        assertEquals(4L, response.getAerolinea().getId());
    }

    @Test
    void findByIdAerolineRecentResolve_shouldReturnResolvedReviews() {
        Review review = buildReview(1L, 5L, buildUser(1L, "user@example.com"));
        AerolineDTO aeroline = new AerolineDTO();
        aeroline.setId(5L);
        aeroline.setName("FastAir");

        when(reviewRepository.findByIdAerolineOrderByDateDesc(5L)).thenReturn(List.of(review));
        when(aerolineClient.getAerolineById(5L)).thenReturn(aeroline);
        when(userService.mapToUserDTO(any(User.class))).thenReturn(buildUserResponseDTO(1L));

        List<ReviewResponseDTO> responseList = reviewService.findByIdAerolineRecentResolve(5L);

        assertEquals(1, responseList.size());
        assertEquals(5L, responseList.get(0).getAerolinea().getId());
    }

    @Test
    void addReview_shouldAssignAuthenticatedUserAndSave() {
        User user = buildUser(1L, "user@example.com");
        Review review = buildReview(null, 1L, null);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        reviewService.addReview(review);

        assertEquals(user, review.getUser());
        verify(reviewRepository).save(review);
    }

    @Test
    void editReview_shouldUpdateReviewWhenAuthenticatedUserOwnsReview() {
        User user = buildUser(1L, "user@example.com");
        Review review = buildReview(1L, 1L, user);
        review.setDescription("Old description");
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(userService.mapToUserDTO(any(User.class))).thenReturn(buildUserResponseDTO(1L));

        ReviewResponseDTO response = reviewService.editReview(1L, new ReviewRequestDTO("New description", 4));

        assertEquals("New description", response.getDescription());
        assertEquals(4, response.getStars());
    }

    @Test
    void deleteReview_shouldDeleteWhenAuthenticatedUserOwnsReview() {
        User user = buildUser(1L, "user@example.com");
        Review review = buildReview(1L, 1L, user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        reviewService.deleteReview(1L);

        verify(reviewRepository).delete(review);
    }

    private Review buildReview(Long id, Long aerolineId, User user) {
        Review review = new Review();
        review.setId(id);
        review.setIdAeroline(aerolineId);
        review.setDescription("Sample review");
        review.setDate(Date.valueOf(LocalDate.of(2024, 1, 1)));
        review.setStars(3);
        review.setUser(user);
        return review;
    }

    private User buildUser(Long id, String email) {
        User user = User.builder()
                .id(id)
                .run(12345678)
                .dv("1")
                .username("user1")
                .name("User")
                .lastname("Tester")
                .secondlastname("Example")
                .birthdate(LocalDate.of(1990, 1, 1))
                .phonenumber("+56900000000")
                .email(email)
                .address("Address")
                .password("password")
                .build();
        return user;
    }

    private UserResponseDTO buildUserResponseDTO(Long id) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(id);
        dto.setRun(12345678);
        dto.setName("User");
        dto.setEmail("user@example.com");
        return dto;
    }
}
