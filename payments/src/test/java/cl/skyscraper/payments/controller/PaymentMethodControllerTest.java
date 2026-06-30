package cl.skyscraper.payments.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.skyscraper.payments.exception.ResourceNotFoundException;
import cl.skyscraper.payments.model.PaymentMethod;
import cl.skyscraper.payments.service.PaymentMethodService;

@WebMvcTest(PaymentMethodController.class)
public class PaymentMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentMethodService paymentMethodService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllPaymentMethods() throws Exception {
        PaymentMethod pm = new PaymentMethod(1L, "Tarjeta de crédito");
        when(paymentMethodService.getAllPaymentMethods()).thenReturn(List.of(pm));

        mockMvc.perform(get("/api/v1/payment_methods")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].paymentMethod").value("Tarjeta de crédito"));
    }

    @Test
    public void testGetPaymentMethodById() throws Exception {
        PaymentMethod pm = new PaymentMethod(1L, "Efectivo");
        when(paymentMethodService.getPaymentMethodById(1L)).thenReturn(pm);

        mockMvc.perform(get("/api/v1/payment_methods/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.paymentMethod").value("Efectivo"));
    }

    @Test
    public void testGetPaymentMethodByIdReturnsNotFound() throws Exception {
        when(paymentMethodService.getPaymentMethodById(99L))
                .thenThrow(new ResourceNotFoundException("PaymentMethod not found with id: 99"));

        mockMvc.perform(get("/api/v1/payment_methods/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("PaymentMethod not found with id: 99"));
    }

    @Test
    public void testCreatePaymentMethod() throws Exception {
        PaymentMethod incomingPm = new PaymentMethod(null, "Efectivo");
        PaymentMethod savedPm = new PaymentMethod(2L, "Efectivo");

        when(paymentMethodService.createPaymentMethod(any(PaymentMethod.class))).thenReturn(savedPm);

        mockMvc.perform(post("/api/v1/payment_methods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomingPm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.paymentMethod").value("Efectivo"));
    }

    @Test
    public void testUpdatePaymentMethod() throws Exception {
        PaymentMethod incomingPm = new PaymentMethod(null, "Transferencia");
        PaymentMethod updatedPm = new PaymentMethod(1L, "Transferencia");

        when(paymentMethodService.updatePaymentMethod(any(Long.class), any(PaymentMethod.class))).thenReturn(updatedPm);

        mockMvc.perform(put("/api/v1/payment_methods/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomingPm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.paymentMethod").value("Transferencia"));
    }

    @Test
    public void testDeletePaymentMethod() throws Exception {
        doNothing().when(paymentMethodService).deletePaymentMethod(1L);

        mockMvc.perform(delete("/api/v1/payment_methods/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}