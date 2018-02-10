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
			// PERSON
			// Inserción en la base de datos
			repository.save(new Agent("Paco", "123456", "paco@hotmail.com", "43.5479621,-5.9304147", "12345678P", "Person"));

			// Inserción en la base de datos
			repository.save(new Agent("Pepe", "123456", "pepe@hotmail.com", "43.5479621,-5.9304147", "12345678A", "Person"));

			// ENTITY
			// Inserción en la base de datos
			repository.save(new Agent("Valgrande Pajares", "123456", "pajares@hotmail.com", "43.5479621,-5.9304147",
					"entidad1", "Entity"));

			// Inserción en la base de datos
			repository.save(new Agent("Estación Fuentes De Invierno", "123456", "fuentes@hotmail.com",
					"43.5479621,-5.9304147,", "entidad2", "Entity"));

			// SENSOR
			// Inserción en la base de datos
			repository.save(new Agent("SensorTemperatura", "123456", "sensorT@hotmail.com", "43.5479621,-5.9304147",
					"sensor1", "Sensor"));

			// Inserción en la base de datos
			repository.save(
					new Agent("SensorHumedad", "123456", "sensorH@hotmail.com", "43.5479621,-5.9304147", "sensor2", "Sensor"));
		};
	}
}