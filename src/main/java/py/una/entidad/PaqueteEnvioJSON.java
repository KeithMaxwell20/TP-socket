package py.una.entidad;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PaqueteEnvioJSON {

    // Convierte una cadena a formato JSON
    public static String objetoString(PaqueteEnvio p) {	
		JSONObject obj = new JSONObject();
		Cama cama = p.getCama();
		
        obj.put("hospital", cama.getHospital());
        obj.put("cama", cama.getCama());
        obj.put("estado", cama.getEstado());
        obj.put("opcion", p.getOpcion());
        

        return obj.toJSONString();
    }
    
    // Convierte una cadena a un objeto
    public static PaqueteEnvio stringObjeto(String str) throws Exception {
    	PaqueteEnvio p = new PaqueteEnvio();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;
		
        String hospital = (String)jsonObject.get("hospital");
        p.getCama().setHospital(hospital);
        p.getCama().setCama((String)jsonObject.get("cama"));
        p.getCama().setEstado((String)jsonObject.get("estado"));
        p.setOpcion((Integer)jsonObject.get("opcion"));
        
        return p;
	}

}
