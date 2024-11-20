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

