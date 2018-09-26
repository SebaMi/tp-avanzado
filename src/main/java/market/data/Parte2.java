package market.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import market.data.model.Salida;

public class Parte2 {
	
	public static void main(String[] args) {
		File file = new File("D:\\user.json");
		
		Map<String, String> mapAcciones = new HashMap<String, String>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList<Salida> acciones = null;
		
		try {
			acciones = mapper.readValue(file,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Salida.class));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Salida salida : acciones) {
			mapAcciones.put(salida.getIsin(), salida.getPrice());
		}
	}
}
