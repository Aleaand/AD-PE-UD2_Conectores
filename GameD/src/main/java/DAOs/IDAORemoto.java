/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOs;

import Modelo.Videojuego;
import Modelo.Partida;
import Modelo.Jugador;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alejandra
 */
public interface IDAORemoto {

    // Métodos relacionados con la entidad Jugador
    /**
     * Inserta un nuevo jugador en la base de datos.
     *
     * @param jugador El objeto jugador a insertar.
     * @return El ID del jugador recién insertado.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public int insertarJugador(Jugador jugador) throws SQLException;

    /**
     * Obtiene un jugador a partir de su ID.
     *
     * @param player_id El ID del jugador a obtener.
     * @return El jugador con el ID especificado.
     * @throws SQLException Si ocurre un error al obtener el jugador.
     */
    public Jugador obtenerJugador(int player_id) throws SQLException;

    /**
     * Obtiene todos los jugadores registrados.
     *
     * @return Una lista de todos los jugadores.
     * @throws SQLException Si ocurre un error al obtener los jugadores.
     */
    public List<Jugador> obtenerJugadores() throws SQLException;

    /**
     * Actualiza la información de un jugador existente.
     *
     * @param jugador El objeto jugador con la nueva información.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public void actualizarJugador(Jugador jugador) throws SQLException;

    /**
     * Elimina un jugador de la base de datos.
     *
     * @param player_id El ID del jugador a eliminar.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public void eliminarJugador(int player_id) throws SQLException;

    // Métodos relacionados con la entidad Partida
    /**
     * Inserta una nueva partida en la base de datos.
     *
     * @param partida El objeto partida a insertar.
     * @return El ID de la sesión de la partida recién creada.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public int insertarpartida(Partida partida) throws SQLException;

    /**
     * Obtiene una partida específica a partir del ID del videojuego y el ID del
     * jugador.
     *
     * @param game_id El ID del videojuego.
     * @param playerID El ID del jugador.
     * @return El objeto partida correspondiente.
     * @throws SQLException Si ocurre un error al obtener la partida.
     */
    public Partida obtenerPartida(int game_id, int playerID) throws SQLException;

    /**
     * Obtiene una partida utilizando su ID de sesión.
     *
     * @param sessionId El ID de sesión de la partida.
     * @return El objeto partida correspondiente.
     * @throws SQLException Si ocurre un error al obtener la partida.
     */
    public Partida obtenerPartidaPorSessionId(int sessionId) throws SQLException;

    /**
     * Obtiene todas las partidas asociadas a un jugador.
     *
     * @param player_id El ID del jugador.
     * @return Una lista de todas las partidas del jugador.
     * @throws SQLException Si ocurre un error al obtener las partidas.
     */
    public List<Partida> obtenerPartidas(int player_id) throws SQLException;

    /**
     * Actualiza la información de una partida existente.
     *
     * @param partida El objeto partida con la nueva información.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public void actualizarPartida(Partida partida) throws SQLException;

    /**
     * Elimina una partida de la base de datos.
     *
     * @param session_id El ID de la sesión de la partida a eliminar.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public void eliminarPartida(int session_id) throws SQLException;

    // Métodos relacionados con la entidad Videojuego
    /**
     * Inserta un nuevo videojuego en la base de datos.
     *
     * @param videojuego El objeto videojuego a insertar.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public void insertarVideojuego(Videojuego videojuego) throws SQLException;

    /**
     * Obtiene el top 10 jugadores de un videojuego basándose en su nivel de
     * vida.
     *
     * @param game_id El ID del videojuego.
     * @return Una lista de los 10 mejores jugadores.
     * @throws SQLException Si ocurre un error al obtener los jugadores del top
     * 10.
     */
    public List<Jugador> obtenerTop10(int game_id) throws SQLException;

    /**
     * Obtiene un videojuego específico a partir de su ID.
     *
     * @param game_id El ID del videojuego.
     * @return El objeto videojuego correspondiente.
     * @throws SQLException Si ocurre un error al obtener el videojuego.
     */
    public Videojuego obtenerVideojuego(int game_id) throws SQLException;

    /**
     * Obtiene todos los videojuegos registrados en la base de datos.
     *
     * @return Una lista de todos los videojuegos.
     * @throws SQLException Si ocurre un error al obtener los videojuegos.
     */
    public List<Videojuego> obtenerVideojuegos() throws SQLException;

    /**
     * Actualiza la información de un videojuego existente.
     *
     * @param videojuego El objeto videojuego con la nueva información.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public void actualizarVideojuego(Videojuego videojuego) throws SQLException;

    /**
     * Elimina un videojuego de la base de datos.
     *
     * @param game_id El ID del videojuego a eliminar.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public void eliminarVideojuego(int game_id) throws SQLException;
}
