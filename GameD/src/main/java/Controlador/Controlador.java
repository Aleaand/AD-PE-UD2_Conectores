/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.ConexionRemoto;
import Conexion.Configuracion;
import Conexion.ConfiguracionBD;
import DAOs.DAOFactory;
import DAOs.DAOLocalSQLite;
import DAOs.IDAORemoto;
import DAOs.Sincronizador;
import Modelo.ConfiguracionJugador;
import Modelo.Jugador;
import Modelo.Partida;
import Modelo.Videojuego;
import Vista.Vista;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alejandra
 */
public class Controlador {

    private Vista vista;
    private IDAORemoto daoRemoto;
    private DAOLocalSQLite daoLocal;
    private Sincronizador sincronizador;
    ConfiguracionJugador configuracion;

    /**
     * Constructor del controlador que inicializa la vista.
     *
     * @param vista La vista asociada al controlador.
     */
    public Controlador(Vista vista) {
        this.vista = vista;

    }

    /**
     * Inicia el flujo principal de la aplicación, mostrando el menú principal y
     * gestionando las opciones seleccionadas por el usuario, como iniciar
     * sesión como administrador o jugador.
     *
     * @throws SQLException Si ocurre un error en la base de datos.
     * @throws Exception Si ocurre un error general en el sistema.
     */
    public void iniciar() throws SQLException, Exception {
        boolean salir = false;
        gestionarBaseDeDatos();

        while (!salir) {
            vista.mostrarMenuPrincipal();
            int opcion = vista.leerEntero(0, 2);
            switch (opcion) {
                case 1:
                    iniciarSesionAdministrador();
                    break;
                case 2:
                    iniciarSesionJugador();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Bye :3");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Configura la conexión con la base de datos remota y local. Crea o carga
     * un archivo de configuración y establece la conexión con la base de datos
     * correspondiente.
     *
     * @throws SQLException Si ocurre un error con la base de datos.
     * @throws Exception Si ocurre un error general al configurar la conexión.
     */
    public void gestionarBaseDeDatos() throws SQLException, Exception {
        vista.mostrarMensaje("Configura la conexión");
        // Crear o cargar configuración
        crearConfiguracion();
        // Conectar usando los datos cargados
        try {
            daoRemoto = DAOFactory.getDAO();
            daoLocal = new DAOLocalSQLite();
            sincronizador = new Sincronizador(daoRemoto, daoLocal);
            System.out.println("Conexión configurada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al configurar la conexión: " + e.getMessage());
        }
    }

    /**
     * Crea o carga la configuración de la base de datos desde un archivo XML.
     * Si el archivo no existe, se solicita al usuario que ingrese los datos
     * necesarios para crear una nueva configuración.
     *
     * @throws Exception Si ocurre un error al crear o cargar el archivo de
     * configuración.
     */
    private void crearConfiguracion() throws Exception {
        Scanner s = new Scanner(System.in);
        vista.mostrarMensaje("Tipo de Base de Datos Remota (1: MySQL, 2: PostgreSQL): ");
        int tipoBD = vista.leerEntero(1, 2);
        String tipoBDString = tipoBD == 1 ? "MySQL" : "PostgreSQL";

        vista.mostrarMensaje("Introduce el nombre del archivo de configuración (sin extensión): ");
        String nombreArchivo = vista.leerTexto();

        File archivo = new File(nombreArchivo + ".xml");

        // Si el archivo no existe, crear nueva configuración
        if (!archivo.exists()) {
            vista.mostrarMensaje("Archivo de configuración no encontrado. Se procederá a crear uno nuevo.");
            vista.mostrarMensaje("Introduce los siguientes datos de configuración:");

            System.out.print("Host: ");
            String host = vista.leerTexto();

            System.out.print("Puerto: ");
            int port = vista.leerEntero(0, Integer.MAX_VALUE);

            System.out.print("Usuario: ");
            String user = s.nextLine();

            System.out.print("Contraseña: ");
            String pass = s.nextLine();

            System.out.print("Nombre de jugador: ");
            String nickName = vista.leerTexto();

            Configuracion config = new Configuracion(host, port, user, pass, nickName);
            ConfiguracionBD.guardarConfiguracionEnXML(config, nombreArchivo);
            ConexionRemoto.configurarConexion(config);
            vista.mostrarMensaje("Configuración guardada y cargada correctamente.");
        } else {
            if (vista.confirmarActualizacion("El archivo ya existe. ¿Deseas actualizarlo? : ")) {
                // Si desea actualizar, pedir nuevos datos
                vista.mostrarMensaje("Introduce los nuevos datos de configuración:");

                System.out.print("Host: ");
                String host = vista.leerTexto();

                System.out.print("Puerto: ");
                int port = vista.leerEntero(0, Integer.MAX_VALUE);

                System.out.print("Usuario: ");
                String user = s.nextLine();

                System.out.print("Contraseña: ");
                String pass = s.nextLine();

                System.out.print("Nombre de jugador: ");
                String nickName = vista.leerTexto();

                // Crear una nueva configuración con los datos actualizados
                Configuracion config = new Configuracion(host, port, user, pass, nickName);
                ConfiguracionBD.guardarConfiguracionEnXML(config, nombreArchivo);
                ConexionRemoto.configurarConexion(config);
                vista.mostrarMensaje("Configuración actualizada correctamente.");
            } else {
                // Si no desea actualizar, solo cargar la configuración existente
                cargarConfiguracionDesdeArchivo(nombreArchivo + ".xml");
            }
        }

        // Establecer el tipo de base de datos
        ConexionRemoto.setActiveDatabase(tipoBDString);
    }

    /**
     * Carga la configuración de la base de datos desde un archivo XML
     * existente.
     *
     * @param archivoNombre Nombre del archivo XML que contiene la
     * configuración.
     */
    private void cargarConfiguracionDesdeArchivo(String archivoNombre) {
        try {
            // Intentar cargar la configuración desde el archivo
            Configuracion config = ConfiguracionBD.cargarDesdeXML(archivoNombre);
            ConexionRemoto.configurarConexion(config);
            vista.mostrarMensaje("Configuración cargada desde " + archivoNombre);
        } catch (Exception e) {
            // En caso de error al cargar el archivo
            vista.mostrarMensaje("Error al cargar configuración: " + e.getMessage());
        }
    }

    /**
     * Inicia la sesión del administrador, solicitando la contraseña y mostrando
     * el menú correspondiente al administrador para gestionar los videojuegos,
     * jugadores y partidas.
     *
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    private void iniciarSesionAdministrador() throws SQLException {
        System.out.println("Iniciando sesión como Administrador...");
        System.out.println("Contraseña: ");
        String cont = vista.leerTexto();
        if (cont.equals("admin")) {
            boolean salir = false;
            while (!salir) {
                vista.mostrarMenuAdministrador();
                int opcion = vista.leerEntero(0, 3);
                switch (opcion) {
                    case 1:
                        gestionarVideojuegos();
                        break;
                    case 2:
                        gestionarJugadores();
                        break;
                    case 3:
                        gestionarPartidas();
                        break;
                    case 0:
                        salir = true;
                        System.out.println("Saliendo del menú del Administrador.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        } else {
            System.out.println("Error al iniciar Administrador");
        }
    }

    /**
     * Gestiona las opciones del menú relacionadas con los videojuegos:
     * insertar, obtener, actualizar, eliminar y mostrar el top 10 de jugadores
     * para un videojuego específico.
     */
    private void gestionarVideojuegos() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenugestionarVideojuegos();
            int opcion = vista.leerEntero(0, 5); // Menú de 0 a 5
            switch (opcion) {
                case 1: // Insertar Videojuego
                try {
                    Videojuego nuevoVideojuego = vista.leerDatosVideojuego();
                    daoRemoto.insertarVideojuego(nuevoVideojuego);
                    vista.mostrarMensaje("Videojuego insertado correctamente.");
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al insertar el videojuego: " + e.getMessage());
                }
                break;

                case 2: // Obtener Top 10
                try {
                    int gameId = vista.obtenerGameId();
                    List<Jugador> top10 = daoRemoto.obtenerTop10(gameId);
                    vista.mostrarTop10(top10);
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al obtener el Top 10: " + e.getMessage());
                }
                break;

                case 3: // Obtener Videojuegos
                try {
                    List<Videojuego> videojuegos = daoRemoto.obtenerVideojuegos();
                    vista.mostrarVideojuegos(videojuegos);
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al obtener la lista de videojuegos: " + e.getMessage());
                }
                break;

                case 4: // Actualizar Videojuego
                try {
                    int gameId = vista.obtenerGameId();
                    Videojuego videojuego = daoRemoto.obtenerVideojuego(gameId);

                    if (videojuego == null) {
                        vista.mostrarMensaje("No se encontró un videojuego con ese ID.");
                        break;
                    }

                    vista.mostrarVideojuego(videojuego);

                    if (vista.confirmarActualizacion("¿Desea actualizar el título?")) {
                        System.out.print("Ingrese el nuevo título: ");
                        videojuego.setTitle(vista.leerTexto());
                    }

                    if (vista.confirmarActualizacion("¿Desea actualizar el ISBN?")) {
                        System.out.print("Ingrese el nuevo ISBN: ");
                        videojuego.setIsbn(vista.leerTexto());
                    }

                    daoRemoto.actualizarVideojuego(videojuego);
                    vista.mostrarMensaje("Videojuego actualizado correctamente.");
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al actualizar el videojuego: " + e.getMessage());
                }
                break;

                case 5: // Eliminar Videojuego
                try {
                    int gameId = vista.obtenerGameId();
                    if (vista.confirmarEliminacion(gameId)) {
                        daoRemoto.eliminarVideojuego(gameId);
                        vista.mostrarMensaje("Videojuego eliminado correctamente.");
                    } else {
                        vista.mostrarMensaje("Operación cancelada.");
                    }
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al eliminar el videojuego: " + e.getMessage());
                }
                break;

                case 0: // Salir
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Gestiona las operaciones de los jugadores, mostrando un menú y ejecutando
     * las acciones correspondientes como insertar, obtener, actualizar o
     * eliminar jugadores.
     */
    private void gestionarJugadores() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenugestionarJugadores();
            int opcion = vista.leerEntero(0, 5);
            switch (opcion) {
                case 1: // Insertar Jugador
                try {
                    Jugador nuevoJugador = vista.leerDatosJugador(); // Vista solicita los datos de un jugador
                    daoRemoto.insertarJugador(nuevoJugador); // Inserta el jugador en la base de datos
                    vista.mostrarMensaje("Jugador insertado correctamente.");
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al insertar el jugador: " + e.getMessage());
                }
                break;

                case 2: // Obtener Jugador
                try {
                    int playerId = vista.obtenerPlayerId(); // Vista solicita el ID del jugador
                    Jugador jugador = daoRemoto.obtenerJugador(playerId); // Obtiene el jugador por ID

                    if (jugador == null) {
                        vista.mostrarMensaje("No se encontró un jugador con ese ID.");
                    } else {
                        vista.mostrarJugador(jugador); // Muestra los datos del jugador obtenido
                    }
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al obtener el jugador: " + e.getMessage());
                }
                break;

                case 3: // Obtener Jugadores
                try {
                    List<Jugador> jugadores = daoRemoto.obtenerJugadores(); // Obtiene la lista de todos los jugadores
                    vista.mostrarJugadores(jugadores); // Muestra la lista de jugadores
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al obtener la lista de jugadores: " + e.getMessage());
                }
                break;

                case 4: // Actualizar Jugador
                try {
                    int playerId = vista.obtenerPlayerId(); // Vista solicita el ID del jugador
                    Jugador jugador = daoRemoto.obtenerJugador(playerId); // Obtiene el jugador por ID

                    if (jugador == null) {
                        vista.mostrarMensaje("No se encontró un jugador con ese ID.");
                        break;
                    }

                    vista.mostrarJugador(jugador); // Muestra los datos actuales del jugador

                    if (vista.confirmarActualizacion("¿Desea actualizar el nickname?")) {
                        System.out.print("Ingrese el nuevo nickname: ");
                        jugador.setNick_name(vista.leerTexto()); // Actualiza el nickname
                    }

                    daoRemoto.actualizarJugador(jugador); // Actualiza los datos del jugador en la base de datos
                    vista.mostrarMensaje("Jugador actualizado correctamente.");
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al actualizar el jugador: " + e.getMessage());
                }
                break;

                case 5: // Eliminar Jugador
                try {
                    int playerId = vista.obtenerPlayerId(); // Vista solicita el ID del jugador
                    if (vista.confirmarEliminacion(playerId)) {
                        daoRemoto.eliminarJugador(playerId); // Elimina el jugador en la base de datos
                        vista.mostrarMensaje("Jugador eliminado correctamente.");
                    } else {
                        vista.mostrarMensaje("Operación cancelada.");
                    }
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al eliminar el jugador: " + e.getMessage());
                }
                break;

                case 0: // Salir
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Gestiona las operaciones de las partidas, mostrando un menú y ejecutando
     * las acciones correspondientes como insertar, obtener, actualizar o
     * eliminar partidas.
     */
    private void gestionarPartidas() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenugestionarPartidas();
            int opcion = vista.leerEntero(0, 5); // Menú de 0 a 5
            switch (opcion) {
                case 1: // Insertar Partida
                try {
                    Partida nuevaPartida = vista.leerDatosPartida();
                    int sessionId = daoRemoto.insertarpartida(nuevaPartida);
                    vista.mostrarMensaje("Partida insertada correctamente. ID de sesión: " + sessionId);
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al insertar la partida: " + e.getMessage());
                }
                break;

                case 2: // Obtener Partida
                try {
                    int gameId = vista.obtenerGameId();
                    int playerId = vista.obtenerPlayerId();
                    Partida partida = daoRemoto.obtenerPartida(gameId, playerId);

                    if (partida == null) {
                        vista.mostrarMensaje("No se encontró una partida con ese ID de videojuego y jugador.");
                    } else {
                        vista.mostrarPartida(partida);
                    }
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al obtener la partida: " + e.getMessage());
                }
                break;

                case 3: // Obtener Partidas
                try {
                    int playerId = vista.obtenerPlayerId();
                    List<Partida> partidas = daoRemoto.obtenerPartidas(playerId);
                    vista.mostrarPartidas(partidas);
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al obtener las partidas: " + e.getMessage());
                }
                break;

                case 4: // Actualizar Partida
                try {
                    int sessionId = vista.obtenerSessionId();
                    Partida partida = daoRemoto.obtenerPartidaPorSessionId(sessionId);

                    if (partida == null) {
                        vista.mostrarMensaje("No se encontró una partida con ese ID de sesión.");
                        break;
                    }

                    vista.mostrarPartida(partida);

                    if (vista.confirmarActualizacion("¿Desea actualizar la experiencia?")) {
                        System.out.print("Ingrese la nueva experiencia: ");
                        partida.setExperience(vista.leerEntero(0, Integer.MAX_VALUE));
                    }

                    if (vista.confirmarActualizacion("¿Desea actualizar el nivel de vida?")) {
                        System.out.print("Ingrese el nuevo nivel de vida: ");
                        partida.setLife_level(vista.leerEntero(0, Integer.MAX_VALUE));
                    }

                    if (vista.confirmarActualizacion("¿Desea actualizar las monedas?")) {
                        System.out.print("Ingrese el nuevo número de monedas: ");
                        partida.setCoins(vista.leerEntero(0, Integer.MAX_VALUE));
                    }
                    daoRemoto.actualizarPartida(partida);
                    vista.mostrarMensaje("Partida actualizada correctamente.");
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al actualizar la partida: " + e.getMessage());
                }
                break;

                case 5: // Eliminar Partida
                try {
                    int sessionId = vista.obtenerSessionId();
                    if (vista.confirmarEliminacion(sessionId)) {
                        daoRemoto.eliminarPartida(sessionId);
                        vista.mostrarMensaje("Partida eliminada correctamente.");
                    } else {
                        vista.mostrarMensaje("Operación cancelada.");
                    }
                } catch (SQLException e) {
                    vista.mostrarMensaje("Error al eliminar la partida: " + e.getMessage());
                }
                break;

                case 0: // Volver
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Inicia sesión como jugador, solicita el ID del jugador y muestra el menú
     * para elegir las opciones como jugar, obtener partidas o acceder a
     * opciones.
     *
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void iniciarSesionJugador() throws SQLException {
        vista.mostrarMensaje("Iniciando sesión como Jugador...");
        int playerId = vista.obtenerPlayerId();

        Jugador jugador = daoRemoto.obtenerJugador(playerId);
        if (jugador != null) {
            vista.mostrarMensaje("¡Bienvenido, " + jugador.getNick_name() + "!");
            sincronizador.sincronizarDesdeRemotoALocal(playerId);
            configuracion = new ConfiguracionJugador(1, false, "1920x1080", "Español");
            daoLocal.insertarConfiguracion(configuracion);
            boolean salir = false;
            while (!salir) {
                vista.mostrarMenuJugador();
                int opcion = vista.leerEntero(0, 3); // Opciones del menú del jugador
                switch (opcion) {
                    case 1:
                        menuJugar(playerId);//Jugar
                        break;
                    case 2:
                        obtenerPartidas();//Obtener Partidas
                        break;
                    case 3:
                        menuOpciones();//Opciones
                        break;
                    case 0:
                        salir = true;
                        vista.mostrarMensaje("Cerrando sesión del jugador.");
                        break;
                    default:
                        vista.mostrarMensaje("Opción no válida.");
                }
            }
            sincronizador.sincronizarDesdeLocalARemoto(daoLocal.obtenerPartidasJugadorLocal());
        } else {
            vista.mostrarMensaje("Jugador no encontrado o credenciales incorrectas.");
        }
    }

    /**
     * Muestra el menú para jugar, donde el jugador puede seleccionar opciones
     * como obtener videojuegos, iniciar una nueva partida, continuar una
     * partida existente o eliminar una partida.
     *
     * @param playerId El ID del jugador que está iniciando sesión.
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void menuJugar(int playerId) throws SQLException {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuJugar();
            int opcion = vista.leerEntero(0, 4);
            switch (opcion) {
                case 1:
                    obtenerVideojuegos();
                    break;
                case 2:
                    nuevaPartida(playerId);
                    break;
                case 3:
                    continuarPartida(playerId);
                    break;
                case 4:
                    eliminarPartida();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Obtiene y muestra los videojuegos disponibles desde el sistema remoto.
     *
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void obtenerVideojuegos() throws SQLException {
        List<Videojuego> videojuegos = daoRemoto.obtenerVideojuegos();//Pregunta los juegos disponibles en remoto
        if (videojuegos.isEmpty()) {
            vista.mostrarMensaje("No se han encontrado videojuegos :c");
        } else {
            vista.mostrarVideojuegos(videojuegos);
        }
    }

    /**
     * Crea una nueva partida para el jugador especificado, pidiendo el ID del
     * videojuego y configurando los parámetros iniciales de la partida.
     *
     * @param playerId El ID del jugador que está iniciando una nueva partida.
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void nuevaPartida(int playerId) throws SQLException {
        int gameId = vista.obtenerGameId(); // Pedir el ID del juego para la nueva partida
        Partida nuevaPartida = new Partida();
        nuevaPartida.setGame_id(gameId);
        nuevaPartida.setPlayer_id(playerId);
        nuevaPartida.setExperience(0);
        nuevaPartida.setLife_level(0);
        nuevaPartida.setCoins(0);
        nuevaPartida.setSession_count(1);//La sesion de ahora
        daoLocal.insertarEstadoPartidaJugadorLocal(nuevaPartida);
        vista.mostrarMensaje("Nueva partida creada.");
        vista.mostrarMensaje(nuevaPartida.toString());
        jugar(nuevaPartida.getPlayer_id(), nuevaPartida.getGame_id());//El juego
    }

    /**
     * Permite al jugador continuar una partida previamente guardada, mostrando
     * las partidas disponibles y solicitando al jugador que seleccione la
     * partida a continuar.
     *
     * @param playerId El ID del jugador que desea continuar una partida.
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void continuarPartida(int player_id) throws SQLException {
        List<Partida> partidas = daoLocal.obtenerPartidasJugadorLocal();
        if (partidas.isEmpty()) {
            vista.mostrarMensaje("No tienes partidas guardadas.");
        } else {
            vista.mostrarPartidas(partidas);
            int game_id = vista.obtenerGameId();  // Obtiene el ID del videojuego de la vista

            Partida partida = daoLocal.obtenerPartidaJugadorLocal(player_id, game_id);

            if (partida != null) {
                jugar(partida.getPlayer_id(), partida.getGame_id());
            } else {
                vista.mostrarMensaje("Partida no encontrada.");
            }
        }
    }

    /**
     * Elimina una partida guardada, solicita al jugador seleccionar una partida
     * y confirma la eliminación.
     *
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void eliminarPartida() throws SQLException {
        List<Partida> partidas = daoLocal.obtenerPartidasJugadorLocal();
        if (partidas.isEmpty()) {
            vista.mostrarMensaje("No tienes partidas guardadas.");
        } else {
            vista.mostrarPartidas(partidas); // Partidas disponibles
            int sessionId = vista.obtenerSessionId(); // Pedir al usuario qué partida eliminar
            Partida partida = daoLocal.obtenerPartidaJugadorLocalID(sessionId);

            if (partida != null) {
                if (vista.confirmarEliminacion(sessionId)) {
                    // Eliminar la partida seleccionada
                    daoLocal.eliminarPartidaJugadorLocal(sessionId);
                    vista.mostrarMensaje("Partida eliminada.");
                }
            } else {
                vista.mostrarMensaje("Partida no encontrada.");
            }
        }
    }

    /**
     * Obtiene y muestra todas las partidas guardadas para el jugador.
     *
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void obtenerPartidas() throws SQLException {
        List<Partida> partidas = daoLocal.obtenerPartidasJugadorLocal();
        if (partidas.isEmpty()) {
            vista.mostrarMensaje("No tienes partidas guardadas.");
        } else {
            vista.mostrarPartidas(partidas);
        }
        partidas = null;
    }

    /**
     * Muestra el menú de opciones donde el jugador puede configurar sonido,
     * resolución, idioma o ver la configuración.
     *
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void menuOpciones() throws SQLException {

        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuOpciones();
            int opcion = vista.leerEntero(0, 4);
            switch (opcion) {
                case 1:
                    configurarSonido();
                    break;
                case 2:
                    configurarResolucion();
                    break;
                case 3:
                    configurarIdioma();
                    break;
                case 4:
                    verConfiguracion(1);
                    break;
                case 0:
                    salir = true;
                    vista.mostrarMensaje("Volviendo al menú anterior.");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Configura el sonido habilitado o deshabilitado. Actualiza la
     * configuración de sonido en la base de datos.
     *
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    private void configurarSonido() throws SQLException {
        boolean sonido = vista.confirmarActualizacion("¿Deseas modificar el sónido");
        vista.mostrarMensaje("Sonido " + (sonido ? "activado" : "desactivado"));
        configuracion.setSoundEnabled(sonido);
        daoLocal.actualizarConfiguracion(configuracion);

    }

    /**
     * Configura la resolución del jugador.
     *
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    private void configurarResolucion() throws SQLException {
        String resolucion = vista.configurarResolucion();
        vista.mostrarMensaje("Resolución configurada a: " + resolucion);
        configuracion.setResolution(resolucion);
        daoLocal.actualizarConfiguracion(configuracion);
    }

    /**
     * Configura el idioma del jugador.
     *
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    private void configurarIdioma() throws SQLException {
        String idioma = vista.configurarIdioma();
        vista.mostrarMensaje("Idioma configurado a: " + idioma);
        configuracion.setLanguage(idioma);
        daoLocal.actualizarConfiguracion(configuracion);
    }

    /**
     * Muestra la configuración actual del jugador.
     *
     * @param configId El ID de la configuración que queremos mostrar.
     * @throws SQLException si ocurre un error al interactuar con la base de
     * datos.
     */
    private void verConfiguracion(int configId) throws SQLException {
        ConfiguracionJugador configuracion = daoLocal.obtenerConfiguracionPorId(configId);
        if (configuracion != null) {
            vista.mostrarMensaje("Configuración actual:");
            vista.mostrarMensaje("Sonido: " + (configuracion.isSoundEnabled() ? "Activado" : "Desactivado"));
            vista.mostrarMensaje("Resolución: " + configuracion.getResolution());
            vista.mostrarMensaje("Idioma: " + configuracion.getLanguage());
        } else {
            vista.mostrarMensaje("No se ha encontrado la configuración.");
        }
    }

    /**
     * Inicia una sesión de juego, mostrando el menú del juego y gestionando las
     * opciones del jugador. Permite modificar el estado de la partida o
     * terminar el juego.
     *
     * @param player_id El ID del jugador.
     * @param game_id El ID del videojuego.
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void jugar(int player_id, int game_id) throws SQLException {
        boolean seguirJugando = true;
        Partida partida = daoLocal.obtenerPartidaJugadorLocal(player_id, game_id);

        // Incrementar y persistir session_count
        int aux = partida.getSession_count();
        partida.setSession_count(aux + 1);
        daoLocal.actualizarEstadoPartidaJugadorLocal(partida);
        sincronizarPartida(partida); // Refrescar objeto después de la persistencia

        while (seguirJugando) {
            vista.mostrarMenuJuego(partida);
            int opcion = vista.leerEntero(0, 3);
            switch (opcion) {
                case 1:
                    modificarPartida(partida); // Modificar los valores de la partida
                    sincronizarPartida(partida);
                    break;
                case 2:
                    vista.mostrarEstadoPartida(partida); // Muestra el estado de la partida
                    break;
                case 0:
                    seguirJugando = false;
                    vista.mostrarMensaje("Terminando juego.");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Modifica el estado de una partida, permitiendo al jugador actualizar su
     * experiencia, nivel de vida y monedas.
     *
     * @param partida La partida que se va a modificar.
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void modificarPartida(Partida partida) throws SQLException {
        vista.mostrarPartida(partida);
        if (vista.confirmarActualizacion("¿Desea actualizar la experiencia?")) {
            System.out.print("Ingrese la nueva experiencia: ");
            partida.setExperience(vista.leerEntero(0, Integer.MAX_VALUE));
        }
        if (vista.confirmarActualizacion("¿Desea actualizar el nivel de vida?")) {
            System.out.print("Ingrese el nuevo nivel de vida: ");
            partida.setLife_level(vista.leerEntero(0, Integer.MAX_VALUE));
        }
        if (vista.confirmarActualizacion("¿Desea actualizar las monedas?")) {
            System.out.print("Ingrese el nuevo número de monedas: ");
            partida.setCoins(vista.leerEntero(0, Integer.MAX_VALUE));
        }
        daoLocal.actualizarEstadoPartidaJugadorLocal(partida); // Actualiza el estado del juego
        vista.mostrarMensaje("Partida actualizada.");
    }

    /**
     * Sincroniza el estado de una partida con la base de datos local,
     * actualizando los valores del objeto partida.
     *
     * @param partida La partida que se va a sincronizar.
     * @throws SQLException Si ocurre un error de base de datos durante el
     * proceso.
     */
    private void sincronizarPartida(Partida partida) throws SQLException {
        Partida partidaActualizada = daoLocal.obtenerPartidaJugadorLocal(partida.getPlayer_id(), partida.getGame_id());
        partida.setExperience(partidaActualizada.getExperience());
        partida.setLife_level(partidaActualizada.getLife_level());
        partida.setCoins(partidaActualizada.getCoins());
        partida.setSession_count(partidaActualizada.getSession_count());
        partida.setSession_date(partidaActualizada.getSession_date());
    }
}
