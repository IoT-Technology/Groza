package com.sanshengshui.server.config;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author james mu
 * @date 18-12-24 上午9:55
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket GrozaApi(){


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Groza")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sanshengshui.server"))
                .paths(PathSelectors.any())
                .build();

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
