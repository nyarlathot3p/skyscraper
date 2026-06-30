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

import cl.skyscraper.payments.model.Transaction;
import cl.skyscraper.payments.service.TransactionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transacciones", description = "Operaciones para gestionar las transacciones de pagos")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // ok
    @Operation(summary = "Listar transacciones", description = "Devuelve todas las transacciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de transacciones devuelta correctamente")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    // ok
    @Operation(summary = "Obtener transacción", description = "Devuelve una transacción por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @Parameter(description = "ID de la transacción", example = "1", required = true) @PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    // ok
    @Operation(summary = "Crear transacción", description = "Registra una nueva transacción de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Transacción creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    // ok
    @Operation(summary = "Actualizar transacción", description = "Actualiza una transacción existente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transacción actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @Parameter(description = "ID de la transacción", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    // ok
    @Operation(summary = "Reembolsar transacción", description = "Cancela o reembolsa una transacción por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Transacción reembolsada correctamente"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> refundTransaction(
            @Parameter(description = "ID de la transacción", example = "1", required = true) @PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
