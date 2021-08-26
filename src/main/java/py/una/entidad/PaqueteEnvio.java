package py.una.entidad;

// Paquete que se envía con la clase cama y un campo entero que es la opción
public class PaqueteEnvio {
	
	int opcion; // Opciones del programa del 1 al 6
	Cama cama; // Clase cama con sus atributos
	
	
	PaqueteEnvio()
	{
		this.cama = new Cama();
		this.opcion = 0;
		this.cama.setCama("");
		this.cama.setEstado("");
		this.cama.setHospital("");
	}
	
	// Para las opciones que requieran una cama
	public PaqueteEnvio (int opc, String hosp, String cam, String estado)
	{
		this.opcion = opc;
		this.cama = new Cama();
		this.cama.setCama(cam);
		this.cama.setEstado(estado);
		this.cama.setHospital(hosp);
	}
	
	// Para las opciones que no requieran una cama
	PaqueteEnvio (int opc)
	{
		this.opcion = opc;
		this.cama = null;
	}
	
	public int getOpcion()
	{
		return this.opcion;
	}
	
	public void setOpcion(int o)
	{
		this.opcion = o;
	}
	
	public void setCama(Cama c)
	{
		this.cama = c;
	}
	
	public Cama getCama()
	{
		return this.cama;
	}
	
}
