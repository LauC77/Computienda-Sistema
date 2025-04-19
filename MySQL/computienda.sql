-- Base de datos si no existe
CREATE DATABASE IF NOT EXISTS CompuTiendaDB;
USE CompuTiendaDB;

-- Tabla Productos
CREATE TABLE IF NOT EXISTS Productos (
    codigo_producto INT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    cantidad_inventario INT,
    precio_venta DECIMAL(10,2)
);

-- Tabla Clientes
CREATE TABLE IF NOT EXISTS Clientes (
    id_cliente INT PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(200),
    correo VARCHAR(100),
    telefono VARCHAR(20)
);

-- Tabla Compras
CREATE TABLE IF NOT EXISTS Compras (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    codigo_producto INT,
    fecha DATE,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (codigo_producto) REFERENCES Productos(codigo_producto)
);

-- Tabla Facturas
CREATE TABLE IF NOT EXISTS Facturas (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    fecha DATE,
    codigo_producto INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    total DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (codigo_producto) REFERENCES Productos(codigo_producto)
);

-- Inicia la transacción
BEGIN;
INSERT IGNORE INTO Clientes (id_cliente, nombre, direccion, correo, telefono) 
VALUES (1, 'Juan Pérez', 'Calle Falsa 123', 'juan@example.com', '3001234567');

-- El producto con codigo_producto = 101 exista, Si no existe, insertammos el producto
INSERT IGNORE INTO Productos (codigo_producto, nombre, descripcion, cantidad_inventario, precio_venta)
VALUES (101, 'Computador PHP', 'Computador ASUS 8 GB de RAM', 100, 2500000);
-- Inserta en la tabla Compras
INSERT INTO Compras (id_cliente, codigo_producto, fecha, cantidad, precio_unitario)
VALUES (1, 101, CURDATE(), 2, 2500000);
-- Inserta en la tabla Facturas
INSERT INTO Facturas (id_cliente, fecha, codigo_producto, cantidad, precio_unitario, total)
VALUES (1, CURDATE(), 101, 2, 2500000, 5000000);


-- Actualiza la cantidad en inventario
UPDATE Productos
SET cantidad_inventario = cantidad_inventario - 2
WHERE codigo_producto = 101;
-- Confirma la transacción si todo fue exitoso
COMMIT;
-- Si ocurre algo:
-- ROLLBACK;


select * from Productos;
select * from Facturas;
select * from Clientes;
select * from Compras;
