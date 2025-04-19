
package CompuTienda;

import java.sql.*;

public class RegistrarCliente {

    public static void registrarCliente(int idCliente, String nombre, String direccion, String correo, String telefono) {
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(
                "INSERT INTO Clientes (id_cliente, nombre, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, idCliente);
            ps.setString(2, nombre);
            ps.setString(3, direccion);
            ps.setString(4, correo);
            ps.setString(5, telefono);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("✅ Cliente registrado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar cliente: " + e.getMessage());
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

