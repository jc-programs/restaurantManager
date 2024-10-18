package dev.example.restaurantManager;

import dev.example.restaurantManager.model.*;
import dev.example.restaurantManager.repository.CustomerRepository;
import dev.example.restaurantManager.repository.TableRestaurantRepository;
import dev.example.restaurantManager.repository.TakeAwayOrderRepository;
import dev.example.restaurantManager.repository.EatInOrderRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RelationshipsOrderRestaurantTest {

    @Autowired
    private TakeAwayOrderRepository takeAwayOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EatInOrderRestaurantRepository eatInOrderRepository;
    @Autowired
    private TableRestaurantRepository tableRestaurantRepository;


    private List<Menu> menus;
    private List<Customer> customers;
    private List<TableRestaurant> tables;
    private List<ShippingOrderRestaurant> shippingOrders;
    private List<EatInOrderRestaurant> eatInOrders;
    private List<TakeAwayOrder> takeAwayOrders;
    private List<OrderRestaurant> allOrders;

    @BeforeEach
    public void createFakeObjects(){
        System.out.println("createFakeObjects");

        // Create sample menus
        menus = new ArrayList<>();
        menus.add(new Menu("Burger Menu", 10.99, "Burger, fries, and drink", true, true));
        menus.add(new Menu("Pizza Menu", 12.99, "Pizza and salad", true, false));
        menus.add(new Menu("Salad Menu", 8.99, "Mixed salad and dressing", true, true));

        // Create sample customers
        customers = new ArrayList<>();
        customers.add(new Customer("C1", "John", "john@email.com", "123-456-7890", 30, false, false));
        customers.add(new Customer("C2", "Sarah", "sarah@email.com", "234-567-8901", 25, true, false));
        customers.add(new Customer("C3", "Emily", "emily@email.com", "345-678-9012", 35, false, false));
        customers.add(new Customer("C4", "David", "david@email.com", "456-789-0123", 40, true, false));
        customers.add(new Customer("C5", "Anna", "anna@email.com", "567-890-1234", 28, false, false));
        customers.add(new Customer("C6", "Mark", "mark@email.com", "678-901-2345", 45, true, false));
        customers.add(new Customer("C7", "Paul", "paul@email.com", "789-012-3456", 33, false, false));
        customers.add(new Customer("C8", "Linda", "linda@email.com", "890-123-4567", 38, true, false));
        customers.add(new Customer("C9", "George", "george@email.com", "901-234-5678", 50, false, false));

        // Create sample tables
        tables = new ArrayList<>();
        tables.add(new TableRestaurant("T1", "Window Table", 4, false));
        tables.add(new TableRestaurant("T2", "Corner Table", 2, true));

        // Create 3 ShippingOrder objects
        shippingOrders = new ArrayList<>();
        shippingOrders.add(new ShippingOrderRestaurant("SO1", new Date(), "John", 2, 21.98, false, new ArrayList<>(Arrays.asList(menus.get(0), menus.get(0))), "123 Main St", "New York", "Mike"));
        shippingOrders.add(new ShippingOrderRestaurant("SO2", new Date(), "Sarah", 1, 12.99, true, new ArrayList<>(Arrays.asList(menus.get(1))), "456 Elm St", "Los Angeles", "Tom"));
        shippingOrders.add(new ShippingOrderRestaurant("SO3", new Date(), "Emily", 3, 32.97, false, new ArrayList<>(Arrays.asList(menus.get(0), menus.get(1), menus.get(2))), "789 Oak St", "Chicago", "Lisa"));

        // Create 3 EatInOrder objects
        eatInOrders = new ArrayList<>();
        eatInOrders.add(new EatInOrderRestaurant("EO1", new Date(), "David", 4, 43.96, true, new ArrayList<>(Arrays.asList(menus.get(0), menus.get(0), menus.get(1), menus.get(1))), new ArrayList<>(Arrays.asList(tables.get(0)))));
        eatInOrders.add(new EatInOrderRestaurant("EO2", new Date(), "Anna", 2, 21.98, false, new ArrayList<>(Arrays.asList(menus.get(1), menus.get(2))), new ArrayList<>(Arrays.asList(tables.get(1)))));
        eatInOrders.add(new EatInOrderRestaurant("EO3", new Date(), "Mark", 6, 65.94, true, new ArrayList<>(Arrays.asList(menus.get(0), menus.get(0), menus.get(1), menus.get(1), menus.get(2), menus.get(2))), new ArrayList<>(Arrays.asList(tables.get(0), tables.get(1)))));

        // Create 3 TakeAwayOrder objects
        takeAwayOrders = new ArrayList<>();
        takeAwayOrders.add(new TakeAwayOrder("TO1", new Date(), "Alice", 1, 10.99, true, new ArrayList<>(Arrays.asList(menus.get(0))), customers.get(0) ));
        takeAwayOrders.add(new TakeAwayOrder("TO2", new Date(), "Bob", 2, 21.98, false, new ArrayList<>(Arrays.asList(menus.get(1), menus.get(2))), customers.get(2) ));
        takeAwayOrders.add(new TakeAwayOrder("TO3", new Date(), "Charlie", 3, 32.97, true, new ArrayList<>(Arrays.asList(menus.get(0), menus.get(1), menus.get(2))), customers.get(4)));
        takeAwayOrders.add(new TakeAwayOrder("TO4", new Date(), "Charlie", 3, 32.97, true, new ArrayList<>(Arrays.asList(menus.get(0), menus.get(1), menus.get(2), menus.get(2), menus.get(2), menus.get(2))), customers.get(8)));

        allOrders = new ArrayList<>();
        allOrders.addAll(shippingOrders);
        allOrders.addAll(eatInOrders);
        allOrders.addAll(takeAwayOrders);

    }

    @Test
    public void saveRelationshipEatInOrderWithTableTest() {
        System.out.println("saveRelationshipEatInOrderWithTableTest");

        EatInOrderRestaurant eatInOrder1 = eatInOrders.get(0);
        TableRestaurant table = tables.get(0);
        eatInOrder1.setOrderTable(table);
        tableRestaurantRepository.save(table);
        System.out.println(eatInOrder1);
        eatInOrderRepository.save(eatInOrder1);


        String idOrder = eatInOrder1.getId();
        Optional<EatInOrderRestaurant> found = eatInOrderRepository.findById(idOrder);
        // must be in the DB
        assertThat(found).isPresent();
        EatInOrderRestaurant orderDB = found.get();
        // orderTable must be the same we set
        TableRestaurant tableDB = orderDB.getOrderTable();
        assertThat(tableDB.equals(table));
        // order must be the same we selected
        assertThat(orderDB.equals(eatInOrder1));

    }


        @Test
    public void TestSaveTakeAwayOrder() {
        System.out.println("TestSaveTakeAwayOrder");

//            // Create a list of all orders
//            ArrayList<OrderRestaurant> orders = new ArrayList<>();
//            orders.addAll(
//            orders.addAll(Arrays.asList(shippingOrder1, shippingOrder2, shippingOrder3,
//                    eatInOrder1, eatInOrder2, eatInOrder3,
//                    takeAwayOrder1, takeAwayOrder2, takeAwayOrder3, takeAwayOrder4));

            // Print the number of orders
            System.out.println("Orders");
            System.out.println("Total number of orders: " + allOrders.size() + " orders.");
            System.out.println("--------------------");
            // Print all orders
            for (OrderRestaurant order : allOrders) {
                System.out.println("Order ID: " + order.getId());
                System.out.println(order);
                System.out.println("--------------------");
            }

            // Save the customer with JPA Repository to use in order T11
            customerRepository.save(customers.get(0));
            // let's create an order to save and test
            // we do not create orderToSave as OrderRestaurant but
            // as TakeAwayOrder to AVOID casting because
            // in this test is easier to work with
            TakeAwayOrder orderToSave = new TakeAwayOrder(
                    "T11", new Date(), "Alice", 1, 10.99,
                    true, new ArrayList<>(Arrays.asList(menus.get(0))), null );
            // we assign the customer to the order
            //((TakeAwayOrder) orderToSave).setCustomerTakeAway(customers.get(0));
            orderToSave.setCustomerTakeAway(customers.get(0));
            // Save the order with JPA Repository
            //takeAwayOrderRepository.save(orderToSave);
            takeAwayOrderRepository.save(orderToSave);


            // when
            Optional<TakeAwayOrder> found = takeAwayOrderRepository.findById("T11");
            System.out.println("--------------------");
            System.out.println("TakeAwayOrder ID: " + found.get().getId());
            System.out.println(found.get());
            // then
            assertThat(found).isPresent();
            assertThat(found.get().getCustomerTakeAway().getName().equals(customers.get(0).getName()));

            System.out.println("--------------------");
    }

}
