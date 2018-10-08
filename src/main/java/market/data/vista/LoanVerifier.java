package market.data.vista;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.utilidades.Utiles;

public class LoanVerifier {
	
	Utiles utils = new Utiles();
	
	Map mapAcciones = new HashMap<String, String>();
	
	
	public void verificarPrestamos(Path entrada, Path salida) throws MappingException, OutputException, InputException {
		
		ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
		ArrayList<PrestamoPeligroso> pp= new ArrayList<PrestamoPeligroso>();
		
		mapAcciones = utils.traerJsonSimplificado(entrada);
		
		try {
			prestamos = utils.traerJsonURL(Prestamo.class, new  ArrayList<Prestamo>(), new URL("https://raw.githubusercontent.com/mlennard-utn/tp_avanzado/master/prestamos.json"));
		} catch (MalformedURLException e) {
			throw new InputException("Error al generar listado de valores, verifique URL");
		}
		
		pp = utils.verificarPrestamosEnPeligro(prestamos);
		
		utils.generarArchivoJson(pp, salida);
	}
}
