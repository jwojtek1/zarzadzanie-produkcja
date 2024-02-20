package com.faktoria.zarzadzanieprodukcja;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApp {

	public static ConfigurableApplicationContext run(String[] args) {
		// Ta metoda teraz zwraca ConfigurableApplicationContext
		return new SpringApplicationBuilder(SpringApp.class).run(args);
	}
}
