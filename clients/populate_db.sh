#!/bin/bash

# ============================================================================
# Database Population Script
# This script populates the clients_db database with test data
# Usage: ./populate_db.sh
# ============================================================================

set -e  # Exit on error

# Configuration
DB_HOST="localhost"
DB_PORT="3306"
DB_NAME="clients_db"
DB_USER="chef"
DB_PASSWORD="!@#!@#31415"

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}========================================${NC}"
echo -e "${YELLOW}Database Population Script${NC}"
echo -e "${YELLOW}========================================${NC}"

# Check if MySQL is running
echo -e "\n${YELLOW}Checking MySQL connection...${NC}"
if ! command -v mysql &> /dev/null; then
    echo -e "${RED}Error: mysql command not found. Please install MySQL client.${NC}"
    exit 1
fi

# Test connection
if ! mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" -e "USE $DB_NAME; SELECT 1;" > /dev/null 2>&1; then
    echo -e "${RED}Error: Cannot connect to database at $DB_HOST:$DB_PORT${NC}"
    echo -e "${RED}Please ensure MySQL is running and credentials are correct.${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Database connection successful${NC}"

# Run the population script
echo -e "\n${YELLOW}Populating database...${NC}"

mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" << 'EOF'

-- Insert test users
INSERT INTO user (address, birthdate, dv, email, middlename, name, lastname, secondlastname, password, phonenumber, run, username) VALUES
('Calle Principal 123, Santiago', '1990-05-15', '5', 'juan.perez@email.com', 'Carlos', 'Juan', 'Perez', 'Garcia', '$2a$10$slYQmyNdGzin7olVgsqFUOH1c4OU39fN1qXK9TLKkBgFQHzQpCNBW', '+56912345678', 19123456, 'juan_perez'),
('Avenida Secundaria 456, Valparaíso', '1988-09-22', '7', 'maria.rodriguez@email.com', 'Isabel', 'Maria', 'Rodriguez', 'Martinez', '$2a$10$slYQmyNdGzin7olVgsqFUOH1c4OU39fN1qXK9TLKkBgFQHzQpCNBW', '+56987654321', 18987654, 'maria_rodriguez'),
('Pasaje Norte 789, Concepción', '1992-03-10', '2', 'carlos.lopez@email.com', 'Fernando', 'Carlos', 'Lopez', 'Diaz', '$2a$10$slYQmyNdGzin7olVgsqFUOH1c4OU39fN1qXK9TLKkBgFQHzQpCNBW', '+56911223344', 20234567, 'carlos_lopez'),
('Calle Este 321, Puerto Varas', '1995-07-18', '9', 'sofia.sanchez@email.com', 'Alejandra', 'Sofia', 'Sanchez', 'Flores', '$2a$10$slYQmyNdGzin7olVgsqFUOH1c4OU39fN1qXK9TLKkBgFQHzQpCNBW', '+56955443322', 21345678, 'sofia_sanchez');

-- Insert test passengers
INSERT INTO passenger (birth_date, dv, email, first_name, maternal_last_name, paternal_last_name, phone, run, second_name) VALUES
('1985-02-14', '3', 'traveler.a@email.com', 'Roberto', 'Vega', 'Morales', '+56956789012', 19456789, 'Andres'),
('1991-11-30', '6', 'traveler.b@email.com', 'Patricia', 'Gutierrez', 'Silva', '+56987123456', 20567890, 'Marcela'),
('1989-06-07', '1', 'traveler.c@email.com', 'Diego', 'Campos', 'Pena', '+56912345670', 21678901, 'Rodrigo'),
('1993-08-25', '8', 'traveler.d@email.com', 'Amanda', 'Torres', 'Nunez', '+56943210987', 22789012, 'Valentina'),
('1987-01-12', '4', 'traveler.e@email.com', 'Miguel', 'Castro', 'Rojas', '+56965432109', 23890123, 'Eduardo');

-- Insert test reviews
INSERT INTO review (date, description, id_aeroline, id_usuario, stars) VALUES
('2026-05-10', 'Excelente servicio, personal muy amable y profesional. Las comidas fueron deliciosas.', 1, 1, 5),
('2026-05-12', 'Buen servicio, pero los asientos podrían ser más cómodos. El vuelo llegó con 15 minutos de retraso.', 2, 1, 3),
('2026-05-08', 'El mejor vuelo que he tenido. Personal atento, comida deliciosa y puntualidad excelente.', 1, 2, 5),
('2026-05-15', 'Servicio básico. El personal fue educado pero faltó en la atención. Recomendable para presupuestos limitados.', 3, 2, 3),
('2026-05-05', 'Tuve problemas con mi equipaje. El personal no fue muy helpful al respecto. No recomendado.', 2, 3, 2),
('2026-05-14', 'Buena experiencia general. Los precios son competitivos y el servicio es adecuado.', 4, 3, 4),
('2026-05-11', 'Increíble. Todo perfecto. Volvería a volar con esta aerolínea sin dudarlo.', 1, 4, 5),
('2026-05-13', 'Decepcionante. Retrasos frecuentes y personal desorganizado. Mala experiencia.', 3, 4, 1);
EOF

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Database populated successfully!${NC}"
else
    echo -e "${RED}✗ Error populating database${NC}"
    exit 1
fi

# Show summary
echo -e "\n${YELLOW}Summary:${NC}"
mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" -e "
SELECT 
    (SELECT COUNT(*) FROM user) as 'Total Users',
    (SELECT COUNT(*) FROM passenger) as 'Total Passengers',
    (SELECT COUNT(*) FROM review) as 'Total Reviews';"

echo -e "\n${YELLOW}Test Users (all with password encoded):${NC}"
echo -e "  1. juan_perez (RUN: 19123456)"
echo -e "  2. maria_rodriguez (RUN: 18987654)"
echo -e "  3. carlos_lopez (RUN: 20234567)"
echo -e "  4. sofia_sanchez (RUN: 21345678)"

echo -e "\n${YELLOW}========================================${NC}"
echo -e "${GREEN}Ready for testing!${NC}"
echo -e "${YELLOW}========================================${NC}"
