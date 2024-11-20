package DAOs;

import Conexion.ConexionRemoto;
import java.sql.SQLException;

/**
 *
 * @author Alejandra
 */
public class DAOFactory {

    /**
     * Obtiene la instancia del DAO adecuado para la base de datos activa.
     *
     * @return Una instancia de {@link IDAORemoto} que depende de la base de
     * datos activa.
     * @throws IllegalArgumentException Si la base de datos activa no es MySQL
     * ni PostgreSQL.
     * @throws SQLException Si hay un problema al obtener la conexión a la base
     * de datos.
     */
    public static IDAORemoto getDAO() throws SQLException {
        String activeDatabase = ConexionRemoto.getActiveDatabase();

        if (activeDatabase == null || activeDatabase.isEmpty()) {
            throw new IllegalArgumentException("La base de datos no está configurada.");
        }

        if ("MySQL".equalsIgnoreCase(activeDatabase)) {
            return new DAORemotoMySQL();
        } else if ("PostgreSQL".equalsIgnoreCase(activeDatabase)) {
            return new DAORemotoPostgreSQL();
        } else {
            throw new IllegalArgumentException("Base de datos no soportada: " + activeDatabase);
        }
    }
}
