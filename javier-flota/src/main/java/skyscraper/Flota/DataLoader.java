package skyscraper.Flota;

import skyscraper.Flota.model.*;
import skyscraper.Flota.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalTime;
@Profile("dev")
@Component

public class DataLoader implements CommandLineRunner {

    @Autowired
    private AvionRepository avionRepository;
    @Autowired
    private AerolineaRepository aerolineaRepository;
    @Autowired
    private VueloRepository vueloRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Crear aerolíneas
        for (int i = 0; i < 5; i++) {
            Aerolinea aerolinea = Aerolinea.builder()
                    .nombre(faker.company().name())
                    .vuelos(null)
                    .build();
            aerolineaRepository.save(aerolinea);
        }

        List<Aerolinea> aerolineas = aerolineaRepository.findAll();

        // Crear aviones
        for (int i = 0; i < 10; i++) {
            Avion avion = Avion.builder()
                    .modelo(faker.aviation().aircraft())
                    .capacidad(faker.number().numberBetween(20, 860))
                    .filas(faker.number().numberBetween(10, 90))
                    .columnas(faker.number().numberBetween(4, 10))
                    .vuelos(null)
                    .build();
            avionRepository.save(avion);
        }

        List<Avion> aviones = avionRepository.findAll();

        // Crear vuelos
        for (int i = 0; i < 20; i++) {
            java.util.Date utilFecha = faker.date().future(30, java.util.concurrent.TimeUnit.DAYS);
            Date sqlFecha = new Date(utilFecha.getTime());
            LocalTime hora = LocalTime.of(faker.number().numberBetween(0, 23), faker.number().numberBetween(0, 59), faker.number().numberBetween(0, 59));
            Time sqlHora = Time.valueOf(hora);

            Vuelo vuelo = Vuelo.builder()
                    .origen(faker.address().city())
                    .destino(faker.address().city())
                    .fecha(sqlFecha)
                    .horaSalida(sqlHora)
                    .duracionMinutos(String.valueOf(faker.number().numberBetween(60, 600)))
                    .estado(faker.options().option("Programado", "En vuelo", "Aterrizado", "Cancelado"))
                    .IdAvion(aviones.get(random.nextInt(aviones.size())))
                    .aerolinea(aerolineas.get(random.nextInt(aerolineas.size())))
                    .build();
            vueloRepository.save(vuelo);
        }

    }

}
