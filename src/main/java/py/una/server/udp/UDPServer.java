package py.una.server.udp;

import java.io.*;
import java.net.*;

import py.una.bd.CamaDAO;
import py.una.entidad.Cama;
import py.una.entidad.CamaJSON;

public class UDPServer {
	
    public static void main(String[] a)
    {
        // Variables de puerto y objeto de Base de Datos
        int puertoServidor = 9876;
        CamaDAO camaBD = new CamaDAO();
        
        try {
            //Creamos el socket Servidor de Datagramas UDP
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //Creamos los buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            //byte[] sendData = new byte[1024];

            //Contador de cuántos clientes atendió
			int contClientes=0;
            
			//El servidor siempre espera por peticiones
            while (true) 
            {

                receiveData = new byte[1024];

                DatagramPacket recibirPaquete =
                        new DatagramPacket(receiveData, receiveData.length);

                contClientes++;
                System.out.println("Esperando a algun cliente... " + contClientes);

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(recibirPaquete);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");
                
                InetAddress direccionIP = recibirPaquete.getAddress(); // Obtener IP del cliente
                int puerto = recibirPaquete.getPort(); // Obtener puerto del cliente
                
                /* Las opciones disponibles deben imprimirse en el programa cliente
	        	   porque imprimir desde el servidor provoca problemas
	        	   	        	
	        	--El código a colocar en el cliente es el siguiente:
	        	
		        System.out.println("Elija una opción:\n"
		        		+ "1-Ver el estado actual de todos los hospitales\n"
		        		+ "2-Crear Cama UTI\n"
		        		+ "3-Eliminar Cama UTI\n"
		        		+ "4-Ocupar Cama UTI\n"
		        		+ "5-Desocupar Cama UTI\n"
		        		+ "6-Desconectar el servidor\n");
	           */
                
                
                int opcion = -1; //Primeramente no se elije ninguna opción y se entra al while
                
                // Cadenas para recibir y enviar datos entre cliente y servidor
                String datoRecibido;
                String datoEnviar;
                
                DatagramPacket paqueteEnviar;
                
                // Mientras no se elija una opción válida (1-6)
                while (opcion<=0 || opcion >= 7)
                {
                	// Tenemos la opción elegida en formato cadena, debemos convertir a entero
                    datoRecibido = new String(recibirPaquete.getData());
                    datoRecibido = datoRecibido.trim();
                	opcion = Integer.parseInt(datoRecibido);
                	
                	// Si la opción no se encuentra entre las válidas, se manda un mensaje al cliente
                	if (opcion<=0 || opcion >=7)
                	{
                		datoEnviar = "Debe elegir una opción válida (1-6)";
                		paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
                		serverSocket.send(paqueteEnviar);
                		//Vuelve a esperar recibir un paquete por parte del cliente
                		serverSocket.receive(recibirPaquete);
                	}
                	
                }
                
                // Una vez que el cliente elige una opción válida, manejamos los distintos casos
                
                Cama cama;
                String hospital, numeroCama, estado;
                // Paquete a recibir en byte[] receiveData y su longitud
                recibirPaquete = new DatagramPacket(receiveData, receiveData.length);
                
                switch(opcion) 
    	        {
    		        case 1: // Ver estado de todos los hospitales    		        	
    		        	datoEnviar = "Mostrando el estado de todos los hospitales...";
    		        	paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
    		        	serverSocket.send(paqueteEnviar);
    		        	
    		        	// Mostrar el estado de los hospitales usando la base de datos
    		        	break;
    		        	
    		        case 2: // Crear Cama UTI
    		        	datoEnviar = "Ingrese los datos de la cama (hospital, cama, estado) a añadir: ";
    		        	paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
    		        	serverSocket.send(paqueteEnviar);
    		        	
    		        	// Recibimos la cama en formato JSON
    		        	serverSocket.receive(recibirPaquete);
    		        	
    		        	//Convertimos a cadena el paquete recibido
    		        	datoRecibido = new String(recibirPaquete.getData());
    		        	datoRecibido = datoRecibido.trim();
    		        	System.out.println("Dato recibido:" + datoRecibido);
    		        	
    		        	//Convertimos a objeto    		        	
    		        	cama = CamaJSON.stringObjeto(datoRecibido);
    		        	
    		        	
    		        	//Agregamos a la base de datos
    		        	
    		        	break;
    		        	
    		        case 3: // Eliminar Cama UTI
    		        	datoEnviar = "Ingrese los datos de la cama (hospital, cama, estado) a borrar: ";
    		        	paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
    		        	serverSocket.send(paqueteEnviar);
    		        	
    		        	// Recibimos la cama en formato JSON
    		        	serverSocket.receive(recibirPaquete);
    		        	
    		        	//Convertimos a cadena el paquete recibido
    		        	datoRecibido = new String(recibirPaquete.getData());
    		        	datoRecibido = datoRecibido.trim();
    		        	System.out.println("Dato recibido:" + datoRecibido);
    		        	
    		        	//Convertimos a objeto    		        	
    		        	cama = CamaJSON.stringObjeto(datoRecibido);
    		        	
    		        	//Borramos de la base de datos
    		        	break;
    		        	
    		        case 4: // Ocupar Cama UTI
    		        	datoEnviar = "Ingrese los datos de la cama (hospital, cama, estado) a ocupar: ";
    		        	paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
    		        	serverSocket.send(paqueteEnviar);
    		        	
    		        	// Recibimos la cama en formato JSON
    		        	serverSocket.receive(recibirPaquete);
    		        	
    		        	//Convertimos a cadena el paquete recibido
    		        	datoRecibido = new String(recibirPaquete.getData());
    		        	datoRecibido = datoRecibido.trim();
    		        	System.out.println("Dato recibido:" + datoRecibido);
    		        	
    		        	//Convertimos a objeto    		        	
    		        	cama = CamaJSON.stringObjeto(datoRecibido);
    		        	
    		        	// Actualizar en la base de datos
    		        	break;
    		        	
    		        case 5: // Desocupar Cama UTI
    		        	datoEnviar = "Ingrese los datos de la cama (hospital, cama, estado) a desocupar: ";
    		        	paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
    		        	serverSocket.send(paqueteEnviar);
    		        	
    		        	// Recibimos la cama en formato JSON
    		        	serverSocket.receive(recibirPaquete);
    		        	
    		        	//Convertimos a cadena el paquete recibido
    		        	datoRecibido = new String(recibirPaquete.getData());
    		        	datoRecibido = datoRecibido.trim();
    		        	System.out.println("Dato recibido:" + datoRecibido);
    		        	
    		        	//Convertimos a objeto    		        	
    		        	cama = CamaJSON.stringObjeto(datoRecibido);
    		        	
    		        	//Desocupar la cama y actualizar en la base de datos
    		        	break;
    		        	
    		        case 6: // Desconectar el sevidor: un if más abajo realiza esta acción con un break para salir del while(true)
    		        	break;
    		        	
    		        default:
    		        	break;
    	        
    	        }
    	        
    	        if (opcion == 6)
    	        {
    	        	datoEnviar = "Se desconecta el servidor";
    	        	paqueteEnviar = new DatagramPacket(datoEnviar.getBytes(), datoEnviar.length(), direccionIP, puerto);
    	        	serverSocket.send(paqueteEnviar);
    	        	
    	        	break; // Salimos del while(true) y terminamos el programa
    	        }
    	        
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }
        //serverSocket.close();
        
    }
    
}


