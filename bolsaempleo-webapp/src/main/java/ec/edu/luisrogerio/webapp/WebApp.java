package ec.edu.luisrogerio.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@ComponentScan({ "ec.edu.luisrogerio" })
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class WebApp extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(WebApp.class, args);
	}

}
