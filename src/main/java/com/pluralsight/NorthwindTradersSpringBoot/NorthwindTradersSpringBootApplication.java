package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication {

	public static void main(String[] args) {

        // Get the username and password from the ENV
        String username = System.getenv("username");
        String password = System.getenv("password");

        // Make sure the username and password were provided for the db.
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Env: File not configured correctly");
            System.exit(1);
        }

        // Set system properties with the username and password so Spring can read them later.
        System.setProperty("dbUsername", System.getenv("username"));
        System.setProperty("dbPassword", System.getenv("password"));

        SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);
	}

}
