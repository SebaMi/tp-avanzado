package market.data;

import java.io.IOException;

import services.Url;

public class MarketData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Prueba");
		
		Url archivo = new Url();
		
			try {
				archivo.traerJson();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
