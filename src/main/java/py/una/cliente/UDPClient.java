package py.una.cliente;

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
            ClienteMenu cliente = new ClienteMenu();

            //creacion del socket
            DatagramSocket clientSocket = new DatagramSocket();
            cliente.login();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor + " via UDP...");

            Cama cam = cliente.iniciarMenu();

            while (cliente.getOperacion() != 8) {
                byte[] sendData = new byte[1024];
                byte[] receiveData = new byte[1024];
                //Prepara paquete para el envío al servidor con la operacion
                PaqueteEnvio paqueteEnvio = new PaqueteEnvio(cliente.getOperacion(), cam.getHospital(), cam.getCama(), cam.getEstado());

                String datoPaquete = PaqueteEnvioJSON.objetoString(paqueteEnvio);
                sendData = datoPaquete.getBytes();

                System.out.println("Enviar " + datoPaquete + " al servidor. (" + sendData.length + " bytes)");
                DatagramPacket sendPacket
                        = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

                clientSocket.send(sendPacket);

                DatagramPacket receivePacket
                        = new DatagramPacket(receiveData, receiveData.length);

                System.out.println("Esperamos si viene la respuesta.");

                //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
                clientSocket.setSoTimeout(10000);

                try {
                    // ESPERAMOS LA RESPUESTA, BLOQUENTE
                    clientSocket.receive(receivePacket);

                    String respuesta = new String(receivePacket.getData());

                    InetAddress returnIPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
                    System.out.println("Respuesta = " + respuesta);

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(respuesta.trim());
                    JSONObject jsonObject = (JSONObject) obj;
                    String cuerpo = (String) jsonObject.get("cuerpo");
                    //Se recibe datos y mensajes del servidor
                    if(cliente.getOperacion()==1){
                        String[] filas = cuerpo.split("\\$");
                        
                        for (String fila : filas) {
                            String[] elementos = fila.split(",");
                            System.out.println("-------------------------");
                            for (String string : elementos) {
                                System.out.println(string);
                            }
                            
                            
                        }
                    }else{
                        System.out.println("Cuerpo: "+ cuerpo);
                    }

                    
                    System.out.println("Press Any Key To Continue...");
                    new java.util.Scanner(System.in).nextLine();
                    //iniciamos el menu de nuevo
                    cam = cliente.iniciarMenu();
                } catch (SocketTimeoutException ste) {

                    System.out.println("TimeOut: El paquete udp se asume perdido.");
                }

            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
