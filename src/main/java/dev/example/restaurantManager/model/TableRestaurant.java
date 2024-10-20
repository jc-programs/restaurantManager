package dev.example.restaurantManager.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class TableRestaurant {

    @Id
    private String id;
    private String name;
    private String description;
    private int qty;
    private boolean busy;

    @OneToMany(mappedBy = "tableBooking", cascade = CascadeType.ALL)
    private List<Booking> bookings ;

    @OneToMany(mappedBy = "orderTable")
    private List<EatInOrderRestaurant> eatInOrders;


    // new constructor with all parameters
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy,
                           List<Booking> bookings, List<EatInOrderRestaurant> eatInOrders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.busy = busy;
        this.bookings = bookings;
        this.eatInOrders = eatInOrders;
    }

    // new constructor with id and initialization of empty arraylist of relations
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy) {
        this(id, name, description , qty,  busy,new ArrayList<>(),new ArrayList<>());
    }



    // we must create a VERY CONCRETE constructor to RUN the OLD tests
    public TableRestaurant(String name, String description , int qty,  boolean busy) {
        // id = name. Only to run some test
        this(name,name,description,qty,busy,new ArrayList<>(),new ArrayList<>());
    }

    // to RUN the OLD Booking tests
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy, List<Booking> bookings) {
        this(id,name,description,qty,busy,bookings,new ArrayList<>());
    }


    //method to add
    public void addBooking(Booking booking) {
        this.getBookings().add(booking);
        if (booking.getTableBooking() != null) booking.getTableBooking().getBookings().add(booking);
        booking.setTableBooking(this);
    }

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", busy=" + busy +
                '}';
    }


}