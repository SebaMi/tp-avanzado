package market.data.model;

public class Prestamo {
	
	private Double amount;
	private String creditpolicy;
	private String id;
	private Positions[] positions;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCreditpolicy() {
		return creditpolicy;
	}
	public void setCreditpolicy(String creditpolicy) {
		this.creditpolicy = creditpolicy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Positions[] getPositions() {
		return positions;
	}
	public void setPositions(Positions[] positions) {
		this.positions = positions;
	}
	

}
