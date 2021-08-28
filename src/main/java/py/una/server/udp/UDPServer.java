package py.una.server.udp;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import py.una.bd.CamaDAO;
import py.una.entidad.Cama;
import py.una.entidad.CamaJSON;
import py.una.entidad.PaqueteEnvioJSON;
import py.una.entidad.PaqueteEnvio;
import py.una.entidad.Log;
import org.json.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class UDPServer {
	
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
		}
	
	public static void main(String[] a) {
		// Variables de puerto y objeto de Base de Datos
		int puertoServidor = 9878;
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
			
			// Para guardar el log
			int cantidadLog = 0;
			// Lista donde se guarda el log
			LinkedList<Log> listaLog = new LinkedList<Log>();
			
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

				System.out.println("cama.toString()" + cama.toString() + opcion + " *********************************");
				String datoEnviar; // Cadena que se envía al cliente para responder sus peticiones

				DatagramPacket paqueteEnviar; // Paquete para enviar al cliente

				// Clase JSONParser para convertir cadena a formato JSON
				JSONParser parser = new JSONParser();

				// Creamos la coneccion a la base de datos
				CamaDAO camaDAO = new CamaDAO();
				// Creamos el json para la respuesta al cliente
				JSONObject respuesta = new JSONObject();
				
				
				Log log;
				// Crear log si la opción es válida
				if (opcion>=1 && opcion <=7)
				{
					String fecha = now();
					String dirDestino = recibirPaquete.getAddress().toString();
					int puertoOrigen = puerto;
					String dirOrigen = direccionIP.toString();
					int puertoDestino = puertoServidor;
					int tipo = opcion;
					
					log = new Log(fecha, dirOrigen, puertoOrigen, dirDestino, puertoDestino, tipo);
					
					listaLog.add(log);	
				}
				
				switch (opcion) {
					case 1: // Ver estado de todos los hospitales
						try {
							List<Cama> ListaCamas = camaDAO.seleccionar();
							for (Cama cama2 : ListaCamas) {
								System.out.println("Cama2 " + cama2.getHospital());
							}
							// formamos el json de respuesta
							respuesta.put("estado", 0);
							respuesta.put("mensaje", "ok");
							respuesta.put("tipo_operacion", 1);
							respuesta.put("cuerpo", listamosCamas(ListaCamas.toArray()));

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);

						} catch (Exception e) { // Se notifica el error al cliente
							datoEnviar = "Se produjo un error al mostrar el estado de los hospitales";
							// formamos el json de respuesta
							respuesta.put("estado",-1);
							respuesta.put("mensaje", e.getMessage());
							respuesta.put("tipo_operacion", 1);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}

						break;

					case 2: // Crear Cama UTI
						try {
							camaDAO.insertar(cama);

							// Notificar al cliente la inserción exitosa
							datoEnviar = "Se insertó la cama exitosamente";

							// formamos el json de respuesta
							respuesta.put("estado", 0);
							respuesta.put("mensaje", "ok");
							respuesta.put("tipo_operacion", 2);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						} catch (Exception e) {
							// Notificar al cliente el error en la inserción
							datoEnviar = "Se produjo un error al crear una cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", -1);
							respuesta.put("mensaje", e.getMessage());
							respuesta.put("tipo_operacion", 2);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}
						break;

					case 3: // Eliminar Cama UTI

						try {
							// Borramos de la base de datos
							camaDAO.borrar(cama.getHospital(), cama.getCama());

							// Notificamos la eliminación exitosa al cliente
							datoEnviar = "Se eliminó la cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", 0);
							respuesta.put("mensaje", "ok");
							respuesta.put("tipo_operacion", 3);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);

						} catch (Exception e) {
							// Notificamos al cliente que ocurrió un error
							datoEnviar = "Se produjo un error al eliminar una cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", -1);
							respuesta.put("mensaje", e.getMessage());
							respuesta.put("tipo_operacion", 3);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}

						break;

					case 4: // Ocupar Cama UTI
						try {
							camaDAO.actualizarEstado(cama);
							// Notificar éxito al cliente
							datoEnviar = "Se actualizó exitosamente la cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", 0);
							respuesta.put("mensaje", "ok");
							respuesta.put("tipo_operacion", 4);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						} catch (Exception e) {
							// Notificar error al cliente
							datoEnviar = "No se pudo actualizar exitosamente la cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", -1);
							respuesta.put("mensaje", e.getMessage());
							respuesta.put("tipo_operacion", 4);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}

						break;

					case 5: // Desocupar Cama UTI
						try {
							camaDAO.actualizarEstado(cama);
							// Notificar éxito al cliente
							datoEnviar = "Se actualizó exitosamente la cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", 0);
							respuesta.put("mensaje", "ok");
							respuesta.put("tipo_operacion", 5);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);

						} catch (Exception e) {
							// Notificar error al cliente
							datoEnviar = "No se pudo actualizar exitosamente la cama UTI";
							// formamos el json de respuesta
							respuesta.put("estado", -1);
							respuesta.put("mensaje", e.getMessage());
							respuesta.put("tipo_operacion", 5);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
						}
						break;

					case 6: // Desconectar el sevidor: un if más abajo realiza esta acción con un break para
							// salir del while(true)
							datoEnviar = "Se cerró exitosamente el Servidor";
							// formamos el json de respuesta
							respuesta.put("estado", 0);
							respuesta.put("mensaje","ok");
							respuesta.put("tipo_operacion", 6);
							respuesta.put("cuerpo", datoEnviar);

							sendData = (respuesta.toJSONString()).getBytes();
							paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
							serverSocket.send(paqueteEnviar);
							System.exit(0);
						break;
					case 7: // Imprimir el log
						Log log2;
						for (int i=0; i<listaLog.size();i++)
						{
							log2 = listaLog.get(i);
							System.out.println("Fecha-hora: "+ log2.getFecha() + ", origen:" + log2.getIpOrigen() + "" + log2.getDirOrigen() + ", destino:" + log2.getIpDestino() + "" + log2.getDirDestino() + ", tipo_operacion: " + log2.getOperacion());
						}
						datoEnviar = "Se muestra el log en el servidor";
						sendData = datoEnviar.getBytes();
						paqueteEnviar = new DatagramPacket(sendData, sendData.length, direccionIP, puerto);
						serverSocket.send(paqueteEnviar);
						
						break;
					default:
						// Opción inválida, se notifica al cliente
						datoEnviar = "Se eligió una opción inválida";
						// formamos el jason de respuesta
						respuesta.put("estado", -1);
						respuesta.put("mensaje", "Error");
						respuesta.put("tipo_operacion", -1);
						respuesta.put("cuerpo", datoEnviar);

						sendData = (respuesta.toJSONString()).getBytes();
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
	static String listamosCamas(Object[] camas){
		String respuesta="";
		for (Object obj : camas) {
			
			Cama cama = (Cama)obj;
			respuesta+=","+"Hospital:"+cama.getHospital()+"+"+"Cama:"+cama.getCama()+"+"+"Estado:"+cama.getEstado();
		}

		return respuesta;
	}

}

