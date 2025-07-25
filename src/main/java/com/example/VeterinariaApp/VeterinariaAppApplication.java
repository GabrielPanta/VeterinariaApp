package com.example.VeterinariaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class VeterinariaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinariaAppApplication.class, args);
	}

	@Configuration
	public static class MyConfiguration {

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
				}
			};
		}
		
	}
}
