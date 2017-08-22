package com.cardpay.pccredit.kd.constant;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_stock")
public class CustomerStockForm extends BusinessModel{
	
	private String	id; 										
	private String  lyDesc; 						  		
	private String  cardId; 							
	private String  age; 									
	private String  marrige; 							
	private String  hr; 										
	private String  edu; 									
	private String  hs; 										
	private String  up; 										
	private String  pn; 										
	private String  worklife; 							
	private String  annualIncome; 				
	private String  householdIncome; 			
	private String  householdNetAssets; 	
	private String  taxSituation; 				
	private String  insuranceSituation; 	
	private String  creditRecord; 				
	private String  caad; 						
	 
	 
	public String toString() {
		return lyDesc+"--"+cardId+"--"+age+"--"+marrige+"--"+hr+"--"+
				edu+"--"+hs+"--"+up+"--"+pn+"--"+
				worklife+"--"+annualIncome+"--"+householdIncome+"--"+householdNetAssets+"--"+taxSituation+"--"+insuranceSituation
				+"--"+creditRecord+"--"+caad;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLyDesc() {
		return lyDesc;
	}


	public void setLyDesc(String lyDesc) {
		this.lyDesc = lyDesc;
	}


	public String getCardId() {
		return cardId;
	}


	public void setCardId(String cardId) {
		this.cardId = cardId;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getMarrige() {
		return marrige;
	}


	public void setMarrige(String marrige) {
		this.marrige = marrige;
	}


	public String getHr() {
		return hr;
	}


	public void setHr(String hr) {
		this.hr = hr;
	}


	public String getEdu() {
		return edu;
	}


	public void setEdu(String edu) {
		this.edu = edu;
	}


	public String getHs() {
		return hs;
	}


	public void setHs(String hs) {
		this.hs = hs;
	}


	public String getUp() {
		return up;
	}


	public void setUp(String up) {
		this.up = up;
	}


	public String getPn() {
		return pn;
	}


	public void setPn(String pn) {
		this.pn = pn;
	}


	public String getWorklife() {
		return worklife;
	}


	public void setWorklife(String worklife) {
		this.worklife = worklife;
	}


	public String getAnnualIncome() {
		return annualIncome;
	}


	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}


	public String getHouseholdIncome() {
		return householdIncome;
	}


	public void setHouseholdIncome(String householdIncome) {
		this.householdIncome = householdIncome;
	}


	public String getHouseholdNetAssets() {
		return householdNetAssets;
	}


	public void setHouseholdNetAssets(String householdNetAssets) {
		this.householdNetAssets = householdNetAssets;
	}


	public String getTaxSituation() {
		return taxSituation;
	}


	public void setTaxSituation(String taxSituation) {
		this.taxSituation = taxSituation;
	}


	public String getInsuranceSituation() {
		return insuranceSituation;
	}


	public void setInsuranceSituation(String insuranceSituation) {
		this.insuranceSituation = insuranceSituation;
	}


	public String getCreditRecord() {
		return creditRecord;
	}


	public void setCreditRecord(String creditRecord) {
		this.creditRecord = creditRecord;
	}


	public String getCaad() {
		return caad;
	}


	public void setCaad(String caad) {
		this.caad = caad;
	}


	

}
