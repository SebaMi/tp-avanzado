package market.data.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Salida {

	private String isin;
	private Double price;
	private String ticker;
	
	public Salida() {
		
	}
	
	public Salida(String isin, Double price, String ticker) {
		this.isin = isin;
		this.setPrice(price);//this.price = price;//
		this.ticker = ticker;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
//		//DecimalFormat df = new DecimalFormat("0.00##");
//		//this.price = df.format(Double.parseDouble(price));
//		Locale currentLocale = Locale.getDefault();
//		
//		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
//		otherSymbols.setDecimalSeparator('.');
//		otherSymbols.setGroupingSeparator(','); 
//		DecimalFormat df = new DecimalFormat("0.00##", otherSymbols);
		this.price = price; //df.format(Double.parseDouble(price));
	}	
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
		
}
