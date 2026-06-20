package com.tap.seed;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.mongodb.client.MongoCollection;
import com.tap.daoimpl.MenuDAOImpl;
import com.tap.daoimpl.RestaurantDAOImpl;
import com.tap.daoimpl.UserDAOImpl;
import com.tap.model.Menu;
import com.tap.model.Restaurant;
import com.tap.model.User;
import com.tap.utility.DBConnection;

import org.bson.Document;

public class SeedData {

    public static void main(String[] args) {
        System.out.println("Seeding QuickBite database...");
        clearData();
        seedUsers();
        seedRestaurants();
        seedMenus();
        System.out.println("Seed complete!");
    }

    static void clearData() {
        DBConnection.getCollection("users").deleteMany(new Document());
        DBConnection.getCollection("restaurants").deleteMany(new Document());
        DBConnection.getCollection("menus").deleteMany(new Document());
        DBConnection.getCollection("orderItems").deleteMany(new Document());
        DBConnection.getCollection("orders").deleteMany(new Document());
        DBConnection.getCollection("counters").deleteMany(new Document());
        System.out.println("Old data cleared.");
    }

    static void seedUsers() {
        UserDAOImpl udao = new UserDAOImpl();
        String adminPwd = BCrypt.withDefaults().hashToString(12, "admin123".toCharArray());
        String custPwd = BCrypt.withDefaults().hashToString(12, "customer123".toCharArray());

        udao.addUser(new User("Admin", "admin@quickbite.com", adminPwd, "Bangalore, India", "admin"));
        udao.addUser(new User("John Doe", "john@example.com", custPwd, "Koramangala, Bangalore", "customer"));
        System.out.println("Users seeded.");
    }

    static void seedRestaurants() {
        RestaurantDAOImpl rdao = new RestaurantDAOImpl();

        rdao.addRestaurant(new Restaurant("The Spice Garden", "Indian, Mughlai, Tandoori", 25, "Indiranagar, Bangalore", 1, 4.9, 1));
        rdao.addRestaurant(new Restaurant("Bella Italia Ristorante", "Italian, Continental, Mediterranean", 30, "Koramangala, Bangalore", 1, 4.8, 1));
        rdao.addRestaurant(new Restaurant("Dragon Palace", "Chinese, Thai, Pan-Asian", 35, "HSR Layout, Bangalore", 1, 4.7, 1));
        rdao.addRestaurant(new Restaurant("The Burger Barn", "American, Burgers, Fast Food", 20, "Whitefield, Bangalore", 1, 4.6, 1));
        rdao.addRestaurant(new Restaurant("Midnight Munchies", "Burgers, Fries, Shakes, Wraps", 22, "JP Nagar, Bangalore", 1, 4.5, 1));
        rdao.addRestaurant(new Restaurant("Biryani Bros", "Biryani, Kebabs, Raita, Salan", 30, "BTM Layout, Bangalore", 1, 4.7, 1));
        rdao.addRestaurant(new Restaurant("The Dessert Lab", "Cakes, Waffles, Ice Cream, Crepes", 25, "Marathahalli, Bangalore", 1, 4.4, 1));
        rdao.addRestaurant(new Restaurant("Green Bowl Co.", "Healthy, Salads, Vegan", 22, "Yelahanka, Bangalore", 1, 4.5, 1));
        System.out.println("Restaurants seeded.");
    }

