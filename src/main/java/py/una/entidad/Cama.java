package py.una.entidad;
import java.util.ArrayList;
import java.util.List;

public class Cama {


	String hospital;
	String cama;
	String estado;
	
	public Cama(){
		
	}

	public Cama(
	String hospital,
	String cama,
	String estado){
            
		this.hospital = hospital;
		this.cama = cama;
		this.estado = estado;

	}
	
	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getCama() {
		return cama;
	}

	public void setCama(String cama) {
		this.cama = cama;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


}
