package cl.skyscraper.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.skyscraper.payments.exception.ResourceNotFoundException;
import cl.skyscraper.payments.model.PaymentMethod;
import cl.skyscraper.payments.model.Transaction;
import cl.skyscraper.payments.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testGetAllTransactions() {
        PaymentMethod pm = new PaymentMethod(1L, "Tarjeta de débito");
        Transaction t1 = new Transaction(1L, 5000L, new Date(), pm);

        when(transactionRepository.findAll()).thenReturn(List.of(t1));

        List<Transaction> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5000L, result.get(0).getTransactionValue());
        assertEquals("Tarjeta de débito", result.get(0).getPaymentMethod().getPaymentMethod());
    }

    @Test
    public void testGetTransactionByIdReturnsTransaction() {
        PaymentMethod pm = new PaymentMethod(1L, "Tarjeta de débito");
        Transaction transaction = new Transaction(1L, 5000L, new Date(), pm);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertEquals(5000L, result.getTransactionValue());
        assertEquals("Tarjeta de débito", result.getPaymentMethod().getPaymentMethod());
    }

    @Test
    public void testGetTransactionByIdThrowsWhenNotFound() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.getTransactionById(99L));
    }

    @Test
    public void testCreateTransactionSetsDate() {
        PaymentMethod pm = new PaymentMethod(1L, "Tarjeta de débito");
        Transaction incomingTransaction = new Transaction(null, 15000L, null, pm);

        Transaction savedTransaction = new Transaction(1L, 15000L, new Date(), pm);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createTransaction(incomingTransaction);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getTransactionDate());
    }

    @Test
    public void testUpdateTransactionUpdatesExistingTransaction() {
        PaymentMethod pm = new PaymentMethod(1L, "Tarjeta de débito");
        Transaction existing = new Transaction(1L, 1000L, new Date(0), pm);
        Transaction incoming = new Transaction(null, 15000L, null, pm);
        Transaction updated = new Transaction(1L, 15000L, new Date(), pm);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updated);

        Transaction result = transactionService.updateTransaction(1L, incoming);

        assertNotNull(result);
        assertEquals(15000L, result.getTransactionValue());
        assertNotNull(result.getTransactionDate());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void testUpdateTransactionThrowsWhenNotFound() {
        when(transactionRepository.findById(55L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.updateTransaction(55L, new Transaction()));
    }

    @Test
    public void testDeleteTransactionDeletesExistingTransaction() {
        when(transactionRepository.existsById(1L)).thenReturn(true);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository).deleteById(1L);
    }

    @Test
    public void testDeleteTransactionThrowsWhenNotFound() {
        when(transactionRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> transactionService.deleteTransaction(99L));
    }
}