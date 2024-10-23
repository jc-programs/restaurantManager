package dev.example.restaurantManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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

    @JsonIgnore
    @OneToMany(mappedBy = "bookingTable")
    private ArrayList<Booking> bookings ;

    @JsonIgnore
    @OneToMany(mappedBy = "tableEatInOrder")
    private ArrayList<EatInOrderRestaurant> eatInOrders;

    // new constructor with id and initialization of arraylist of relations
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.busy = busy;
        this.bookings = null;
        this.eatInOrders = null;
    }



    // we must create a VERY CONCRETE constructor to RUN the OLD tests
    public TableRestaurant(String name, String description , int qty,  boolean busy) {
        // id = name. Only to run some test
        this(name,name,description,qty,busy);
    }

    // to RUN the OLD Booking tests
    public TableRestaurant(String id, String name, String description , int qty,  boolean busy, ArrayList<Booking> bookings) {
        // id = name. Only to run some test
        this(id,name,description,qty,busy);
        this.bookings = bookings;
    }


    //method to add
    public void addBooking(Booking booking) {
        if(booking== null){
            return;
        }
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        if (booking.getBookingTable() != null) booking.getBookingTable().getBookings().remove(booking);
        booking.setBookingTable(this);


//        public void addBooking(Booking booking) {
//            this.getBookings().add(booking);
//            if (booking.getTableRestaurantMapped() != null) booking.getTableRestaurantMapped().getBookings().remove(booking);
//            booking.setTableRestaurantMapped(this);
//        }
    }

    //method to add
    public void addEatInOrder(EatInOrderRestaurant eatInOrder) {
        if(eatInOrders == null){
            eatInOrders = new ArrayList<>();
        }
        if(!eatInOrders.contains(eatInOrder)){
            eatInOrders.add(eatInOrder);
            // eatInOrder.setTableEatInOrder(this);
        }
        // if (eatInOrder.getTableEatInOrder() != null) eatInOrder.getTableEatInOrder().getEatInOrders().remove(eatInOrder);
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