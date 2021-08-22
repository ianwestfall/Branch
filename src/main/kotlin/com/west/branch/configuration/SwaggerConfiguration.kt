package com.west.branch.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.util.*


@Configuration
class SwaggerConfiguration {
    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "Branch: Text Adventures",
            "",
            "0.1",
            "",
            Contact("Ian West", "", ""),
            "License of API",
            "API license URL",
            Collections.emptyList()
        )
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()) as List<SecurityScheme>?)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", AUTHORIZATION_HEADER, "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}