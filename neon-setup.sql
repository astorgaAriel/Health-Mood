-- Script para configurar Health Mood en Neon PostgreSQL
-- Ejecutar este script en la consola SQL de Neon

-- Verificar conexión
SELECT version();

-- Crear extensiones necesarias (si no existen)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Verificar que las tablas principales existan
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
AND table_type = 'BASE TABLE'
ORDER BY table_name;

-- Si las tablas no existen, ejecutar schema.sql y data.sql
-- (Copiar y pegar el contenido de esos archivos aquí)

-- Verificar datos de prueba
SELECT 'categories' as tabla, COUNT(*) as registros FROM categories
UNION ALL
SELECT 'customers', COUNT(*) FROM customers
UNION ALL
SELECT 'products', COUNT(*) FROM products
UNION ALL
SELECT 'pedidos', COUNT(*) FROM pedidos
ORDER BY tabla;
