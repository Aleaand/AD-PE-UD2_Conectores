package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alejandra
 */
public class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:localGamedb.db";

    /**
     * Establece y devuelve una conexión a la base de datos SQLite.
     *
     * Este método carga el controlador JDBC de SQLite y se conecta a la base de
     * datos especificada en la constante {@link #URL}. Además, verifica la
     * existencia de las tablas necesarias y las crea si no existen.
     *
     * @return Conexión a la base de datos SQLite
     * @throws SQLException Si ocurre un error al intentar conectar con la base
     * de datos o al ejecutar las consultas para crear las tablas.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el controlador JDBC de SQLite
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(URL);

            // Verificar y crear tablas si no existen
            inicializarBaseDeDatos(connection);

            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("No se encontró el controlador de SQLite.", e);
        }
    }

    /**
     * Inicializa la base de datos asegurándose de que las tablas necesarias
     * existan. Si alguna tabla no está presente, se crea utilizando las
     * sentencias SQL definidas.
     *
     * @param connection La conexión activa a la base de datos
     * @throws SQLException Si ocurre un error al ejecutar las sentencias SQL
     */
    private static void inicializarBaseDeDatos(Connection connection) throws SQLException {
        String crearTablaEstadoPartidas = """
            CREATE TABLE IF NOT EXISTS EstadoPartidasJugadorLocal (
                session_id INTEGER,
                game_id INTEGER NOT NULL,
                player_id INTEGER NOT NULL,
                experience INTEGER DEFAULT 0,
                life_level INTEGER DEFAULT 0,
                coins INTEGER DEFAULT 0,
                session_count INTEGER DEFAULT 1,
                session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        String crearTablaConfiguracionJugador = """
            CREATE TABLE IF NOT EXISTS ConfiguracionJugador (
                config_id INTEGER PRIMARY KEY AUTOINCREMENT,
                sound_enabled BOOLEAN DEFAULT 1,
                resolution TEXT,
                language TEXT
            );
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(crearTablaEstadoPartidas);
            statement.execute(crearTablaConfiguracionJugador);
        }
    }
}