    static void seedMenus() {
        MenuDAOImpl mdao = new MenuDAOImpl();

        // The Spice Garden (restaurantId = 1)
        mdao.addMenu(new Menu(1, "Butter Chicken", "Slow-cooked chicken in velvety tomato-cream gravy", 329, 1, "Main Course", null, null, null));
        mdao.addMenu(new Menu(1, "Garlic Naan (4 pcs)", "Freshly baked buttered garlic naan bread", 89, 1, "Breads", null, null, null));
        mdao.addMenu(new Menu(1, "Chicken Biryani", "Aromatic basmati rice with tender chicken and spices", 299, 1, "Biryani", null, null, null));
        mdao.addMenu(new Menu(1, "Dal Makhani", "Slow-cooked black lentils with cream and butter", 199, 1, "Main Course", null, null, null));

        // Bella Italia (restaurantId = 2)
        mdao.addMenu(new Menu(2, "Margherita Pizza (Large)", "Wood-fired crust, San Marzano tomato, buffalo mozzarella", 399, 1, "Pizza", null, null, null));
        mdao.addMenu(new Menu(2, "Pasta Arrabbiata", "Penne in spicy tomato sauce with garlic and herbs", 279, 1, "Pasta", null, null, null));
        mdao.addMenu(new Menu(2, "Tiramisu", "Classic Italian coffee-flavoured layered dessert", 199, 1, "Desserts", null, null, null));
        mdao.addMenu(new Menu(2, "Bruschetta (4 pcs)", "Toasted bread with tomato, basil, and olive oil", 149, 1, "Starters", null, null, null));

        // Dragon Palace (restaurantId = 3)
        mdao.addMenu(new Menu(3, "Steamed Dim Sum Basket (8 pcs)", "Handcrafted dim sums with chilli oil", 279, 1, "Starters", null, null, null));
        mdao.addMenu(new Menu(3, "Kung Pao Chicken", "Spicy stir-fried chicken with peanuts and vegetables", 329, 1, "Main Course", null, null, null));
        mdao.addMenu(new Menu(3, "Fried Rice", "Wok-fried rice with eggs, vegetables, and soy sauce", 199, 1, "Rice", null, null, null));
        mdao.addMenu(new Menu(3, "Spring Rolls (6 pcs)", "Crispy vegetable spring rolls with sweet chilli dip", 149, 1, "Starters", null, null, null));

        // The Burger Barn (restaurantId = 4)
        mdao.addMenu(new Menu(4, "Double Smash Burger", "Double beef patty, cheddar, caramelised onions, secret sauce", 249, 1, "Burgers", null, null, null));
        mdao.addMenu(new Menu(4, "Crispy Chicken Burger", "Buttermilk fried chicken, lettuce, mayo in brioche bun", 219, 1, "Burgers", null, null, null));
        mdao.addMenu(new Menu(4, "Loaded Fries", "Fries with cheese sauce, bacon bits, and spring onions", 149, 1, "Sides", null, null, null));
        mdao.addMenu(new Menu(4, "Chocolate Milkshake", "Thick creamy chocolate shake with whipped cream", 129, 1, "Beverages", null, null, null));

        // Midnight Munchies (restaurantId = 5)
        mdao.addMenu(new Menu(5, "Midnight Burger", "Loaded burger with double patty and special sauce", 199, 1, "Burgers", null, null, null));
        mdao.addMenu(new Menu(5, "Peri Peri Fries", "Spicy tossed fries with peri peri seasoning", 99, 1, "Sides", null, null, null));
        mdao.addMenu(new Menu(5, "Chicken Wrap", "Grilled chicken wrap with veggies and ranch dressing", 179, 1, "Wraps", null, null, null));
        mdao.addMenu(new Menu(5, "Oreo Shake", "Creamy Oreo milkshake", 149, 1, "Beverages", null, null, null));

        // Biryani Bros (restaurantId = 6)
        mdao.addMenu(new Menu(6, "Hyderabadi Chicken Biryani", "Fragrant biryani with tender chicken and saffron", 299, 1, "Biryani", null, null, null));
        mdao.addMenu(new Menu(6, "Mutton Biryani", "Slow-cooked mutton biryani with aromatic spices", 399, 1, "Biryani", null, null, null));
        mdao.addMenu(new Menu(6, "Chicken 65", "Crispy deep-fried chicken with South Indian spices", 199, 1, "Starters", null, null, null));
        mdao.addMenu(new Menu(6, "Double Ka Meetha", "Indian bread pudding with dry fruits", 99, 1, "Desserts", null, null, null));

        // The Dessert Lab (restaurantId = 7)
        mdao.addMenu(new Menu(7, "Belgian Waffle", "Crispy waffle with chocolate sauce and ice cream", 249, 1, "Waffles", null, null, null));
        mdao.addMenu(new Menu(7, "Chocolate Lava Cake", "Warm chocolate cake with molten centre", 199, 1, "Cakes", null, null, null));
        mdao.addMenu(new Menu(7, "Brownie Sundae", "Warm brownie with vanilla ice cream and fudge", 229, 1, "Ice Cream", null, null, null));
        mdao.addMenu(new Menu(7, "Crepe Nutella", "Thin French crepe with Nutella and bananas", 179, 1, "Crepes", null, null, null));

        // Green Bowl Co. (restaurantId = 8)
        mdao.addMenu(new Menu(8, "Caesar Salad", "Crisp romaine, parmesan, croutons, Caesar dressing", 199, 1, "Salads", null, null, null));
        mdao.addMenu(new Menu(8, "Buddha Bowl", "Quinoa, roasted veggies, avocado, tahini dressing", 279, 1, "Bowls", null, null, null));
        mdao.addMenu(new Menu(8, "Green Smoothie", "Spinach, banana, apple, ginger fresh smoothie", 129, 1, "Beverages", null, null, null));
        mdao.addMenu(new Menu(8, "Avocado Toast", "Sourdough with mashed avocado, cherry tomatoes", 179, 1, "Snacks", null, null, null));

        System.out.println("Menus seeded.");
    }
}
