package cl.skyscraper.clients;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cl.skyscraper.clients.model.Passenger;
import cl.skyscraper.clients.model.Review;
import cl.skyscraper.clients.model.Token;
import cl.skyscraper.clients.model.User;
import cl.skyscraper.clients.repository.PassengerRepository;
import cl.skyscraper.clients.repository.ReviewRepository;
import cl.skyscraper.clients.repository.TokenRepository;
import cl.skyscraper.clients.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final ReviewRepository reviewRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0 || passengerRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker(Locale.forLanguageTag("es-CL"));
        List<User> users = createUsers(faker);
        userRepository.saveAll(users);

        List<Token> tokens = createTokens(users, faker);
        tokenRepository.saveAll(tokens);

        passengerRepository.saveAll(createPassengers(faker, 20));
        reviewRepository.saveAll(createReviews(faker, users, 30));

        System.out.println("[DataLoader] seeded dev data: " + users.size() + " users, "
                + tokens.size() + " tokens, 20 passengers, 30 reviews.");
    }

    private List<User> createUsers(Faker faker) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int run = 1000000 + i;
            User user = User.builder()
                    .run(run)
                    .dv(String.valueOf(faker.number().numberBetween(0, 9)))
                    .username(faker.name().firstName().toLowerCase() + run)
                    .name(faker.name().firstName())
                    .middlename(faker.name().firstName())
                    .lastname(faker.name().lastName())
                    .secondlastname(faker.name().lastName())
                    .birthdate(LocalDate.now().minusYears(faker.number().numberBetween(20, 60)))
                    .phonenumber(faker.phoneNumber().cellPhone())
                    .email("usuario" + run + "@ejemplo.cl")
                    .address(faker.address().fullAddress())
                    .password(passwordEncoder.encode("password123"))
                    .build();
            users.add(user);
        }

        return users;
    }

    private List<Token> createTokens(List<User> users, Faker faker) {
        List<Token> tokens = new ArrayList<>();
        for (User user : users) {
            Token token = Token.builder()
                    .token(faker.internet().uuid())
                    .tokenType(Token.TokenType.BEARER)
                    .revoked(false)
                    .expired(false)
                    .user(user)
                    .build();
            tokens.add(token);
        }
        return tokens;
    }

    private List<Passenger> createPassengers(Faker faker, int count) {
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int run = 2000000 + i;
            Passenger passenger = Passenger.builder()
                    .run(run)
                    .dv(String.valueOf(faker.number().numberBetween(0, 9)))
                    .firstName(faker.name().firstName())
                    .secondName(faker.name().firstName())
                    .paternalLastName(faker.name().lastName())
                    .maternalLastName(faker.name().lastName())
                    .birthDate(LocalDate.now().minusYears(faker.number().numberBetween(18, 80)))
                    .phone(faker.phoneNumber().cellPhone())
                    .email("pasajero" + run + "@ejemplo.cl")
                    .build();
            passengers.add(passenger);
        }
        return passengers;
    }

    private List<Review> createReviews(Faker faker, List<User> users, int count) {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = users.get(faker.number().numberBetween(0, users.size()));
            LocalDate randomDate = LocalDate.now().minusDays(faker.number().numberBetween(1, 365));
            Review review = new Review();
            review.setUser(user);
            review.setIdAeroline((long) faker.number().numberBetween(1, 6));
            review.setDescription(faker.lorem().sentence(faker.number().numberBetween(5, 20)));
            review.setDate(Date.valueOf(randomDate));
            review.setStars(faker.number().numberBetween(1, 6));
            reviews.add(review);
        }
        return reviews;
    }
}
