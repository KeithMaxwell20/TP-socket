package py.una.server.udp;

import java.io.*;
import java.net.*;
import java.util.List;

import org.json.simple.parser.JSONParser;

import py.una.bd.CamaDAO;
import py.una.entidad.Cama;
import py.una.entidad.CamaJSON;
import py.una.entidad.PaqueteEnvioJSON;
import py.una.entidad.PaqueteEnvio;
import org.json.*;

public class UDPServer {

	public static void main(String[] a) {
		// Variables de puerto y objeto de Base de Datos
		int puertoServidor = 9876;
		CamaDAO camaBD = new CamaDAO();

		try {
			// Creamos el socket Servidor de Datagramas UDP
			DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");

			// Creamos los buffer de datos a enviar y recibir
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];

			// Contador de cuántos clientes atendió
			int contClientes = 0;

			// El servidor siempre espera por peticiones
			while (true) {

				receiveData = new byte[1024];

				DatagramPacket recibirPaquete = new DatagramPacket(receiveData, receiveData.length);

				contClientes++;
				System.out.println("Esperando a algun cliente... " + contClientes);

				// 4) Receive LLAMADA BLOQUEANTE
				serverSocket.receive(recibirPaquete); // Recibimos un paquete de la clase PaqueteEnvio

				// El paquete ya contiene la opción y la clase cama
				System.out.println("________________________________________________");
				System.out.println("Aceptamos un paquete");

				InetAddress direccionIP = recibirPaquete.getAddress(); // Obtener IP del cliente
				int puerto = recibirPaquete.getPort(); // Obtener puerto del cliente
				// Recibimos la opción y la cama dentro del paquete
				String datoRecibido = new String(recibirPaquete.getData());
				datoRecibido = datoRecibido.trim();

				// Convertir de String a objeto
				PaqueteEnvio paquete = PaqueteEnvioJSON.stringObjeto(datoRecibido);

				/*
				 * Las opciones disponibles deben imprimirse en el programa cliente porque
				 * imprimir desde el servidor provoca problemas
				 * 
				 * --El código a colocar en el cliente es el siguiente:
				 * 
				 * System.out.println("Elija una opción:\n" +
				 * "1-Ver el estado actual de todos los hospitales\n" + "2-Crear Cama UTI\n" +
				 * "3-Eliminar Cama UTI\n" + "4-Ocupar Cama UTI\n" + "5-Desocupar Cama UTI\n" +
				 * "6-Desconectar el servidor\n");
				 */

				// Obtenemos los valores de cama y opción dentro del paquete enviado
				Cama cama = paquete.getCama();
				int opcion = paquete.getOpcion();

				System.out.println("cama.toString()"+cama.toString() + opcion + " *********************************");
				String datoEnviar; // Cadena que se envía al cliente para responder sus peticiones

				DatagramPacket paqueteEnviar; // Paquete para enviar al cliente

				// Clase JSONParser para convertir cadena a formato JSON
				JSONParser parser = new JSONParser();

				// Creamos la coneccion a la base de datos
				CamaDAO camaDAO = new CamaDAO();

				switch (opcion) {
					case 1: // Ver estado de todos los hospitales
						try {
							List<Cama> ListaCamas = camaDAO.seleccionar();
							for (Cama cama2 : ListaCamas) {
								System.out.println("Cama2 "+cama2.getHospital());
							}
							
						} catch (Exception e) { // Se notifica el error al cliente
							 datoEnviar = "Se produjo un error al mostrar el estado de los hospitales";
							 sendData = datoEnviar.getBytes();
							 paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							 serverSocket.send(paqueteEnviar);
						}

						break;

					case 2: // Crear Cama UTI
						try {
							camaDAO.insertar(cama);
							
							// Notificar al cliente la inserción exitosa
							datoEnviar = "Se insertó la cama exitosamente";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						} catch (Exception e) {
							// Notificar al cliente el error en la inserción
							 datoEnviar = "Se produjo un error al crear una cama UTI";
							 sendData = datoEnviar.getBytes();
							 paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							 serverSocket.send(paqueteEnviar);
						}
						break;

					case 3: // Eliminar Cama UTI

						try {
							//Borramos de la base de datos
							camaDAO.borrar(cama.getHospital(), cama.getCama());
							
							// Notificamos la eliminación exitosa al cliente
							datoEnviar = "Se produjo un error al eliminar una cama UTI";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
							
						} catch (Exception e) {
							//Notificamos al cliente que ocurrió un error
							datoEnviar = "Se produjo un error al eliminar una cama UTI";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}

						break;

					case 4: // Ocupar Cama UTI
						try {
							camaDAO.actualizarEstado(cama);
							// Notificar éxito al cliente
							datoEnviar = "Se actualizó exitosamente la cama UTI";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						} catch (Exception e) {
							// Notificar error al cliente
							datoEnviar = "No se pudo actualizar exitosamente la cama UTI";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}
    		        	
						break;

					case 5: // Desocupar Cama UTI
						try {
							camaDAO.actualizarEstado(cama);
							// Notificar éxito al cliente
							datoEnviar = "Se actualizó exitosamente la cama UTI";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
							
						} catch (Exception e) {
							// Notificar error al cliente
							datoEnviar = "No se pudo actualizar exitosamente la cama UTI";
							sendData = datoEnviar.getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}
						break;

					case 6: // Desconectar el sevidor: un if más abajo realiza esta acción con un break para
							// salir del while(true)
							System.exit(0);
						break;

					default:
						// Opción inválida, se notifica al cliente
						datoEnviar = "Se eligió una opción inválida";
						sendData = datoEnviar.getBytes();
						paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
						serverSocket.send(paqueteEnviar);
						break;

				}

					
			}


		  } catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

}
