package market.data.utilidades;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.fasterxml.jackson.databind.SerializationFeature;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.model.Salida;
import market.data.model.Valores;

public class Utiles {
	
	Map<String, String> mapAcciones;
	
	public Map verificarArgs (String args[]) {
		
		FileSystem fs = FileSystems.getDefault();
		
		Map<String, Path> direcciones = new HashMap<String, Path>();
		
		for(int i=0; i<(args.length); i=(i+2)) {
			if(args[i].substring(0,2).equals("-P")) {
				direcciones.put("entrada2",  fs.getPath (args[i+1]));
			}
			if(args[i].substring(0,2).equals("-C")) {
				direcciones.put("salida2", fs.getPath (args[i+1]));				
			}
			if(args[i].substring(0,2).equals("-I")) {
				direcciones.put("salida1", fs.getPath (args[i+1]));			
			}
		}
		
		return direcciones;

	}
	
	public <T> ArrayList<T> traerJsonURL(Class<T> clase, ArrayList<T> tipo, URL url) throws MappingException, OutputException  {

		ObjectMapper mapper = new ObjectMapper();

		ArrayList<T> array = new ArrayList<T>();
		
		try {
			array = mapper.readValue(url,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, clase));
		
		} catch (JsonGenerationException e) {
			throw new MappingException("Error al generar el archivo Json");
		} catch (JsonMappingException e) {
			throw new MappingException("Error al mapear el archivo Json");
		} catch (IOException e) {
			throw new OutputException("No se pudo crear el archivo Json");
		}

		return array;
	}  
	
	public <T> void generarArchivoJson (ArrayList<T> pp, Path archivo) throws OutputException, MappingException{

		
		ObjectMapper mapper = new ObjectMapper();
		//mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {	
			mapper.writeValue(new File(archivo.toString()), pp);
		} catch (JsonGenerationException e) {
			throw new MappingException("Error al generar el archivo Json");
		} catch (JsonMappingException e) {
			throw new MappingException("Error al mapear el archivo Json");
		} catch (IOException e) {
			throw new OutputException("No se pudo crear el archivo Json");
		}

	}
	
	public Map<String,String> traerJsonSimplificado (Path archivo) throws MappingException, OutputException, InputException {
		
		mapAcciones = new HashMap<String, String>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList <Salida> acciones = null;
		
		try {
			acciones = mapper.readValue(new File(archivo.toString()),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Salida.class));
			
		} catch (JsonGenerationException e) {
			throw new MappingException("Error al generar el archivo Json");
		} catch (JsonMappingException e) {
			throw new MappingException("Error al mapear el archivo Json");
		} catch (IOException e) {
			throw new OutputException("No se pudo crear el archivo Json");
		}
		
		for (Salida salida : acciones) {
			mapAcciones.put(salida.getIsin(), salida.getPrice());
		}
		
		if(mapAcciones.size()==0)
				throw new InputException("No se pudo generar el listado de valores, verifique el archivo de entrada");
		else
				return mapAcciones;
	}
	
	public ArrayList<PrestamoPeligroso> verificarPrestamosEnPeligro(ArrayList<Prestamo> prestamos) {
		
		ArrayList<PrestamoPeligroso> enPeligro = new ArrayList<PrestamoPeligroso>();
		
		Double tenencia;
		
		for (Prestamo prestamo : prestamos) {
			
			tenencia = this.verificarPrestamo(prestamo);
			
			if(tenencia < prestamo.getAmount()) {
				enPeligro.add(new PrestamoPeligroso(prestamo.getId(), prestamo.getCreditpolicy(), prestamo.getAmount(), tenencia));
			}
		}
		
		return enPeligro;
	}
	
	
	

	public Double verificarPrestamo(Prestamo prestamo) {

		Double monto = prestamo.getAmount();
		Double tenencia = (double) 0;
		
		for(int i=0; i<prestamo.getPositions().length; i++) {
			tenencia += calcular(prestamo.getPositions()[i].getId(), prestamo.getPositions()[i].getQuantity());
		}
		
		return tenencia;
	}

	private Double calcular(String id, Long quantity) {
		//System.out.println("valor individual: "+mapAcciones.get(id) + " Cantidad: "+quantity);
		Double tenencia = Double.parseDouble(mapAcciones.get(id))* quantity.doubleValue();
		return tenencia;
	}

	public ArrayList<Salida> convertirJson (ArrayList<Valores> entrada) {

		ArrayList<Salida> salida = new ArrayList<Salida>();

		for (Valores valor : entrada) {
			salida.add(new Salida(valor.getId(), valor.getPrice(), valor.getTicker()));
		}

		return salida;
	}


}
