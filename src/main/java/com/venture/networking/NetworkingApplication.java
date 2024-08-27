package com.venture.networking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NetworkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkingApplication.class, args);
	}

}
