package dev.example.restaurantManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import jakarta.persistence.FetchType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EatInOrderRestaurant extends OrderRestaurant {

    // TODO
    // tables occupied by
    // private ArrayList<TableRestaurant> occupiedTables = new ArrayList<>();

    // table that will pay in EatInOrder
    @JoinColumn(name="TABLE_EAT_IN_ORDER_FK")
    @ManyToOne(fetch = FetchType.LAZY)
    private TableRestaurant tableEatInOrder;

    public void setTableEatInOrder(TableRestaurant tableEatInOrder) {
        this.tableEatInOrder = tableEatInOrder;
        if( this.tableEatInOrder != null){
            this.tableEatInOrder.addEatInOrder(this);
        }
    }

//    public void setMenus(ArrayList<Menu> menus) {
//        this.setMenus();
//        this.tableEatInOrder = tableEatInOrder;
//        if( this.tableEatInOrder != null){
//            this.tableEatInOrder.addEatInOrder(this);
//        }
//    }

    // constructor with all attributes
    public EatInOrderRestaurant(String id, Date date, String waiter, int peopleQty,
                                double totalPayment, boolean paid, ArrayList<Menu> menus,
                                ArrayList<TableRestaurant> occupiedTables,
                                TableRestaurant tableEatInOrder) {
        super(id, date, waiter, peopleQty, totalPayment, paid, menus);
        this.setTableEatInOrder( tableEatInOrder);
        // this.occupiedTables = occupiedTables;
    }

    // constructor without orderTable and occupiedTables
    public EatInOrderRestaurant(String id, Date date, String waiter, int peopleQty,
                                double totalPayment, boolean paid, ArrayList<Menu> menus) {
        this(id, date, waiter, peopleQty, totalPayment, paid, menus, null, null);
    }

    // constructor without orderTable
    // to keep compatibility with older test
    public EatInOrderRestaurant(String id, Date date, String waiter, int peopleQty,
                                double totalPayment, boolean paid, ArrayList<Menu> menus,
                                ArrayList<TableRestaurant> occupiedTables) {
        this(id, date, waiter, peopleQty, totalPayment, paid, menus, occupiedTables, null);
    }


    @Override
    public String toString() {
        String output = super.toString() + "\n" +
                  "Type: Eat In\n" +
                  "OrderTable: " + (tableEatInOrder == null ? "null" : tableEatInOrder.getName()) + "\n";
//        output += "OccupiedTables: " + (occupiedTables == null ?
//                    "null" :
//                occupiedTables.stream().map(TableRestaurant::getName).collect(Collectors.joining(", "))
//                );
//        output += "\n";
        return output;
    }
}
