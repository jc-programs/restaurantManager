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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TableRestaurant {

    @Id
    private String id;
    private String name;
    private String description;
    private int qty;
    private boolean busy;

    @OneToMany(mappedBy = "tableRestaurantMapped", cascade = CascadeType.ALL)
    private List<Booking> bookings ;

    @OneToMany(mappedBy = "orderTable", cascade = CascadeType.ALL)
    private List<EatInOrderRestaurant> eatInOrders;

    // new constructor with id and initialization of arraylist of relations
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.busy = busy;
        this.bookings = new ArrayList<>();
        this.eatInOrders = new ArrayList<>();
    }



    // we must create a VERY CONCRETE constructor to RUN the OLD tests
    public TableRestaurant(String name, String description , int qty,  boolean busy) {
        // id = name. Only to run some test
        this(name,name,description,qty,busy);
    }

    // to RUN the OLD Booking tests
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy, List<Booking> bookings) {
        // id = name. Only to run some test
        this(id,name,description,qty,busy);
        this.bookings = bookings;
    }


    //method to add
    public void addBooking(Booking booking) {
        this.getBookings().add(booking);
        if (booking.getTableBooking() != null) booking.getTableBooking().getBookings().remove(booking);
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