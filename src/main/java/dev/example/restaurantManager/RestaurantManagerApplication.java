package dev.example.restaurantManager;

import dev.example.restaurantManager.utilities.FakeDataLoader;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@PropertySource("classpath:application.properties")
@SpringBootApplication
public class RestaurantManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagerApplication.class, args);
	}


	@Bean
	public ApplicationRunner dataLoader(FakeDataLoader fakeDataLoader) {
		return args -> fakeDataLoader.createData();
	}

/*

	@Bean
	public ApplicationRunner dataLoader(CustomerDataLoader customerDataLoader) {
		return args -> customerDataLoader.createFakeCustomers();
	}
*/

}