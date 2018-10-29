package market.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import market.data.exceptions.InputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.model.Salida;
import market.data.model.Valores;



public class Negocio {
	
	private Map<String, Object> mapAcciones;
	
	
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
