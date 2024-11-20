# Game Developers
Es una aplicación de consola que permite gestionar videojuegos, jugadores y partidas a través de un sistema de menús interactivos. Esta aplicación está diseñada para funcionar con bases de datos remotas y locales, y permite a los administradores y jugadores interactuar con el sistema en diferentes niveles.

## Requisitos del Sistema

- **Java JDK 20** o superior.
- **Apache Maven**.
- **NetBeans IDE 17** (opcional, pero recomendado para facilitar el desarrollo).
- **Base de Datos Remota** (MySQL o PostgreSQL).

## Instrucciones de Uso

### 1. Configuración Inicial

Antes de ejecutar el programa, asegúrate de tener configurada la conexión a la base de datos.

Configuración de Base de Datos

1. Se te pedirá que configures la base de datos. Selecciona el tipo de base de datos remota (MySQL o PostgreSQL), ingresa el nombre del archivo de configuración (sin extensión), y elige si deseas actualizar el archivo si ya existe o en caso de que no puedes configurarla por defecto tienes XMLMySQL y XMLPostgre. Después la configuración de la conexión se cargará desde el archivo seleccionado.

### 2. Menú Principal

Al iniciar el programa, verás el **Menú Principal** con las siguientes opciones:

1. **Iniciar sesión como Administrador**: Accede al sistema con privilegios de administrador. Clave: admin
2. **Iniciar sesión como Jugador**: Accede al sistema con privilegios de jugador. Con tu ID de jugador
3. **Salir**: Cierra la aplicación.


### 3. Menú Administrador

Si seleccionas la opción **Iniciar sesión como Administrador**, se te pedirá la contraseña de administrador (por defecto, **admin**). Una vez iniciada la sesión, tendrás acceso al **Menú Administrador** con las siguientes opciones:

1. **Gestión de Videojuegos**: Insertar, actualizar, eliminar o consultar videojuegos.
2. **Gestión de Jugadores**: Insertar, actualizar, eliminar o consultar jugadores.
3. **Gestión de Partidas**: Insertar, actualizar, eliminar o consultar partidas.
4. **Salir del Menú Administrador**: Cierra la sesión de administrador y vuelve al menú principal.

### 4. Menú Jugador

Si seleccionas la opción **Iniciar sesión como Jugador**, se te pedirá ingresar tu ID de jugador. Una vez iniciado sesión, tendrás acceso al **Menú Jugador** con las siguientes opciones:

1. **Jugar**: Iniciar una nueva partida, continuar una partida existente o eliminar partidas.
2. **Obtener Partidas**: Ver las partidas asociadas al jugador.
3. **Opciones**: Configurar sonido, resolución o idioma del juego.
4. **Volver**: Regresar al menú principal.


### 5. Opciones de Juego

En el menú **Jugar**, se pueden realizar las siguientes acciones:

- **Obtener Videojuegos**: Consulta los videojuegos disponibles.
- **Nueva Partida**: Crea una nueva partida.
- **Continuar Partida**: Continúa una partida existente.
- **Eliminar Partida**: Elimina una partida previamente registrada.
  
Al elegir **Continuar Partida**, se mostrarán las partidas disponibles y podrás seleccionar una para continuar.

### 6. Opciones de Configuración

En el menú de **Opciones**, puedes configurar:

- **Sonido**: Ajustar el volumen.
- **Resolución**: Seleccionar la resolución del juego (Ej. 1920x1080, 1280x720, 800x600).
- **Idioma**: Elegir el idioma (Español, Inglés, Francés).
  
### 7. Sincronización de Datos

El sistema soporta sincronización de datos entre la base de datos local y remota. Las partidas se sincronizan automáticamente al iniciar y cerrar sesión como jugador.

### 8. Salir

Para salir de la aplicación, selecciona la opción de salir en el **Menú Principal**. El sistema realizará la sincronización final y luego cerrará la sesión.


### 9. Ejemplo de Flujo de Trabajo

1. Inicia sesión como **Administrador** y gestiona videojuegos, jugadores y partidas.
2. Inicia sesión como **Jugador** e interactúa con tus partidas: jugar, continuar o eliminar partidas.
3. Configura las opciones del juego y consulta los videojuegos disponibles.
4. Sal de la aplicación cuando termines.

