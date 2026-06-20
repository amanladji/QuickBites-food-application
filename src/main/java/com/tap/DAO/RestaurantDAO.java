package com.tap.DAO;

import java.util.List;

import com.tap.model.Restaurant;

public interface RestaurantDAO {
	void addRestaurant(Restaurant r);
	void updateRestaurant(Restaurant r);
	void removeRestaurant(int restaurantId);
	Restaurant getRestaurant(int restaurantId);
	List<Restaurant> getAll();
}
