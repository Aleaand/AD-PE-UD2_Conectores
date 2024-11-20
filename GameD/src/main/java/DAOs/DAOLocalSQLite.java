/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionSQLite;
import Modelo.ConfiguracionJugador;
import Modelo.Partida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandra
 */
public class DAOLocalSQLite {

    public DAOLocalSQLite() {
    }

    /**
     * Inserta una nueva configuración del jugador en la base de datos.
     *
     * @param configuracion el objeto ConfiguracionJugador que contiene los
     * datos a insertar.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public void insertarConfiguracion(ConfiguracionJugador configuracion) throws SQLException {
        String consulta = "INSERT INTO ConfiguracionJugador (sound_enabled, resolution, language) VALUES (?, ?, ?)";

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {

            stmt.setBoolean(1, configuracion.isSoundEnabled());
            stmt.setString(2, configuracion.getResolution());
            stmt.setString(3, configuracion.getLanguage());

            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza los datos de una configuración del jugador en la base de datos.
     *
     * @param configuracion el objeto ConfiguracionJugador que contiene los
     * datos actualizados.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public void actualizarConfiguracion(ConfiguracionJugador configuracion) throws SQLException {
        String consulta = "UPDATE ConfiguracionJugador SET sound_enabled = ?, resolution = ?, language = ? WHERE config_id = ?";

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {

            stmt.setBoolean(1, configuracion.isSoundEnabled());
            stmt.setString(2, configuracion.getResolution());
            stmt.setString(3, configuracion.getLanguage());
            stmt.setInt(4, configuracion.getConfigId());

            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene una configuración específica del jugador basada en su ID.
     *
     * @param configId el ID de la configuración que se desea obtener.
     * @return un objeto ConfiguracionJugador con los datos de la configuración
     * o null si no se encuentra.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public ConfiguracionJugador obtenerConfiguracionPorId(int configId) throws SQLException {
        String consulta = "SELECT * FROM ConfiguracionJugador WHERE config_id = ?";
        ConfiguracionJugador configuracion = null;

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {

            stmt.setInt(1, configId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    configuracion = new ConfiguracionJugador();
                    configuracion.setConfigId(rs.getInt("config_id"));
                    configuracion.setSoundEnabled(rs.getBoolean("sound_enabled"));
                    configuracion.setResolution(rs.getString("resolution"));
                    configuracion.setLanguage(rs.getString("language"));
                }
            }
        }

        return configuracion;
    }

    /**
     * Elimina una configuración específica del jugador de la base de datos.
     *
     * @param configId el ID de la configuración que se desea eliminar.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public void eliminarConfiguracion(int configId) throws SQLException {
        String consulta = "DELETE FROM ConfiguracionJugador WHERE config_id = ?";

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {

            stmt.setInt(1, configId);
            stmt.executeUpdate();
        }
    }

    /**
     * Inserta una nueva partida en la tabla EstadoPartidasJugadorLocal si no
     * existe. Y si ya existe solo la actualiza(Para no tener problemas en la
     * sincornización)
     *
     * @param partida el objeto Partida que contiene los datos a insertar.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public void insertarEstadoPartidaJugadorLocal(Partida partida) throws SQLException {
        // Verificar si la partida ya existe
        String consultaExistencia = "SELECT COUNT(*) FROM EstadoPartidasJugadorLocal "
                + "WHERE game_id = ? AND player_id = ?";

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmtVerificar = connection.prepareStatement(consultaExistencia)) {

            stmtVerificar.setInt(1, partida.getGame_id());
            stmtVerificar.setInt(2, partida.getPlayer_id());

            try (ResultSet rs = stmtVerificar.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Si la partida existe, actualizarla
                    String consultaActualizar = "UPDATE EstadoPartidasJugadorLocal "
                            + "SET experience = ?, life_level = ?, coins = ?, session_date = CURRENT_TIMESTAMP "
                            + "WHERE game_id = ? AND player_id = ?";

                    try (PreparedStatement stmtActualizar = connection.prepareStatement(consultaActualizar)) {
                        stmtActualizar.setInt(1, partida.getExperience());
                        stmtActualizar.setInt(2, partida.getLife_level());
                        stmtActualizar.setInt(3, partida.getCoins());
                        stmtActualizar.setInt(4, partida.getGame_id());
                        stmtActualizar.setInt(5, partida.getPlayer_id());

                        stmtActualizar.executeUpdate();
                    }
                } else {
                    // Si la partida no existe, insertar una nueva partida con session_date = NOW()
                    String consultaInsertar = "INSERT INTO EstadoPartidasJugadorLocal (game_id, player_id, experience, life_level, coins, session_date) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement stmtInsertar = connection.prepareStatement(consultaInsertar)) {
                        stmtInsertar.setInt(1, partida.getGame_id());
                        stmtInsertar.setInt(2, partida.getPlayer_id());
                        stmtInsertar.setInt(3, partida.getExperience());
                        stmtInsertar.setInt(4, partida.getLife_level());
                        stmtInsertar.setInt(5, partida.getCoins());
                        stmtInsertar.setTimestamp(6, partida.getSession_date());
                        stmtInsertar.executeUpdate();
                    }
                }
            }
        }
    }

    /**
     * Inserta o actualiza un estado de partida del jugador en la base de datos
     * local. Si el estado de partida con el mismo `session_id`, `game_id` y
     * `player_id` ya existe, se actualizarán los valores. Si no existe, se
     * insertará un nuevo registro con los valores proporcionados. Lo importante
     * es que mantiene el sessionid de Remoto
     *
     * @param partida La partida que contiene los datos a insertar o actualizar.
     * @throws SQLException Si ocurre un error al realizar la consulta SQL.
     */
    public void insertarEstadoPartidaJugadorLocalParaRemoto(Partida partida) throws SQLException {
        String consultaVerificarExistencia = "SELECT 1 FROM EstadoPartidasJugadorLocal "
                + "WHERE session_id = ? AND game_id = ? AND player_id = ? LIMIT 1";

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmtVerificar = connection.prepareStatement(consultaVerificarExistencia)) {

            stmtVerificar.setInt(1, partida.getSession_id());
            stmtVerificar.setInt(2, partida.getGame_id());
            stmtVerificar.setInt(3, partida.getPlayer_id());

            try (ResultSet rs = stmtVerificar.executeQuery()) {
                if (rs.next()) {
                    // Actualizar si la partida existe
                    String consultaActualizar = "UPDATE EstadoPartidasJugadorLocal SET "
                            + "experience = ?, life_level = ?, coins = ?, session_count = ?, session_date = ? "
                            + "WHERE session_id = ? AND game_id = ? AND player_id = ?";

                    try (PreparedStatement stmtActualizar = connection.prepareStatement(consultaActualizar)) {
                        stmtActualizar.setInt(1, partida.getExperience());
                        stmtActualizar.setInt(2, partida.getLife_level());
                        stmtActualizar.setInt(3, partida.getCoins());
                        stmtActualizar.setInt(4, partida.getSession_count());

                        // Verificar si session_date no es nulo antes de asignarlo
                        if (partida.getSession_date() != null) {
                            stmtActualizar.setString(5, partida.getSession_date().toString());
                        } else {
                            stmtActualizar.setNull(5, Types.VARCHAR); // O manejar con un valor predeterminado
                        }

                        stmtActualizar.setInt(6, partida.getSession_id());
                        stmtActualizar.setInt(7, partida.getGame_id());
                        stmtActualizar.setInt(8, partida.getPlayer_id());

                        stmtActualizar.executeUpdate();
                    }
                } else {
                    // Insertar si no existe
                    String consultaInsertar = "INSERT INTO EstadoPartidasJugadorLocal "
                            + "(session_id, game_id, player_id, experience, life_level, coins, session_count, session_date) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement stmtInsertar = connection.prepareStatement(consultaInsertar)) {
                        stmtInsertar.setInt(1, partida.getSession_id());
                        stmtInsertar.setInt(2, partida.getGame_id());
                        stmtInsertar.setInt(3, partida.getPlayer_id());
                        stmtInsertar.setInt(4, partida.getExperience());
                        stmtInsertar.setInt(5, partida.getLife_level());
                        stmtInsertar.setInt(6, partida.getCoins());
                        stmtInsertar.setInt(7, partida.getSession_count());
                        if (partida.getSession_date() != null) {
                            stmtInsertar.setString(8, partida.getSession_date().toString());
                        } else {
                            stmtInsertar.setNull(8, Types.VARCHAR);
                        }

                        stmtInsertar.executeUpdate();
                    }
                }
            }
        }
    }

    /**
     * Obtiene todas las partidas registradas en la tabla
     * EstadoPartidasJugadorLocal.
     *
     * @return una lista de objetos Partida con los datos obtenidos.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public List<Partida> obtenerPartidasJugadorLocal() throws SQLException {
        String consulta = "SELECT * FROM EstadoPartidasJugadorLocal";
        List<Partida> partidas = new ArrayList<>();

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Partida partida = new Partida();
                partida.setSession_id(rs.getInt("session_id"));
                partida.setGame_id(rs.getInt("game_id"));
                partida.setPlayer_id(rs.getInt("player_id"));
                partida.setExperience(rs.getInt("experience"));
                partida.setLife_level(rs.getInt("life_level"));
                partida.setCoins(rs.getInt("coins"));
                // Verificar si session_date es null y establecer un valor predeterminado
                Timestamp sessionDate = rs.getTimestamp("session_date");
                partida.setSession_date(sessionDate != null ? sessionDate : new Timestamp(System.currentTimeMillis()));

                partidas.add(partida);
            }
        }

        return partidas;
    }

    /**
     * Obtiene una partida específica de la tabla EstadoPartidasJugadorLocal
     * según el ID del jugador y el ID del videojuego.
     *
     * @param player_id el ID del jugador.
     * @param game_id el ID del videojuego.
     * @return un objeto {@link Partida} con los datos obtenidos o {@code null}
     * si no se encuentra ninguna partida que coincida con los parámetros.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public Partida obtenerPartidaJugadorLocal(int player_id, int game_id) throws SQLException {
        String consulta = "SELECT * FROM EstadoPartidasJugadorLocal WHERE player_id = ? AND game_id = ? LIMIT 1";
        Partida partida = null;

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {

            // Establecer los parámetros de la consulta
            stmt.setInt(1, player_id);
            stmt.setInt(2, game_id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear y llenar el objeto Partida con los datos obtenidos
                    partida = new Partida();
                    partida.setSession_id(rs.getInt("session_id"));
                    partida.setGame_id(rs.getInt("game_id"));
                    partida.setPlayer_id(rs.getInt("player_id"));
                    partida.setExperience(rs.getInt("experience"));
                    partida.setLife_level(rs.getInt("life_level"));
                    partida.setCoins(rs.getInt("coins"));
                    partida.setSession_date(rs.getTimestamp("session_date"));

                    System.out.println("Partida encontrada: " + partida);
                } else {
                    System.out.println("No se encontró la partida.");
                }
            }
        }

        return partida;
    }

    /**
     * Obtiene una partida específica de la tabla EstadoPartidasJugadorLocal
     * según su ID.
     *
     * @param session_id el ID de la sesión a buscar.
     * @return un objeto Partida con los datos obtenidos o null si no se
     * encuentra.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public Partida obtenerPartidaJugadorLocalID(int session_id) throws SQLException {
        String consulta = "SELECT * FROM EstadoPartidasJugadorLocal WHERE session_id = ?";
        Partida partida = null;

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {

            stmt.setInt(1, session_id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    partida = new Partida();
                    partida.setSession_id(rs.getInt("session_id"));
                    partida.setGame_id(rs.getInt("game_id"));
                    partida.setPlayer_id(rs.getInt("player_id"));
                    partida.setExperience(rs.getInt("experience"));
                    partida.setLife_level(rs.getInt("life_level"));
                    partida.setCoins(rs.getInt("coins"));
                    partida.setSession_date(rs.getTimestamp("session_date"));
                }
            }
        }

        return partida;
    }

    /**
     * Actualiza los datos de una partida en la tabla
     * EstadoPartidasJugadorLocal.
     *
     * @param partida el objeto Partida con los datos actualizados.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public void actualizarEstadoPartidaJugadorLocal(Partida partida) throws SQLException {
        String consulta = "UPDATE EstadoPartidasJugadorLocal SET game_id = ?, player_id = ?, experience = ?, "
                + "life_level = ?, coins = ?, session_date = CURRENT_TIMESTAMP, session_count = ? "
                + "WHERE player_id = ? AND game_id = ?";

        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {
            stmt.setInt(1, partida.getGame_id());
            stmt.setInt(2, partida.getPlayer_id());
            stmt.setInt(3, partida.getExperience());
            stmt.setInt(4, partida.getLife_level());
            stmt.setInt(5, partida.getCoins());
            stmt.setInt(6, partida.getSession_count());
            stmt.setInt(7, partida.getPlayer_id());
            stmt.setInt(8, partida.getGame_id());

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina una partida específica de la tabla EstadoPartidasJugadorLocal
     * según su ID.
     *
     * @param session_id el ID de la sesión a eliminar.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    public void eliminarPartidaJugadorLocal(int session_id) throws SQLException {
        String consulta = "DELETE FROM EstadoPartidasJugadorLocal WHERE session_id = ?";
        try (Connection connection = ConexionSQLite.getConnection(); PreparedStatement stmt = connection.prepareStatement(consulta)) {
            stmt.setInt(1, session_id);
            stmt.executeUpdate();
        }
    }

}