---
# Backend de la Aplicación

Permite gestionar jugadores, partidas y videojuegoss. La aplicación está conectada a una base de datos que puede ser MySQL o PostgreSQL. 

### Tipo de Base de Datos
Puedes elegir entre MySQL o PostgreSQL para la base de datos de la aplicación. Ambas opciones están soportadas, y la configuración depende de tu elección de base de datos al momento de la implementación.

### Funcionalidades del Backend

#### **1. Sesión como Administrador**

**Menú del Administrador:**
- **Gestionar Jugador**
  - **Insertar Jugador**: Permite agregar un nuevo jugador.
  - **Obtener Jugador**: Permite obtener los detalles de un jugador específico.
  - **Obtener Jugadores**: Permite obtener todos los jugadores registrados.
  - **Actualizar Jugador**: Permite actualizar la información de un jugador.
  - **Eliminar Jugador**: Permite eliminar a un jugador del sistema.
  - **Volver**: Regresa al menú principal del administrador.

- **Gestionar Partida**
  - **Insertar Partida**: Permite agregar una nueva partida, vinculando un jugador, un videojuego, y sus estadísticas (experiencia, monedas, etc.).
  - **Obtener Partida**: Permite obtener los detalles de una partida específica.
  - **Obtener Partidas**: Permite obtener una lista de todas las partidas.
  - **Actualizar Partida**: Permite modificar los datos de una partida existente.
  - **Eliminar Partida**: Permite eliminar una partida.
  - **Volver**: Regresa al menú principal del administrador.

- **Gestionar Videojuego**
  - **Insertar VideoJuego**: Permite agregar un nuevo videojuego al sistema.
  - **Obtener Top 10**: Permite obtener los 10 videojuegos más populares o relevantes.
  - **Obtener VideoJuegos**: Permite obtener todos los videojuegos registrados.
  - **Actualizar VideoJuego**: Permite modificar los detalles de un videojuego.
  - **Eliminar VideoJuego**: Permite eliminar un videojuego del sistema.
  - **Volver**: Regresa al menú principal del administrador.

- **Volver**: Opción para regresar al menú principal del sistema.

---

# **Base de Datos**

Este proyecto implementa una solución para gestionar videojuegos, jugadores y sus partidas. La arquitectura incluye bases de datos remotas (**MySQL** y **PostgreSQL**) para centralizar datos y una base de datos local (**SQLite**) para gestionar el progreso del jugador de manera offline.

## **Base de Datos(MySQL y PostgreSQL)**

La base de datos está diseñada para cumplir con las siguientes funciones:
1. **Registrar Videojuegos**: Gestión de información y estadísticas de los videojuegos.
2. **Registrar Jugadores**: Almacenar información y progreso de los jugadores.
3. **Registrar Partidas**: Controlar las sesiones de juego y estadísticas asociadas.
4. **Mantener Estadísticas Automáticamente**: Los triggers actualizan las estadísticas acumuladas (total de sesiones, jugadores y última sesión) en `Videojuegos` después de cualquier cambio en `Partidas`.

### **Tablas**
#### **1. Tabla: `Videojuegos`**
Almacena información básica y estadísticas acumuladas sobre cada videojuego.

| Campo          | Tipo de Dato (MySQL) | Tipo de Dato (PostgreSQL) | Descripción                                      |
|----------------|----------------------|---------------------------|--------------------------------------------------|
| `game_id`      | INT AUTO_INCREMENT   | SERIAL                    | ID único del videojuego (Primary Key).           |
| `isbn`         | VARCHAR(20)          | VARCHAR(20)               | Código identificador único del videojuego.       |
| `title`        | VARCHAR(100)         | VARCHAR(100)              | Título del videojuego.                           |
| `player_count` | INT DEFAULT 0        | INT DEFAULT 0             | Número total de jugadores relacionados.          |
| `total_sessions` | INT DEFAULT 0      | INT DEFAULT 0             | Total de partidas jugadas para este videojuego.  |
| `last_session` | DATETIME             | TIMESTAMP                 | Fecha y hora de la última partida. Actualizado automáticamente. |


