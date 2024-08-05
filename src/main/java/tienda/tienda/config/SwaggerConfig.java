package tienda.tienda.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API for Online Store")
                        .version("1.0")
                        .description("API documentation for an online store.")
                        .contact(new Contact().name("Julian Cappellari").url("https://www.linkedin.com/in/juliancappellari/").email("juliancappellari13@gmail.com"))

                );
    }


}