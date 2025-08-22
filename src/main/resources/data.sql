-- ===================================================================
-- DATOS DE PRUEBA PARA HEALTH_MOOD - TIENDA DE MASCOTAS
-- ===================================================================

USE health_mood;

-- Deshabilitar verificación de claves foráneas temporalmente
SET foreign_key_checks = 0;

-- ===================================================================
-- CATEGORÍAS DE PRODUCTOS
-- ===================================================================
INSERT INTO categories (name, description) VALUES
                                               ('Alimento para Perros', 'Comida seca y húmeda para perros de todas las edades'),
                                               ('Alimento para Gatos', 'Comida especializada para felinos'),
                                               ('Juguetes', 'Juguetes interactivos y de entretenimiento para mascotas'),
                                               ('Accesorios', 'Correas, collares, camas y otros accesorios'),
                                               ('Higiene y Cuidado', 'Productos para el aseo y cuidado de mascotas'),
                                               ('Medicamentos', 'Suplementos y medicamentos veterinarios'),
                                               ('Snacks y Premios', 'Premios y golosinas para mascotas'),
                                               ('Peceras y Acuarios', 'Productos para peces y acuarios');

-- ===================================================================
-- CLIENTES
-- ===================================================================
INSERT INTO customers (first_name, last_name, phone, email, street, city, commune, password, register_date, rol) VALUES
                                                                                                                     ('María', 'González', '+56987654321', 'maria.gonzalez@email.com', 'Av. Las Condes 1234', 'Santiago', 'Las Condes', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-01-15', 'cliente'),
                                                                                                                     ('Carlos', 'Rodríguez', '+56987654322', 'carlos.rodriguez@email.com', 'Calle Providencia 567', 'Santiago', 'Providencia', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-01-20', 'cliente'),
                                                                                                                     ('Ana', 'Silva', '+56987654323', 'ana.silva@email.com', 'Av. Ñuñoa 890', 'Santiago', 'Ñuñoa', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-02-01', 'cliente'),
                                                                                                                     ('Pedro', 'Martínez', '+56987654324', 'pedro.martinez@email.com', 'Calle Maipú 345', 'Santiago', 'Maipú', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-02-10', 'cliente'),
                                                                                                                     ('Laura', 'Fernández', '+56987654325', 'laura.fernandez@email.com', 'Av. San Miguel 678', 'Santiago', 'San Miguel', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-02-15', 'cliente'),
                                                                                                                     ('Admin', 'Sistema', '+56987654320', 'admin@healthmood.com', 'Oficina Central', 'Santiago', 'Santiago', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-01-01', 'admin'),
                                                                                                                     ('Juan', 'López', '+56987654326', 'juan.lopez@email.com', 'Calle Independencia 123', 'Santiago', 'Independencia', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-03-01', 'cliente'),
                                                                                                                     ('Carmen', 'Torres', '+56987654327', 'carmen.torres@email.com', 'Av. La Florida 456', 'Santiago', 'La Florida', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '2024-03-05', 'cliente');

-- ===================================================================
-- PRODUCTOS
-- ===================================================================
INSERT INTO products (name, description, price, category_id) VALUES
-- Alimento para Perros
('Royal Canin Adult 15kg', 'Alimento completo para perros adultos de razas medianas', 45990, 1),
('Pro Plan Puppy 3kg', 'Alimento para cachorros en crecimiento', 18990, 1),
('Eukanuba Senior 12kg', 'Alimento especializado para perros mayores de 7 años', 52990, 1),
('Pedigree Adult 21kg', 'Alimento económico para perros adultos', 29990, 1),

-- Alimento para Gatos
('Whiskas Adult 10kg', 'Alimento completo para gatos adultos', 35990, 2),
('Royal Canin Kitten 2kg', 'Alimento para gatitos hasta 12 meses', 16990, 2),
('Pro Plan Cat Sterilized 7.5kg', 'Alimento para gatos esterilizados', 42990, 2),
('Felix Húmedo Pollo 85g', 'Alimento húmedo sabor pollo', 1290, 2),

-- Juguetes
('Pelota de Tenis Kong', 'Pelota resistente para perros grandes', 8990, 3),
('Ratón de Peluche Gato', 'Juguete con catnip para gatos', 3990, 3),
('Cuerda Dental Perro', 'Juguete que ayuda a limpiar los dientes', 5990, 3),
('Comedero interactivo Catit', 'Comedero interactivo', 12990, 3),

-- Accesorios
('Correa Retráctil 5m', 'Correa extensible para perros medianos', 15990, 4),
('Collar Antipulgas', 'Collar con protección de 8 meses', 12990, 4),
('Cama Ortopédica Large', 'Cama terapéutica para perros grandes', 45990, 4),
('Transportadora Gato', 'Jaula de transporte para gatos', 25990, 4),

-- Higiene y Cuidado
('Shampoo Antipulgas 500ml', 'Shampoo medicado para eliminar pulgas', 8990, 5),
('Cepillo Desenredante', 'Cepillo profesional para pelo largo', 12990, 5),
('Toallitas Húmedas 80 unidades', 'Toallitas para limpieza diaria', 4990, 5),
('Cortaúñas Profesional', 'Cortaúñas de acero inoxidable', 9990, 5),

-- Medicamentos
('Nexgard Antipulgas L', 'Tableta masticable antipulgas para perros 10-25kg', 18990, 6),
('Suplemento Omega 3', 'Aceite de salmón para pelaje brillante', 14990, 6),
('Probiótico Digestivo', 'Suplemento para salud intestinal', 22990, 6),
('Vitaminas Senior', 'Complejo vitamínico para mascotas mayores', 16990, 6),

-- Snacks y Premios
('Huesos de Cuero 5 unidades', 'Huesos naturales para masticar', 7990, 7),
('Premios Training 200g', 'Snacks pequeños para entrenamiento', 5990, 7),
('Galletas Digestivas', 'Galletas funcionales para perros', 8990, 7),
('Sticks Dentales 28 unidades', 'Premios que limpian los dientes', 11990, 7),

-- Peceras y Acuarios
('Pecera 20L con filtro', 'Acuario completo para principiantes', 35990, 8),
('Alimento Peces Tropicales', 'Escamas balanceadas para peces', 6990, 8),
('Filtro Interno 300L/h', 'Sistema de filtración para acuarios', 18990, 8),
('Decoración Castillo', 'Ornamento decorativo para pecera', 12990, 8);

-- ===================================================================
-- POSTS DEL BLOG
-- ===================================================================
INSERT INTO posts (title, content, author, published_date) VALUES
                                                               ('Cuidados Básicos para Cachorros', 'Los primeros meses de vida de un cachorro son fundamentales para su desarrollo. En este artículo te explicamos todo lo que necesitas saber sobre alimentación, vacunas, socialización y entrenamiento básico para que tu nueva mascota crezca sana y feliz.', 'Dr. Veterinario Health&Mood', '2024-08-01'),
                                                               ('Alimentación Balanceada para Gatos Senior', 'Los gatos mayores de 7 años requieren una alimentación específica que se adapte a sus necesidades cambiantes. Descubre qué nutrientes son esenciales y cómo elegir el mejor alimento para tu felino senior.', 'Nutricionista Pet', '2024-08-05'),
                                                               ('Juguetes Seguros: Cómo Elegir lo Mejor', 'No todos los juguetes son seguros para nuestras mascotas. Te enseñamos a identificar materiales peligrosos y a elegir juguetes apropiados según la edad y tamaño de tu mascota.', 'Especialista en Comportamiento', '2024-08-10'),
                                                               ('Prevención de Pulgas y Garrapatas', 'La prevención es la mejor estrategia contra los parásitos externos. Conoce los diferentes métodos preventivos, desde collares hasta tratamientos spot-on, y cuál es el más adecuado para tu mascota.', 'Dr. Veterinario Health&Mood', '2024-08-15'),
                                                               ('Acuarios para Principiantes', 'Comenzar en el mundo de la acuariofilia puede parecer complicado, pero con la información correcta es más sencillo de lo que imaginas. Guía completa para montar tu primer acuario.', 'Experto en Acuarios', '2024-08-18');

-- ===================================================================
-- PEDIDOS
-- ===================================================================
INSERT INTO pedidos (customer_id, pedido_status, pedido_date, required_date, shipped_date) VALUES
                                                                                               (1, 'Entregado', '2024-08-01', '2024-08-05', '2024-08-04'),
                                                                                               (2, 'Entregado', '2024-08-03', '2024-08-08', '2024-08-07'),
                                                                                               (3, 'En Tránsito', '2024-08-15', '2024-08-20', '2024-08-18'),
                                                                                               (4, 'Procesando', '2024-08-19', '2024-08-24', NULL),
                                                                                               (5, 'Entregado', '2024-08-10', '2024-08-15', '2024-08-14'),
                                                                                               (1, 'Entregado', '2024-07-20', '2024-07-25', '2024-07-24'),
                                                                                               (2, 'Cancelado', '2024-08-16', '2024-08-21', NULL),
                                                                                               (3, 'Entregado', '2024-07-30', '2024-08-04', '2024-08-03');

-- ===================================================================
-- ITEMS DE PEDIDOS
-- ===================================================================
INSERT INTO order_items (pedido_id, product_id, quantity, list_price, discount) VALUES
-- Pedido 1 (María González)
(1, 1, 1, 45990, 0),
(1, 9, 2, 8990, 1000),
(1, 17, 1, 15990, 0),

-- Pedido 2 (Carlos Rodríguez)
(2, 5, 1, 35990, 2000),
(2, 10, 3, 3990, 0),
(2, 18, 1, 12990, 0),

-- Pedido 3 (Ana Silva)
(3, 2, 1, 18990, 0),
(3, 11, 1, 5990, 0),
(3, 21, 2, 8990, 500),

-- Pedido 4 (Pedro Martínez)
(4, 7, 1, 42990, 5000),
(4, 12, 1, 7990, 0),

-- Pedido 5 (Laura Fernández)
(5, 29, 1, 35990, 0),
(5, 30, 2, 6990, 0),
(5, 31, 1, 18990, 1000),

-- Pedido 6 (María González - pedido anterior)
(6, 3, 1, 52990, 3000),
(6, 19, 1, 45990, 2000),

-- Pedido 7 (Carlos Rodríguez - cancelado)
(7, 4, 1, 29990, 0),

-- Pedido 8 (Ana Silva - pedido anterior)
(8, 6, 1, 16990, 0),
(8, 13, 1, 25990, 0);

-- ===================================================================
-- PAGOS
-- ===================================================================
INSERT INTO payments (customer_id, pedido_id, payment_date, amount) VALUES
                                                                        (1, 1, '2024-08-01', 69970),  -- 45990 + (8990*2-1000) + 15990
                                                                        (2, 2, '2024-08-03', 62950),  -- 35990-2000 + (3990*3) + 12990
                                                                        (3, 3, '2024-08-15', 41470),  -- 18990 + 5990 + (8990*2-500)
                                                                        (4, 4, '2024-08-19', 50980),  -- 42990-5000 + 7990
                                                                        (5, 5, '2024-08-10', 70960),  -- 35990 + (6990*2) + 18990-1000
                                                                        (1, 6, '2024-07-20', 95980),  -- 52990-3000 + 45990-2000
                                                                        (3, 8, '2024-07-30', 42980);  -- 16990 + 25990

-- ===================================================================
-- IMÁGENES DE PRODUCTOS
-- ===================================================================
INSERT INTO img (imgURL, pedido_number, is_primary, products_product_id) VALUES
-- Imágenes para alimentos de perros
('https://www.drpet.cl/2418-large_default/royal-dog-medium-adult-alimento-para-perro.jpg', 1, 'Y', 1),
('https://www.drpet.cl/2419-medium_default/royal-dog-medium-adult-alimento-para-perro.jpg', 2, 'N', 1),
('https://www.tusmascotas.cl/wp-content/uploads/2020/10/Pro-Plan-Puppy-Complete.jpg.webp', 1, 'Y', 2),
('https://www.tusmascotas.cl/wp-content/uploads/2020/10/Pro-Plan-Puppy-Complete-15Kg-%E2%80%93-60x45x10.jpg.webp', 2, 'N', 2),
('https://www.eukanuba.com/cdn-cgi/image/width=600,height=600,f=auto,quality=90/cl/sites/g/files/fnmzdf6796/files/2025-04/eukanuba-senior-medium-breed-packshot-en-sp.png', 1, 'Y', 3),
('https://http2.mlstatic.com/D_NQ_NP_2X_939497-MLA84838791279_052025-F.webp', 1, 'Y', 4),

-- Imágenes para alimentos de gatos
('https://petdelivery.shop/wp-content/uploads/2025/05/whiskas-carne-10kg-frontal.jpg', 1, 'Y', 5),
('https://www.tusmascotas.cl/wp-content/uploads/2020/10/royal-canin-kitten-.jpg.webp', 1, 'Y', 6),
('https://www.tusmascotas.cl/wp-content/uploads/2020/10/Pro-Plan-Sterilized-Cat.jpg.webp', 1, 'Y', 7),
('https://petdelivery.shop/wp-content/uploads/2025/04/felix-classic-sachet-pollo.jpg', 1, 'Y', 8),

-- Imágenes para juguetes
('https://cl-cenco-pim-resizer.ecomm.cencosud.com/unsafe/adaptive-fit-in/640x0/filters:quality(75)/prd-cl/product-medias/d7103eeb-7ae4-4c4b-b56b-a98db6dcf283/MK9NGX6UBQ/MK9NGX6UBQ-1/1728496474104-MK9NGX6UBQ-1-1.png', 1, 'Y', 9),
('https://cl-cenco-pim-resizer.ecomm.cencosud.com/unsafe/adaptive-fit-in/1920x0/prd-cl/product-medias/d7103eeb-7ae4-4c4b-b56b-a98db6dcf283/MK9NGX6UBQ/MK9NGX6UBQ-1/1728496474253-MK9NGX6UBQ-1-2.png', 2, 'N', 9),
('https://arenaparamascotas.cl/wp-content/uploads/2024/09/Juguete-Raton-con-sonido-para-gatos-2-1024x1024.jpg', 1, 'Y', 10),
('https://petcity.cl/wp-content/uploads/2025/06/Juguete-Cuerda-Dental-Perro-Stitch-600x600.jpg', 1, 'Y', 11),
('https://europet.cl/wp-content/uploads/2022/04/catit-senses-digger.jpg', 1, 'Y', 12),

-- Imágenes para accesorios
('https://www.distribuidoralira.cl/wp-content/uploads/2025/03/blanco.jpg', 1, 'Y', 13),
('https://www.distribuidoralira.cl/wp-content/uploads/2025/07/seresto-mas-de-8kg-768x768.jpg', 1, 'Y', 14),
('https://http2.mlstatic.com/D_NQ_NP_2X_950302-MLA83737342629_042025-F.webp', 1, 'Y', 15),
('https://bestforpets.cl/tienda/18900-large_default/catit-carrier-voyageur-jaula-transportadora.jpg', 1, 'Y', 16),

-- Imágenes para higiene
('https://www.superzoo.cl/on/demandware.static/-/Sites-SuperZoo-master-catalog/default/dw338357bd/images/540%20.jpg', 1, 'Y', 17),
('https://andis.cl/wp-content/uploads/2019/03/Deslanador-Ancho-Andis-Green-Line-02.jpg', 1, 'Y', 18),
('https://http2.mlstatic.com/D_NQ_NP_2X_851039-MLA81204027726_122024-F-toallas-humedas-perrosgatosmascotas-clorhexidina-y-mirra.webp', 1, 'Y', 19),
('https://www.superzoo.cl/on/demandware.static/-/Sites-SuperZoo-master-catalog/default/dwa1c8d211/images/8464_m.jpg', 1, 'Y', 20),

-- Imágenes para medicamentos
('https://www.clubdeperrosygatos.cl/wp-content/uploads/2025/02/Nexgard-para-Perros-101-a-25-Kg-1-Dosis-Masticable.webp', 1, 'Y', 21),
('https://www.clubdeperrosygatos.cl/wp-content/uploads/2018/10/SUPERPET-OMEGA-PERRO-ADULTO.webp', 1, 'Y', 22),
('https://centralvet03.akamaized.net/27395/probio-pasta-60-ml-probiotico-prebiotico-para-mascotas-mervue.jpg', 1, 'Y', 23),
('https://www.tusmascotas.cl/wp-content/uploads/2021/03/doguivit-senior.jpg', 1, 'Y', 24),

-- Imágenes para snacks
('https://zalavet.com/cdn/shop/products/hueso_prensado_piel_de_vacuno_87e99f86-d0bd-40b7-a23a-b1998503d307.jpg?v=1618584987', 1, 'Y', 25),
('https://arcadenoe.com.gt/cdn/shop/products/ADN800X800-2022-08-09T181000.908_700x.png?v=1660079405', 1, 'Y', 26),
('https://d23qt3x1ychzdy.cloudfront.net/dev_images_products/3cc07f14a908b99174f0de6761a5da75_1704299953.jpg', 1, 'Y', 27),
('https://arquivet.com/305-large_default/dental-sticks-28-unidades.jpg', 1, 'Y', 28),

-- Imágenes para acuarios
('https://olacuario.es/2456-large_default/aqua-20-led.jpg', 1, 'Y', 29),
('https://www.aquamania.cl/3885-large_default/porpoise-cichlid-food-120-g.jpg', 1, 'Y', 30),
('https://faunasalud.cl/wp-content/uploads/2020/12/2-7.jpg', 1, 'Y', 31),
('https://http2.mlstatic.com/D_NQ_NP_2X_624413-MLC82656035361_022025-F-adorno-acuario-castillo-fantasia-premium-accesorio-pecera.webp', 1, 'Y', 32);

-- ===================================================================
-- CARRITO DE COMPRAS (ALGUNOS ITEMS PENDIENTES)
-- ===================================================================
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
-- Carrito del cliente 1 (María González)
(1, 8, 12),  -- Felix húmedo - 12 latas
(1, 26, 1),  -- Premios training

-- Carrito del cliente 2 (Carlos Rodríguez)
(2, 15, 1),  -- Cama ortopédica
(2, 22, 1),  -- Suplemento Omega 3

-- Carrito del cliente 4 (Pedro Martínez)
(4, 1, 1),   -- Royal Canin Adult
(4, 13, 1),  -- Correa retráctil
(4, 25, 1);  -- Huesos de cuero

-- ===================================================================
-- COMENTARIOS EN POSTS
-- ===================================================================
INSERT INTO comments (post_id, customer_id, comment_text, comment_date) VALUES
                                                                            (1, 1, 'Excelente artículo! Me ayudó mucho con mi nuevo cachorro golden retriever.', '2024-08-02 10:30:00'),
                                                                            (1, 3, 'Muy útil la información sobre las vacunas. ¿Podrían hacer un artículo específico sobre el calendario de vacunación?', '2024-08-02 14:15:00'),
                                                                            (2, 2, 'Mi gata tiene 8 años y estaba buscando información sobre alimentación. Gracias por los consejos!', '2024-08-06 09:45:00'),
                                                                            (2, 5, 'Cambié el alimento de mi gato siguiendo estos consejos y ha mejorado mucho su digestión.', '2024-08-07 16:20:00'),
                                                                            (3, 4, 'No sabía que algunos juguetes podían ser peligrosos. Muy importante esta información para los dueños primerizos.', '2024-08-11 11:30:00'),
                                                                            (4, 1, '¿El collar antipulgas es mejor que las pipetas? Mi perro siempre se rasca mucho en verano.', '2024-08-16 08:45:00'),
                                                                            (4, 7, 'Uso Nexgard desde hace años y funciona muy bien. Lo recomiendo totalmente.', '2024-08-17 13:20:00'),
                                                                            (5, 8, 'Estoy pensando en comprar mi primer acuario. ¿Qué peces recomiendan para principiantes?', '2024-08-19 15:45:00');

-- ===================================================================
-- MENSAJES DE CONTACTO
-- ===================================================================
INSERT INTO contact_messages (name, email, subject, message, sent_at) VALUES
                                                                          ('Roberto Sánchez', 'roberto.sanchez@email.com', 'Consulta sobre envíos', 'Hola, quería saber si hacen envíos a regiones y cuáles son los costos. Estoy interesado en comprar alimento para mi perro.', '2024-08-01 09:30:00'),
                                                                          ('Valentina Morales', 'valentina.morales@email.com', 'Producto defectuoso', 'Compré una correa retráctil la semana pasada y se rompió al segundo día. Me gustaría solicitar un cambio o devolución.', '2024-08-05 14:20:00'),
                                                                          ('Francisco León', 'francisco.leon@email.com', 'Consulta veterinaria', '¿Tienen veterinarios que puedan asesorar sobre qué suplementos necesita mi perro senior?', '2024-08-10 11:45:00'),
                                                                          ('Isabella Rivera', 'isabella.rivera@email.com', 'Felicitaciones', 'Quería felicitarlos por el excelente servicio. Mi pedido llegó súper rápido y en perfectas condiciones.', '2024-08-12 16:30:00'),
                                                                          ('Andrés Castillo', 'andres.castillo@email.com', 'Consulta sobre stock', 'Buenos días, quería saber si tienen disponible el Pro Plan para gatos esterilizados de 15kg. En la página aparece agotado.', '2024-08-18 10:15:00'),
                                                                          ('Camila Herrera', 'camila.herrera@email.com', 'Sugerencia de productos', 'Sería genial que incluyeran más productos para conejos y hámsters. Hay muy pocas opciones en el mercado.', '2024-08-20 13:45:00');

-- ===================================================================
-- LOGS DEL CHATBOT
-- ===================================================================
INSERT INTO chatbot_logs (customer_id, message, response, timestamp) VALUES
                                                                         (1, '¿Qué alimento recomiendan para un golden retriever de 2 años?', 'Para un Golden Retriever adulto de 2 años, te recomiendo Royal Canin Adult o Pro Plan Adult Large Breed. Estos alimentos están formulados específicamente para razas grandes y proporcionan los nutrientes necesarios para mantener un peso saludable y articulaciones fuertes.', '2024-08-01 10:00:00'),
                                                                         (1, '¿Cuánto debe comer por día?', 'Un Golden Retriever de 2 años debería comer aproximadamente 3-4 tazas de alimento seco de alta calidad al día, divididas en 2 comidas. Sin embargo, la cantidad exacta depende del peso, nivel de actividad y metabolismo. Te recomiendo consultar con un veterinario para una recomendación más específica.', '2024-08-01 10:02:00'),
                                                                         (2, 'Mi gato de 5 años está perdiendo peso, ¿qué puedo hacer?', 'La pérdida de peso en gatos puede tener varias causas. Te recomiendo programar una cita con un veterinario lo antes posible para descartar problemas de salud. Mientras tanto, asegúrate de que esté comiendo un alimento de alta calidad y considera ofrecerle comida húmeda para aumentar la palatabilidad.', '2024-08-03 15:30:00'),
                                                                         (3, '¿Los huesos de cuero son seguros para cachorros?', 'Los huesos de cuero pueden ser seguros para cachorros mayores de 4 meses, pero siempre bajo supervisión. Para cachorros muy pequeños, te recomiendo juguetes específicos para dentición como cuerdas o juguetes Kong para cachorros que son más blandos y seguros.', '2024-08-15 09:15:00'),
                                                                         (4, '¿Cada cuánto debo bañar a mi perro?', 'La frecuencia del baño depende del tipo de pelo y estilo de vida de tu perro. En general, cada 4-6 semanas es suficiente para la mayoría de los perros. Si tu perro pasa mucho tiempo al aire libre o tiene piel sensible, podría necesitar baños más o menos frecuentes. Usa siempre shampoo específico para perros.', '2024-08-19 11:45:00'),
                                                                         (5, 'Quiero montar un acuario pequeño, ¿qué necesito?', 'Para un acuario pequeño necesitarás: una pecera de al menos 20 litros, un filtro interno, un calentador (si planeas tener peces tropicales), grava o sustrato, decoraciones, y por supuesto, peces compatibles. Te recomiendo comenzar con peces resistentes como guppies o bettas.', '2024-08-10 14:20:00');

-- Rehabilitar verificación de claves foráneas
SET foreign_key_checks = 1;

-- ===================================================================
-- VERIFICACIÓN DE DATOS INSERTADOS
-- ===================================================================
SELECT 'Datos de prueba insertados exitosamente' as Status;

SELECT
    'categories' as tabla, COUNT(*) as registros FROM categories
UNION ALL SELECT
              'customers', COUNT(*) FROM customers
UNION ALL SELECT
              'posts', COUNT(*) FROM posts
UNION ALL SELECT
              'products', COUNT(*) FROM products
UNION ALL SELECT
              'pedidos', COUNT(*) FROM pedidos
UNION ALL SELECT
              'order_items', COUNT(*) FROM order_items
UNION ALL SELECT
              'payments', COUNT(*) FROM payments
UNION ALL SELECT
              'img', COUNT(*) FROM img
UNION ALL SELECT
              'cart_items', COUNT(*) FROM cart_items
UNION ALL SELECT
              'comments', COUNT(*) FROM comments
UNION ALL SELECT
              'contact_messages', COUNT(*) FROM contact_messages
UNION ALL SELECT
              'chatbot_logs', COUNT(*) FROM chatbot_logs;

-- ===================================================================
-- CONSULTAS DE VERIFICACIÓN ÚTILES
-- ===================================================================

-- Ver resumen de ventas por cliente
-- SELECT CONCAT(c.first_name, ' ', c.last_name) as cliente, COUNT(p.pedido_id) as pedidos, SUM(pay.amount) as total_gastado
-- FROM customers c
-- LEFT JOIN pedidos p ON c.customer_id = p.customer_id
-- LEFT JOIN payments pay ON p.pedido_id = pay.pedido_id
-- WHERE c.rol = 'cliente'
-- GROUP BY c.customer_id
-- ORDER BY total_gastado DESC;

-- Ver productos más vendidos
-- SELECT pr.name, SUM(oi.quantity) as cantidad_vendida, SUM(oi.quantity * (oi.list_price - oi.discount)) as ingresos
-- FROM products pr
-- JOIN order_items oi ON pr.product_id = oi.product_id
-- JOIN pedidos pe ON oi.pedido_id = pe.pedido_id
-- WHERE pe.pedido_status != 'Cancelado'
-- GROUP BY pr.product_id
-- ORDER BY cantidad_vendida DESC
-- LIMIT 10;