package de.msg.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class FlightManagementApp {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FlightManagementApp.class);
		app.run(args);
	}
}
