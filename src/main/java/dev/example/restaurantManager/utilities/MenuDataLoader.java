package dev.example.restaurantManager.utilities;

import dev.example.restaurantManager.model.Menu;
import dev.example.restaurantManager.model.Table;
import dev.example.restaurantManager.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;

public class MenuDataLoader {

        @Autowired
        private MenuRepository menuRepository;

    public void createFakeMenus() {
        // Check if the database is empty
        if (menuRepository.count() != 0) {
            return;
        }

        System.out.println(" 0 menus at the database found");

        Random random = new Random();
        int menusQty = 6;
        // Create and save 100 fake customers
        for (int i = 0; i < menusQty; i++) {
            String menuName = "Menu " + (i < 10 ? "0" : "") + i;
            // random price between 8.00 and 30.00
            double price = (double)random.nextInt(800, 3001) / 100.0;
            boolean active = true;
            // random water is included in menu
            boolean water = random.nextInt(2 ) > 0; // water included
            Menu menu = new Menu(
                    UUID.randomUUID().toString(),
                    menuName,
                    price,
                    menuName + " content",
                    active,
                    water
            );
            menuRepository.save(menu);
        }

        System.out.println(menusQty + " fake menus have been created and saved to the database.");
    }

}
