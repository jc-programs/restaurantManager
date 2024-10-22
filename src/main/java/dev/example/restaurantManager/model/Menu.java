package dev.example.restaurantManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Data
@NoArgsConstructor
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

    // constructor with all attributes
    public Menu(String id,String name,Double price,String content,boolean active,boolean water){
        this.id=id;
        this.name=name;
        this.price = price;
        this.content=content;
        this.active=active;
        this.water=water;
    }

    // to cover old tests
    public Menu(String name,Double price,String content,boolean active,boolean water){
        // id = name
        this(name,name,price,content,active,water);
    }



}

