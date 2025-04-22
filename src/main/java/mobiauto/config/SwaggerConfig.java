package mobiauto.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(info = @Info(
  title = "Mobiauto API", version = "1.0",
  description = "API documentation for Mobiauto",
  contact = @Contact(name = "Júlio César Tinin",
  email = "jctinin@outlook.com")),
  servers = @Server(url = "http://localhost:8080",
  description = "Ambiente de Desenvolvimento"))
public class SwaggerConfig {

  public OpenAPI customOpenAPI(){
    return new OpenAPI()
      .components(new Components()
        .addSecuritySchemes("bearer-jwt",
          new io.swagger.v3.oas.models.security.SecurityScheme()
            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")))
      .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
  }

}
