package pl.maciejdudek.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    /** Turning off error basic controller in swagger2. */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.regex("^(?!/(error).*$).*$"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(createScheme()))
                .securityContexts(Collections.singletonList(createContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Web Notes",
                "A sample application showing the use of the Spring platform in practice.\n" +
                        "I mainly use Spring Boot, Spring Security, Spring Framework, Spring Data.",
                "v1.0.0",
                "https://google.com",
                new Contact(
                        "Maciej Dudek",
                        "https://github.com/DuDiiC",
                        "Maciej.Dudek.DEV@gmail.com"
                ),
                "MIT License",
                "https://github.com/DuDiiC/web-notes-server/blob/master/LICENSE",
                Collections.emptyList());
    }

    private SecurityScheme createScheme() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private SecurityContext createContext() {
        return SecurityContext.builder()
                .securityReferences(createRef())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> createRef() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
    }
}
