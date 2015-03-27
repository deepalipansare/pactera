package com.recipeFinder.common.valueObject;

import static com.recipeFinder.common.Constants.Unit;

public class Item {
	private String name;
	private Integer amount;
	private Unit unit;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	
}
