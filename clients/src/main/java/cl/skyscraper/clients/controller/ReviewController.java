package cl.skyscraper.clients.controller;

import cl.skyscraper.clients.dto.ReviewRequestDTO;
import cl.skyscraper.clients.dto.ReviewResponseDTO;
import cl.skyscraper.clients.model.Review;
import cl.skyscraper.clients.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api_clients/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Reseñas", description = "Endpoints para el manejo de reseñas")
public class ReviewController {

    private final ReviewService reviewService;

    // Métodos sin JWT) 

    // Metodos sin Feign Client
    // ok
    @Operation(summary = "Listar todas las reseñas", description = "Devuelve todas las reseñas disponibles")
    @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente")
    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> findAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    // ok
    @Operation(summary = "Obtener una reseña por id", description = "Devuelve la reseña que coincide con el identificador proporcionado")
    @ApiResponse(responseCode = "200", description = "Reseña encontrada")
    @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> findById (@Parameter(description = "Review id", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    // Metodos que usan Feign Client

    //
    @Operation(summary = "Resolver todas las reseñas", description = "Devuelve todas las reseñas con los datos resueltos de aerolíneas")
    @ApiResponse(responseCode = "200", description = "Reseñas resueltas obtenidas correctamente")
    @GetMapping("/resolve")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAllResolve()); 
    }

    @Operation(summary = "Resolver una reseña por id", description = "Devuelve una reseña con sus datos externos resueltos")
    @ApiResponse(responseCode = "200", description = "Reseña resuelta encontrada")
    @GetMapping("/resolve/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewByIdResolve(@Parameter(description = "Review id", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewByIdResolve(id)); 
    }

    @Operation(summary = "Obtener reseñas por aerolínea", description = "Devuelve las reseñas recientes para la aerolínea especificada")
    @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente")
    @GetMapping("/resolve/aeroline/{idAerolinea}")
    public ResponseEntity<List<ReviewResponseDTO>> getByAerolineId(@Parameter(description = "Id de la aerolínea", required = true) @PathVariable Long idAerolinea) {
        return ResponseEntity.ok(reviewService.findByIdAerolineRecentResolve(idAerolinea)); 
    }
    
    // Métodos que requieren JWT

    // ok
    @Operation(summary = "Crear una reseña", description = "Crea una nueva reseña para una aerolínea")
    @ApiResponse(responseCode = "201", description = "Reseña creada correctamente")
    @ApiResponse(responseCode = "400", description = "Payload de reseña inválido")
    @PostMapping
    public ResponseEntity<Void> addReview(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalles de la reseña", required = true) @org.springframework.web.bind.annotation.RequestBody Review review) {
        reviewService.addReview(review); 
        return new ResponseEntity<>(HttpStatus.CREATED); 
    }

    // ok
    @Operation(summary = "Actualizar una reseña", description = "Actualiza una reseña existente")
    @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
        @Parameter(description = "Id de la reseña", required = true) @PathVariable Long id, 
        @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la reseña", required = true) @org.springframework.web.bind.annotation.RequestBody ReviewRequestDTO updateData) {
        ReviewResponseDTO updated = reviewService.editReview(id, updateData); 
    return ResponseEntity.ok(updated); 
}

    // ok
    @Operation(summary = "Eliminar una reseña", description = "Elimina la reseña que coincide con el identificador proporcionado")
    @ApiResponse(responseCode = "204", description = "Reseña eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@Parameter(description = "Id de la reseña", required = true) @PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); 
    }

}