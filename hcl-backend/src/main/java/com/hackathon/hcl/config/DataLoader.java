package com.hackathon.hcl.config;

import com.hackathon.hcl.model.*;
import com.hackathon.hcl.repository.MenuItemRepository;
import com.hackathon.hcl.repository.RestaurantRepository;
import com.hackathon.hcl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(user("Spring Beans Admin", "admin@springbeans.com", "Admin@123", Role.ADMIN, "9000000001"));
            userRepository.save(user("Spring Beans User", "user@springbeans.com", "User@123", Role.USER, "9000000002"));
        }
        if (restaurantRepository.count() > 0) {
            return;
        }

        List<RestaurantSeed> restaurants = List.of(
                r("Spice Garden", "Indian", "Hyderabad", 4.5, "photo-1585937421612-70a008356fbe"),
                r("The Pizza Hub", "Italian", "Mumbai", 4.3, "photo-1565299624946-b28f40a0ae38"),
                r("Dragon Palace", "Chinese", "Delhi", 4.1, "photo-1525755662778-989d0524087e"),
                r("Burger Barn", "American", "Bangalore", 4.4, "photo-1568901346375-23c9450c58cd"),
                r("Sushi World", "Japanese", "Chennai", 4.6, "photo-1579871494447-9811cf80d66c"),
                r("Taco Fiesta", "Mexican", "Pune", 4.2, "photo-1565299585323-38d6b0865b47"),
                r("Mediterranean Breeze", "Mediterranean", "Hyderabad", 4.7, "photo-1540189549336-e6e99c3679fe"),
                r("Wok & Roll", "Pan-Asian", "Kolkata", 4.0, "photo-1512058564366-18510be2db19"),
                r("The Grill House", "BBQ", "Mumbai", 4.5, "photo-1555939594-58d7cb561ad1"),
                r("Green Bowl", "Healthy/Vegan", "Delhi", 4.3, "photo-1512621776951-a57141f2eefd"),
                r("Biryani Brothers", "Indian", "Hyderabad", 4.8, "photo-1563379091339-03246963d51a"),
                r("Pasta Paradise", "Italian", "Bangalore", 4.4, "photo-1551183053-bf91a1d81141"),
                r("Seoul Kitchen", "Korean", "Chennai", 4.2, "photo-1498654896293-37aacf113fd9"),
                r("The Sandwich Co.", "Cafe", "Pune", 4.1, "photo-1528735602780-2552fd46c7af"),
                r("Coastal Catch", "Seafood", "Mumbai", 4.6, "photo-1565680018434-b513d5e5fd47"),
                r("Punjab Da Dhaba", "North Indian", "Delhi", 4.7, "photo-1603894584373-5ac82b2ae398"),
                r("Thai Orchid", "Thai", "Hyderabad", 4.3, "photo-1559314809-0d155014e29e"),
                r("The Waffle House", "Desserts", "Bangalore", 4.5, "photo-1562376552-0d160a2f238d"),
                r("Shawarma Station", "Middle Eastern", "Chennai", 4.0, "photo-1529006557810-274b9b2fc783"),
                r("Farm Fresh", "Organic/Salads", "Pune", 4.4, "photo-1540420773420-3366772f4999")
        );

        for (int i = 0; i < restaurants.size(); i++) {
            RestaurantSeed seed = restaurants.get(i);
            Restaurant restaurant = new Restaurant();
            restaurant.setName(seed.name());
            restaurant.setCuisine(seed.cuisine());
            restaurant.setLocation(seed.location());
            restaurant.setImageUrl(img(seed.photoId()));
            restaurant.setRating(seed.rating());
            restaurant.setDeliveryTime((25 + (i % 6) * 5) + "-" + (35 + (i % 6) * 5) + " min");
            restaurant.setMinOrder(149 + (i % 5) * 50);
            restaurant.setIsOpen(true);
            restaurantRepository.save(restaurant);
            seedMenu(restaurant, i);
        }
    }

    private void seedMenu(Restaurant restaurant, int index) {
        String[][] pool = {
                {"Paneer Tikka", "Smoky paneer cubes with peppers and mint chutney", "249", "Starter", "true", "photo-1567188040759-fb8a883dc6d8"},
                {"Chicken Biryani", "Aromatic basmati rice layered with tender spiced chicken", "329", "Main Course", "false", "photo-1563379091339-03246963d51a"},
                {"Margherita Pizza", "Classic tomato, basil, and mozzarella pizza", "299", "Main Course", "true", "photo-1574071318508-1cdbab80d002"},
                {"Cheese Burger", "Juicy patty, cheddar, lettuce, and house sauce", "279", "Snack", "false", "photo-1568901346375-23c9450c58cd"},
                {"Salmon Sushi", "Fresh salmon rolls with wasabi and soy", "449", "Main Course", "false", "photo-1579871494447-9811cf80d66c"},
                {"Veg Tacos", "Crisp tacos with beans, salsa, crema, and herbs", "229", "Snack", "true", "photo-1565299585323-38d6b0865b47"},
                {"Pasta Alfredo", "Creamy parmesan pasta with herbs", "319", "Main Course", "true", "photo-1551183053-bf91a1d81141"},
                {"Chicken Shawarma", "Warm pita wrap with garlic sauce and pickles", "199", "Snack", "false", "photo-1529006557810-274b9b2fc783"},
                {"Greek Salad", "Crunchy greens, olives, feta, and lemon dressing", "239", "Starter", "true", "photo-1540420773420-3366772f4999"},
                {"Grilled Chicken", "Char-grilled chicken with smoky barbecue glaze", "379", "Main Course", "false", "photo-1598515214211-89d3c73ae83b"},
                {"Kimchi Fried Rice", "Spicy rice tossed with kimchi and scallions", "289", "Main Course", "true", "photo-1512058564366-18510be2db19"},
                {"Belgian Waffles", "Crisp waffles with maple and berries", "219", "Dessert", "true", "photo-1562376552-0d160a2f238d"}
        };
        for (int i = 0; i < 6; i++) {
            String[] row = pool[(index + i) % pool.length];
            item(restaurant, row[0], row[1], row[2], row[3], Boolean.parseBoolean(row[4]), row[5], 4.0 + ((index + i) % 10) / 10.0, 18 + i * 3, i < 2);
        }
        if (index < 10) {
            item(restaurant, "Masala Chai", "Hot Indian tea brewed with milk and warming spices", "30", "Beverage", true, "photo-1571934811356-5cc061b6821f", 4.6, 8, true);
            item(restaurant, "Mineral Water", "Chilled sealed mineral water bottle", "20", "Beverage", true, "photo-1550505095-81378a674395", 4.2, 1, false);
            item(restaurant, "Gulab Jamun", "Soft milk dumplings soaked in cardamom syrup", "60", "Dessert", true, "photo-1601303516534-b6d447e4db5c", 4.7, 10, true);
            item(restaurant, "French Fries", "Crispy salted fries with tomato ketchup", "99", "Snack", true, "photo-1573080496219-bb080dd4f877", 4.4, 12, true);
            item(restaurant, "Chocolate Brownie", "Dense chocolate brownie with a fudgy center", "120", "Dessert", true, "photo-1606313564200-e75d5e30476c", 4.8, 12, true);
        }
    }

    private void item(Restaurant restaurant, String name, String description, String price, String category, boolean veg, String photoId, double rating, int prep, boolean popular) {
        MenuItem item = new MenuItem();
        item.setRestaurant(restaurant);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(new BigDecimal(price));
        item.setImageUrl(img(photoId));
        item.setCategory(category);
        item.setIsVeg(veg);
        item.setRating(rating);
        item.setPreparationTime(prep);
        item.setIsPopular(popular);
        item.setIsAvailable(true);
        menuItemRepository.save(item);
    }

    private User user(String name, String email, String password, Role role, String phone) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setPhone(phone);
        user.setAddress("Hyderabad, India");
        return user;
    }

    private RestaurantSeed r(String name, String cuisine, String location, double rating, String photoId) {
        return new RestaurantSeed(name, cuisine, location, rating, photoId);
    }

    private String img(String id) {
        return "https://images.unsplash.com/" + id + "?auto=format&fit=crop&w=400&q=80";
    }

    private record RestaurantSeed(String name, String cuisine, String location, double rating, String photoId) {
    }
}
