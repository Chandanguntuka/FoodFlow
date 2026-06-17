-- Dummy Data for HCL Restaurant Application

-- Insert Users
INSERT INTO users (first_name, last_name, email, password, phone, address, role, created_at) VALUES
('John', 'Doe', 'john.doe@example.com', '$2a$10$slYQmyNdGzin7olVN3p5aOsvqz0WGbFZV0X0f8X3m5k8zC1i2xZTm', '9876543210', '123 Main Street, Downtown', 'CUSTOMER', NOW()),
('Jane', 'Smith', 'jane.smith@example.com', '$2a$10$slYQmyNdGzin7olVN3p5aOsvqz0WGbFZV0X0f8X3m5k8zC1i2xZTm', '9876543211', '456 Oak Avenue, Uptown', 'CUSTOMER', NOW()),
('Robert', 'Johnson', 'robert.j@example.com', '$2a$10$slYQmyNdGzin7olVN3p5aOsvqz0WGbFZV0X0f8X3m5k8zC1i2xZTm', '9876543212', '789 Pine Road, Midtown', 'CUSTOMER', NOW()),
('Maria', 'Garcia', 'maria.garcia@example.com', '$2a$10$slYQmyNdGzin7olVN3p5aOsvqz0WGbFZV0X0f8X3m5k8zC1i2xZTm', '9876543213', '321 Elm Boulevard, Suburb', 'CUSTOMER', NOW()),
('Ahmed', 'Ali', 'ahmed.ali@example.com', '$2a$10$slYQmyNdGzin7olVN3p5aOsvqz0WGbFZV0X0f8X3m5k8zC1i2xZTm', '9876543214', '654 Maple Lane, Downtown', 'CUSTOMER', NOW()),
('Admin', 'User', 'admin@example.com', '$2a$10$slYQmyNdGzin7olVN3p5aOsvqz0WGbFZV0X0f8X3m5k8zC1i2xZTm', '9999999999', 'Admin Building', 'ADMIN', NOW());

-- Insert Restaurants
INSERT INTO restaurants (name, cuisine, address, rating, open, dish, image_content_type) VALUES
('The Italian Corner', 'Italian', '123 Main Street, Downtown', 4.5, TRUE, 'Pasta', 'image/jpeg'),
('Golden Dragon', 'Chinese', '456 Oak Avenue, Chinatown', 4.2, TRUE, 'Noodles', 'image/jpeg'),
('Spice Route', 'Indian', '789 Elm Boulevard, Midtown', 4.7, TRUE, 'Curry', 'image/jpeg'),
('Burger Haven', 'American', '321 Pine Road, Uptown', 4.0, TRUE, 'Burgers', 'image/jpeg'),
('Sushi Paradise', 'Japanese', '654 Maple Lane, Waterfront', 4.8, TRUE, 'Sushi', 'image/jpeg');

-- Insert Menu Items for Restaurant 1 (The Italian Corner)
INSERT INTO menu_items (restaurant_id, name, category, price, description, image_content_type) VALUES
(1, 'Spaghetti Carbonara', 'Pasta', 12.99, 'Classic Italian pasta with bacon and parmesan', 'image/jpeg'),
(1, 'Fettuccine Alfredo', 'Pasta', 11.99, 'Creamy pasta with garlic and parmesan sauce', 'image/jpeg'),
(1, 'Lasagna Bolognese', 'Pasta', 13.99, 'Layered pasta with meat sauce', 'image/jpeg'),
(1, 'Penne Arrabbiata', 'Pasta', 10.99, 'Spicy tomato-based pasta', 'image/jpeg'),
(1, 'Garlic Bread', 'Appetizer', 4.99, 'Crispy bread with garlic butter', 'image/jpeg');

-- Insert Menu Items for Restaurant 2 (Golden Dragon)
INSERT INTO menu_items (restaurant_id, name, category, price, description, image_content_type) VALUES
(2, 'Kung Pao Chicken', 'Main Course', 11.99, 'Spicy chicken with peanuts and vegetables', 'image/jpeg'),
(2, 'Chow Mein', 'Noodles', 9.99, 'Stir-fried noodles with vegetables', 'image/jpeg'),
(2, 'Spring Rolls', 'Appetizer', 5.99, 'Crispy spring rolls with sweet sauce', 'image/jpeg'),
(2, 'Fried Rice', 'Main Course', 8.99, 'Mixed fried rice with egg and vegetables', 'image/jpeg'),
(2, 'Sweet and Sour Pork', 'Main Course', 12.99, 'Tender pork in sweet and sour sauce', 'image/jpeg');