#### **2. Tabla: `Jugadores`**
Contiene información sobre los jugadores y su progreso acumulado.

| Campo           | Tipo de Dato (MySQL) | Tipo de Dato (PostgreSQL) | Descripción                                      |
|-----------------|----------------------|---------------------------|--------------------------------------------------|
| `player_id`     | INT AUTO_INCREMENT   | SERIAL                    | ID único del jugador (Primary Key).              |
| `nick_name`     | VARCHAR(50) UNIQUE   | VARCHAR(50) UNIQUE        | Apodo único del jugador.                        |


#### **3. Tabla: `Partidas`**
Registra cada sesión de juego junto con las estadísticas asociadas.

| Campo          | Tipo de Dato (MySQL) | Tipo de Dato (PostgreSQL) | Descripción                                      |
|----------------|----------------------|---------------------------|--------------------------------------------------|
| `session_id`   | INT AUTO_INCREMENT   | SERIAL                    | ID único de la partida (Primary Key).            |
| `game_id`      | INT                  | INT                       | Referencia al videojuego jugado.                 |
| `player_id`    | INT                  | INT                       | Referencia al jugador.                           |
| `experience`   | INT DEFAULT 0        | INT DEFAULT 0             | Incremento de experiencia en esta partida.       |
| `life_level`   | INT DEFAULT 0        | INT DEFAULT 0             | Cambio en el nivel de vida.                      |
| `coins`        | INT DEFAULT 0        | INT DEFAULT 0             | Cambio en monedas acumuladas.                    |
| `session_count`| INT DEFAULT 1        | INT DEFAULT 1             | Número de sesiones individuales por jugador.     |
| `session_date` | DATETIME DEFAULT CURRENT_TIMESTAMP | TIMESTAMP DEFAULT CURRENT_TIMESTAMP | Fecha y hora de la partida. |


## **Relaciones**
- La tabla `Partidas` tiene **claves foráneas** hacia `Videojuegos` (`game_id`) y `Jugadores` (`player_id`) para relacionar las partidas con sus respectivos videojuegos y jugadores.


## **Triggers**

### **1. Trigger para Actualizar `total_sessions`**
Este trigger actualiza el total de sesiones jugadas para un videojuego específico tras la inserción de una nueva partida.
### **2. Trigger para Actualizar `player_count`**
Este trigger actualiza el número de jugadores únicos asociados a un videojuego.
### **3. Trigger para Actualizar `last_session`**
Este trigger mantiene actualizada la fecha y hora de la última partida registrada para un videojuego.


