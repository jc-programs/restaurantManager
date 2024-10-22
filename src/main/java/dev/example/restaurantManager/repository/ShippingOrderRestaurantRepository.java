package dev.example.restaurantManager.repository;

import dev.example.restaurantManager.model.ShippingOrderRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOrderRestaurantRepository extends JpaRepository<ShippingOrderRestaurant,String> {}
