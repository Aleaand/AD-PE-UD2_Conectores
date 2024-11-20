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
public class Partida {

    private int session_id; // Identificador único de la sesión de la partida
    private int game_id; // Identificador del videojuego
    private int player_id; // Identificador del jugador
    private int experience; // Experiencia ganada en la partida
    private int life_level; // Nivel de vida alcanzado por el jugador en la partida
    private int coins; // Cantidad de monedas obtenidas durante la partida
    private int session_count; // Número de veces que el jugador ha jugado en la sesión
    private Timestamp session_date; // Fecha y hora en que la partida fue jugada

    /**
     * Constructor vacío para crear una instancia de Partida sin inicializar los
     * valores.
     */
    public Partida() {
    }

    /**
     * Obtiene el identificador único de la sesión de la partida.
     *
     * @return el identificador de la sesión
     */
    public int getSession_id() {
        return session_id;
    }

    /**
     * Establece el identificador único de la sesión de la partida.
     *
     * @param session_id el nuevo identificador de la sesión
     */
    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    /**
     * Obtiene el identificador del videojuego asociado a la partida.
     *
     * @return el identificador del videojuego
     */
    public int getGame_id() {
        return game_id;
    }

    /**
     * Establece el identificador del videojuego asociado a la partida.
     *
     * @param game_id el nuevo identificador del videojuego
     */
    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    /**
     * Obtiene el identificador del jugador que participa en la partida.
     *
     * @return el identificador del jugador
     */
    public int getPlayer_id() {
        return player_id;
    }

    /**
     * Establece el identificador del jugador que participa en la partida.
     *
     * @param player_id el nuevo identificador del jugador
     */
    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    /**
     * Obtiene la experiencia ganada durante la partida.
     *
     * @return la cantidad de experiencia obtenida
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Establece la experiencia ganada durante la partida.
     *
     * @param experience la nueva cantidad de experiencia obtenida
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Obtiene el nivel de vida alcanzado por el jugador en la partida.
     *
     * @return el nivel de vida
     */
    public int getLife_level() {
        return life_level;
    }

    /**
     * Establece el nivel de vida alcanzado por el jugador en la partida.
     *
     * @param life_level el nuevo nivel de vida
     */
    public void setLife_level(int life_level) {
        this.life_level = life_level;
    }

    /**
     * Obtiene la cantidad de monedas obtenidas durante la partida.
     *
     * @return la cantidad de monedas obtenidas
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Establece la cantidad de monedas obtenidas durante la partida.
     *
     * @param coins la nueva cantidad de monedas obtenidas
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Obtiene el número de veces que el jugador ha jugado en la sesión.
     *
     * @return el número de sesiones jugadas
     */
    public int getSession_count() {
        return session_count;
    }

    /**
     * Establece el número de veces que el jugador ha jugado en la sesión.
     *
     * @param session_count el nuevo número de sesiones jugadas
     */
    public void setSession_count(int session_count) {
        this.session_count = session_count;
    }

    /**
     * Obtiene la fecha y hora en que la partida fue jugada.
     *
     * @return la fecha y hora de la sesión
     */
    public Timestamp getSession_date() {
        return session_date;
    }

    /**
     * Establece la fecha y hora en que la partida fue jugada.
     *
     * @param session_date la nueva fecha y hora de la sesión
     */
    public void setSession_date(Timestamp session_date) {
        this.session_date = session_date;
    }

    /**
     * Representa la partida como una cadena de texto con todos sus atributos.
     *
     * @return una representación en cadena de la partida
     */
    @Override
    public String toString() {
        return "Partida{" + "session_id=" + session_id + ", game_id=" + game_id + ", player_id=" + player_id
                + ", experience=" + experience + ", life_level=" + life_level + ", coins=" + coins
                + ", session_count=" + session_count + ", session_date=" + session_date + '}';
    }
}
