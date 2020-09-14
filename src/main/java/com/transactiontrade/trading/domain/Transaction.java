package com.transactiontrade.trading.domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Transaction {
	
	public enum Type {
		
		DEBIT(){
			
			public int getAmmount(int value) {
				return value;
			}
			
		}, 
		CREDIT(){
			
			public int getAmmount(int value) {
				return -1 * value;
			}
		};
		
		private static final Map<String, Type> stringToEnum =
			Stream.of(values()).collect(Collectors.toMap(Object::toString, e -> e));
	
		public abstract int getAmmount(int value);
	}
	
	private String Id;
	private int amount;
	private Type txType;
	private LocalDateTime efectiveDate;
	
	public int getAmount() {
		return txType.getAmmount(amount);
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public Type getType() {
		return txType;
	}
	
	public void setType(Type type) {
		txType = type;
	}
	public void setType(String type) {
		Type validType = Type.stringToEnum.get(type);
		if (validType != null) {
			throw new RuntimeException("Invalid Exception");
		}
	}
	public String getEfectiveDate() {
		return efectiveDate.toString();
	}
	public void setEfectiveDate(LocalDateTime efectiveDate) {
		this.efectiveDate = efectiveDate;
	}
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
	
}
