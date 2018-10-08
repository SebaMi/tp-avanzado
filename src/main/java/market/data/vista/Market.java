package market.data.vista;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Salida;
import market.data.model.Valores;
import market.data.utilidades.Utiles;

public class Market {
	
	Utiles utils = new Utiles();
	

	public void extraerValoresSimplificado(Path archivoSalida) throws  InputException, MappingException, OutputException{
		
		ArrayList<Valores> valores;
		
		ArrayList<Salida> simplificados= new ArrayList<Salida>();
		
		try {
			valores = utils.traerJsonURL(Valores.class ,new ArrayList<Valores>(), new URL("https://raw.githubusercontent.com/mlennard-utn/tp_avanzado/master/mercado.json"));
		} catch (MalformedURLException e) {
			throw new InputException("Error al generar listado de valores, verifique URL");
		}
		
		simplificados = utils.convertirJson(valores);

		utils.generarArchivoJson(simplificados, archivoSalida);
		
	}

}
