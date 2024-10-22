package dev.example.restaurantManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="menu_restaurant")
public class Menu implements Serializable {
    // Add a serialVersionUID
    private static final long serialVersionUID = 2L;

    @Id
    private String id;
    private String name;
    private Double price;
    private String content;
    private boolean active;
    private boolean water;

    // table that will pay in EatInOrder
    @JoinColumn(name="ORDER_FK")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderRestaurant order;


    // constructor with all attributes but order
    public Menu(String id,String name,Double price,String content,boolean active,boolean water){
        this(id,name,price,content,active,water,null);
    }

    // to cover old tests
    public Menu(String name,Double price,String content,boolean active,boolean water){
        // id = name
        this(name,name,price,content,active,water,null);
    }



}

