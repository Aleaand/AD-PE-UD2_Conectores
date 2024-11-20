/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Alejandra
 */
public class Jugador {

    private int player_id; // Identificador único del jugador
    private String nick_name; // Nombre de usuario del jugador

    /**
     * Constructor vacío para crear una instancia de Jugador sin inicializar los
     * valores.
     */
    public Jugador() {
    }

    /**
     * Obtiene el identificador único del jugador.
     *
     * @return el identificador del jugador
     */
    public int getPlayer_id() {
        return player_id;
    }

    /**
     * Establece el identificador único del jugador.
     *
     * @param player_id el nuevo identificador del jugador
     */
    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    /**
     * Obtiene el nombre de usuario (nickname) del jugador.
     *
     * @return el nombre de usuario del jugador
     */
    public String getNick_name() {
        return nick_name;
    }

    /**
     * Establece el nombre de usuario (nickname) del jugador.
     *
     * @param nick_name el nuevo nombre de usuario del jugador
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    /**
     * Representa el jugador como una cadena de texto.
     *
     * @return una representación en cadena del jugador, incluyendo su ID y
     * nombre de usuario
     */
    @Override
    public String toString() {
        return "Jugador{" + "player_id=" + player_id + ", nick_name=" + nick_name + '}';
    }
}
