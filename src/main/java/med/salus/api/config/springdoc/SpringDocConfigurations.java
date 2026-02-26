package med.salus.api.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Environment"),
                        new Server().url("https://api.salus.med").description("Production Environment")))
                .components(new Components()
                        .addSecuritySchemes(
                                "bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Salus.med API")
                        .version("v1.0.0")
                        .description("Medical appointment management system API")
                        .termsOfService("https://salus.med/terms-of-service")
                        .contact(new Contact()
                                .name("Salus.med Backend Team")
                                .email("backend@salus.med")
                                .url("https://salus.med"))
                        .license(new License().name("Proprietary License").url("https://salus.med/legal/license")));
    }
}
