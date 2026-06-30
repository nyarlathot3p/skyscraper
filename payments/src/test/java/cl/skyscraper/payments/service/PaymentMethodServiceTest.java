package cl.skyscraper.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.skyscraper.payments.exception.ResourceNotFoundException;
import cl.skyscraper.payments.model.PaymentMethod;
import cl.skyscraper.payments.repository.PaymentMethodRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodServiceTest {

    @InjectMocks
    private PaymentMethodService paymentMethodService;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Test
    public void testGetAllPaymentMethods() {
        when(paymentMethodRepository.findAll())
            .thenReturn(List.of(new PaymentMethod(1L, "Tarjeta de crédito")));

        List<PaymentMethod> result = paymentMethodService.getAllPaymentMethods();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tarjeta de crédito", result.get(0).getPaymentMethod());
    }

    @Test
    public void testGetPaymentMethodByIdReturnsPaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod(1L, "Tarjeta de crédito");
        when(paymentMethodRepository.findById(1L)).thenReturn(Optional.of(paymentMethod));

        PaymentMethod result = paymentMethodService.getPaymentMethodById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tarjeta de crédito", result.getPaymentMethod());
    }

    @Test
    public void testGetPaymentMethodByIdThrowsWhenNotFound() {
        when(paymentMethodRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentMethodService.getPaymentMethodById(99L));
    }

    @Test
    public void testCreatePaymentMethodSavesAndReturnsMethod() {
        PaymentMethod incoming = new PaymentMethod(null, "Transferencia");
        PaymentMethod saved = new PaymentMethod(2L, "Transferencia");
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(saved);

        PaymentMethod result = paymentMethodService.createPaymentMethod(incoming);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Transferencia", result.getPaymentMethod());
    }

    @Test
    public void testUpdatePaymentMethodUpdatesExistingMethod() {
        PaymentMethod existing = new PaymentMethod(1L, "Tarjeta de débito");
        PaymentMethod incoming = new PaymentMethod(null, "Transferencia");
        PaymentMethod updated = new PaymentMethod(1L, "Transferencia");
        when(paymentMethodRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(updated);

        PaymentMethod result = paymentMethodService.updatePaymentMethod(1L, incoming);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Transferencia", result.getPaymentMethod());
        verify(paymentMethodRepository).save(any(PaymentMethod.class));
    }

    @Test
    public void testUpdatePaymentMethodThrowsWhenNotFound() {
        when(paymentMethodRepository.findById(55L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentMethodService.updatePaymentMethod(55L, new PaymentMethod()));
    }

    @Test
    public void testDeletePaymentMethodDeletesExistingMethod() {
        when(paymentMethodRepository.existsById(1L)).thenReturn(true);

        paymentMethodService.deletePaymentMethod(1L);

        verify(paymentMethodRepository).deleteById(1L);
    }

    @Test
    public void testDeletePaymentMethodThrowsWhenNotFound() {
        when(paymentMethodRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> paymentMethodService.deletePaymentMethod(99L));
    }
}