## **Codigo de Tablas**
#### MySQL
```sql
CREATE DATABASE gamedb;
USE gamedb;

-- Tabla Videojuegos
CREATE TABLE Videojuegos (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(100) NOT NULL,
    player_count INT DEFAULT 0,
    total_sessions INT DEFAULT 0,
    last_session DATETIME
);

-- Tabla Jugadores
CREATE TABLE Jugadores (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    nick_name VARCHAR(50) NOT NULL
);

-- Tabla Partidas
CREATE TABLE Partidas (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT NOT NULL,
    player_id INT NOT NULL,
    experience INT DEFAULT 0,
    life_level INT DEFAULT 0,
    coins INT DEFAULT 0,
    session_count INT DEFAULT 1, 
    session_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (game_id) REFERENCES Videojuegos(game_id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES Jugadores(player_id) ON DELETE CASCADE
);

-- Trigger para actualizar total_sessions en Videojuegos
DELIMITER //
CREATE TRIGGER UpdateTotalSessions
AFTER INSERT ON Partidas
FOR EACH ROW
BEGIN
    UPDATE Videojuegos
    SET total_sessions = (
        SELECT COALESCE(SUM(session_count), 0)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
END;
//
DELIMITER ;

-- Trigger para actualizar player_count en Videojuegos
DELIMITER //
CREATE TRIGGER UpdatePlayerCount
AFTER INSERT ON Partidas
FOR EACH ROW
BEGIN
    UPDATE Videojuegos
    SET player_count = (
        SELECT COUNT(DISTINCT player_id)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
END;
//
DELIMITER ;

-- Trigger para actualizar last_session en Videojuegos
DELIMITER //
CREATE TRIGGER UpdateLastSession
AFTER INSERT ON Partidas
FOR EACH ROW
BEGIN
    UPDATE Videojuegos
    SET last_session = (
        SELECT MAX(session_date)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
END;
//
DELIMITER ;

```
#### PostgreSQL
```sql
-- Crear base de datos
CREATE DATABASE gamedb;

-- Conectar a la base de datos gamedb
\c gamedb;

-- Tabla Videojuegos
CREATE TABLE Videojuegos (
    game_id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(100) NOT NULL,
    player_count INT DEFAULT 0,
    total_sessions INT DEFAULT 0,
    last_session TIMESTAMP
);

-- Tabla Jugadores
CREATE TABLE Jugadores (
    player_id SERIAL PRIMARY KEY,
    nick_name VARCHAR(50) NOT NULL
);

-- Tabla Partidas
CREATE TABLE Partidas (
    session_id SERIAL PRIMARY KEY,
    game_id INT NOT NULL,
    player_id INT NOT NULL,
    experience INT DEFAULT 0,
    life_level INT DEFAULT 0,
    coins INT DEFAULT 0,
    session_count INT DEFAULT 1, 
    session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (game_id) REFERENCES Videojuegos(game_id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES Jugadores(player_id) ON DELETE CASCADE
);
-- Función para actualizar total_sessions
CREATE OR REPLACE FUNCTION update_total_sessions()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Videojuegos
    SET total_sessions = (
        SELECT COALESCE(SUM(session_count), 0)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar total_sessions después de insertar una partida
CREATE TRIGGER UpdateTotalSessions
AFTER INSERT ON Partidas
FOR EACH ROW
EXECUTE FUNCTION update_total_sessions();
-- Función para actualizar player_count
CREATE OR REPLACE FUNCTION update_player_count()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Videojuegos
    SET player_count = (
        SELECT COUNT(DISTINCT player_id)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar player_count después de insertar una partida
CREATE TRIGGER UpdatePlayerCount
AFTER INSERT ON Partidas
FOR EACH ROW
EXECUTE FUNCTION update_player_count();
-- Función para actualizar last_session
CREATE OR REPLACE FUNCTION update_last_session()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Videojuegos
    SET last_session = (
        SELECT MAX(session_date)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar last_session después de insertar una partida
CREATE TRIGGER UpdateLastSession
AFTER INSERT ON Partidas
FOR EACH ROW
EXECUTE FUNCTION update_last_session();

```
## **Base de Datos Local (SQLite)**

La base de datos local está diseñada para almacenar el progreso del jugador en el dispositivo sin requerir una conexión a internet. Se sincroniza periódicamente con las bases de datos remotas.

### **Tablas**

#### **1. Configuración del Jugador**
```sql
CREATE TABLE ConfiguracionJugador (
    config_id INTEGER PRIMARY KEY AUTOINCREMENT,
    sound_enabled BOOLEAN DEFAULT 1,
    resolution TEXT,
    language TEXT
);
```

#### **2. Estado de Partidas del Jugador**
```sql
CREATE TABLE EstadoPartidasJugadorLocal (
    session_id INTEGER,
    game_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    experience INTEGER DEFAULT 0,
    life_level INTEGER DEFAULT 0,
    coins INTEGER DEFAULT 0,
    session_count INT DEFAULT 1,
    session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## **Sincronización entre Bases de Datos**

1. **Al iniciar sesión**:
   - Se sincroniza la base de datos local (`SQLite`) con la base de datos remota (`MySQL` o `PostgreSQL`).
   - Los datos del progreso del jugador se descargan desde el servidor remoto.

2. **Durante el juego**:
   - El progreso del jugador se almacena en la base de datos local que se va registrando en ella el progreso del jugador en tiempo real.

3. **Al cerrar sesión**:
   - Los datos locales se suben al servidor remoto para mantener la información actualizada globalmente.