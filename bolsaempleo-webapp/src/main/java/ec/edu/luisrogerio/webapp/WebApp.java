package ec.edu.luisrogerio.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@ComponentScan({ "ec.edu.luisrogerio" })
@EnableJpaRepositories(basePackages = {"ec.edu.luisrogerio.persistence"})
@EntityScan(basePackages = {"ec.edu.luisrogerio.domain"})
@PropertySource("classpath:application.properties")
public class WebApp {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(WebApp.class, args);
	}

}
