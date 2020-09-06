package ps.exalt.service.resource.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ps.exalt.service.resource.management.enums.ServerState;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.repository.ServerRepository;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner serverDemo(ServerRepository serverRepository) {
		return (args) -> {

			// create books
			for (int i = 0; i < 1; i++) {
				serverRepository.save(new Server(100L,100L, ServerState.ACTIVE));
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
