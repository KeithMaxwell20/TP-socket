package py.una.server.udp;


import java.io.*;
import java.net.*;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import py.una.entidad.Cama;
import py.una.entidad.CamaJSON;
import py.una.entidad.PaqueteEnvio;
import py.una.entidad.PaqueteEnvioJSON;

class UDPClient {

    public static void main(String a[]) throws Exception {

        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;
        
        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            
            System.out.println("Elija una opción:\n"
		        		+ "1-Ver el estado actual de todos los hospitales\n"
		        		+ "2-Crear Cama UTI\n"
		        		+ "3-Eliminar Cama UTI\n"
		        		+ "4-Ocupar Cama UTI\n"
		        		+ "5-Desocupar Cama UTI\n"
		        		+ "6-Desconectar el servidor\n");
            int operacion = Integer.parseInt(inFromUser.readLine());
            System.out.print("Ingrese el hospital: ");
            String hospital = inFromUser.readLine();
            System.out.print("Ingrese la cama: ");
            String cama = inFromUser.readLine();
            System.out.print("Ingrese el estado: ");
            String estado = inFromUser.readLine();
            
            PaqueteEnvio paqueteEnvio = new PaqueteEnvio(operacion,hospital, cama, estado);
        
            
            String datoPaquete = PaqueteEnvioJSON.objetoString(paqueteEnvio); 
            sendData = datoPaquete.getBytes();

            System.out.println("Enviar " + datoPaquete + " al servidor. ("+ sendData.length + " bytes)");
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            System.out.println("Esperamos si viene la respuesta.");

            //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
            clientSocket.setSoTimeout(10000);

            try {
                // ESPERAMOS LA RESPUESTA, BLOQUENTE
                clientSocket.receive(receivePacket);

                String respuesta = new String(receivePacket.getData());
                //Cama presp = PersonaJSON.stringObjeto(respuesta.trim());
                
                InetAddress returnIPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
                System.out.println("Respuesta = " + respuesta);

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(respuesta.trim());
                JSONObject jsonObject = (JSONObject) obj;
                String hospitales = (String)jsonObject.get("cuerpo");
                System.out.println("Hospitales = " + hospitales);
                
                

            } catch (SocketTimeoutException ste) {

                System.out.println("TimeOut: El paquete udp se asume perdido.");
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
} 

