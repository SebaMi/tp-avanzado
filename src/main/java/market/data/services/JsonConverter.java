package market.data.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import market.data.model.Valores;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.model.Salida;

public class JsonConverter {
	
	Map<String, String> mapAcciones;
	
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
			mapper.writeValue(new File("d:\\Sebas\\salida.json"), salida);
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
	
	public ArrayList<Prestamo> traerPrestamos() {
		
		ArrayList<Prestamo> listaPrestamos= null;
		ObjectMapper mapper = new ObjectMapper();
		
		URL url;
		try {
			url = new URL("https://raw.githubusercontent.com/mlennard-utn/tp_avanzado/master/prestamos.json");
		
			listaPrestamos = mapper.readValue(url,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Prestamo.class));
			
//			for (Prestamo prestamos : listaPrestamos) {
//				System.out.println(prestamos.getId());
//			}
		
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); 
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
		
		return listaPrestamos;
	
	}
	
	/*  */
	
	public ArrayList<PrestamoPeligroso> verificarPrestamo(Prestamo prestamo, ArrayList<PrestamoPeligroso> enPeligro) {
		
		
		
		Double monto = prestamo.getAmount();
		Double tenencia = (double) 0;
		for(int i=0; i<prestamo.getPositions().length; i++) {
			tenencia += calcular(prestamo.getPositions()[i].getId(), prestamo.getPositions()[i].getQuantity());
		}
		if(tenencia<prestamo.getAmount())
			//System.out.println("Tenencia: "+tenencia +"vs "+ "Monto: " + prestamo.getAmount() );
			enPeligro.add(new PrestamoPeligroso(prestamo.getId(), prestamo.getCreditpolicy(), prestamo.getAmount(), tenencia));
		
		return enPeligro;
		
	}

	private Double calcular(String id, Long quantity) {
		//System.out.println("valor individual: "+mapAcciones.get(id) + " Cantidad: "+quantity);
		Double tenencia = Double.parseDouble(mapAcciones.get(id))* quantity.doubleValue();
		return tenencia;
	}
	
	public Map<String,String> traerSalida() {
		File file = new File("D:\\Sebas\\salida.json");
		
		mapAcciones = new HashMap<String, String>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList <Salida> acciones = null;
		
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
		
		return mapAcciones;
	}
	
	public void verificarPrestamosEnPeligro() {
		ArrayList<Prestamo> lista = this.traerPrestamos();
		ArrayList<PrestamoPeligroso> enPeligro = new ArrayList();
		
		for (Prestamo prestamo : lista) {
			enPeligro = this.verificarPrestamo(prestamo, enPeligro);
		}
		
		for (PrestamoPeligroso prestamoPeligroso : enPeligro) {
			System.out.println(prestamoPeligroso.getId() +"  " + prestamoPeligroso.getAmount() + "  " +prestamoPeligroso.getEligible_colateral());
			
		}
		generarPeligrosos(enPeligro);
	}
	
	private void generarPeligrosos (ArrayList<PrestamoPeligroso> pp) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(new File("d:\\Sebas\\Prestamos_con_riesgo.json"), pp);
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
