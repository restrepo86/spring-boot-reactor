package com.reactivo.springboot.reactor.app;

import com.reactivo.springboot.reactor.app.models.Usuario;
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
		Flux<String> nombres = Flux.just("Juan", "Sonia", "Sofi", "Pablo")
				.map(nombre -> new Usuario(nombre.toUpperCase(), null))
				.doOnNext(usuario -> {
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden estar vacíos");
					} else {
						System.out.println(usuario);
					}
				})
				.map(usuario -> usuario.getNombre().toLowerCase());

		nombres.subscribe(
				LOG::info,
				error -> LOG.error(error.getMessage()),
				() -> LOG.info("Ha finalizado la ejecución del FLUX")
		);
	}

}
