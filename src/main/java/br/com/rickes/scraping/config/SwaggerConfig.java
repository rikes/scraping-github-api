package br.com.rickes.scraping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.rickes.scraping.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(metaInfo());
    }
    

	private ApiInfo metaInfo() {
		return new ApiInfoBuilder().title("Scraping GitHub").
				description("API Scraping GitHub").version("1.0.0")
				.contact(new Contact("Henrique Santana", "https://github.com/rikes/", "henrique-contato@outlook.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();
		
	}
}