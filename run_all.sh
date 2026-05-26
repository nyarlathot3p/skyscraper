#!/bin/bash

# Directorio base
BASE_DIR=$(pwd)
LOG_DIR="$BASE_DIR/logs"

# Crear carpeta de logs si no existe
mkdir -p "$LOG_DIR"

echo "===================================================="
echo "🚀 Iniciando Microservicios de Skyscraper"
echo "===================================================="

# Verificar MySQL
if ! systemctl is-active --quiet mysql; then
    echo "❌ Error: MySQL no está corriendo. Por favor inicia MySQL antes."
    exit 1
fi
echo "✅ MySQL está activo."

# Lista de microservicios (Carpeta:Puerto:Nombre)
SERVICES=(
    "MS_vuelos:8081:Vuelos"
    "javier-flota:8082:Flota"
    "javier-geaografia:8083:Geografia"
    "javier-sedes:8084:Sedes"
    "javier-puestos:8085:Puestos"
    "MS_reservas:8090:Reservas"
    "clients:9090:Clientes"
    "payments:9091:Pagos"
)

for service in "${SERVICES[@]}"; do
    IFS=":" read -r dir port name <<< "$service"
    
    echo "🔄 Arrancando $name (Puerto $port)..."
    
    cd "$BASE_DIR/$dir" || continue
    
    # Asegurar permisos de ejecución
    chmod +x mvnw 2>/dev/null
    
    # Iniciar en segundo plano
    nohup ./mvnw spring-boot:run > "$LOG_DIR/$dir.log" 2>&1 &
    
    echo "👉 log en: logs/$dir.log"
done

echo "===================================================="
echo "⏳ Esperando a que los servicios inicien..."
echo "Usa 'tail -f logs/[nombre].log' para ver el progreso."
echo "===================================================="

# Función para verificar puerto
check_port() {
    nc -z localhost $1 > /dev/null 2>&1
    return $?
}

# Verificación rápida
sleep 10
echo "📊 Estado actual (Verificando puertos):"
for service in "${SERVICES[@]}"; do
    IFS=":" read -r dir port name <<< "$service"
    if check_port "$port"; then
        echo "✅ $name: UP (Puerto $port)"
    else
        echo "⏳ $name: Iniciando... (Puerto $port)"
    fi
done

echo "===================================================="
echo "¡Listo! Los servicios están cargando en segundo plano."
echo "Puedes revisar el estado final en unos segundos."
