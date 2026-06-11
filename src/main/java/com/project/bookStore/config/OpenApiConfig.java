package com.project.bookStore.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenApiConfig {


    // This replaces the path/package filtering of the Docket bean
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("bookstore-public-api")
                .packagesToScan("com.project.bookStore.controller")
                .pathsToMatch("/**")
                .build();
    }

    // This replaces the ApiInfo metadata
    @Bean
    public OpenAPI bookStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Store REST API")
                        .description("All APIs for Book Store Application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("BookStore Admin")
                                .url("https://bookstore-dev.up.railway.app/api/v1/books")
                                .email("bookstore@gmail.com"))
                        .license(new License()
                                .name("Bookstore License")
                                .url("https://license-url.com")));
    }

}
