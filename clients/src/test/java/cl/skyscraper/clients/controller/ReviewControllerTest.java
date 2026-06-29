package cl.skyscraper.clients.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cl.skyscraper.clients.dto.ReviewRequestDTO;
import cl.skyscraper.clients.dto.ReviewResponseDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.model.Review;
import cl.skyscraper.clients.service.ReviewService;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void shouldReturnAllReviews() throws Exception {
        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(1L);
        response.setDescription("Excelente servicio");
        response.setStars(5);
        response.setIdAeroline(2L);
        response.setUser(new UserResponseDTO());

        given(reviewService.findAll()).willReturn(List.of(response));

        mockMvc.perform(get("/api_clients/v1/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].stars").value(5))
                .andExpect(jsonPath("$[0].description").value("Excelente servicio"));
    }
    
    @Test
    void shouldReturnReviewById() throws Exception {
        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(1L);
        response.setDescription("Muy bueno");
        response.setStars(4);
        response.setIdAeroline(3L);
        response.setUser(new UserResponseDTO());

        given(reviewService.findById(1L)).willReturn(response);

        mockMvc.perform(get("/api_clients/v1/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stars").value(4));
    }

    @Test
    void shouldCreateReview() throws Exception {
        Review request = new Review();
        request.setIdAeroline(2L);
        request.setDescription("Perfecto vuelo");
        request.setStars(5);
        request.setDate(new Date(System.currentTimeMillis()));

        mockMvc.perform(post("/api_clients/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateReview() throws Exception {
        ReviewRequestDTO request = new ReviewRequestDTO("Servicio mejorado", 4);
        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(1L);
        response.setDescription("Servicio mejorado");
        response.setStars(4);
        response.setIdAeroline(1L);
        response.setUser(new UserResponseDTO());

        given(reviewService.editReview(eq(1L), any(ReviewRequestDTO.class))).willReturn(response);

        mockMvc.perform(put("/api_clients/v1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stars").value(4));
    }

    @Test
    void shouldDeleteReview() throws Exception {
        doNothing().when(reviewService).deleteReview(1L);

        mockMvc.perform(delete("/api_clients/v1/reviews/1"))
                .andExpect(status().isNoContent());
    }
}
