-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS health_mood
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE health_mood;

-- Configuración inicial
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';
SET time_zone = '+00:00';

DROP TABLE IF EXISTS chatbot_logs;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS img;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS contact_messages;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS categories;


CREATE TABLE categories (
                            category_id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            INDEX idx_categories_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE customers (
                           customer_id INT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           phone VARCHAR(20),
                           email VARCHAR(100) UNIQUE NOT NULL,
                           street VARCHAR(100),
                           city VARCHAR(50),
                           commune VARCHAR(50),
                           password VARCHAR(257) NOT NULL,
                           register_date DATE,
                           rol VARCHAR(45),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           INDEX idx_customers_email (email),
                           INDEX idx_customers_name (first_name, last_name),
                           INDEX idx_customers_rol (rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE posts (
                       post_id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(150) NOT NULL,
                       content TEXT NOT NULL,
                       author VARCHAR(100) NOT NULL,
                       published_date DATE NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       INDEX idx_posts_published_date (published_date),
                       INDEX idx_posts_author (author)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE contact_messages (
                                  message_id INT AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  email VARCHAR(100) NOT NULL,
                                  subject VARCHAR(150),
                                  message TEXT NOT NULL,
                                  sent_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  INDEX idx_contact_messages_email (email),
                                  INDEX idx_contact_messages_sent_at (sent_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE products (
                          product_id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          price INT NOT NULL,
                          category_id INT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          INDEX idx_products_category_id (category_id),
                          INDEX idx_products_name (name),
                          INDEX idx_products_price (price)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE pedidos (
                         pedido_id INT AUTO_INCREMENT PRIMARY KEY,
                         customer_id INT,
                         pedido_status VARCHAR(50),
                         pedido_date DATE,
                         required_date DATE,
                         shipped_date DATE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         INDEX idx_pedidos_customer_id (customer_id),
                         INDEX idx_pedidos_status (pedido_status),
                         INDEX idx_pedidos_date (pedido_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE payments (
                          payment_id INT AUTO_INCREMENT PRIMARY KEY,
                          customer_id INT,
                          pedido_id INT,
                          payment_date DATE,
                          amount INT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          INDEX idx_payments_customer_id (customer_id),
                          INDEX idx_payments_pedido_id (pedido_id),
                          INDEX idx_payments_date (payment_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: order_items
CREATE TABLE order_items (
                             item_id INT AUTO_INCREMENT PRIMARY KEY,
                             pedido_id INT,
                             product_id INT,
                             quantity INT NOT NULL,
                             list_price INT NOT NULL,
                             discount INT DEFAULT 0,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             INDEX idx_order_items_pedido_id (pedido_id),
                             INDEX idx_order_items_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: img
CREATE TABLE img (
                     idimg INT AUTO_INCREMENT PRIMARY KEY,
                     imgURL VARCHAR(150) NOT NULL,
                     pedido_number INT NOT NULL,
                     is_primary CHAR(1) NOT NULL,
                     products_product_id INT NOT NULL,
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     INDEX idx_img_product_id (products_product_id),
                     INDEX idx_img_is_primary (is_primary)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: cart_items
CREATE TABLE cart_items (
                            cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
                            cart_id INT,
                            product_id INT,
                            quantity INT NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            INDEX idx_cart_items_cart_id (cart_id),
                            INDEX idx_cart_items_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: comments
CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,
                          post_id INT,
                          customer_id INT,
                          comment_text TEXT NOT NULL,
                          comment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          INDEX idx_comments_post_id (post_id),
                          INDEX idx_comments_customer_id (customer_id),
                          INDEX idx_comments_date (comment_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: chatbot_logs
CREATE TABLE chatbot_logs (
                              log_id INT AUTO_INCREMENT PRIMARY KEY,
                              customer_id INT,
                              message TEXT NOT NULL,
                              response TEXT,
                              timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                              INDEX idx_chatbot_logs_customer_id (customer_id),
                              INDEX idx_chatbot_logs_timestamp (timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===================================================================
-- CREACIÓN DE CONSTRAINTS (FOREIGN KEYS)
-- ===================================================================

-- Products -> Categories
ALTER TABLE products
    ADD CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories(category_id)
            ON DELETE SET NULL ON UPDATE CASCADE;

-- Pedidos -> Customers
ALTER TABLE pedidos
    ADD CONSTRAINT fk_pedidos_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Payments -> Customers
ALTER TABLE payments
    ADD CONSTRAINT fk_payments_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Payments -> Pedidos
ALTER TABLE payments
    ADD CONSTRAINT fk_payments_pedido
        FOREIGN KEY (pedido_id) REFERENCES pedidos(pedido_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Order Items -> Pedidos
ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_pedido
        FOREIGN KEY (pedido_id) REFERENCES pedidos(pedido_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Order Items -> Products
ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Images -> Products
ALTER TABLE img
    ADD CONSTRAINT fk_img_product
        FOREIGN KEY (products_product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Cart Items -> Products
ALTER TABLE cart_items
    ADD CONSTRAINT fk_cart_items_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Comments -> Posts
ALTER TABLE comments
    ADD CONSTRAINT fk_comments_post
        FOREIGN KEY (post_id) REFERENCES posts(post_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Comments -> Customers
ALTER TABLE comments
    ADD CONSTRAINT fk_comments_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- Chatbot Logs -> Customers
ALTER TABLE chatbot_logs
    ADD CONSTRAINT fk_chatbot_logs_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- ===================================================================
-- CONFIGURACIÓN FINAL
-- ===================================================================

-- Restaurar configuración
SET foreign_key_checks = 1;

-- Mostrar información de la base de datos creada
SELECT 'Base de datos health_mood creada exitosamente' as Status;
SELECT COUNT(*) as 'Tablas creadas' FROM information_schema.tables WHERE table_schema = 'health_mood';
