-- Crear la base de datos PostgreSQL
-- Nota: En PostgreSQL, CREATE DATABASE debe ejecutarse fuera de un script de transacción
-- Ejecutar este comando por separado en psql o pgAdmin:
-- CREATE DATABASE health_mood WITH ENCODING 'UTF8' LC_COLLATE = 'es_ES.UTF-8' LC_CTYPE = 'es_ES.UTF-8';

-- Conectarse a la base de datos health_mood antes de ejecutar el resto del script
-- \c health_mood;

-- Eliminar tablas si existen (en orden correcto para evitar problemas de FK)
DROP TABLE IF EXISTS chatbot_logs CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS cart_items CASCADE;
DROP TABLE IF EXISTS img CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS pedidos CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS contact_messages CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS categories CASCADE;

-- Crear extensión para generar UUIDs si se necesita
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ===================================================================
-- CREACIÓN DE TABLAS
-- ===================================================================

-- Tabla: categories
CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para categories
CREATE INDEX idx_categories_name ON categories(name);

-- Tabla: customers
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
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
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para customers
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_customers_name ON customers(first_name, last_name);
CREATE INDEX idx_customers_rol ON customers(rol);

-- Tabla: posts
CREATE TABLE posts (
    post_id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    published_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para posts
CREATE INDEX idx_posts_published_date ON posts(published_date);
CREATE INDEX idx_posts_author ON posts(author);

-- Tabla: contact_messages
CREATE TABLE contact_messages (
    message_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    subject VARCHAR(150),
    message TEXT NOT NULL,
    sent_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para contact_messages
CREATE INDEX idx_contact_messages_email ON contact_messages(email);
CREATE INDEX idx_contact_messages_sent_at ON contact_messages(sent_at);

-- Tabla: products
CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price INTEGER NOT NULL,
    category_id INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para products
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_price ON products(price);

-- Tabla: pedidos
CREATE TABLE pedidos (
    pedido_id SERIAL PRIMARY KEY,
    customer_id INTEGER,
    pedido_status VARCHAR(50),
    pedido_date DATE,
    required_date DATE,
    shipped_date DATE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para pedidos
CREATE INDEX idx_pedidos_customer_id ON pedidos(customer_id);
CREATE INDEX idx_pedidos_status ON pedidos(pedido_status);
CREATE INDEX idx_pedidos_date ON pedidos(pedido_date);

-- Tabla: payments
CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    customer_id INTEGER,
    pedido_id INTEGER,
    payment_date DATE,
    amount INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para payments
CREATE INDEX idx_payments_customer_id ON payments(customer_id);
CREATE INDEX idx_payments_pedido_id ON payments(pedido_id);
CREATE INDEX idx_payments_date ON payments(payment_date);

-- Tabla: order_items
CREATE TABLE order_items (
    item_id SERIAL PRIMARY KEY,
    pedido_id INTEGER,
    product_id INTEGER,
    quantity INTEGER NOT NULL,
    list_price INTEGER NOT NULL,
    discount INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para order_items
CREATE INDEX idx_order_items_pedido_id ON order_items(pedido_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-- Tabla: img
CREATE TABLE img (
    idimg SERIAL PRIMARY KEY,
    imgurl VARCHAR(150) NOT NULL,
    pedido_number INTEGER NOT NULL,
    is_primary CHAR(1) NOT NULL,
    products_product_id INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para img
CREATE INDEX idx_img_product_id ON img(products_product_id);
CREATE INDEX idx_img_is_primary ON img(is_primary);

-- Tabla: cart_items
CREATE TABLE cart_items (
    cart_item_id SERIAL PRIMARY KEY,
    cart_id INTEGER,
    product_id INTEGER,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para cart_items
CREATE INDEX idx_cart_items_cart_id ON cart_items(cart_id);
CREATE INDEX idx_cart_items_product_id ON cart_items(product_id);

-- Tabla: comments
CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    post_id INTEGER,
    customer_id INTEGER,
    comment_text TEXT NOT NULL,
    comment_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para comments
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_customer_id ON comments(customer_id);
CREATE INDEX idx_comments_date ON comments(comment_date);

-- Tabla: chatbot_logs
CREATE TABLE chatbot_logs (
    log_id SERIAL PRIMARY KEY,
    customer_id INTEGER,
    message TEXT NOT NULL,
    response TEXT,
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para chatbot_logs
CREATE INDEX idx_chatbot_logs_customer_id ON chatbot_logs(customer_id);
CREATE INDEX idx_chatbot_logs_timestamp ON chatbot_logs(timestamp);

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
-- FUNCIONES Y TRIGGERS PARA updated_at (equivalente a ON UPDATE CURRENT_TIMESTAMP de MySQL)
-- ===================================================================

-- Crear función para actualizar updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

- Hacer los triggers idempotentes
DROP TRIGGER IF EXISTS update_categories_updated_at ON categories;
CREATE TRIGGER update_categories_updated_at
    BEFORE UPDATE ON categories
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_customers_updated_at ON customers;
CREATE TRIGGER update_customers_updated_at
    BEFORE UPDATE ON customers
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_posts_updated_at ON posts;
CREATE TRIGGER update_posts_updated_at
    BEFORE UPDATE ON posts
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_products_updated_at ON products;
CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON products
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_pedidos_updated_at ON pedidos;
CREATE TRIGGER update_pedidos_updated_at
    BEFORE UPDATE ON pedidos
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_payments_updated_at ON payments;
CREATE TRIGGER update_payments_updated_at
    BEFORE UPDATE ON payments
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_cart_items_updated_at ON cart_items;
CREATE TRIGGER update_cart_items_updated_at
    BEFORE UPDATE ON cart_items
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
-- ===================================================================
-- CONFIGURACIÓN FINAL
-- ===================================================================

-- Mostrar información de la base de datos creada
SELECT 'Base de datos health_mood creada exitosamente para PostgreSQL' as status;

-- Contar tablas creadas
SELECT COUNT(*) as "Tablas creadas" 
FROM information_schema.tables 
WHERE table_schema = 'public' 
AND table_type = 'BASE TABLE';
