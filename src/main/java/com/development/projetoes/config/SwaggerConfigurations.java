package com.development.projetoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfigurations {

    @Bean
    public Docket documentacaoApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.development.projetoes")).paths(PathSelectors.any()).build()
                .apiInfo(infoApi());
    }

    private ApiInfo infoApi() {
        return new ApiInfoBuilder().title("API Projeto Eng. de Software")
                .description("API para o sistema do projeto de Eng. de Software.").version("1.0")
                .contact(new Contact("Yuri Soares", "http://www.ufrb.edu.br", "yurissoares@outlook.com"))
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/license/LICENSE-2.0").build();
    }
}
