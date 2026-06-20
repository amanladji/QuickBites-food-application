package com.tap.model;

import java.sql.Timestamp;

public class Menu {
	private int menuId;
	private int restaurantId;
	private String itemName;
	private String description;
	private double price;
	private int isAvailable;
	private String category;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp deleteAt;
	
	
	public Menu(int restaurantId, String itemName, String description, double price, int isAvailable, String category,
			Timestamp createdAt, Timestamp updatedAt, Timestamp deleteAt) {
		super();
		this.restaurantId = restaurantId;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.isAvailable = isAvailable;
		this.category = category;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleteAt = deleteAt;
	}


	public Menu(int menuId, int restaurantId, String itemName, String description, double price, int isAvailable,
			String category, Timestamp createdAt, Timestamp updatedAt, Timestamp deleteAt) {
		super();
		this.menuId = menuId;
		this.restaurantId = restaurantId;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.isAvailable = isAvailable;
		this.category = category;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleteAt = deleteAt;
	}


	public int getMenuId() {
		return menuId;
	}


	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}


	public int getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getIsAvailable() {
		return isAvailable;
	}


	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public Timestamp getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}


	public Timestamp getDeleteAt() {
		return deleteAt;
	}


	public void setDeleteAt(Timestamp deleteAt) {
		this.deleteAt = deleteAt;
	}


	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", restaurantId=" + restaurantId + ", itemName=" + itemName + ", description="
				+ description + ", price=" + price + ", isAvailable=" + isAvailable + ", category=" + category
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deleteAt=" + deleteAt + "]";
	}
	
	
	
	
}
