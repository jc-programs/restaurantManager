package dev.example.restaurantManager.utilities;

import com.github.javafaker.Faker;
import dev.example.restaurantManager.model.*;
import dev.example.restaurantManager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FakeDataLoader {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private TableRestaurantRepository tableRepository;
    @Autowired
    private EatInOrderRestaurantRepository eatInOrderRepository;
    @Autowired
    private BookingRepository bookingRepository;



    private Faker faker;

    private List<Customer> customers;
    private List<Menu> menus;
    private List<TableRestaurant> tables;
    private List<EatInOrderRestaurant> eatInOrders;
    private List<Booking> bookings;

    public void createData(){
        createFakeData();
        saveDataWithoutRelations();
    }

    private void createFakeData(){
        // always the same results
        faker = new Faker(new Locale("en-US"), new Random(42));

        customers = createCustomers(50);
        menus = createMenus(20);
        tables = createTables(10);
        eatInOrders = createEatInOrders(100);
        bookings= createBookings(100);
    }

    private void saveDataWithoutRelations(){
        for(Customer element:customers){
            customerRepository.save(element);
        }
        for(Menu element:menus){
            menuRepository.save(element);
        }
        for(TableRestaurant element:tables){
            tableRepository.save(element);
        }
        for(EatInOrderRestaurant element:eatInOrders){
            eatInOrderRepository.save(element);
        }
        for(Booking element:bookings){
            bookingRepository.save(element);
        }
    }


    private Object getOneRandom(List<Object> list){
        assert(list.size() > 0);
        return list.get(faker.number().numberBetween(0,list.size()));
    }

    private List<Object> getManyRandom(List<Object> list, int elements){
        assert(elements >0 && elements <= list.size());
        List<Object> listSelecteds = new ArrayList<>();
        while(elements> 0){
            Object selected = list.get(faker.number().numberBetween(0,list.size()));
            if(listSelecteds.contains(selected)){
                continue;
            }
            listSelecteds.add(selected);
            elements--;
        }
        return listSelecteds;
    }

    private List<Customer> createCustomers(int number){
        List<Customer> list = new ArrayList<>();
        for(int i=0;i<number;i++){
            list.add(new Customer(
                            UUID.randomUUID().toString(),
                            faker.name().fullName(),
                            faker.internet().emailAddress(),
                            faker.phoneNumber().cellPhone(),
                            faker.random().nextInt(18, 130),
                            faker.random().nextBoolean(),
                            faker.random().nextBoolean()
                    )
            );
        }
        return list;
    }

    private List<TableRestaurant> createTables(int number){
        List<TableRestaurant> list = new ArrayList<>();
        for(int i=0;i<number;i++) {
            String tableName = "Table " + String.format("%02d", i+1);
            list.add(new TableRestaurant(
                    UUID.randomUUID().toString(),
                    tableName,
                    tableName + " description",
                    faker.number().numberBetween(2, 6),
                    false
            ));
        }
        return list;
    }

    private List<Menu> createMenus(int number){
        List<Menu> list = new ArrayList<>();
        for(int i=0;i<number;i++) {
            String menuName = faker.food().dish();
            // random price between 7.95 and 30.00
            double menuPrice = faker.number().randomDouble(1, 8, 30)
                    - (faker.random().nextInt(6) < 4 ? 0 : 0.05);
            list.add(new Menu(
                    UUID.randomUUID().toString(),
                    menuName,
                    menuPrice,
                    menuName + " content",
                    faker.random().nextInt(4) > 0, // 75% of the menus are active
                    faker.random().nextInt(4) > 0 // 75% of the menus have water included
            ));
        }
        return list;
    }

    private List<Booking> createBookings(int number){
        List<Booking> list = new ArrayList<>();
        for(int i=0;i<number;i++) {

            // List<Menu> menusSelected = (List<Menu>)(Object) getManyRandom((List<Object>)(Object) menus,faker.number().numberBetween(1,4));
            list.add(new Booking(
                    UUID.randomUUID().toString(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber(),
                    faker.number().numberBetween(1, 6),
                    faker.date().between(new Date(2024, 1, 1), new Date(2024, 12, 31)),
                    faker.bool().bool()
            ));
        }
        return list;
    }


    private List<EatInOrderRestaurant> createEatInOrders(int number){
        List<EatInOrderRestaurant> list = new ArrayList<>();
        for(int i=0;i<number;i++) {
            ArrayList<Menu> menusSelected = (ArrayList<Menu>)(Object) getManyRandom((List<Object>)(Object) menus,faker.number().numberBetween(1,4));
            list.add(new EatInOrderRestaurant(
                    UUID.randomUUID().toString(),
                    faker.date().between(new Date(2024, 1, 1), new Date(2024, 12, 31)),
                    faker.name().firstName(),
                    faker.number().numberBetween(1, 6),
                    0.0,
                    false,
                    menusSelected
                ));
        }
        return list;
    }
}
