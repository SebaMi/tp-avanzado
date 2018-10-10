package market.data.controller;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
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
	

	public void extraerValoresSimplificado(String entrada) throws  InputException, MappingException, OutputException{
		
		ArrayList<Valores> valores;
		
		ArrayList<Salida> simplificados= new ArrayList<Salida>();
		
		FileSystem fs = FileSystems.getDefault();
		
		Path output = fs.getPath("C:/Temp/simplificado.json");
		
		valores = utils.traerJson(Valores.class ,new ArrayList<Valores>(), entrada);

		simplificados = utils.convertirJson(valores);

		utils.generarArchivoJson(simplificados, output);
		
		System.out.println(String.format("Se ha generado el archivo %s", output));
		
	}

}
