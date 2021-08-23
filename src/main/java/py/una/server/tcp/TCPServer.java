package py.una.server.tcp;

//import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.*;

public class TCPServer {

    public static void main(String[] args) throws Exception {

    	// Elegimos el puerto donde realizaremos la comunicación
        int puertoServidor = 4445;
        
		// Creamos los sockets para comunicar servidor y cliente
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(puertoServidor);
        } catch (IOException e) {
            System.err.println("No se puede abrir el puerto: " +puertoServidor+ ".");
            System.exit(1);
        }
        System.out.println("Puerto abierto: "+puertoServidor+".");
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Fallo el accept().");
            System.exit(1);
        }

        /* Escogemos los streams para que el input del cliente
         llegue al servidor, y el output del servidor
         vaya al cliente */
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
        
        
        /* Realizar las tareas del servidor aceptando peticiones
         del cliente */
        while(true)
        {
	        out.println("¡Bienvenido!. Elija una opción:");
	        String inputLine = null, outputLine;
	        
	        int opcion = -1;
		    
	        // Mientras no se elija una opción válida
	        while(opcion <= 0 || opcion >=7)
	        {
	        	/* Las opciones disponibles deben imprimirse en el programa cliente
	        	   porque imprimir desde el servidor da problemas
	        	   	        	
	        	--El código a colocar en el cliente es el siguiente:
	        	
		        System.out.println("Elija una opción:\n"
		        		+ "1-Ver el estado actual de todos los hospitales\n"
		        		+ "2-Crear Cama UTI\n"
		        		+ "3-Eliminar Cama UTI\n"
		        		+ "4-Ocupar Cama UTI\n"
		        		+ "5-Desocupar Cama UTI\n"
		        		+ "6-Desconectar el servidor\n");
	           */
	        	
	        	
		        //Leer input del cliente
		        inputLine = in.readLine();
		        
		        //Convertir input del cliente a entero para elegir una opción
		        opcion = Integer.parseInt(inputLine);
		        
		        // Imprime en el programa servidor qué opción eligió el cliente
		        System.out.println("Opción: " + opcion);
		        
		        if(opcion<=0 || opcion>=7)
		        	out.println("Error, debe escoger una de las opciones válidas (1-6)");
	        }
	        
	        
	        // Para recibir peticiones del cliente sobre camas específicas
        	String hospital, numeroCama, estado;
        	Cama cama;

	        // Manejamos todos los casos según la opción elegida
	        switch(opcion) 
	        {
		        case 1: // Ver estado de todos los hospitales
		        	out.println("Mostrando el estado de todos los hospitales...");
		        	
		        	// Mostrar el estado de los hospitales usando la base de datos
		        	break;
		        	
		        case 2: // Crear Cama UTI
		        	out.println("Ingrese el hospital:");
		        	hospital = in.readLine();
		        	out.println("Ingrese el número de cama:");
		        	numeroCama = in.readLine();
		        	out.println("Ingrese el estado de la cama:");
		        	estado = in.readLine();
		        	
		        	cama = new Cama(hospital, cama, estado);
		        	
		        	//Agregar a la base de datos
		        	break;
		        	
		        case 3: // Eliminar Cama UTI
		        	out.println("Ingrese el hospital:");
		        	hospital = in.readLine();
		        	out.println("Ingrese el número de cama:");
		        	numeroCama = in.readLine();
		        	out.println("Ingrese el estado de la cama:");
		        	estado = in.readLine();
		        	
		        	cama = new Cama(hospital, cama, estado);
		        	
		        	//Borrar de la base de datos
		        	break;
		        	
		        case 4: // Ocupar Cama UTI
		        	out.println("Ingrese el hospital:");
		        	hospital = in.readLine();
		        	out.println("Ingrese el número de cama:");
		        	numeroCama = in.readLine();
		        	out.println("Ingrese el estado de la cama:");
		        	estado = in.readLine();
		        	
		        	cama = new Cama(hospital, cama, estado);
		        	
		        	// Actualizar en la base de datos
		        	break;
		        	
		        case 5: // Desocupar Cama UTI
		        	out.println("Ingrese el hospital:");
		        	hospital = in.readLine();
		        	out.println("Ingrese el número de cama:");
		        	numeroCama = in.readLine();
		        	out.println("Ingrese el estado de la cama:");
		        	estado = in.readLine();
		        	
		        	cama = new Cama(hospital, cama, estado);
		        	//Desocupar la cama y actualizar en la base de datos
		        	
		        	break;
		        	
		        case 6: // Desconectar el sevidor: un if más abajo realiza esta acción con un break para salir del while
		        	break;
		        	
		        default:
		        	break;
	        
	        }
	        
	        if (opcion == 6)
	        {
	        	out.println("Se desconecta el servidor");
	        	break; // Salimos del while(true) y terminamos el programa
	        }
	        
	        System.out.println("Mensaje recibido: " + inputLine);
        }
        
    	//Terminamos el programa servidor
        out.println("Se desconecta el servidor");
        System.out.println("Fin de la ejecución");
        
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}

