/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionSQLite;
import Modelo.Partida;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Alejandra
 */
public class Sincronizador {

    private IDAORemoto daoRemoto; // Interfaz para DAO remoto
    private DAOLocalSQLite daoLocal; // DAO para SQLite

    /**
     * Constructor del Sincronizador.
     *
     * @param daoRemoto instancia del DAO remoto.
     * @param daoLocal instancia del DAO local.
     */
    public Sincronizador(IDAORemoto daoRemoto, DAOLocalSQLite daoLocal) {
        this.daoRemoto = daoRemoto;
        this.daoLocal = daoLocal;
    }

    /**
     * Sincroniza las partidas desde la base de datos remota hacia la base de
     * datos local para un jugador específico.
     *
     * Este método elimina los registros previos de la tabla local antes de
     * insertar las partidas sincronizadas desde la base de datos remota.
     *
     * @param playerId ID del jugador que inicia sesión.
     * @throws SQLException si ocurre un error durante la sincronización o
     * eliminación de registros.
     */
    public void sincronizarDesdeRemotoALocal(int playerId) throws SQLException {
        System.out.println("Sincronizando partidas del jugador " + playerId + " desde la base de datos remota a la local...");

        try (Connection connection = ConexionSQLite.getConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM EstadoPartidasJugadorLocal");
            System.out.println("Registros eliminados de la tabla EstadoPartidasJugadorLocal.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar registros en la base de datos local: " + e.getMessage());
            throw e;
        }

        // Obtiene las partidas del jugador desde la base de datos remota
        List<Partida> partidasRemotas = daoRemoto.obtenerPartidas(playerId);

        // Inserta las partidas remotas en la base de datos local
        for (Partida partida : partidasRemotas) {
            try {
                daoLocal.insertarEstadoPartidaJugadorLocalParaRemoto(partida);
            } catch (SQLException e) {
                System.err.println("Error al procesar partida: " + partida);
                e.printStackTrace();
            }
        }

        System.out.println("Sincronización desde remoto a local completada para el jugador " + playerId + ".");
    }

    /**
     * Sincroniza las partidas desde la base de datos local hacia la base de
     * datos remota.
     *
     * Este método revisa si las partidas existen en la base de datos remota. Si
     * la partida ya existe, se actualiza; si no existe, se inserta una nueva
     * partida.
     *
     * @param partidasLocal Lista de partidas que se sincronizarán hacia la base
     * de datos remota.
     * @throws SQLException si ocurre un error durante la sincronización o
     * actualización/inserción de partidas.
     */
    public void sincronizarDesdeLocalARemoto(List<Partida> partidasLocal) throws SQLException {
        System.out.println("Sincronizando partidas del jugador desde la base de datos local a la remota...");

        // Inserta o actualiza las partidas en la base de datos remota
        for (Partida partida : partidasLocal) {
            try {
                // Verifica si la partida ya existe en la base de datos remota
                Partida aux = daoRemoto.obtenerPartida(partida.getGame_id(), partida.getPlayer_id());

                // Si la partida existe en la base de datos remota, se actualiza
                if (aux != null) {
                    daoRemoto.actualizarPartida(partida);
                } else {
                    // Si no existe, se inserta la nueva partida
                    int resultado = daoRemoto.insertarpartida(partida);
                }

            } catch (SQLException e) {
                System.err.println("Error al procesar partida: " + partida);
                e.printStackTrace();
            }
        }

        System.out.println("Sincronización desde local a remoto completada.");
    }
}
