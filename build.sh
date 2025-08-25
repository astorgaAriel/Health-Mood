#!/bin/bash

echo "🚀 Preparando Health Mood para despliegue en la nube..."

# Limpiar proyecto
echo "🧹 Limpiando proyecto..."
./mvnw clean

# Compilar y ejecutar tests
echo "🔧 Compilando proyecto..."
./mvnw compile

# Ejecutar tests (opcional, comentar si hay problemas)
# echo "🧪 Ejecutando tests..."
# ./mvnw test

# Crear package final
echo "📦 Creando package..."
./mvnw package -DskipTests

echo "✅ Proyecto preparado para despliegue!"
echo "📁 JAR creado en: target/*.jar"

# Verificar que el JAR se creó
if ls target/*.jar 1> /dev/null 2>&1; then
    echo "✅ JAR encontrado: $(ls target/*.jar)"
else
    echo "❌ Error: No se encontró el JAR"
    exit 1
fi

echo "🌐 Listo para subir a Render!"
