package cl.skyscraper.payments.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Transaction value cannot be null")
    @Positive(message = "Transaction value must be positive")
    private Long transactionValue;

    private Date transactionDate;

    @NotNull(message = "Payment method cannot be null")
    @JoinColumn(name = "id_PaymentMethod")
    @ManyToOne  
    private PaymentMethod paymentMethod;

}