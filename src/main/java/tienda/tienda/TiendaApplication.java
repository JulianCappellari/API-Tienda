package tienda.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "tienda.tienda")
public class TiendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaApplication.class, args);
	}

}