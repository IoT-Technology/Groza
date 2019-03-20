package com.sanshengshui.server.config;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Predicate;
import com.sanshengshui.server.common.data.security.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author james mu
 * @date 18-12-24 上午9:55
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket GrozaApi(){

        TypeResolver typeResolver = new TypeResolver();
        final ResolvedType jsonNodeType =
                typeResolver.resolve(
                        JsonNode.class);
        final ResolvedType stringType =
                typeResolver.resolve(
                        String.class);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Groza")
                .apiInfo(apiInfo())
                .alternateTypeRules(
                        new AlternateTypeRule(
                                jsonNodeType,
                                stringType))
                .select()
                .paths(apiPaths())
                .build()
                .securitySchemes(newArrayList(jwtTokenKey()))
                .securityContexts(newArrayList(securityContext()));

    }
    private ApiKey jwtTokenKey() {
        return new ApiKey("X-Authorization", "JWT token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(securityPaths())
                .build();
    }

    private Predicate<String> apiPaths() {
        return regex("/api.*");
    }

    private Predicate<String> securityPaths() {
        return and(
                regex("/api.*"),
                not(regex("/api/noauth.*"))
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope(Authority.SYS_ADMIN.name(), "System administrator");
        authorizationScopes[1] = new AuthorizationScope(Authority.TENANT_ADMIN.name(), "Tenant administrator");
        authorizationScopes[2] = new AuthorizationScope(Authority.CUSTOMER_USER.name(), "Customer");
        return newArrayList(
                new SecurityReference("X-Authorization", authorizationScopes));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Groza REST API")
                .description("For instructions how to authorize requests please visit <a href='writing...'>REST API documentation page</a>.")
                .contact(new Contact("Groza team","https://sanshengshui.github.io/","lovewsic@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/sanshengshui/Groza/blob/master/LICENSE")
                .version("1.0")
                .build();
    }
}
