package com.recipeFinder.common.valueObject;

import java.util.Date;

public class FridgeItem extends Item {
	private Date useBy;

	public Date getUseBy() {
		return useBy;
	}

	public void setUseBy(Date useBy) {
		this.useBy = useBy;
	}
}
