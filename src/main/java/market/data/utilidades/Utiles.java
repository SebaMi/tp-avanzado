package market.data.utilidades;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.model.Salida;
import market.data.model.Valores;

public class Utiles {
	
	private Map<String, Object> mapAcciones;
	
	public Map <String,String> verificarArgs (String args[]) {
		
		Map<String, String> direcciones = new HashMap<String, String>();
		
		for(int i=0; i<(args.length); i=(i+2)) {
			if(args[i].substring(0,2).equals("-P")) {
					direcciones.put("entrada2",  args[i+1]);
			}
			if(args[i].substring(0,2).equals("-C")) {
				direcciones.put("salida1", args[i+1]);				
			}
			if(args[i].substring(0,2).equals("-I")) {
				direcciones.put("entrada1", args[i+1]);	
			}
		}
		
		return direcciones;

	}
	
	public boolean isUrl(String s) {
	    String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
	 
	    try {
	        Pattern patt = Pattern.compile(regex);
	        Matcher matcher = patt.matcher(s);
	        return matcher.matches();
	 
	    } catch (RuntimeException e) {
	        return false;
	    }
	}
	
	public <T> ArrayList<T> traerJson(Class<T> clase, ArrayList<T> tipo, String url) throws MappingException, InputException  {

		ObjectMapper mapper = new ObjectMapper();

		ArrayList<T> array = new ArrayList<T>();
		
		try {
			if(this.isUrl(url)) {
				
				array = mapper.readValue(new URL(url), mapper.getTypeFactory().constructCollectionType(ArrayList.class, clase));
			
			} else if (this.existeArchivo(url)) {
				
				array = mapper.readValue(new File(url),	mapper.getTypeFactory().constructCollectionType(ArrayList.class, clase));
			
			} else 
				
				throw new InputException(String.format("El archivo ingresado %s no existe",url));

		} catch (JsonGenerationException e) {
			throw new MappingException("Error al generar el objeto Json");
		} catch (JsonMappingException e) {
			throw new MappingException("Error al mapear el Json");
		} catch (JsonParseException e) {
			throw new MappingException("Error al parsear el Json");
		} catch (IOException e) {
			throw new InputException("No se pudo leer la URL del Json");
		}
		return array;
	}  
	
	private boolean existeArchivo(String url) {
		
		FileSystem fs = FileSystems.getDefault();
		
		return Files.exists(fs.getPath(url));
	}

	public <T> void generarArchivoJson (ArrayList<T> lista, Path archivo) throws OutputException, MappingException{

		
		ObjectMapper mapper = new ObjectMapper();
		
		try {	
			mapper.writeValue(new File(archivo.toString()), lista);
		} catch (JsonGenerationException e) {
			throw new MappingException("Error al generar el archivo Json");
		} catch (JsonMappingException e) {
			throw new MappingException("Error al mapear el archivo Json");
		} catch (IOException e) {
			throw new OutputException("No se pudo crear el archivo Json");
		}

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

		Double tenencia =  (double) 0;
		
		for(int i=0; i<prestamo.getPositions().length; i++) {
			
			tenencia += calcular(prestamo.getPositions()[i].getId(), prestamo.getPositions()[i].getQuantity());
		}
				
		return tenencia;
	}

	private Double calcular(String id, Long quantity) {
		
		return Double.parseDouble(mapAcciones.get(id).toString())* quantity.doubleValue();
		
	}

	public ArrayList<Salida> convertirJson (ArrayList<Valores> entrada) {

		ArrayList<Salida> salida = new ArrayList<Salida>();

		for (Valores valor : entrada) {
			salida.add(new Salida(valor.getId(), valor.getPrice(), valor.getTicker()));
		}

		return salida;
	}

	public  Map<String, Object> convertir(ArrayList<Salida> acciones) throws InputException {
		
		mapAcciones = new HashMap<String,Object>();
		
		for (Salida salida : acciones) {
			mapAcciones.put(salida.getIsin(), salida.getPrice());
		}
		
		if(mapAcciones.size()==0)
				throw new InputException("No se pudo generar el listado de valores, verifique el archivo de entrada");
		else
				return mapAcciones;
	}


}
