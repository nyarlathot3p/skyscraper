-- SEED DATA FOR SKYSCRAPER (Expanded Domestic Chile)

-- 1. Flota
USE flota;
INSERT INTO aerolinea (id, nombre) VALUES 
(1, 'Skyscraper Air'), 
(2, 'Oceanic Airlines'),
(3, 'Sky Airline'),
(4, 'LATAM Chile'),
(5, 'JetSMART'),
(6, 'Aerocardal'),
(7, 'DAP'),
(8, 'Principal'),
(9, 'Ladeco'),
(10, 'Chilean Jet')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO avion (id, modelo, capacidad, filas, columnas) VALUES 
(1, 'Boeing 737', 150, 25, 6), 
(2, 'Airbus A320', 180, 30, 6),
(3, 'Airbus A321', 220, 37, 6),
(4, 'Boeing 787', 250, 42, 6),
(5, 'Airbus A319', 140, 24, 6),
(6, 'De Havilland Dash 8', 70, 18, 4),
(7, 'ATR 72', 72, 18, 4),
(8, 'Embraer E190', 100, 25, 4),
(9, 'Bombardier CRJ900', 90, 23, 4),
(10, 'Airbus A350', 300, 50, 6)
ON DUPLICATE KEY UPDATE modelo=VALUES(modelo);

-- 2. Geografia
USE geaografia_db;
INSERT INTO regiones (id, nombre) VALUES 
(1, 'Metropolitana'), 
(2, 'Valparaíso'),
(3, 'Antofagasta'),
(4, 'Biobío'),
(5, 'Los Lagos'),
(6, 'Tarapacá'),
(7, 'Coquimbo'),
(8, 'La Araucanía'),
(9, 'Magallanes'),
(10, 'Atacama')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO ciudades (id, nombre, region_id) VALUES 
(1, 'Santiago', 1), 
(2, 'Valparaíso', 2),
(3, 'Antofagasta', 3),
(4, 'Concepción', 4),
(5, 'Puerto Montt', 5),
(6, 'Iquique', 6),
(7, 'La Serena', 7),
(8, 'Temuco', 8),
(9, 'Punta Arenas', 9),
(10, 'Copiapó', 10)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- 3. Sedes (Aeropuertos)
USE sedes_db;
INSERT INTO origene (id, nombre) VALUES 
(1, 'SCL - Santiago'), (2, 'ANF - Antofagasta'), (3, 'CCP - Concepción'), 
(4, 'PMC - Puerto Montt'), (5, 'IQQ - Iquique'), (6, 'LSC - La Serena'),
(7, 'ZCO - Temuco'), (8, 'PUQ - Punta Arenas'), (9, 'CPO - Copiapó'), (10, 'CJC - Calama')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO destino (id, nombre) VALUES 
(1, 'SCL - Santiago'), (2, 'ANF - Antofagasta'), (3, 'CCP - Concepción'), 
(4, 'PMC - Puerto Montt'), (5, 'IQQ - Iquique'), (6, 'LSC - La Serena'),
(7, 'ZCO - Temuco'), (8, 'PUQ - Punta Arenas'), (9, 'CPO - Copiapó'), (10, 'CJC - Calama')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO aeropuerto (id, nombre, ciudad_id, destino_id, origen_id) VALUES 
(1, 'Arturo Merino Benítez', 1, 1, 1),
(2, 'Andrés Sabella', 3, 2, 2),
(3, 'Carriel Sur', 4, 3, 3),
(4, 'El Tepual', 5, 4, 4),
(5, 'Diego Aracena', 6, 5, 5),
(6, 'La Florida', 7, 6, 6),
(7, 'La Araucanía', 8, 7, 7),
(8, 'Presidente Ibáñez', 9, 8, 8),
(9, 'Desierto de Atacama', 10, 9, 9),
(10, 'El Loa', 3, 10, 10)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- 4. Puestos
USE puestos_db;
INSERT INTO clase (id, nombre) VALUES 
(1, 'ECONOMY'), 
(2, 'BUSINESS'), 
(3, 'FIRST') 
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO posicion (id, nombre) VALUES 
(1, 'VENTANILLA'), 
(2, 'PASILLO'), 
(3, 'CENTRO') 
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO asiento (id, fila, letra, disponible, id_avion, clase_id, posicion_id) VALUES 
(1, 1, 'A', 1, 1, 3, 1), 
(2, 1, 'B', 1, 1, 3, 3),
(3, 1, 'C', 1, 1, 3, 2),
(4, 1, 'D', 1, 1, 3, 2),
(5, 1, 'E', 1, 1, 3, 3),
(6, 1, 'F', 1, 1, 3, 1),
(7, 10, 'A', 1, 1, 1, 1),
(8, 10, 'B', 1, 1, 1, 3),
(9, 10, 'C', 1, 1, 1, 2),
(10, 10, 'D', 1, 1, 1, 2)
ON DUPLICATE KEY UPDATE letra=VALUES(letra);

