package shop.tryit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TryitApplication {

	public static void main(String[] args) {
		SpringApplication.run(TryitApplication.class, args);
	}

}
