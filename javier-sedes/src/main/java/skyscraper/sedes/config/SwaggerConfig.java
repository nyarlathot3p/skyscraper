import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.model.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configurationpackage skyscraper.sedes.config;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sedes API")
                        .version("1.0")
                        .description("Documentacion de la API de Sedes"));
    }

}
