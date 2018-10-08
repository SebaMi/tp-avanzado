package market.data;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.text.Utilities;

import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.model.Prestamo;
import market.data.model.PrestamoPeligroso;
import market.data.services.JsonConverter;
import market.data.utilidades.Utiles;
import market.data.vista.LoanVerifier;
import market.data.vista.Market;

public class MarketData {

	public static void main(String[] args) {
	
		Map<String, Path> direcciones = null;
		Utiles utils = new Utiles();
		
		if(args.length>0) {
			
			direcciones = utils.verificarArgs(args);
		}else {
			System.out.println("Debe introducir uno o mas argumentos validos");
			System.out.println("-P archivo \tUbicacion del archivo de entrada Parte 2");
			System.out.println("-C archivo \tUbicacion donde se guardara archivo de salida Parte 2");
			System.out.println("-I archivo \tUbicacion del archivo de salida Parte 1");
			System.exit(1);
		}
		
		if(direcciones.containsKey("salida1")) {
			//if(!Files.exists(direcciones.get("salida1"))) {
			//	System.out.println(String.format("El archivo ingresado %s no existe", direcciones.get("salida1")));
			//	System.exit(1);
			//}else {
				Market market = new Market();
				try {
					market.extraerValoresSimplificado(direcciones.get("salida1"));
				} catch (InputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OutputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}
		}
		
		if(direcciones.containsKey("entrada2")) {
			if(!Files.exists(direcciones.get("entrada2"))) {
				System.out.println(String.format("El archivo ingresado %s no existe, no se puede continuar", direcciones.get("entrada2")));
				System.exit(1);
			} else if(direcciones.containsKey("salida2")) {
//				if(!Files.exists(direcciones.get("salida2"))) {
//					System.out.println(String.format("El archivo ingresado %s no existe, no se puede contnuar", direcciones.get("salida2")));
//					System.exit(1);
//				}
			//}else {
				LoanVerifier loan = new LoanVerifier();
				try {
					loan.verificarPrestamos(direcciones.get("entrada2"), direcciones.get("salida2"));
				} catch (MappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OutputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		//converter.traerSalida(direcciones.get("entrada2"));
		
		//converter.verificarPrestamosEnPeligro(direcciones.get("salida2"));
	}
		
	

}