-- 5. MS_vuelos
USE sistema_vuelos;
INSERT INTO estado_vuelo (id, nombre) VALUES 
(1, 'PROGRAMADO'), 
(2, 'A TIEMPO'), 
(3, 'RETRASADO'), 
(4, 'CANCELADO'),
(5, 'EN VUELO'),
(6, 'ATERRIZADO')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO vuelo (id, id_aerolinea, id_aeropuerto_destino, id_aeropuerto_origen, id_avion, id_estado, fecha, hora_partida, duracion_estimada_minutos) VALUES 
(1, 1, 2, 1, 1, 1, '2026-06-01', '2026-06-01 10:00:00', 120),
(2, 3, 3, 1, 2, 2, '2026-06-01', '2026-06-01 12:30:00', 60),
(3, 4, 4, 1, 3, 1, '2026-06-02', '2026-06-02 08:00:00', 105),
(4, 5, 5, 1, 4, 2, '2026-06-02', '2026-06-02 15:45:00', 145),
(5, 3, 1, 2, 2, 1, '2026-06-03', '2026-06-03 11:20:00', 120),
(6, 4, 1, 3, 3, 2, '2026-06-03', '2026-06-03 14:10:00', 60),
(7, 5, 1, 4, 4, 1, '2026-06-04', '2026-06-04 09:30:00', 105),
(8, 3, 6, 1, 2, 3, '2026-06-04', '2026-06-04 18:00:00', 60),
(9, 4, 7, 1, 3, 1, '2026-06-05', '2026-06-05 07:15:00', 80),
(10, 5, 8, 1, 4, 2, '2026-06-05', '2026-06-05 21:00:00', 210)
ON DUPLICATE KEY UPDATE id_estado=VALUES(id_estado);

INSERT INTO precio (id, id_asiento, precio_actual, fecha_captura, link_externo) VALUES
(1, 1, 45000.00, '2026-05-25', 'http://skyscraper/offer1'),
(2, 2, 45000.00, '2026-05-25', 'http://skyscraper/offer2'),
(3, 3, 45000.00, '2026-05-25', 'http://skyscraper/offer3'),
(4, 7, 25000.00, '2026-05-25', 'http://skyscraper/offer7'),
(5, 8, 25000.00, '2026-05-25', 'http://skyscraper/offer8'),
(6, 9, 25000.00, '2026-05-25', 'http://skyscraper/offer9'),
(7, 10, 25000.00, '2026-05-25', 'http://skyscraper/offer10')
ON DUPLICATE KEY UPDATE precio_actual=VALUES(precio_actual);

-- 6. Clients
USE clients_db;
INSERT INTO user (id, name, lastname, secondlastname, address, birthdate, dv, email, password, phonenumber, run, username) VALUES 
(1, 'Admin', 'Sky', 'Walker', 'Street 1', '1990-01-01', 'k', 'admin@sky.com', 'admin123', '9999999', 12345678, 'admin'),
(2, 'Javier', 'Guzman', 'Sedes', 'Avenida Los Alerces 123', '1995-05-15', '0', 'javier@test.com', 'pass123', '98888888', 18765432, 'javierg'),
(3, 'Camila', 'Rojas', 'Perez', 'Calle Falsa 123', '1992-10-20', '7', 'camila@test.com', 'pass456', '97777777', 15123456, 'camilar'),
(4, 'Pedro', 'Picapiedra', 'Roca', 'Piedra Dura 456', '1985-03-10', '1', 'pedro@test.com', 'yabadabadoo', '96666666', 12333444, 'pedrop')
ON DUPLICATE KEY UPDATE email=VALUES(email);

INSERT INTO passenger (id, first_name, paternal_last_name, maternal_last_name, run, dv, email, phone, birth_date, second_name) VALUES 
(1, 'Juan', 'Perez', 'Garcia', 12345678, '9', 'juan@perez.com', '999999', '1990-01-01', 'Antonio'),
(2, 'Maria', 'Soto', 'Lara', 19876543, '2', 'maria@soto.com', '988877', '1994-02-12', 'Luisa'),
(3, 'Diego', 'Silva', 'Gomez', 17654321, 'k', 'diego@silva.com', '977766', '1998-11-30', 'Andrés'),
(4, 'Elena', 'Torres', 'Diaz', 16543210, '5', 'elena@torres.com', '966655', '1991-07-25', 'Sofia')
ON DUPLICATE KEY UPDATE email=VALUES(email);

-- 7. MS_reservas
USE sistema_reservas;
INSERT INTO ticket (id, id_comprador, id_pasajero, id_asiento_vuelo, id_transaccion) VALUES
(1, 2, 1, 1, 1),
(2, 3, 2, 2, 2)
ON DUPLICATE KEY UPDATE id_comprador=VALUES(id_comprador);

-- 8. Payments
USE payments_db;
INSERT INTO payment_method (id, payment_method) VALUES
(1, 'CREDIT_CARD'),
(2, 'DEBIT_CARD'),
(3, 'TRANSBANK'),
(4, 'PAYPAL'),
(5, 'CASH'),
(6, 'TRANSFER')
ON DUPLICATE KEY UPDATE payment_method=VALUES(payment_method);

INSERT INTO transaction (id, transaction_value, transaction_date, id_payment_method) VALUES
(1, 45000, '2026-05-25 10:00:00', 1),
(2, 45000, '2026-05-25 10:05:00', 2)
ON DUPLICATE KEY UPDATE transaction_value=VALUES(transaction_value);
