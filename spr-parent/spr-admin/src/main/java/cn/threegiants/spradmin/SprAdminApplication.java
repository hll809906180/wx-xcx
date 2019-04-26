package cn.threegiants.spradmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@EnableAdminServer
@SpringBootApplication
public class SprAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprAdminApplication.class, args);
	}

}
