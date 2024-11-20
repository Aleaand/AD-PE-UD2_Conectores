package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alejandra
 */
public class ConexionRemoto {

    private static String activeDatabase = "";
    // XML
    private static String host;
    private static int port;
    private static String user;
    private static String pass;

    /**
     * Configura los parámetros de conexión usando los valores de un objeto
     * {@link Configuracion}. Estos parámetros incluyen el host, el puerto, el
     * usuario y la contraseña necesarios para la conexión a la base de datos.
     *
     * @param config El objeto de configuración que contiene los valores de
     * conexión (host, puerto, usuario, contraseña)
     */
    public static void configurarConexion(Configuracion config) {
        host = config.getHost();
        port = config.getPort();
        user = config.getUser();
        pass = config.getPass();
    }

    /**
     * Obtiene una conexión a la base de datos activa (MySQL o PostgreSQL).
     *
     * Este método verifica el tipo de base de datos activa y establece la
     * conexión correspondiente a través del DriverManager.
     *
     * @return Una conexión activa a la base de datos especificada
     * @throws SQLException Si ocurre un error al intentar conectar con la base
     * de datos, como una configuración incorrecta o un fallo en la conexión
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        String url = "";
        switch (activeDatabase) {
            case "MySQL":
                url = "jdbc:mysql://" + host + ":" + port + "/gamedb";
                connection = DriverManager.getConnection(url, user, pass);
                break;
            case "PostgreSQL":
                url = "jdbc:postgresql://" + host + ":" + port + "/gamedb";
                connection = DriverManager.getConnection(url, user, pass);
                break;
            default:
                throw new SQLException("Base de datos no especificada correctamente.");
        }

        return connection;
    }

    /**
     * Establece la base de datos activa a usar para las conexiones.
     *
     * Este método permite seleccionar el tipo de base de datos (MySQL o
     * PostgreSQL) que será utilizado en las futuras conexiones. El valor debe
     * ser uno de los siguientes: "MySQL" o "PostgreSQL".
     *
     * @param dbType El tipo de base de datos a usar ("MySQL" o "PostgreSQL")
     * @throws IllegalArgumentException Si el tipo de base de datos no es
     * válido, es decir, no es "MySQL" ni "PostgreSQL"
     */
    public static void setActiveDatabase(String dbType) {
        if (dbType.equalsIgnoreCase("MySQL") || dbType.equalsIgnoreCase("PostgreSQL")) {
            activeDatabase = dbType;
        } else {
            throw new IllegalArgumentException("Tipo de base de datos no soportado. Usa 'MySQL' o 'PostgreSQL'.");
        }
    }

    /**
     * Obtiene el tipo de base de datos activa.
     *
     * Este método devuelve el tipo de base de datos que está actualmente
     * seleccionado como activo (MySQL o PostgreSQL).
     *
     * @return El tipo de base de datos activa, como "MySQL" o "PostgreSQL"
     */
    public static String getActiveDatabase() {
        return activeDatabase;
    }
}
