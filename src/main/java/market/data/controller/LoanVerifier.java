package market.data.controller;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.model.Salida;
import market.data.utilidades.IO;

public class LoanVerifier {
	
	Negocio utils = new Negocio();
	
	IO io = new IO();
	
	Map<String, Object> mapAcciones = new HashMap<String, Object>();
	
	
	public void verificarPrestamos(String prestamo, String entrada) throws MappingException, OutputException, InputException {
		
		ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
		ArrayList<PrestamoPeligroso> pp= new ArrayList<PrestamoPeligroso>();
		ArrayList<Salida> generado = new ArrayList<Salida>();
		
		FileSystem fs = FileSystems.getDefault();

		Path output = fs.getPath("prestamos_riesgosos.json");
		
		generado = io.traerJson(Salida.class, generado, entrada);
		
		prestamos = io.traerJson(Prestamo.class, new  ArrayList<Prestamo>(), prestamo);
		
		mapAcciones = utils.convertir(generado);
		
		pp = utils.verificarPrestamosEnPeligro(prestamos);
		
		io.generarArchivoJson(pp, output);
		
		System.out.println(String.format("Se ha generado el archivo %s", output));
	}
}
