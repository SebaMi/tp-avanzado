package market.data.model;

public class PrestamoPeligroso {
	
	private String id;
	private String creditPolicy;
	private Double amount;
	private Double eligible_colateral;
	
	public PrestamoPeligroso() {
		
	}
	
	public PrestamoPeligroso(String id, String creditPolicy, Double amount, Double eligible_colateral) {
		this.id = id;
		this.creditPolicy = creditPolicy;
		this.amount = amount;
		this.eligible_colateral = eligible_colateral;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreditPolicy() {
		return creditPolicy;
	}
	public void setCreditPolicy(String creditPolicy) {
		this.creditPolicy = creditPolicy;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getEligible_colateral() {
		return eligible_colateral;
	}
	public void setEligible_colateral(Double eligible_colateral) {
		this.eligible_colateral = eligible_colateral;
	}
	

}
