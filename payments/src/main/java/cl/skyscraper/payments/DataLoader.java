package cl.skyscraper.payments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Strings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import cl.skyscraper.payments.model.PaymentMethod;
import cl.skyscraper.payments.model.Transaction;
import cl.skyscraper.payments.repository.PaymentMethodRepository;
import cl.skyscraper.payments.repository.TransactionRepository;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionRepository transactionRepository;
    private final Faker faker;

    public DataLoader(PaymentMethodRepository paymentMethodRepository, TransactionRepository transactionRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.transactionRepository = transactionRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        if (paymentMethodRepository.count() == 0) {
            loadFakeData();
        }
    }

    private void loadFakeData() {
        System.out.println("Inicia generación de data falsa...");

        // metodos
        String[] methodNames = {"Tarjeta de crédito", "Tarjeta de débito", "Transferencia bancaria", "Efectivo"};
        List<PaymentMethod> savedMethods = new ArrayList<>();
        for (String name : methodNames) {
            PaymentMethod pm = new PaymentMethod();
            pm.setPaymentMethod(name);
            savedMethods.add(paymentMethodRepository.save(pm));
        }

        // transacciones
        for (int i = 0; i < 50; i++){
            Transaction transaction = new Transaction();

            transaction.setTransactionValue(faker.number().numberBetween(1000L, 100000L));
            transaction.setTransactionDate(Date.from(faker.timeAndDate().past(30, TimeUnit.DAYS)));

            PaymentMethod randomMethod = savedMethods.get(faker.number().numberBetween(0, savedMethods.size()));
            transaction.setPaymentMethod(randomMethod);

            transactionRepository.save(transaction);
        }

        System.out.println("Generada data falsa: Transaction, PaymentMethod");
    }
    
}
