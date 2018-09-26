package market.data.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import market.data.model.Valores;
import market.data.model.Salida;

public class JsonConverter {
	
	public ArrayList<Salida> convertirJson (ArrayList<Valores> entrada) {
		
		ArrayList<Salida> salida = new ArrayList<Salida>();
		
		for (Valores valor : entrada) {
			salida.add(new Salida(valor.getId(), valor.getPrice(), valor.getTicker()));
		}
		
		return salida;
	}
	
	public void generarSalidaJson (ArrayList<Salida> salida) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(new File("D:\\user.json"), salida);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
