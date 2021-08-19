package py.una.bd;

import java.sql.SQLException;
//import java.util.Iterator;
import java.util.List;

import py.una.entidad.Cama;

public class TestCamaDAO {

	
	
	public static void main(String args[]) throws SQLException{
		
		CamaDAO pdao = new CamaDAO();
		
		
		pdao.insertar(new Cama("IPS","Cama-1", "Libre") );
		pdao.insertar(new Cama("IPS","Cama-2", "Ocupado") );
		pdao.insertar(new Cama("IPS","Cama-3", "Libre") );
		pdao.insertar(new Cama("IPS","Cama-4", "Libre") );
		
		
		pdao.actualizarEstado(new Cama("IPS","Cama-4", "Ocupado") );
		
		pdao.borrar("IPS","Cama-4");
		
		//List<Persona> lista = pdao.seleccionarPorCedula(202L);
		
		List<Cama> lista = pdao.seleccionar(); 
		
		
		for (Cama p: lista){
			System.out.println(p.getHospital() + " " + p.getCama() + " " + p.getEstado());
		}
	}
	
	
}
