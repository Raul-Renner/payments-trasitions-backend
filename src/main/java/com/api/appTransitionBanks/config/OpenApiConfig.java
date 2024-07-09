package com.api.appTransitionBanks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    @Value("${spring.demo-payments.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.description("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("contact@payments-system.com");
        contact.setName("System payments");
        contact.setUrl("https://www.payments-system.com");

        License license = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .contact(contact)
                .license(license)
                .description("This API simples with endpoints to transitions account bank").termsOfService("https://www.payments-system.com")
                .title("Demo Service API")
                .version("1.0");

        return new OpenAPI().info(info).servers(List.of(devServer));
    };

}
