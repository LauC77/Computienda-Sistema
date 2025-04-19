package CompuTienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Obtener la conexión a la base de datos
        Connection con = ConexionBD.getConnection();

        // Si la conexión es exitosa, puedes proceder a agregar datos
        if (con != null) {
            try {
                // Agregar un cliente
                agregarCliente(con);

                // Agregar un producto
                agregarProducto(con);

                // Llamar al método que realiza las transacciones
                Transacciones.realizarTransaccion();
            } catch (SQLException e) {
                System.out.println("Error al agregar datos: " + e.getMessage());
            }
        }

        // No olvides cerrar la conexión cuando termines
        ConexionBD.closeConnection(con);
    }

    // Método para agregar un cliente
    private static void agregarCliente(Connection con) throws SQLException {
        String sql = "INSERT INTO Clientes (id_cliente, nombre, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, 3); // ID del cliente
            ps.setString(2, "Laura Sánchez"); // Nombre del cliente
            ps.setString(3, "Calle Prueba 456"); // Dirección
            ps.setString(4, "LauraSanchez@gmail.com"); // Correo
            ps.setString(5, "3114567384"); // Teléfono
            ps.executeUpdate();
            System.out.println("Cliente agregado correctamente.");
        }
    }

    // Método para agregar un producto
    private static void agregarProducto(Connection con) throws SQLException {
        String sql = "INSERT INTO Productos (codigo_producto, nombre, descripcion, cantidad_inventario, precio_venta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, 1002); // Código del producto
            ps.setString(2, "Laptop ASUS"); // Nombre del producto
            ps.setString(3, "Laptop ASUS con 8GB RAM y 1TB SSD"); // Descripción
            ps.setInt(4, 10); // Cantidad en inventario
            ps.setDouble(5, 2000000.00); // Precio de venta
            ps.executeUpdate();
            System.out.println("Producto agregado correctamente.");
        }
    }
}


