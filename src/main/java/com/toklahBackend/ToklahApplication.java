package com.toklahBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySources({ @PropertySource(value = "classpath:application.properties")/*,
		@PropertySource(value = "classpath:internal.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "file:${CONF_DIR}/external.properties", ignoreResourceNotFound = true)*/ })
public class ToklahApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ToklahApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ToklahApplication.class, args);
	}

}
