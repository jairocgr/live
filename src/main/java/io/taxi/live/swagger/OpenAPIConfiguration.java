package io.taxi.live.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI gen() {
        var contact = new Contact()
            .email("jairocgr@gmail.com")
            .name("Jairo Rodrigues Filho")
            .url("http://github.com/jairocgr");

        var info = new Info()
            .title("Taxi Live Booking")
            .version("HEAD")
            .contact(contact)
            .description("Taxi live booking and fleet management ap");

        return new OpenAPI()
            .info(info);
    }
}
