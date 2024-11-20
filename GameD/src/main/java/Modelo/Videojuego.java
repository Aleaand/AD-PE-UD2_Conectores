/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author Alejandra
 */
public class Videojuego {

    private int game_id; // Identificador único del videojuego
    private int player_count; // Número de jugadores que participan en el videojuego
    private int total_sessions; // Número total de sesiones jugadas en el videojuego
    private String isbn; // ISBN del videojuego
    private String title; // Título del videojuego
    private Timestamp last_session; // Fecha y hora de la última sesión jugada en el videojuego

    /**
     * Constructor vacío para crear una instancia de Videojuego sin inicializar
     * los valores.
     */
    public Videojuego() {
    }

    /**
     * Obtiene el identificador único del videojuego.
     *
     * @return el identificador del videojuego
     */
    public int getGame_id() {
        return game_id;
    }

    /**
     * Establece el identificador único del videojuego.
     *
     * @param game_id el nuevo identificador del videojuego
     */
    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    /**
     * Obtiene el número de jugadores que participan en el videojuego.
     *
     * @return el número de jugadores
     */
    public int getPlayer_count() {
        return player_count;
    }

    /**
     * Establece el número de jugadores que participan en el videojuego.
     *
     * @param player_count el nuevo número de jugadores
     */
    public void setPlayer_count(int player_count) {
        this.player_count = player_count;
    }

    /**
     * Obtiene el número total de sesiones jugadas en el videojuego.
     *
     * @return el número total de sesiones
     */
    public int getTotal_sessions() {
        return total_sessions;
    }

    /**
     * Establece el número total de sesiones jugadas en el videojuego.
     *
     * @param total_sessions el nuevo número total de sesiones
     */
    public void setTotal_sessions(int total_sessions) {
        this.total_sessions = total_sessions;
    }

    /**
     * Obtiene el ISBN del videojuego.
     *
     * @return el ISBN del videojuego
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del videojuego.
     *
     * @param isbn el nuevo ISBN del videojuego
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el título del videojuego.
     *
     * @return el título del videojuego
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título del videojuego.
     *
     * @param title el nuevo título del videojuego
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene la fecha y hora de la última sesión jugada del videojuego.
     *
     * @return la fecha y hora de la última sesión
     */
    public Timestamp getLast_session() {
        return last_session;
    }

    /**
     * Establece la fecha y hora de la última sesión jugada del videojuego.
     *
     * @param last_session la nueva fecha y hora de la última sesión
     */
    public void setLast_session(Timestamp last_session) {
        this.last_session = last_session;
    }

    /**
     * Representa el videojuego como una cadena de texto con todos sus
     * atributos.
     *
     * @return una representación en cadena del videojuego
     */
    @Override
    public String toString() {
        return "Videojuego{" + "game_id=" + game_id + ", player_count=" + player_count + ", total_sessions=" + total_sessions
                + ", isbn=" + isbn + ", title=" + title + ", last_session=" + last_session + '}';
    }
}
