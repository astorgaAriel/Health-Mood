# Health Mood - Configuración PostgreSQL

## Requisitos Previos

- Java 21
- PostgreSQL 15 o superior
- Maven 3.6+
- Docker (opcional)

## Configuración de Base de Datos PostgreSQL

### Opción 1: PostgreSQL Local

1. Instalar PostgreSQL en tu sistema
2. Crear la base de datos:
```sql
CREATE DATABASE health_mood WITH ENCODING 'UTF8' LC_COLLATE = 'es_ES.UTF-8' LC_CTYPE = 'es_ES.UTF-8';
```

3. Configurar las variables de entorno en el archivo `.env`:
```env
DB_URL=jdbc:postgresql://localhost:5432/health_mood
DB_USERNAME=postgres
DB_PASSWORD=tu_password_aqui
```

4. Ejecutar los scripts de base de datos:
```bash
# Conectarse a PostgreSQL
psql -U postgres -d health_mood

# Ejecutar el esquema
\i src/main/resources/schema.sql

# Ejecutar los datos de prueba
\i src/main/resources/data.sql
```

### Opción 2: Docker Compose (Recomendado)

1. Ejecutar con Docker Compose:
```bash
docker-compose up -d
```

Esto iniciará:
- PostgreSQL en el puerto 5432
- La aplicación Spring Boot en el puerto 8080
- Creará automáticamente la base de datos con datos de prueba

## Perfiles de Configuración

### Desarrollo Local (`local`)
```yaml
spring:
  profiles:
    active: local
```

### Producción (`prod`)
```yaml
spring:
  profiles:
    active: prod
```

## Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://localhost:5432/health_mood` |
| `DB_USERNAME` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | *(vacío)* |

## Ejecución

### Con Maven
```bash
# Desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Producción
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Con Docker
```bash
# Construir y ejecutar
docker-compose up --build

# Solo ejecutar
docker-compose up

# Parar servicios
docker-compose down
```

## Verificación de la Conexión

La aplicación estará disponible en: `http://localhost:8080`

Endpoints de prueba:
- GET `/api/categories` - Listar categorías
- GET `/api/products` - Listar productos
- GET `/api/customers` - Listar clientes

## Estructura de la Base de Datos

La base de datos incluye las siguientes tablas:
- `categories` - Categorías de productos
- `customers` - Clientes registrados
- `products` - Productos disponibles
- `pedidos` - Órdenes de compra
- `order_items` - Items de las órdenes
- `payments` - Pagos realizados
- `img` - Imágenes de productos
- `cart_items` - Items del carrito de compras
- `posts` - Posts del blog
- `comments` - Comentarios en posts
- `contact_messages` - Mensajes de contacto
- `chatbot_logs` - Logs del chatbot

## Solución de Problemas

### Error de Conexión a PostgreSQL
1. Verificar que PostgreSQL esté ejecutándose
2. Confirmar las credenciales en `.env`
3. Verificar que la base de datos `health_mood` exista

### Error de Dependencias
```bash
mvn clean install
```

### Logs de la Aplicación
Los logs mostrarán las consultas SQL en modo desarrollo (`show-sql: true`)
