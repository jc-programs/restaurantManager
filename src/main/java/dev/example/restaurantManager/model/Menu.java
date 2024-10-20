package dev.example.restaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    // Add a serialVersionUID
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private Double price;
    private String content;
    private boolean active;
    private boolean water;

    public Menu(String name,Double price,String content,boolean active,boolean water){
        // id = name, to run some old tests
        this(name,name,price,content,active,water);
    }

}

