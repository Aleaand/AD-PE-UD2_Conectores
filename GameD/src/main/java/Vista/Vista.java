/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Jugador;
import Modelo.Partida;
import Modelo.Videojuego;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alejandra
 */
public class Vista {

    private Scanner scanner;

    /**
     * Constructor de la clase Vista. Inicializa el escáner para la entrada del
     * usuario.
     */
    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Lee un número entero desde la entrada estándar, asegurándose de que esté
     * dentro del rango especificado.
     *
     * @param min El valor mínimo permitido para el número.
     * @param max El valor máximo permitido para el número.
     * @return El número entero ingresado por el usuario.
     */
    public int leerEntero(int min, int max) {
        int numero = -1;
        while (numero < min || numero > max) {
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                if (numero < min || numero > max) {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }
            scanner.nextLine();
        }
        return numero;
    }

    /**
     * Lee una línea de texto desde la entrada estándar.
     *
     * @return La cadena de texto ingresada por el usuario.
     */
    public String leerTexto() {
        String entrada;
        while (true) {
            entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            } else {
                System.out.println("La entrada no puede estar vacía. Intente nuevamente.");
            }
        }
    }

    /**
     * Muestra un mensaje al usuario en la consola.
     *
     * @param mensaje El mensaje que se mostrará.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra un menú para seleccionar el tipo de base de datos.
     *
     * @return La opción seleccionada por el usuario.
     */
    public int mostrarMenuBaseDeDatos() {
        System.out.println("Selecciona el tipo de base de datos:");
        System.out.println("1. MySQL");
        System.out.println("2. PostgreSQL");
        System.out.print("Seleccione una opción: ");
        return leerEntero(1, 2);
    }

    /**
     * Muestra el menú principal con opciones para iniciar sesión como
     * administrador o jugador.
     */
    public void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Iniciar sesión como Administrador");
        System.out.println("2. Iniciar sesión como Jugador");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones para el administrador.
     */
    public void mostrarMenuAdministrador() {
        System.out.println("\n--- Menú Administrador ---");
        System.out.println("1. Gestión de Videojuegos");
        System.out.println("2. Gestión de Jugadores");
        System.out.println("3. Gestión de Partidas");
        System.out.println("0. Salir del Menú Administrador");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones para gestionar videojuegos.
     */
    public void mostrarMenugestionarVideojuegos() {
        System.out.println("=== Gestión de Videojuegos ===");
        System.out.println("1. Insertar Videojuego");
        System.out.println("2. Obtener Top 10");
        System.out.println("3. Obtener Videojuegos");
        System.out.println("4. Actualizar Videojuego");
        System.out.println("5. Eliminar Videojuego");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones para gestionar jugadores.
     */
    public void mostrarMenugestionarJugadores() {
        System.out.println("=== Gestión de Jugador ===");
        System.out.println("1. Insertar Jugador");
        System.out.println("2. Obtener Jugador");
        System.out.println("3. Obtener Jugadores");
        System.out.println("4. Actualizar Jugador");
        System.out.println("5. Eliminar Jugador");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones para gestionar partidas.
     */
    public void mostrarMenugestionarPartidas() {
        System.out.println("=== Gestión de Partida ===");
        System.out.println("1. Insertar Partida");
        System.out.println("2. Obtener Partida");
        System.out.println("3. Obtener Partidas");
        System.out.println("4. Actualizar Partida");
        System.out.println("5. Eliminar Partida");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones del jugador, permitiendo elegir entre jugar,
     * obtener partidas u opciones.
     */
    public void mostrarMenuJugador() {
        System.out.println("\n--- Menú Jugador ---");
        System.out.println("1. Jugar");
        System.out.println("2. Obtener Partidas");
        System.out.println("3. Opciones");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Lee los datos necesarios para crear un nuevo videojuego, como el título y
     * el ISBN.
     *
     * @return Un objeto Videojuego con los datos ingresados por el usuario.
     */
    public Videojuego leerDatosVideojuego() {
        System.out.println("=== Insertar Nuevo Videojuego ===");
        Videojuego videojuego = new Videojuego();
        System.out.println("Ingrese el título del videojuego:");
        videojuego.setTitle(leerTexto());
        System.out.println("Ingrese el ISBN del videojuego:");
        videojuego.setIsbn(leerTexto());
        return videojuego;
    }

    /**
     * Solicita al usuario el ID de un videojuego.
     *
     * @return El ID del videojuego ingresado por el usuario.
     */
    public int obtenerGameId() {
        System.out.println("Ingrese el ID del videojuego:");
        return leerEntero(1, Integer.MAX_VALUE);
    }

    /**
     * Muestra los jugadores que conforman el Top 10 de un videojuego.
     *
     * @param top10 La lista de los 10 mejores jugadores.
     */
    public void mostrarTop10(List<Jugador> top10) {
        System.out.println("=== Top 10 Jugadores ===");
        if (top10.isEmpty()) {
            System.out.println("No hay jugadores en el Top 10 para este videojuego.");
        } else {
            for (Jugador jugador : top10) {
                System.out.println(jugador); // Suponiendo que Jugador tiene un toString adecuado.
            }
        }
    }

    /**
     * Muestra la lista de videojuegos disponibles.
     *
     * @param videojuegos La lista de videojuegos a mostrar.
     */
    public void mostrarVideojuegos(List<Videojuego> videojuegos) {
        System.out.println("=== Lista de Videojuegos ===");
        if (videojuegos.isEmpty()) {
            System.out.println("No hay videojuegos registrados.");
        } else {
            for (Videojuego videojuego : videojuegos) {
                System.out.println(videojuego); // Suponiendo que Videojuego tiene un toString adecuado.
            }
        }
    }

    /**
     * Muestra la información detallada de un videojuego.
     *
     * @param videojuego El videojuego cuya información se mostrará.
     */
    public void mostrarVideojuego(Videojuego videojuego) {
        System.out.println("=== Información del Videojuego ===");
        if (videojuego == null) {
            System.out.println("El videojuego no existe.");
        } else {
            System.out.println("ID: " + videojuego.getGame_id());
            System.out.println("Título: " + videojuego.getTitle());
            System.out.println("ISBN: " + videojuego.getIsbn());
            System.out.println("Cantidad de jugadores: " + videojuego.getPlayer_count());
            System.out.println("Total de sesiones: " + videojuego.getTotal_sessions());
            System.out.println("Última sesión: " + videojuego.getLast_session());
        }
    }

    /**
     * Solicita al usuario confirmar una actualización con una respuesta sí o
     * no.
     *
     * @param mensaje El mensaje que se mostrará antes de la confirmación.
     * @return true si el usuario responde afirmativamente, false en caso
     * contrario.
     */
    public boolean confirmarActualizacion(String mensaje) {
        System.out.println(mensaje + " (s/n):");
        String respuesta = leerTexto().toLowerCase();
        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.println("Entrada inválida. Ingrese 's' para sí o 'n' para no:");
            respuesta = leerTexto().toLowerCase();
        }
        return respuesta.equals("s");
    }

    /**
     * Solicita la confirmación del usuario para eliminar un videojuego
     * identificado por su ID.
     *
     * @param gameId El ID del videojuego a eliminar.
     * @return true si el usuario confirma la eliminación, false si no lo hace.
     */
    public boolean confirmarEliminacion(int gameId) {
        System.out.println("¿Está seguro de eliminar: ID " + gameId + "? (s/n):");
        String respuesta = leerTexto().toLowerCase();
        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.println("Entrada inválida. Ingrese 's' para sí o 'n' para no:");
            respuesta = leerTexto().toLowerCase();
        }
        return respuesta.equals("s");
    }

    /**
     * Solicita los datos necesarios para crear un nuevo jugador, como el
     * nickname.
     *
     * @return Un objeto Jugador con los datos ingresados por el usuario.
     */
    public Jugador leerDatosJugador() {
        System.out.println("=== Insertar Jugador ===");
        Jugador jugador = new Jugador();

        System.out.print("Ingrese el nickname del jugador: ");
        jugador.setNick_name(leerTexto());

        return jugador;
    }

    /**
     * Solicita al usuario el ID del jugador.
     *
     * @return El ID del jugador ingresado por el usuario.
     */
    public int obtenerPlayerId() {
        System.out.print("Ingrese el ID del jugador: ");
        return leerEntero(1, Integer.MAX_VALUE); // Suponemos IDs positivos
    }

    /**
     * Muestra los detalles de un jugador.
     *
     * @param jugador El objeto Jugador cuyos detalles se mostrarán.
     */
    public void mostrarJugador(Jugador jugador) {
        System.out.println("\n=== Datos del Jugador ===");
        System.out.println("ID: " + jugador.getPlayer_id());
        System.out.println("Nickname: " + jugador.getNick_name());
        System.out.println("=========================");
    }

    /**
     * Muestra la lista de jugadores registrados.
     *
     * @param jugadores La lista de jugadores a mostrar.
     */
    public void mostrarJugadores(List<Jugador> jugadores) {
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        System.out.println("\n=== Lista de Jugadores ===");
        for (Jugador jugador : jugadores) {
            System.out.println("ID: " + jugador.getPlayer_id() + ", Nickname: " + jugador.getNick_name());
        }
        System.out.println("==========================");
    }

    /**
     * Solicita los datos necesarios para crear una nueva partida, como el ID
     * del videojuego, ID del jugador, experiencia, nivel de vida, y monedas.
     *
     * @return Un objeto Partida con los datos ingresados por el usuario.
     */
    public Partida leerDatosPartida() {
        Partida partida = new Partida();

        try {
            // Ingreso del ID del videojuego
            System.out.print("Ingrese el ID del videojuego: ");
            partida.setGame_id(leerEntero(0, Integer.MAX_VALUE));
            // Ingreso del ID del jugador
            System.out.print("Ingrese el ID del jugador: ");
            partida.setPlayer_id(leerEntero(0, Integer.MAX_VALUE));
            // Ingreso de la experiencia
            System.out.print("Ingrese la experiencia del jugador: ");
            partida.setExperience(leerEntero(0, Integer.MAX_VALUE));
            // Ingreso del nivel de vida
            System.out.print("Ingrese el nivel de vida del jugador: ");
            partida.setLife_level(leerEntero(0, Integer.MAX_VALUE));
            // Ingreso de las monedas
            System.out.print("Ingrese la cantidad de monedas del jugador: ");
            partida.setCoins(leerEntero(0, Integer.MAX_VALUE));
            // Establecer la fecha de la sesión como la fecha actual
            partida.setSession_date(new Timestamp(System.currentTimeMillis())); // Establece la fecha actual
            partida.setSession_count(1);
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida. Por favor ingrese un número entero.");
            scanner.nextLine();  // Limpiar el buffer en caso de error de tipo numérico
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Formato de fecha inválido.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        if (partida != null) {
            System.out.println("Partida creada correctamenta");
        } else {
            System.out.println("No se ha creado la partida correctamenta");
        }

        return partida;  // Devolvemos la partida con los datos ingresados
    }

    /**
     * Muestra los detalles de una partida.
     *
     * @param partida El objeto Partida cuyos detalles se mostrarán.
     */
    public void mostrarPartida(Partida partida) {
        if (partida != null) {
            System.out.println("Detalles de la Partida:");
            System.out.println("ID de la sesión: " + partida.getSession_id());
            System.out.println("ID del videojuego: " + partida.getGame_id());
            System.out.println("ID del jugador: " + partida.getPlayer_id());
            System.out.println("Experiencia: " + partida.getExperience());
            System.out.println("Nivel de vida: " + partida.getLife_level());
            System.out.println("Monedas: " + partida.getCoins());
            System.out.println("Número de sesiones: " + partida.getSession_count());
            System.out.println("Fecha de la sesión: " + partida.getSession_date());

        } else {
            System.out.println("La partida no existe.");
        }
    }

    /**
     * Muestra la lista de partidas registradas.
     *
     * @param partidas La lista de partidas a mostrar.
     */
    public void mostrarPartidas(List<Partida> partidas) {
        if (partidas != null && !partidas.isEmpty()) {
            System.out.println("Lista de Partidas:");
            for (Partida partida : partidas) {
                mostrarPartida(partida);
                System.out.println("=========================");
            }
        } else {
            System.out.println("No se encontraron partidas.");
        }
    }

    /**
     * Solicita al usuario el ID de una partida.
     *
     * @return El ID de la partida ingresado por el usuario.
     */
    public int obtenerSessionId() {
        System.out.print("Ingrese el ID de la Partida: ");
        return scanner.nextInt();
    }

    /**
     * Muestra el menú de opciones para jugar, con opciones para obtener
     * videojuegos, iniciar una nueva partida, continuar una partida existente o
     * eliminar una partida.
     */
    public void mostrarMenuJugar() {
        System.out.println("===== Menú Jugar =====");
        System.out.println("1. Obtener Videojuegos");
        System.out.println("2. Nueva Partida");
        System.out.println("3. Continuar Partida");
        System.out.println("4. Eliminar Partida");
        System.out.println("0. Volver");
    }

    /**
     * Muestra el menú de opciones de configuración, como sonido, resolución,
     * idioma y ver configuración.
     */
    public void mostrarMenuOpciones() {
        System.out.println("===== Opciones =====");
        System.out.println("1. Configurar Sonido");
        System.out.println("2. Configurar Resolución");
        System.out.println("3. Configurar Idioma");
        System.out.println("4. Ver configuración");
        System.out.println("0. Volver");
    }

    /**
     * Muestra el menú de opciones de configuración, como sonido, resolución,
     * idioma y ver configuración.
     */
    public String configurarResolucion() {
        System.out.println("Selecciona la resolución:");
        System.out.println("1. 1920x1080");
        System.out.println("2. 1280x720");
        System.out.println("3. 800x600");
        System.out.print("Opción: ");
        int opcion = new java.util.Scanner(System.in).nextInt();
        switch (opcion) {
            case 1:
                return "1920x1080";
            case 2:
                return "1280x720";
            case 3:
                return "800x600";
            default:
                return "1920x1080"; // Valor por defecto
        }
    }

    /**
     * Solicita al usuario seleccionar un idioma de las opciones disponibles.
     *
     * @return El idioma seleccionado por el usuario.
     */
    public String configurarIdioma() {
        System.out.println("Selecciona el idioma:");
        System.out.println("1. Español");
        System.out.println("2. Inglés");
        System.out.println("3. Francés");
        System.out.print("Opción: ");
        int opcion = new java.util.Scanner(System.in).nextInt();
        switch (opcion) {
            case 1:
                return "Español";
            case 2:
                return "Inglés";
            case 3:
                return "Francés";
            default:
                return "Español"; // Valor por defecto
        }
    }

    /**
     * Muestra el menú de juego en progreso, con opciones para modificar la
     * partida o ver el estado de la partida.
     *
     * @param partida La partida en progreso que se está jugando.
     */
    public void mostrarMenuJuego(Partida partida) {
        System.out.println("===== Juego en Progreso =====");
        System.out.println("1. Modificar Partida");
        System.out.println("2. Estado de Partida");
        System.out.println("0. Salir");
    }

    /**
     * Muestra el estado actual de una partida, incluyendo detalles como ID de
     * la partida, experiencia, nivel de vida, monedas, y fecha de la sesión.
     *
     * @param partida La partida cuyo estado se mostrará.
     */
    public void mostrarEstadoPartida(Partida partida) {
        System.out.println("===== Estado de la Partida =====");
        System.out.println("ID Partida: " + partida.getSession_id());
        System.out.println("Juego ID: " + partida.getGame_id());
        System.out.println("Experiencia: " + partida.getExperience());
        System.out.println("Nivel de Vida: " + partida.getLife_level());
        System.out.println("Monedas: " + partida.getCoins());
        System.out.println("Fecha de la sesión: " + partida.getSession_date());
    }

    /**
     * Solicita al usuario ingresar la nueva experiencia de un jugador.
     *
     * @return La nueva experiencia ingresada por el usuario.
     */
    public int obtenerNuevaExperiencia() {
        System.out.print("Introduce la nueva experiencia: ");
        return new java.util.Scanner(System.in).nextInt();
    }

    /**
     * Solicita al usuario ingresar el nuevo nivel de vida de un jugador.
     *
     * @return El nuevo nivel de vida ingresado por el usuario.
     */
    public int obtenerNuevoNivelVida() {
        System.out.print("Introduce el nuevo nivel de vida: ");
        return new java.util.Scanner(System.in).nextInt();
    }

    /**
     * Solicita al usuario ingresar el nuevo número de monedas de un jugador.
     *
     * @return El nuevo número de monedas ingresado por el usuario.
     */
    public int obtenerNuevasMonedas() {
        System.out.print("Introduce el nuevo número de monedas: ");
        return new java.util.Scanner(System.in).nextInt();
    }
}
