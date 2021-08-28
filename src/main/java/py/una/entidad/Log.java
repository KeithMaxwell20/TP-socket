package py.una.entidad;

public class Log {
	String fecha;
	String direccionOrigen;
	int ipOrigen;
	String direccionDestino;
	int ipDestino;
	int tipo_operacion;
	
	public Log(String f, String or, int ipOrig, String dest, int ipDest, int tipo)
	{
		this.fecha = f;
		this.ipOrigen = ipOrig;
		this.ipDestino = ipDest;
		this.direccionOrigen = or;
		this.direccionDestino = dest;
		this.tipo_operacion = tipo;
	}
	
	public String getFecha()
	{
		return this.fecha;
	}
	
	public int getIpDestino()
	{
		return this.ipDestino;
	}
	
	public int getIpOrigen()
	{
		return this.ipOrigen;
	}
	
	public String getDirOrigen()
	{
		return this.direccionOrigen;
	}
	
	public String getDirDestino()
	{
		return this.direccionDestino;
	}
	
	public int getOperacion()
	{
		return this.tipo_operacion;
	}
}	