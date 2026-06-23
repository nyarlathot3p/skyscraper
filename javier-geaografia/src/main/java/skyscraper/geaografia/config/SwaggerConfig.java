package skyscraper.geaografia.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.model.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Geaografia API")
                        .version("1.0")
                        .description("API for Geaografia service"));
}
}
