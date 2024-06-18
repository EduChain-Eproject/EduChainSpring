package aptech.project.educhain.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("Educhain - API -Documentation")
                                .version("v1.0.0")
                                .description("Educhain is a coursera clone project")
                                .license(new License().name("API License").url("http://google.com.vn")))
                                .servers(List.of(new Server().url("http://localhost:8080").description("Server test")));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("api-service")
                .packagesToScan("aptech.project.educhain.controllers")
                .build();
    }

}
