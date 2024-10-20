package dev.example.restaurantManager;

import dev.example.restaurantManager.model.Menu;
import dev.example.restaurantManager.repository.MenuRepository;
import dev.example.restaurantManager.utilities.FakeDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//@PropertySource("classpath:application.properties")
@SpringBootApplication
public class RestaurantManagerApplication {

	@Autowired
	private MenuRepository menuRepository;

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			Menu menu = new Menu();
			menu.setId((UUID.randomUUID().toString()));
			menu.setName("Test Menu");
			menu.setPrice(10.0);
			menu.setContent("Test Content");
			menu.setActive(true);
			menu.setWater(false);
			menuRepository.save(menu);
		};
	}
//	@Bean
//	public ApplicationRunner dataLoader(FakeDataLoader fakeDataLoader) {
//		return args -> fakeDataLoader.createData();
//	}

/*

	@Bean
	public ApplicationRunner dataLoader(CustomerDataLoader customerDataLoader) {
		return args -> customerDataLoader.createFakeCustomers();
	}
*/

}

