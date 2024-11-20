/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionRemoto;
import Modelo.Jugador;
import Modelo.Partida;
import Modelo.Videojuego;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandra
 */
public class DAORemotoPostgreSQL implements IDAORemoto {

    /**
     * Inserta un nuevo jugador en la base de datos y devuelve su
     * {@code player_id}.
     *
     * @param jugador El objeto {@link Jugador} con los datos del jugador a
     * insertar.
     * @return El {@code player_id} del jugador insertado.
     * @throws SQLException Si ocurre un error durante la operación de
     * inserción.
     */
    @Override
    public int insertarJugador(Jugador jugador) throws SQLException {
        // Usamos RETURNING para obtener el player_id generado por PostgreSQL
        String consulta = "INSERT INTO Jugadores (nick_name) VALUES (?) RETURNING player_id";
        int playerId = -1;

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            // Establecemos el nickname del jugador en el parámetro de la consulta
            statement.setString(1, jugador.getNick_name());

            try (ResultSet rs = statement.executeQuery()) { // Usamos executeQuery para obtener el player_id generado
                if (rs.next()) {
                    playerId = rs.getInt("player_id"); // PostgreSQL devolverá el player_id generado
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al insertar el jugador: " + e.getMessage(), e);
        }

        return playerId;
    }

    /**
     * Obtiene un jugador de la base de datos usando su {@code player_id}.
     *
     * @param player_id El id del jugador a obtener.
     * @return Un objeto {@link Jugador} con los datos del jugador.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public Jugador obtenerJugador(int playerId) throws SQLException {
        String consulta = "SELECT player_id, nick_name FROM jugadores WHERE player_id = ?";
        Jugador jugador = null;

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            // Establecemos el valor del player_id en la consulta
            statement.setInt(1, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Creamos el objeto jugador con los datos obtenidos
                    jugador = new Jugador();
                    jugador.setPlayer_id(resultSet.getInt("player_id"));
                    jugador.setNick_name(resultSet.getString("nick_name"));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el jugador: " + e.getMessage(), e);
        }

        return jugador;
    }

    /**
     * Obtiene todos los jugadores registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Jugador} con todos los jugadores.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public List<Jugador> obtenerJugadores() throws SQLException {
        String consulta = "SELECT player_id, nick_name FROM jugadores";
        List<Jugador> jugadores = new ArrayList<>();
        // Intentamos obtener la lista de jugadores desde la base de datos remota (PostgreSQL)
        try (Connection connection = ConexionRemoto.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(consulta)) {

            while (resultSet.next()) {
                Jugador jugador = new Jugador();
                jugador.setPlayer_id(resultSet.getInt("player_id"));
                jugador.setNick_name(resultSet.getString("nick_name"));
                jugadores.add(jugador);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener la lista de jugadores: " + e.getMessage(), e);
        }
        return jugadores;
    }

    /**
     * Actualiza los datos de un jugador en la base de datos.
     *
     * @param jugador El objeto {@link Jugador} con los datos a actualizar.
     * @throws SQLException Si ocurre un error durante la operación de
     * actualización.
     */
    @Override
    public void actualizarJugador(Jugador jugador) throws SQLException {
        String consulta = "UPDATE jugadores SET nick_name = ? WHERE player_id = ?";

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, jugador.getNick_name());
            statement.setInt(2, jugador.getPlayer_id());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se encontró el jugador con el id: " + jugador.getPlayer_id());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el jugador: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un jugador de la base de datos mediante su {@code player_id}.
     *
     * @param player_id El id del jugador a eliminar.
     * @throws SQLException Si ocurre un error durante la operación de
     * eliminación.
     */
    @Override
    public void eliminarJugador(int player_id) throws SQLException {
        String consulta = "DELETE FROM jugadores WHERE player_id = ?";
        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, player_id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se encontró el jugador con el id: " + player_id);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar el jugador: " + e.getMessage(), e);
        }
    }

    /**
     * Inserta una nueva partida en la base de datos. La partida se agrega a la
     * tabla `partidas` si el videojuego y el jugador existen en la base de
     * datos. La fecha de la sesión se asigna automáticamente a la fecha y hora
     * actuales (NOW()).
     *
     * @param partida El objeto de tipo {@link Partida} que contiene los datos
     * de la partida a insertar.
     * @return El ID de la sesión generada para la partida insertada. Si ocurre
     * algún error, se lanza una excepción {@link SQLException}.
     * @throws SQLException Si ocurre un error durante la inserción o si el
     * videojuego o el jugador no existen en la base de datos.
     */
    @Override
    public int insertarpartida(Partida partida) throws SQLException {
        String consulta = "INSERT INTO partidas (game_id, player_id, experience, life_level, coins, session_count, session_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW()) RETURNING session_id";

        int sessionId = -1;
        try (Connection connection = ConexionRemoto.getConnection()) {

            if (!existeVideojuego(partida.getGame_id(), connection) || !existeJugador(partida.getPlayer_id(), connection)) {
                throw new SQLException("El videojuego o el jugador no existen.");
            }
            try (PreparedStatement statement = connection.prepareStatement(consulta)) {
                statement.setInt(1, partida.getGame_id());
                statement.setInt(2, partida.getPlayer_id());
                statement.setInt(3, partida.getExperience());
                statement.setInt(4, partida.getLife_level());
                statement.setInt(5, partida.getCoins());
                statement.setInt(6, partida.getSession_count());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        sessionId = resultSet.getInt("session_id"); // Obtener el ID de la sesión generada
                    }
                }
            } catch (SQLException e) {
                throw new SQLException("Error al insertar la partida: " + e.getMessage(), e);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al verificar la existencia del videojuego o jugador: " + e.getMessage(), e);
        }

        return sessionId;
    }

    /**
     * Verifica si un videojuego existe en la base de datos.
     *
     * Este método consulta la tabla `videojuegos` para comprobar si existe un
     * registro con el {@code game_id} especificado.
     *
     * @param gameId El ID del videojuego a verificar.
     * @param connection La conexión activa a la base de datos.
     * @return {@code true} si el videojuego existe en la base de datos,
     * {@code false} si no existe.
     * @throws SQLException Si ocurre un error durante la ejecución de la
     * consulta.
     */
    public boolean existeVideojuego(int gameId, Connection connection) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM videojuegos WHERE game_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar la existencia del videojuego: " + e.getMessage(), e);
        }

        return false;
    }

    /**
     * Verifica si un jugador existe en la base de datos.
     *
     * Este método consulta la tabla `jugadores` para comprobar si existe un
     * registro con el {@code player_id} especificado.
     *
     * @param playerId El ID del jugador a verificar.
     * @param connection La conexión activa a la base de datos.
     * @return {@code true} si el jugador existe en la base de datos,
     * {@code false} si no existe.
     * @throws SQLException Si ocurre un error durante la ejecución de la
     * consulta.
     */
    public boolean existeJugador(int playerId, Connection connection) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM jugadores WHERE player_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar la existencia del jugador: " + e.getMessage(), e);
        }

        return false;
    }

    /**
     * Obtiene una partida específica de la base de datos utilizando el
     * {@code game_id} y el {@code player_id}.
     *
     * @param game_id El ID del videojuego de la partida a obtener.
     * @param player_id El ID del jugador que participó en la partida.
     * @return Un objeto {@link Partida} con los datos de la partida.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public Partida obtenerPartida(int game_id, int playerID) throws SQLException {
        String consulta = "SELECT session_id, game_id, player_id, experience, life_level, coins, session_count, session_date "
                + "FROM partidas WHERE game_id = ? AND player_id = ?";
        Partida partida = null;

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            statement.setInt(1, game_id);
            statement.setInt(2, playerID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    partida = new Partida();
                    partida.setSession_id(resultSet.getInt("session_id"));
                    partida.setGame_id(resultSet.getInt("game_id"));
                    partida.setPlayer_id(resultSet.getInt("player_id"));
                    partida.setExperience(resultSet.getInt("experience"));
                    partida.setLife_level(resultSet.getInt("life_level"));
                    partida.setCoins(resultSet.getInt("coins"));
                    partida.setSession_count(resultSet.getInt("session_count"));
                    partida.setSession_date(resultSet.getTimestamp("session_date"));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener la partida: " + e.getMessage(), e);
        }

        return partida;
    }

    /**
     * Obtiene todas las partidas de un jugador específico.
     *
     * @param player_id El id del jugador.
     * @return Una lista de objetos {@link Partida} que representan todas las
     * partidas del jugador.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public List<Partida> obtenerPartidas(int player_id) throws SQLException {
        String consulta = "SELECT session_id, game_id, player_id, experience, life_level, coins, session_count, session_date "
                + "FROM partidas WHERE player_id = ?";
        List<Partida> partidas = new ArrayList<>();

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            statement.setInt(1, player_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Partida partida = new Partida();
                    partida.setSession_id(resultSet.getInt("session_id"));
                    partida.setGame_id(resultSet.getInt("game_id"));
                    partida.setPlayer_id(resultSet.getInt("player_id"));
                    partida.setExperience(resultSet.getInt("experience"));
                    partida.setLife_level(resultSet.getInt("life_level"));
                    partida.setCoins(resultSet.getInt("coins"));
                    partida.setSession_count(resultSet.getInt("session_count"));
                    partida.setSession_date(resultSet.getTimestamp("session_date"));
                    partidas.add(partida);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener las partidas: " + e.getMessage(), e);
        }

        return partidas;
    }

    /**
     * Actualiza los detalles de una partida en la base de datos.
     *
     * @param partida El objeto {@link Partida} con los nuevos datos a
     * actualizar.
     * @throws SQLException Si ocurre un error durante la operación de
     * actualización.
     */
    @Override
    public void actualizarPartida(Partida partida) throws SQLException {
        String consulta = "UPDATE partidas SET experience = ?, life_level = ?, coins = ?, session_count = ?, session_date = ? "
                + "WHERE session_id = ?";

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            statement.setInt(1, partida.getExperience());
            statement.setInt(2, partida.getLife_level());
            statement.setInt(3, partida.getCoins());
            statement.setInt(4, partida.getSession_count());

            // Si session_date es null, ponemos la fecha y hora actuales, si no, mantenemos el valor original.
            if (partida.getSession_date() == null) {
                statement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis())); // fecha actual
            } else {
                statement.setTimestamp(5, partida.getSession_date()); // mantener el valor actual
            }

            statement.setInt(6, partida.getSession_id());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró la partida con el id: " + partida.getSession_id());
            }

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar la partida: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una partida de la base de datos mediante su {@code session_id}.
     *
     * @param session_id El id de la sesión de la partida a eliminar.
     * @throws SQLException Si ocurre un error durante la operación de
     * eliminación.
     */
    @Override
    public void eliminarPartida(int session_id) throws SQLException {
        String consulta = "DELETE FROM partidas WHERE session_id = ?";

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            statement.setInt(1, session_id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró la partida con el id: " + session_id);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar la partida: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una partida específica a partir de su {@code session_id}.
     *
     * @param sessionId El ID de la sesión de la partida que se desea obtener.
     * @return Un objeto {@link Partida} con los detalles de la partida
     * correspondiente al {@code sessionId}. Si no se encuentra la partida,
     * retorna {@code null}.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public Partida obtenerPartidaPorSessionId(int sessionId) throws SQLException {
        // Consulta SQL para obtener la partida por session_id
        String consulta = "SELECT session_id, game_id, player_id, experience, life_level, coins, session_count, session_date "
                + "FROM partidas WHERE session_id = ?";

        Partida partida = null;

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            // Establecemos el parámetro de la consulta
            statement.setInt(1, sessionId);

            // Ejecutamos la consulta
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Si encontramos resultados, creamos el objeto Partida
                    partida = new Partida();
                    partida.setSession_id(resultSet.getInt("session_id"));
                    partida.setGame_id(resultSet.getInt("game_id"));
                    partida.setPlayer_id(resultSet.getInt("player_id"));
                    partida.setExperience(resultSet.getInt("experience"));
                    partida.setLife_level(resultSet.getInt("life_level"));
                    partida.setCoins(resultSet.getInt("coins"));
                    partida.setSession_count(resultSet.getInt("session_count"));
                    partida.setSession_date(resultSet.getTimestamp("session_date"));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener la partida con session_id: " + sessionId, e);
        }
        return partida;
    }

    /**
     * Inserta un nuevo videojuego en la base de datos.
     *
     * @param videojuego El objeto {@link Videojuego} con los datos del
     * videojuego a insertar.
     * @throws SQLException Si ocurre un error durante la operación de
     * inserción.
     */
    @Override
    public void insertarVideojuego(Videojuego videojuego) throws SQLException {
        String consulta = "INSERT INTO videojuegos (isbn, title, player_count, total_sessions, last_session) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, videojuego.getIsbn());
            statement.setString(2, videojuego.getTitle());
            statement.setInt(3, videojuego.getPlayer_count());
            statement.setInt(4, videojuego.getTotal_sessions());
            statement.setTimestamp(5, videojuego.getLast_session());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al insertar el videojuego.");
            }

        } catch (SQLException e) {
            throw new SQLException("Error al insertar el videojuego: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el top 10 jugadores en un videojuego, basado en el
     * {@code game_id} y su {@code life_level}.
     *
     * @param game_id El id del videojuego.
     * @return Una lista de objetos {@link Jugador} que representan los
     * jugadores en el top 10.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public List<Jugador> obtenerTop10(int game_id) throws SQLException {
        // Consulta SQL para obtener el top 10 de jugadores en un videojuego
        String consulta = "SELECT j.player_id, j.nick_name, p.life_level "
                + "FROM jugadores j JOIN partidas p ON j.player_id = p.player_id "
                + "WHERE p.game_id = ? ORDER BY p.life_level DESC LIMIT 10";

        List<Jugador> jugadores = new ArrayList<>();

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, game_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Jugador jugador = new Jugador();
                    jugador.setPlayer_id(resultSet.getInt("player_id"));
                    jugador.setNick_name(resultSet.getString("nick_name"));
                    jugadores.add(jugador); // Agregar el jugador al listado
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el top 10: " + e.getMessage(), e);
        }

        return jugadores;
    }

    /**
     * Obtiene todos los videojuegos disponibles en la base de datos.
     *
     * @return Una lista de objetos {@link Videojuego} con todos los
     * videojuegos.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public List<Videojuego> obtenerVideojuegos() throws SQLException {
        String consulta = "SELECT game_id, isbn, title, player_count, total_sessions, last_session FROM videojuegos";
        List<Videojuego> videojuegos = new ArrayList<>();

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Videojuego videojuego = new Videojuego();
                videojuego.setGame_id(resultSet.getInt("game_id"));
                videojuego.setIsbn(resultSet.getString("isbn"));
                videojuego.setTitle(resultSet.getString("title"));
                videojuego.setPlayer_count(resultSet.getInt("player_count"));
                videojuego.setTotal_sessions(resultSet.getInt("total_sessions"));
                videojuego.setLast_session(resultSet.getTimestamp("last_session"));
                videojuegos.add(videojuego);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener los videojuegos: " + e.getMessage(), e);
        }

        return videojuegos;
    }

    /**
     * Actualiza la información de un videojuego existente en la base de datos.
     *
     * @param videojuego El objeto {@link Videojuego} con los datos a
     * actualizar.
     * @throws SQLException Si ocurre un error durante la operación de
     * actualización.
     */
    @Override
    public void actualizarVideojuego(Videojuego videojuego) throws SQLException {
        // Consulta SQL para actualizar un videojuego existente
        String consulta = "UPDATE videojuegos SET isbn = ?, title = ?, player_count = ?, total_sessions = ?, last_session = ? "
                + "WHERE game_id = ?";

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {

            statement.setString(1, videojuego.getIsbn());
            statement.setString(2, videojuego.getTitle());
            statement.setInt(3, videojuego.getPlayer_count());
            statement.setInt(4, videojuego.getTotal_sessions());
            statement.setTimestamp(5, videojuego.getLast_session());
            statement.setInt(6, videojuego.getGame_id());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el videojuego con el id: " + videojuego.getGame_id());
            }

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el videojuego: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un videojuego de la base de datos mediante su {@code game_id}.
     *
     * @param game_id El id del videojuego a eliminar.
     * @throws SQLException Si ocurre un error durante la operación de
     * eliminación.
     */
    @Override
    public void eliminarVideojuego(int game_id) throws SQLException {
        // Consulta SQL para eliminar un videojuego
        String consulta = "DELETE FROM videojuegos WHERE game_id = ?";

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, game_id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el videojuego con el id: " + game_id);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar el videojuego: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un videojuego específico a partir de su {@code game_id}.
     *
     * @param game_id El ID del videojuego que se desea obtener.
     * @return Un objeto {@link Videojuego} con los detalles del videojuego
     * correspondiente al {@code game_id}. Si no se encuentra el videojuego,
     * retorna {@code null}.
     * @throws SQLException Si ocurre un error durante la operación de
     * obtención.
     */
    @Override
    public Videojuego obtenerVideojuego(int game_id) throws SQLException {
        String consulta = "SELECT game_id, isbn, title, player_count, total_sessions, last_session "
                + "FROM videojuegos WHERE game_id = ?";

        Videojuego videojuego = null;

        try (Connection connection = ConexionRemoto.getConnection(); PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, game_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Si encontramos el videojuego, creamos el objeto Videojuego
                    videojuego = new Videojuego();
                    videojuego.setGame_id(resultSet.getInt("game_id"));
                    videojuego.setIsbn(resultSet.getString("isbn"));
                    videojuego.setTitle(resultSet.getString("title"));
                    videojuego.setPlayer_count(resultSet.getInt("player_count"));
                    videojuego.setTotal_sessions(resultSet.getInt("total_sessions"));
                    videojuego.setLast_session(resultSet.getTimestamp("last_session"));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el videojuego con game_id: " + game_id, e);
        }
        return videojuego;
    }
}
