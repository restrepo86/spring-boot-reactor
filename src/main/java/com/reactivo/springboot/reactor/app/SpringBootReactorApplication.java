package com.reactivo.springboot.reactor.app;

import com.reactivo.springboot.reactor.app.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		List<String> usuarios = new ArrayList<>();
		usuarios.add("Juan Restrepo");
		usuarios.add("Sonia Bedoya");
		usuarios.add("Sofi Mesa");
		usuarios.add("Pablo Moreno");
		usuarios.add("Elkin");
		usuarios.add("Maria Moreno");

		Flux<String> nombres = Flux.fromIterable(usuarios)
				.map(nombre -> new Usuario(
						nombre.split(" ")[0].toUpperCase(),
						nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getNombre().equalsIgnoreCase("Sonia"))
				.doOnNext(usuario -> {
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden estar vacíos");
					} else {
						System.out.println(usuario);
					}
				})
				.map(usuario -> usuario.getNombre().toLowerCase().concat(" ").concat(usuario.getApellidos().toLowerCase()));

		nombres.subscribe(
				LOG::info,
				error -> LOG.error(error.getMessage()),
				() -> LOG.info("Ha finalizado la ejecución del FLUX")
		);
	}

}
