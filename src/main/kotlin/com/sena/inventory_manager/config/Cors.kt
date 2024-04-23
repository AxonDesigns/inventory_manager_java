package com.sena.inventory_manager.config

/*import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class Cors : WebMvcConfigurer{

    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
        registry.addMapping("/**")
            //.allowedOrigins("http://localhost:5500")
            .allowedOrigins("*")
            .allowedOrigins("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            .allowCredentials(true)
            .maxAge(3600)

        registry.addMapping("/auth/**")
            //.allowedOrigins("http://localhost:5500")
            .allowedOrigins("*")
            .allowedOrigins("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            .allowCredentials(false)
            .maxAge(3600)
    }

}*/