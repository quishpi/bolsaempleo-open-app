package ec.edu.luisrogerio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "ec.edu.bolsaempleo" })
@EnableJpaRepositories(basePackages = {"ec.edu.bolsaempleo.persistence"})
@EntityScan(basePackages = {"ec.edu.bolsaempleo.domain"})
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BolsaempleoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BolsaempleoApplication.class, args);
	}

}
