package asw;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import asw.dbManagement.model.Agent;
import asw.dbManagement.repository.AgentRepository;


import java.text.ParseException;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initDB(AgentRepository repository) throws ParseException {

		return (args) -> {
			repository.save(new Agent("Paco Gómez", "123456", "paco@hotmail.com", "12345678A", "Calle Uría", 2));
			repository.save(new Agent("Pepe Fernández", "123456", "pepe@gmail.com", "87654321B", "Calle Principal", 1));
			repository.save(new Agent("Carmen López", "123456", "carmen@yahoo.com", "11223344C", "Calle Calvo Sotelo", 1));
			repository.save(new Agent("Isabel Rodríguez", "123456", "isabel@gmail.com", "22334455D","Avenida Galicia", 1));
			repository.save(new Agent("María Sánchez", "123456", "maria@gmail.com", "33445566E","Avenida Santander",  1));
			repository.save(new Agent("Jose Ballesteros", "123456", "jose@gmail.com", "44556677F", "Calle Asturias", 1));
		};
	}
}