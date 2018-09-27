package market.data;

import java.io.IOException;
import java.util.ArrayList;

import market.data.model.Prestamo;
import market.data.services.JsonConverter;
import market.data.services.Url;

public class MarketData {

	public static void main(String[] args) {
				
		Url archivo = new Url();
		JsonConverter converter = new JsonConverter();
		
		try {
			converter.generarSalidaJson(converter.convertirJson(archivo.traerJson()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		converter.traerSalida();
		ArrayList<Prestamo> lista = converter.traerPrestamos();
		
		for (Prestamo prestamo : lista) {
			converter.verificarPrestamo(prestamo);
		}
		
	}

}
