/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.una.entidad.Cama;

/**
 *
 * @author romia
 */
public class ClienteMenu {

    String hospital, cama, estado;
    int operacion;
    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
    
    
    public void login(){
        BufferedReader inFromUser
                    = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese el nombre del Hospital: \n");
        try {                
           hospital=inFromUser.readLine();
            System.out.println("Bienvenido hospital "+hospital);
        } catch (IOException ex) {
            Logger.getLogger(ClienteMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public Cama iniciarMenu() {
        Cama cam = new Cama(hospital, cama, estado);
        try {
            BufferedReader inFromUser
                    = new BufferedReader(new InputStreamReader(System.in));
            
                System.out.println("Elija una opción:\n"
                        + "1-Ver el estado actual de todos los hospitales\n"
                        + "2-Crear Cama UTI\n"
                        + "3-Eliminar Cama UTI\n"
                        + "4-Ocupar Cama UTI\n"
                        + "5-Desocupar Cama UTI\n"
                        + "6-Desconectar el servidor\n"
                        + "7-Salir\n");
                operacion =Integer.parseInt(inFromUser.readLine());

                switch (operacion) {
                    case 1:
                        //Para consultas, se despliegan todos los hospitales con sus caas y estado
                        System.out.println("El estado actual de los hospitales es: ");
                        cam.setCama("0");
                        break;
                    case 2:
                        //Para agregar se pide la cama y estado
                        System.out.print("Ingrese la cama: ");
                        cama = inFromUser.readLine();
                        System.out.print("Ingrese el estado: ");
                        estado = inFromUser.readLine();
                        break;
                    case 3:
                        //Para eliminar una cama se pide su nro
                        System.out.print("Ingrese la cama: ");
                        cama = inFromUser.readLine();
                        break;
                    case 4:
                        //Para cambiar estado se pide la cama
                        System.out.print("Ingrese la cama: ");
                        cama = inFromUser.readLine();
                        estado = "ocupado";
                        break;
                    case 5:
                        //Para cambiar estado se pide la cama
                        System.out.print("Ingrese la cama: ");
                        cama = inFromUser.readLine();
                        estado = "descocupado";
                        break;
                    case 6:
                        break;
                }
                cam.setCama(cama);
                cam.setEstado(estado);

        } catch (IOException ex) {
            Logger.getLogger(ClienteMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cam;
    }
}
