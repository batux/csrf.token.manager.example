package csrf.manager.redis.example.controller;

import java.io.Serializable;

public class AmountRequest implements Serializable{

	private static final long serialVersionUID = -3059385666679456009L;

	private String amount;

	private String requestId;
	

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
