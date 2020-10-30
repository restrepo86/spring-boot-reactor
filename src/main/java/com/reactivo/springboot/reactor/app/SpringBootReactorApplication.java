package com.reactivo.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Flux<String> nombres = Flux.just("Juan", "Sonia", "", "Pablo")
				.doOnNext(e -> {
					if (e.isEmpty()) {
						throw new RuntimeException("Nombres no pueden estar vacíos");
					} else {
						System.out.println();
					}
				});

		nombres.subscribe(
				LOG::info,
				error -> LOG.error(error.getMessage())
		);
	}

}
