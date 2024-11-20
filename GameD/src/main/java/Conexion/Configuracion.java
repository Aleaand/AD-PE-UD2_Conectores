package Conexion;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Esta clase representa la configuración de conexión a la base de datos y el
 * nombre de usuario del jugador, con la capacidad de ser convertida a y desde
 * XML usando JAXB.
 *
 * La clase tiene atributos para almacenar la información de conexión como el
 * host, el puerto, el usuario y la contraseña, así como el nombre del jugador.
 * Cada atributo es mapeado a un elemento XML mediante las anotaciones
 * {@link XmlElement}.
 *
 * @author Alejandra
 */
@XmlRootElement // Marca esta clase como el elemento raíz del XML
public class Configuracion {

    private String host;
    private int port;
    private String user;
    private String pass;
    private String nick_name;

    /**
     * Constructor vacío requerido por JAXB para el proceso de deserialización.
     */
    public Configuracion() {
    }

    /**
     * Constructor que inicializa la configuración con los valores
     * proporcionados.
     *
     * @param host Dirección del host de la base de datos.
     * @param port Puerto de la base de datos.
     * @param user Usuario para la conexión a la base de datos.
     * @param pass Contraseña para la conexión a la base de datos.
     * @param nick_name Nombre del jugador.
     */
    public Configuracion(String host, int port, String user, String pass, String nick_name) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.nick_name = nick_name;
    }

    /**
     * Obtiene el host de la base de datos.
     *
     * @return El host de la base de datos.
     */
    @XmlElement
    public String getHost() {
        return host;
    }

    /**
     * Establece el host de la base de datos.
     *
     * @param host El host de la base de datos.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Obtiene el puerto de la base de datos.
     *
     * @return El puerto de la base de datos.
     */
    @XmlElement
    public int getPort() {
        return port;
    }

    /**
     * Establece el puerto de la base de datos.
     *
     * @param port El puerto de la base de datos.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Obtiene el usuario de la base de datos.
     *
     * @return El usuario de la base de datos.
     */
    @XmlElement
    public String getUser() {
        return user;
    }

    /**
     * Establece el usuario de la base de datos.
     *
     * @param user El usuario de la base de datos.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Obtiene la contraseña de la base de datos.
     *
     * @return La contraseña de la base de datos.
     */
    @XmlElement
    public String getPass() {
        return pass;
    }

    /**
     * Establece la contraseña de la base de datos.
     *
     * @param pass La contraseña de la base de datos.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    @XmlElement
    public String getNick_name() {
        return nick_name;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nick_name El nombre del jugador.
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
