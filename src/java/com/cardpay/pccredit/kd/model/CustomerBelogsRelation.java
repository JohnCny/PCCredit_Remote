package com.cardpay.pccredit.kd.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_belogs_relation")
public class CustomerBelogsRelation extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String cardId;
	private String customerManagerId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
