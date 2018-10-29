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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;

public class IO {
	
public Map <String,String> verificarArgs (String args[]) {
		
		Map<String, String> direcciones = new HashMap<String, String>();
		
	/*	for(int i=0; i<(args.length); i=(i+2)) {
			if(args[i].substring(0,2).equals("-P")) {
					direcciones.put("entrada2",  args[i+1]);
			}
			if(args[i].substring(0,2).equals("-C")) {
				direcciones.put("salida1", args[i+1]);				
			}
			if(args[i].substring(0,2).equals("-I")) {
				direcciones.put("entrada1", args[i+1]);	
			}
		}*/
		
		if(args.length==1){
			direcciones.put("entrada1", args[0]);
		}
		else if (args.length==2) {
			direcciones.put("salida1", args[0]);
			direcciones.put("entrada2", args[1]);
		}else {
			System.out.println("Introdujo un numero incorrecto deargumentos, reintente");
			return null;
		}
		
		return direcciones;

	}
	
	public boolean isUrl(String s) {
	    //String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
		String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
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
			if(this.isUrl(url) ) {
				
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
			throw new InputException(String.format("No se pudo leer %s",url));
		}
		return array;
	}  
	
	private boolean existeArchivo(String url) {
		
		FileSystem fs = FileSystems.getDefault();
		return Files.exists(fs.getPath(url));
	}

	public <T> void generarArchivoJson (ArrayList<T> lista, Path archivo) throws OutputException, MappingException{

		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
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

}
