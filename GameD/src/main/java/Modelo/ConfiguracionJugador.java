/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Alejandra
 */
public class ConfiguracionJugador {

    private int configId; // Identificador único de la configuración
    private boolean soundEnabled; // Indica si el sonido está habilitado
    private String resolution; // Resolución de pantalla seleccionada
    private String language; // Idioma seleccionado por el jugador

    /**
     * Constructor que inicializa la configuración del jugador con los valores
     * proporcionados.
     *
     * @param configId identificador de la configuración
     * @param soundEnabled estado del sonido (true si está habilitado, false si
     * está deshabilitado)
     * @param resolution resolución de la pantalla del jugador
     * @param language idioma seleccionado por el jugador
     */
    public ConfiguracionJugador(int configId, boolean soundEnabled, String resolution, String language) {
        this.configId = configId;
        this.soundEnabled = soundEnabled;
        this.resolution = resolution;
        this.language = language;
    }

    /**
     * Constructor vacío para crear una instancia de ConfiguracionJugador sin
     * inicializar los valores.
     */
    public ConfiguracionJugador() {
    }

    /**
     * Obtiene el identificador de la configuración.
     *
     * @return el identificador de la configuración
     */
    public int getConfigId() {
        return configId;
    }

    /**
     * Establece el identificador de la configuración.
     *
     * @param configId el nuevo identificador de la configuración
     */
    public void setConfigId(int configId) {
        this.configId = configId;
    }

    /**
     * Obtiene el estado del sonido.
     *
     * @return true si el sonido está habilitado, false si está deshabilitado
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    /**
     * Establece el estado del sonido.
     *
     * @param soundEnabled el nuevo estado del sonido
     */
    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    /**
     * Obtiene la resolución de la pantalla.
     *
     * @return la resolución de la pantalla
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Establece la resolución de la pantalla.
     *
     * @param resolution la nueva resolución de la pantalla
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     * Obtiene el idioma seleccionado por el jugador.
     *
     * @return el idioma seleccionado
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Establece el idioma seleccionado por el jugador.
     *
     * @param language el nuevo idioma seleccionado
     */
    public void setLanguage(String language) {
        this.language = language;
    }
}
