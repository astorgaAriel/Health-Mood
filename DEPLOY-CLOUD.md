# Health Mood - Despliegue en la Nube

## 🌐 Arquitectura de la Nube

- **Backend**: Render (https://health-mood.onrender.com/)
- **Base de Datos**: Neon PostgreSQL (https://console.neon.tech/app/projects/snowy-firefly-85655141)
- **Frontend**: Vercel (health-mood-evmqnv399-ariels-projects-08c7f509.vercel.app)

## 🚀 Configuración de Render

### 1. Variables de Entorno en Render

En el dashboard de Render (https://dashboard.render.com), configura estas variables de entorno:

```env
# Base de datos Neon
DATABASE_URL=postgresql://[username]:[password]@[host]/[database]
DB_USERNAME=[tu_usuario_neon]
DB_PASSWORD=[tu_password_neon]

# Configuración de la aplicación
SPRING_PROFILES_ACTIVE=cloud
JAVA_TOOL_OPTIONS=-Xmx512m -XX:+UseContainerSupport

# Puerto (Render lo configura automáticamente)
PORT=8080
```

### 2. Configuración del Servicio en Render

- **Build Command**: `./mvnw clean package -DskipTests`
- **Start Command**: `java -Dserver.port=$PORT -jar target/*.jar`
- **Health Check Path**: `/actuator/health`
- **Environment**: `java`
- **Plan**: Free (para empezar)

## 🗄️ Configuración de Neon PostgreSQL

### 1. Obtener Credenciales de Neon

En tu proyecto de Neon (https://console.neon.tech/app/projects/snowy-firefly-85655141):

1. Ve a la sección "Connection Details"
2. Copia la connection string que se ve así:
   ```
   postgresql://[username]:[password]@[host]/[database]?sslmode=require
   ```

### 2. Ejecutar Scripts de Base de Datos

Para cargar el esquema y datos iniciales en Neon:

```bash
# Conectarse a Neon
psql "postgresql://[username]:[password]@[host]/[database]?sslmode=require"

# Ejecutar esquema
\i src/main/resources/schema.sql

# Ejecutar datos de prueba
\i src/main/resources/data.sql
```

O usar un cliente como pgAdmin con la connection string de Neon.

## 🎨 Configuración de Vercel

### Variables de Entorno en Vercel

En el dashboard de Vercel, configura:

```env
NEXT_PUBLIC_API_URL=https://health-mood.onrender.com/api
NEXT_PUBLIC_BACKEND_URL=https://health-mood.onrender.com
```

### Configuración de CORS

El backend ya está configurado para permitir requests desde:
- `https://health-mood-evmqnv399-ariels-projects-08c7f509.vercel.app`
- `https://*.vercel.app` (cualquier subdominio de Vercel)
- `https://health-mood.onrender.com`

## 🔧 Comandos de Despliegue

### Para Render (automático con Git)

1. Conecta tu repositorio de GitHub a Render
2. Render detectará automáticamente el `render.yaml`
3. Cada push a la rama principal desplegará automáticamente

### Para desarrollo local con configuración de nube

```bash
# Usar perfil de nube localmente
export DATABASE_URL="postgresql://[neon-connection-string]"
export DB_USERNAME="[neon-username]"
export DB_PASSWORD="[neon-password]"

mvn spring-boot:run -Dspring-boot.run.profiles=cloud
```

## 📊 Monitoreo y Health Checks

### Endpoints de Monitoreo

- **Health Check**: `https://health-mood.onrender.com/actuator/health`
- **Info**: `https://health-mood.onrender.com/actuator/info`

### Logs en Render

Los logs estarán disponibles en el dashboard de Render para debugging.

## 🔐 Seguridad

### Headers de Seguridad Configurados

- CORS configurado para Vercel y Render
- JWT para autenticación
- Headers permitidos: Authorization, Content-Type
- Métodos HTTP: GET, POST, PUT, DELETE, OPTIONS, PATCH

### Endpoints Públicos

- `/api/auth/**` - Autenticación
- `/api/public/**` - Recursos públicos
- `GET /api/products/**` - Consulta de productos
- `GET /api/images/**` - Consulta de imágenes
- `GET /api/posts/**` - Consulta de posts del blog
- `/actuator/health` - Health check

## 🚨 Solución de Problemas

### Error de Conexión a Base de Datos

1. Verificar que las variables de entorno estén correctas en Render
2. Confirmar que la connection string de Neon sea válida
3. Verificar que la base de datos esté activa en Neon

### Error de CORS en el Frontend

1. Verificar que la URL del frontend esté en la configuración de CORS
2. Confirmar que el backend esté respondiendo en Render
3. Verificar headers en las requests del frontend

### Error de Despliegue en Render

1. Revisar los logs de build en Render
2. Verificar que todas las dependencias estén en el `pom.xml`
3. Confirmar que Java 21 esté configurado correctamente

## 📝 Próximos Pasos

1. **Configurar el dominio personalizado** en Render y Vercel
2. **Configurar SSL** (automático en ambas plataformas)
3. **Configurar monitoreo** avanzado
4. **Optimizar performance** con caching
5. **Configurar CI/CD** más avanzado

## 🔄 Flujo de Desarrollo

1. **Desarrollo local**: Usar perfil `local` con PostgreSQL local
2. **Testing**: Usar perfil `cloud` con base de datos Neon
3. **Producción**: Despliegue automático en Render con perfil `cloud`
