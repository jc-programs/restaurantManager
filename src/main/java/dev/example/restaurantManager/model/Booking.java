package dev.example.restaurantManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private int peopleQty;
    private Date date;
    private boolean confirmed;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Table_Booking_Id_FK")
    private TableRestaurant tableBooking;

    // constructor with all parameters but tableBooking. The last one is set to null (no relation)
    public Booking(String id,String name,String phone,int people, Date date, boolean confirmed){
        this(id,name,phone,people, date, confirmed,null);
    }

    // Custom toString method (optional, as @Data provides a default toString)
    @Override
    public String toString() {
        return
                "id: " + id + "\n"  +
                "name: " + name + "\n"  +
                "phoneNumber: " + phoneNumber + "\n"  +
                "peopleQty: " + peopleQty +"\n"  +
                "date: " + date +"\n"  +
                "table: " + tableBooking
                ;
    }
}