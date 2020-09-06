package ps.exalt.service.resource.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.repository.ServerRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner serverDemo(ServerRepository serverRepository) {
		return (args) -> {

			// create books
			for (int i = 0; i < 4; i++) {
				serverRepository.save(new Server(100L));
			}
			// fetch all books
			System.out.println("Servers found with findAll():");
			System.out.println("---------------------------");
			for (Server Server : serverRepository.findAll()) {
				System.out.println(Server.toString());
			}
			System.out.println();

			// fetch Server by id
			Server Server = serverRepository.findById(1L).get();
			System.out.println("Server found with findById(1L):");
			System.out.println("-----------------------------");
			System.out.println(Server.toString());
			System.out.println();
		};
	}
}
