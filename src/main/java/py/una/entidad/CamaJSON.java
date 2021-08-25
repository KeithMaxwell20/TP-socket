package py.una.entidad;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CamaJSON {

	// Ejemplo de uso de la clase CamaJSON
    public static void main(String[] args) throws Exception {
    	CamaJSON representacion = new CamaJSON();
    	
    	// Pasar de objeto a String
    	System.out.println("Ejemplo de uso 1: pasar de objeto a string");
    	Cama p = new Cama();
    	p.setHospital("IPS");
    	p.setCama("1");
    	p.setEstado("Desocupada");
    	
    	String r1 = representacion.objetoString(p);
    	System.out.println(r1);
    	
    	// Pasar de String a objeto
    	System.out.println("\n*************************************************************************");
    	System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
    	String un_string = "{\"hospital\":\"Migone\",\"cama\":\"1\",\"estado\":\"Ocupada\"}";
        
    	Cama r2 = representacion.stringObjeto(un_string);
    	System.out.println(r2.getHospital() + " " + r2.getCama() + " " +r2.getEstado() );
        
    }
    
    // Convierte una cadena a formato JSON
    public static String objetoString(Cama p) {	
		JSONObject obj = new JSONObject();
        obj.put("hospital", p.getHospital());
        obj.put("cama", p.getCama());
        obj.put("estado", p.getEstado());

        return obj.toJSONString();
    }
    
    // Convierte una cadena a un objeto
    public static Cama stringObjeto(String str) throws Exception {
    	Cama p = new Cama();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        String hospital = (String)jsonObject.get("hospital");
        p.setHospital(hospital);
        p.setCama((String)jsonObject.get("cama"));
        p.setEstado((String)jsonObject.get("estado"));

        return p;
	}

}
