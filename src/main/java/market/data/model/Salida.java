package market.data.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Salida {

	private String isin;
	private String price;
	private String ticker;
	
	public Salida() {
		
	}
	
	public Salida(String isin, String price, String ticker) {
		this.isin = isin;
		this.price = price;
		this.ticker = ticker;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		DecimalFormat df = new DecimalFormat("0.00##");
		this.price = df.format(Double.parseDouble(price));
	}	
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
		
}
