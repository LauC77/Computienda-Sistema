package CompuTienda;

import CompuTienda.ConexionBD;
import java.sql.*;

public class Transacciones {

    public static void realizarTransaccion() {
        Connection con = ConexionBD.getConnection();
        PreparedStatement psCompra = null;
        PreparedStatement psInventario = null;
        PreparedStatement psFactura = null;
        Statement stmt = null;
        PreparedStatement psVerificarInventario = null;
        ResultSet rsInventario = null;

        try {
            con.setAutoCommit(false); // BEGIN

            // Verificar si hay suficiente inventario
            psVerificarInventario = con.prepareStatement(
                "SELECT cantidad_inventario FROM Productos WHERE codigo_producto = ?");
            psVerificarInventario.setInt(1, 1002); // Código del producto
            rsInventario = psVerificarInventario.executeQuery();

            int cantidadInventario = 0;
            if (rsInventario.next()) {
                cantidadInventario = rsInventario.getInt("cantidad_inventario");
            }

            int cantidadCompra = 2; // La cantidad que el cliente quiere comprar

            if (cantidadCompra > cantidadInventario) {
                System.out.println("❌ No hay suficiente inventario para completar la compra.");
                return; // Detener la transacción si no hay suficiente inventario
            }

            // Insertar compra
            psCompra = con.prepareStatement(
                "INSERT INTO Compras (id_cliente, codigo_producto, fecha, cantidad, precio_unitario) VALUES (?, ?, CURDATE(), ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            psCompra.setInt(1, 3); // ID cliente
            psCompra.setInt(2, 1002); // Código producto
            psCompra.setInt(3, cantidadCompra); // Cantidad
            psCompra.setDouble(4, 2000000.00); // Precio unitario
            psCompra.executeUpdate();
            
            ResultSet rsCompra = psCompra.getGeneratedKeys();
            int idCompra = 0;
            if (rsCompra.next()) {
                idCompra = rsCompra.getInt(1); // Obtener ID de la compra
            }

            // Actualizar inventario
            psInventario = con.prepareStatement(
                "UPDATE Productos SET cantidad_inventario = cantidad_inventario - ? WHERE codigo_producto = ?");
            psInventario.setInt(1, cantidadCompra);
            psInventario.setInt(2, 1002);
            psInventario.executeUpdate();

            // Insertar factura
            psFactura = con.prepareStatement(
                "INSERT INTO Facturas (id_cliente, fecha, codigo_producto, cantidad, precio_unitario, total) VALUES (?, CURDATE(), ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            psFactura.setInt(1, 3); // ID cliente
            psFactura.setInt(2, 1002); // Código producto
            psFactura.setInt(3, cantidadCompra); // Cantidad
            psFactura.setDouble(4, 2000000.00); // Precio unitario
            psFactura.setDouble(5, cantidadCompra * 2000000.00); // Total
            psFactura.executeUpdate();
            
            // Obtener el ID de la factura generada
            ResultSet rsFactura = psFactura.getGeneratedKeys();
            int idFactura = 0;
            if (rsFactura.next()) {
                idFactura = rsFactura.getInt(1); // Obtener ID de la factura
            }

            // Mostrar factura
            System.out.println("Factura generada:");
            System.out.println("ID Factura: " + idFactura);
            System.out.println("Cliente ID: 3");
            System.out.println("Producto Código: 1002");
            System.out.println("Cantidad: " + cantidadCompra);
            System.out.println("Precio Unitario: 2000000.00");
            System.out.println("Total: " + (cantidadCompra * 2000000.00));

            con.commit(); // COMMIT
            System.out.println("✅ Transacción completada correctamente.");

        } catch (Exception e) {
            try {
                con.rollback(); // ROLLBACK
                System.out.println("❌ Error, transacción cancelada: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("⚠️ Error al hacer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (psCompra != null) psCompra.close();
                if (psInventario != null) psInventario.close();
                if (psFactura != null) psFactura.close();
                if (psVerificarInventario != null) psVerificarInventario.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("⚠️ Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }
}
