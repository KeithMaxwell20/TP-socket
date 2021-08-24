package py.una.entidad;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CamaJSON {


    public static void main(String[] args) throws Exception {
    	CamaJSON representacion = new CamaJSON();
    	
    	System.out.println("Ejemplo de uso 1: pasar de objeto a string");
    	Cama p = new Cama();
    	p.setHospital("IPS");
    	p.setCama("1");
    	p.setEstado("Desocupada");
    	/*p.getAsignaturas().add("Algoritmos y Estructuras de Datos 2");
    	p.getAsignaturas().add("Quimica");
    	p.getAsignaturas().add("Ingles");
    	*/
    	String r1 = representacion.objetoString(p);
    	System.out.println(r1);
    	
    	
    	System.out.println("\n*************************************************************************");
    	System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
    	String un_string = "{\"hospital\":\"Migone\",\"cama\":\"1\",\"estado\":\"Ocupada\"}";
    	

        
    	Cama r2 = representacion.stringObjeto(un_string);
    	System.out.println(r2.getHospital() + " " + r2.getCama() + " " +r2.getEstado() );
        /*for(String temp: r2.getAsignaturas()){
        	System.out.println(temp);
        }*/
    }
    
    // Convierte una cadena a formato JSON
    public static String objetoString(Cama p) {	
    	
		JSONObject obj = new JSONObject();
        obj.put("hospital", p.getHospital());
        obj.put("cama", p.getCama());
        obj.put("estado", p.getEstado());

        //JSONArray list = new JSONArray();
        
       /* for(String temp: p.getAsignaturas()){
        	list.add(temp);
        }
       // if(list.size() > 0) {
        	obj.put("asignaturas", list);
        //}*/
        

        return obj.toJSONString();
    }
    
    
    public static Cama stringObjeto(String str) throws Exception {
    	Cama p = new Cama();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        String hospital = (String)jsonObject.get("hospital");
        p.setHospital(hospital);
        p.setCama((String)jsonObject.get("cama"));
        p.setEstado((String)jsonObject.get("estado"));
        
        /*JSONArray msg = (JSONArray) jsonObject.get("asignaturas");
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
        	p.getAsignaturas().add(iterator.next());
        }*/
        return p;
	}

}
