package dev.example.restaurantManager;

import dev.example.restaurantManager.model.*;
import dev.example.restaurantManager.repository.EatInOrderRepository;
import dev.example.restaurantManager.repository.TableRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EatInOrderTest {

    @Autowired
    private TableRestaurantRepository tableRestaurantRepository;
    @Autowired
    private EatInOrderRepository eatInOrderRepository;

    @Test
    public void createEatInOrderTest() {
        // Create sample menus
        Menu menu1 = new Menu("Burger Menu", 10.99, "Burger, fries, and drink", true, true);
        Menu menu2 = new Menu("Pizza Menu", 12.99, "Pizza and salad", true, false);
        Menu menu3 = new Menu("Salad Menu", 8.99, "Mixed salad and dressing", true, true);

        // Create sample tables
        TableRestaurant table1 = new TableRestaurant("T1", "Window Table", 4, false);
        TableRestaurant table2 = new TableRestaurant("T2", "Corner Table", 2, true);

        // Create 3 ShippingOrder objects
        OrderRestaurant eatInOrder1 = new EatInOrderRestaurant("EO1", new Date(), "David", 4, 43.96, true, new ArrayList<>(Arrays.asList(menu1, menu1, menu2, menu2)), table1);
        OrderRestaurant eatInOrder2 = new EatInOrderRestaurant("EO2", new Date(), "Anna", 2, 21.98, false, new ArrayList<>(Arrays.asList(menu2, menu3)), table2);
        OrderRestaurant eatInOrder3 = new EatInOrderRestaurant("EO3", new Date(), "Mark", 6, 65.94, true, new ArrayList<>(Arrays.asList(menu1, menu1, menu2, menu2, menu3, menu3)), table1);

        // Create a list of all orders
        ArrayList<OrderRestaurant> orders = new ArrayList<>();
        orders.addAll(Arrays.asList(eatInOrder1, eatInOrder2, eatInOrder3));

        // Print the number of orders
        System.out.println("Orders");
        System.out.println("Total number of orders: " + orders.size() + " orders.");
        System.out.println("--------------------");
        // Print all orders
        for (OrderRestaurant order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println(order);
            System.out.println("--------------------");
        }


        // let's create an order to save and test
        // we do not create orderToSave as OrderRestaurant but
        // as TakeAwayOrder to AVOID casting because
        // in this test is easier to work with
        EatInOrderRestaurant eatInOrderToSave = new EatInOrderRestaurant("EO1", new Date(), "David", 4, 43.96, true, new ArrayList<>(Arrays.asList(menu1, menu1, menu2, menu2)),table1);
        eatInOrderToSave.setTableEatInOrder(table1);
        // Save the table
        tableRestaurantRepository.save(table1);
        // Save the order with JPA Repository
        eatInOrderRepository.save(eatInOrderToSave);

        // when
        Optional<EatInOrderRestaurant> found = eatInOrderRepository.findById("EO1");
        System.out.println("--------------------");
        System.out.println("EatInOrder ID: " + found.get().getId());
        System.out.println(found.get());
        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTableEatInOrder().equals(table1));

        System.out.println("--------------------");
    }
}


