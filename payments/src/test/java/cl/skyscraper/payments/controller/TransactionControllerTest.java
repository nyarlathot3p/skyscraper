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

import java.util.Date;
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
import cl.skyscraper.payments.model.Transaction;
import cl.skyscraper.payments.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllTransactions() throws Exception {
        PaymentMethod pm = new PaymentMethod(1L, "Tarjeta de crédito");
        Transaction transaction = new Transaction(1L, 15000L, new Date(), pm);

        when(transactionService.getAllTransactions()).thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].transactionValue").value(15000L))
                .andExpect(jsonPath("$[0].paymentMethod.paymentMethod").value("Tarjeta de crédito"));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        PaymentMethod pm = new PaymentMethod(1L, "Efectivo");
        Transaction transaction = new Transaction(1L, 25000L, new Date(), pm);
        when(transactionService.getTransactionById(1L)).thenReturn(transaction);

        mockMvc.perform(get("/api/v1/transactions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.transactionValue").value(25000L));
    }

    @Test
    public void testGetTransactionByIdReturnsNotFound() throws Exception {
        when(transactionService.getTransactionById(99L))
                .thenThrow(new ResourceNotFoundException("Transaction not found with id: 99"));

        mockMvc.perform(get("/api/v1/transactions/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Transaction not found with id: 99"));
    }

    @Test
    public void testCreateTransaction() throws Exception {
        PaymentMethod pm = new PaymentMethod(1L, "Efectivo");
        Transaction incomingTransaction = new Transaction(null, 25000L, null, pm);
        Transaction savedTransaction = new Transaction(2L, 25000L, new Date(), pm);

        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomingTransaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.transactionValue").value(25000L));
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        PaymentMethod pm = new PaymentMethod(1L, "Efectivo");
        Transaction incomingTransaction = new Transaction(null, 30000L, null, pm);
        Transaction updatedTransaction = new Transaction(1L, 30000L, new Date(), pm);

        when(transactionService.updateTransaction(any(Long.class), any(Transaction.class))).thenReturn(updatedTransaction);

        mockMvc.perform(put("/api/v1/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomingTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.transactionValue").value(30000L));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/api/v1/transactions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}