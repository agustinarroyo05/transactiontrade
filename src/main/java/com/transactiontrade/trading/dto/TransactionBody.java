package com.transactiontrade.trading.dto;

public class TransactionBody {
	private String type;
	private int amount;
	
	public TransactionBody(){}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
