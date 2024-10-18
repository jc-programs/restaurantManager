package dev.example.restaurantManager.utilities;

import com.github.javafaker.Faker;
import dev.example.restaurantManager.model.Customer;
import dev.example.restaurantManager.model.EatInOrderRestaurant;
import dev.example.restaurantManager.model.Menu;
import dev.example.restaurantManager.model.TableRestaurant;
import dev.example.restaurantManager.repository.CustomerRepository;
import dev.example.restaurantManager.repository.EatInOrderRestaurantRepository;
import dev.example.restaurantManager.repository.TableRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FakeDataLoader {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TableRestaurantRepository tableRepository;
    @Autowired
    private EatInOrderRestaurantRepository eatInOrderRepository;



    private Faker faker;

    private ArrayList<Customer> customers;
    private ArrayList<Menu> menus;
    private ArrayList<TableRestaurant> tables;
    private ArrayList<EatInOrderRestaurant> eatInOrders;


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
    }

    private void saveDataWithoutRelations(){
        for(Customer element:customers){
            customerRepository.save(element);
        }
        for(TableRestaurant element:tables){
            tableRepository.save(element);
        }
        for(EatInOrderRestaurant element:eatInOrders){
            eatInOrderRepository.save(element);
        }
    }


    private Object getOneRandom(List<Object> list){
        assert(list.size() > 0);
        return list.get(faker.number().numberBetween(0,list.size()));
    }

    private ArrayList<Object> getManyRandom(List<Object> list, int elements){
        assert(elements >0 && elements <= list.size());
        ArrayList<Object> listSelecteds = new ArrayList<>();
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

    private ArrayList<Customer> createCustomers(int number){
        ArrayList<Customer> list = new ArrayList<>();
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

    private ArrayList<TableRestaurant> createTables(int number){
        ArrayList<TableRestaurant> list = new ArrayList<>();
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

    private ArrayList<Menu> createMenus(int number){
        ArrayList<Menu> list = new ArrayList<>();
        for(int i=0;i<number;i++) {
            String menuName = faker.food().dish();
            // random price between 7.95 and 30.00
            double menuPrice = faker.number().randomDouble(1, 8, 30)
                    - (faker.random().nextInt(6) < 4 ? 0 : 0.05);
            list.add(new Menu(
                    // UUID.randomUUID().toString(),
                    menuName,
                    menuPrice,
                    menuName + " content",
                    faker.random().nextInt(4) > 0, // 75% of the menus are active
                    faker.random().nextInt(4) > 0 // 75% of the menus have water included
            ));
        }
        return list;
    }

    private ArrayList<EatInOrderRestaurant> createEatInOrders(int number){
        ArrayList<EatInOrderRestaurant> list = new ArrayList<>();
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
