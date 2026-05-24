package cl.skyscraper.clients.controller;

import cl.skyscraper.clients.dto.ReviewRequestDTO;
import cl.skyscraper.clients.dto.ReviewResponseDTO;
import cl.skyscraper.clients.model.Review;
import cl.skyscraper.clients.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api_clients/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Métodos sin JWT) 

    // Metodos sin Feign Client
    // ok
    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> findAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    // ok
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    // Metodos que usan Feign Client

    //
    @GetMapping("/resolve")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAllResolve()); 
    }

    @GetMapping("/resolve/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewByIdResolve(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewByIdResolve(id)); 
    }

    @GetMapping("/resolve/aeroline/{idAerolinea}")
    public ResponseEntity<List<ReviewResponseDTO>> getByAerolineId(@PathVariable Long idAerolinea) {
        return ResponseEntity.ok(reviewService.findByIdAerolineRecentResolve(idAerolinea)); 
    }
    
    // Métodos que requieren JWT

    // ok
    @PostMapping
    public ResponseEntity<Void> addReview(@Valid @RequestBody Review review) {
        reviewService.addReview(review); 
        return new ResponseEntity<>(HttpStatus.CREATED); 
    }

    // ok
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
        @PathVariable Long id, 
        @Valid @RequestBody ReviewRequestDTO updateData) {
        ReviewResponseDTO updated = reviewService.editReview(id, updateData); 
    return ResponseEntity.ok(updated); 
}

    // ok
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); 
    }

}