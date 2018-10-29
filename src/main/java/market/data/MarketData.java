package market.data;

import java.util.HashMap;
import java.util.Map;

import market.data.controller.LoanVerifier;
import market.data.controller.Market;
import market.data.controller.Negocio;
import market.data.exceptions.InputException;
import market.data.exceptions.MappingException;
import market.data.exceptions.OutputException;
import market.data.utilidades.IO;

public class MarketData {

	public static void main(String[] args) {
	
		Map<String, String> direcciones = new HashMap<String, String>();
		IO io = new IO();
		
		if(args.length> 0 && args.length< 3) {
			direcciones = io.verificarArgs(args);
		}else {
			
			System.out.println("Introdujo un numero incorrecto deargumentos, reintente");
			System.exit(1);
		}
		
		if(direcciones.containsKey("entrada1")) {

			Market market = new Market();
			try {
				market.extraerValoresSimplificado(direcciones.get("entrada1"));
			} catch (InputException e) {
				e.printStackTrace();
			} catch (MappingException e) {
				e.printStackTrace();
			} catch (OutputException e) {
				e.printStackTrace();
			}

		}
		
		if(direcciones.containsKey("entrada2") && direcciones.containsKey("salida1")) {
			LoanVerifier loan = new LoanVerifier();
			try {
				loan.verificarPrestamos(direcciones.get("entrada2"), direcciones.get("salida1"));
			} catch (MappingException e) {
				e.printStackTrace();
			} catch (OutputException e) {
				e.printStackTrace();
			} catch (InputException e) {
				e.printStackTrace();
			}
		}
	
	}
		
	

}
