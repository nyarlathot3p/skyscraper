package cl.skyscraper.payments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import cl.skyscraper.payments.model.PaymentMethod;
import cl.skyscraper.payments.service.PaymentMethodService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment_methods")
@Tag(name = "Métodos de Pago", description = "Operaciones para gestionar los métodos de pago")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    // ok
    @Operation(summary = "Listar métodos de pago", description = "Devuelve todos los métodos de pago registrados")
    @ApiResponse(responseCode = "200", description = "Lista de métodos de pago devuelta correctamente")
    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }

    // ok
    @Operation(summary = "Obtener método de pago", description = "Devuelve un método de pago por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Método de pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(
            @Parameter(description = "ID del método de pago", example = "1", required = true) @PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return ResponseEntity.ok(paymentMethod);
    }

    // ok
    @Operation(summary = "Crear método de pago", description = "Registra un nuevo método de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Método de pago creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(
            @Valid @RequestBody PaymentMethod paymentMethod) {
        PaymentMethod createdPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentMethod);
    }

    // ok
    @Operation(summary = "Actualizar método de pago", description = "Actualiza un método de pago existente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Método de pago actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(
            @Parameter(description = "ID del método de pago", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody PaymentMethod paymentMethod) {
        PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethod);
        return ResponseEntity.ok(updatedPaymentMethod);
    }

    // ok
    @Operation(summary = "Eliminar método de pago", description = "Elimina un método de pago existente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Método de pago eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(
            @Parameter(description = "ID del método de pago", example = "1", required = true) @PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
}

