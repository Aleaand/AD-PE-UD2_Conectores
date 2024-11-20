/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Run;

import Controlador.Controlador;
import Vista.Vista;
import java.sql.SQLException;

/**
 *
 * @author Alejandra
 */
public class Run {

    public static void main(String[] args) throws SQLException, Exception {
        Vista vista = new Vista();
        Controlador controlador = new Controlador(vista);
        controlador.iniciar();
    }
}