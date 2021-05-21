package guru.springfamework.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI spring5MvcRestOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("colwynb2k@github.com");
        contact.setName("Colwyb[B2K]");
        contact.setUrl("https://github.com/colwynB2K");

        return new OpenAPI()
                .info(new Info().title("Spring 5 MVC REST Application")
                        .description("Demo of RESTful web services with Spring MVC")
                        .version("0.0.1")
                        .termsOfService("<Terms of Service URL goes here>")
                        .contact(contact)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("More about SpringDoc and OpenAPI 3")
                        .url("https://springdoc.org/"));
    }
}
