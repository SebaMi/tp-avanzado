package market.data.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import market.data.model.Valores;
import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.model.Salida;

public class JsonConverter {
	
	
	
	public ArrayList<Salida> convertirJson (ArrayList<Valores> entrada) {
		
		ArrayList<Salida> salida = new ArrayList<Salida>();
		
		for (Valores valor : entrada) {
			salida.add(new Salida(valor.getId(), valor.getPrice(), valor.getTicker()));
		}
		
		return salida;
	}
	
	public ArrayList<Prestamo> traerPrestamos() throws InputException, MappingException, OutputException {
		ArrayList<Prestamo> listaPrestamos= null;
		ObjectMapper mapper = new ObjectMapper();

		URL url=null;

		try {
			url = new URL("https://raw.githubusercontent.com/mlennard-utn/tp_avanzado/master/prestamos.json");
		} catch (MalformedURLException e1) {
			throw new InputException(String.format("Error al leer de la direccion %s",url));
		}

		try {
			listaPrestamos = mapper.readValue(url,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Prestamo.class));
		} catch (JsonGenerationException e) {
			throw new MappingException("Error al generar el archivo Json");
		} catch (JsonMappingException e) {
			throw new MappingException("Error al mapear el archivo Json");
		} catch (IOException e) {
			throw new OutputException("No se pudo crear el archivo Json");
		}

		return listaPrestamos;

	}
	
//	public void generarSalidaJson (ArrayList<Salida> salida) {
//		
//		ObjectMapper mapper = new ObjectMapper();
//		
//		try {
//			mapper.writeValue(new File("d:\\Sebas\\salida.json"), salida);
//		} catch (JsonGenerationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	
	/*  */
	
	

	
	
	
	
	
	
	public <T> void generarArchivoJson (ArrayList<T> pp, Path archivo) {
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("salida: "+archivo);
		try {
			mapper.writeValue(new File(archivo.toString()), pp);
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
