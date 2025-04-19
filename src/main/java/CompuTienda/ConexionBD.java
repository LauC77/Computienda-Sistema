package CompuTienda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Método para obtener la conexión
    public static Connection getConnection() {
        Connection con = null;
        try {
            // Registrar el driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Intentar conectar con la base de datos
            System.out.println("Intentando conectar...");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/computiendadb", "root", "Laura307.");

            // Verificar la conexión
            if (con != null) {
                System.out.println("¡Conexión exitosa!");
            } else {
                System.out.println("No se pudo establecer conexión.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();  // Esto imprimirá más detalles del error
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado - " + e.getMessage());
        }
        return con;
    }

    // Método para cerrar la conexión (opcional)
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
