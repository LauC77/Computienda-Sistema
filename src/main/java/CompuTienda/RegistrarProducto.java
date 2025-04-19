package CompuTienda;

import java.sql.*;

public class RegistrarProducto {

    public static void registrarProducto(int codigoProducto, String nombre, String descripcion, int cantidad, double precioVenta) {
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(
                "INSERT INTO Productos (codigo_producto, nombre, descripcion, cantidad_inventario, precio_venta) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, codigoProducto);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, cantidad);
            ps.setDouble(5, precioVenta);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("✅ Producto registrado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar producto: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("⚠️ Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }
}
