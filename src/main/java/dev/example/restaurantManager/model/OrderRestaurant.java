package dev.example.restaurantManager.model;

import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
// @AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDER_RESTAURANT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OrderRestaurant {

    @Id
    private String id;
    private Date date;
    private String waiter;
    private int peopleQty;
    private double totalPayment;
    private boolean paid;

    @OneToMany(mappedBy = "order")
    private ArrayList<Menu> menus;

    public OrderRestaurant(String id,Date date,String waiter,int peopleQty,double totalPayment,boolean paid, ArrayList<Menu> menus){
        this.id=id;
        this.date=date;
        this.waiter=waiter;
        this.peopleQty=peopleQty;
        this.totalPayment=totalPayment;
        this.paid=paid;
        this.setMenus(menus);
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
        if( menus != null){
            for (Menu menu : menus) {
                menu.setOrder(this);
            }
        }
    }


    @Override
    public String toString() {
        return
                "date: " + date + "\n"  +
                "waiter: " + waiter  + "\n"  +
                "peopleQty: " + peopleQty +"\n"  +
                "totalPayment: " + totalPayment +" euros\n"  +
                "paid: " + paid +"\n"  +
                "Menus quantity: " + menus.size() +"\n"  +
                "menus: " + menus
                ;
    }

}