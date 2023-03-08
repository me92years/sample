package org.portfolio.sample;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SampleApplication {

	@PersistenceContext
	private EntityManager entityManager;
	
	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
