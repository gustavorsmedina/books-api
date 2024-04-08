package tech.gustavomedina.booksapi.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI().info(new Info()
                .title("Books API")
                .version("1.0")
                .description("The Books API simplifies the management of information regarding books and authors.")
                .contact(new Contact()
                        .name("Gustavo Medina")
                        .url("https://gustavomedina.tech"))
        );

    }

}