-- Insert Menu Items for Restaurant 3 (Spice Route)
INSERT INTO menu_items (restaurant_id, name, category, price, description, image_content_type) VALUES
(3, 'Butter Chicken', 'Curry', 13.99, 'Tender chicken in creamy tomato sauce', 'image/jpeg'),
(3, 'Tikka Masala', 'Curry', 12.99, 'Marinated chicken in aromatic curry sauce', 'image/jpeg'),
(3, 'Biryani', 'Rice', 11.99, 'Fragrant rice with meat and spices', 'image/jpeg'),
(3, 'Samosa', 'Appetizer', 4.99, 'Crispy pastry with spiced filling', 'image/jpeg'),
(3, 'Naan Bread', 'Bread', 3.99, 'Traditional Indian flatbread', 'image/jpeg');

-- Insert Menu Items for Restaurant 4 (Burger Haven)
INSERT INTO menu_items (restaurant_id, name, category, price, description, image_content_type) VALUES
(4, 'Classic Burger', 'Burger', 9.99, 'Juicy beef burger with lettuce and tomato', 'image/jpeg'),
(4, 'Cheese Burger', 'Burger', 10.99, 'Burger with melted cheddar cheese', 'image/jpeg'),
(4, 'Bacon Burger', 'Burger', 11.99, 'Burger loaded with crispy bacon', 'image/jpeg'),
(4, 'French Fries', 'Sides', 3.99, 'Crispy golden fries', 'image/jpeg'),
(4, 'Milkshake', 'Beverages', 5.99, 'Creamy vanilla milkshake', 'image/jpeg');

-- Insert Menu Items for Restaurant 5 (Sushi Paradise)
INSERT INTO menu_items (restaurant_id, name, category, price, description, image_content_type) VALUES
(5, 'California Roll', 'Sushi', 9.99, 'Crab, avocado, and cucumber sushi roll', 'image/jpeg'),
(5, 'Spicy Tuna Roll', 'Sushi', 10.99, 'Spicy tuna with cucumber and avocado', 'image/jpeg'),
(5, 'Dragon Roll', 'Sushi', 12.99, 'Premium roll with eel and avocado', 'image/jpeg'),
(5, 'Miso Soup', 'Soup', 3.99, 'Traditional soybean soup', 'image/jpeg'),
(5, 'Edamame', 'Appetizer', 4.99, 'Steamed soybeans with sea salt', 'image/jpeg');

-- Insert Carts for Users
INSERT INTO carts (user_id, total_amount, updated_at) VALUES
(1, 0.00, NOW()),
(2, 0.00, NOW()),
(3, 0.00, NOW()),
(4, 0.00, NOW()),
(5, 0.00, NOW());

-- Insert Cart Items
INSERT INTO cart_items (cart_id, menu_item_id, quantity) VALUES
(1, 1, 2),
(1, 5, 1),
(2, 6, 1),
(2, 7, 3),
(3, 11, 1),
(3, 12, 2),
(4, 16, 2),
(4, 17, 1),
(5, 21, 1),
(5, 22, 2);

-- Insert Orders
INSERT INTO orders (user_id, status, total_amount, delivery_address, created_at, updated_at) VALUES
(1, 'DELIVERED', 45.97, '123 Main Street, Downtown', NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 7 DAY),
(2, 'DELIVERED', 38.96, '456 Oak Avenue, Uptown', NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 5 DAY),
(3, 'IN_PROGRESS', 32.97, '789 Pine Road, Midtown', NOW() - INTERVAL 1 DAY, NOW()),
(4, 'PENDING', 27.97, '321 Elm Boulevard, Suburb', NOW(), NOW()),
(5, 'DELIVERED', 49.96, '654 Maple Lane, Downtown', NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 3 DAY),
(1, 'DELIVERED', 54.96, '123 Main Street, Downtown', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY);

-- Insert Order Items
INSERT INTO order_items (order_id, menu_item_id, quantity, item_price) VALUES
(1, 1, 2, 12.99),
(1, 5, 1, 4.99),
(2, 6, 1, 11.99),
(2, 7, 3, 9.99),
(3, 11, 1, 13.99),
(3, 12, 2, 12.99),
(4, 16, 2, 10.99),
(4, 17, 1, 3.99),
(5, 21, 1, 9.99),
(5, 22, 2, 10.99),
(6, 1, 2, 12.99),
(6, 3, 1, 13.99),
(6, 5, 2, 4.99);

-- Show inserted data count
SELECT 'Users' as table_name, COUNT(*) as count FROM users
UNION ALL
SELECT 'Restaurants', COUNT(*) FROM restaurants
UNION ALL
SELECT 'Menu Items', COUNT(*) FROM menu_items
UNION ALL
SELECT 'Carts', COUNT(*) FROM carts
UNION ALL
SELECT 'Cart Items', COUNT(*) FROM cart_items
UNION ALL
SELECT 'Orders', COUNT(*) FROM orders
UNION ALL
SELECT 'Order Items', COUNT(*) FROM order_items;
