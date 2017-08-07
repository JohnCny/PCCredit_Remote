package com.cardpay.pccredit.kd.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author admin
 * 补充调查
 */
@ModelParam(table = "supplementary_survey_data")
public class SupplementarySurveyData extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String applyId;
	private String maritalStatus;
	private String highEdu;
	private String domicile;
	private String ownHouses;
	private String mortgageHouses;
	private String mortgageBalamt;
	private String ownVehicles;
	private String creditStatus;
	private String creditOverdueTimes;
	private String loanOverdueTimes;
	private String loanBalamt;
	private String guaranteed;
	private String numOfEcoDepend;
	private String annualIncomeSpouse;
	private String childEdu;
	private String serviceLife;
	private String mainBusinessIncome;
	private String currentAssets;
	private String fixedAssets;
	private String shortTermLiab;
	private String ownedEqu;
	private String annualDisIncome;
	private String otherImcome;
	private String stock;
	private String totalAssets;
	private String totalLiab;
	private String payPrivateUse;
	private String remarks;
	
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getHighEdu() {
		return highEdu;
	}
	public void setHighEdu(String highEdu) {
		this.highEdu = highEdu;
	}
	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	public String getOwnHouses() {
		return ownHouses;
	}
	public void setOwnHouses(String ownHouses) {
		this.ownHouses = ownHouses;
	}
	public String getMortgageHouses() {
		return mortgageHouses;
	}
	public void setMortgageHouses(String mortgageHouses) {
		this.mortgageHouses = mortgageHouses;
	}
	public String getMortgageBalamt() {
		return mortgageBalamt;
	}
	public void setMortgageBalamt(String mortgageBalamt) {
		this.mortgageBalamt = mortgageBalamt;
	}
	public String getOwnVehicles() {
		return ownVehicles;
	}
	public void setOwnVehicles(String ownVehicles) {
		this.ownVehicles = ownVehicles;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getCreditOverdueTimes() {
		return creditOverdueTimes;
	}
	public void setCreditOverdueTimes(String creditOverdueTimes) {
		this.creditOverdueTimes = creditOverdueTimes;
	}
	public String getLoanOverdueTimes() {
		return loanOverdueTimes;
	}
	public void setLoanOverdueTimes(String loanOverdueTimes) {
		this.loanOverdueTimes = loanOverdueTimes;
	}
	public String getLoanBalamt() {
		return loanBalamt;
	}
	public void setLoanBalamt(String loanBalamt) {
		this.loanBalamt = loanBalamt;
	}
	public String getGuaranteed() {
		return guaranteed;
	}
	public void setGuaranteed(String guaranteed) {
		this.guaranteed = guaranteed;
	}
	public String getNumOfEcoDepend() {
		return numOfEcoDepend;
	}
	public void setNumOfEcoDepend(String numOfEcoDepend) {
		this.numOfEcoDepend = numOfEcoDepend;
	}
	public String getAnnualIncomeSpouse() {
		return annualIncomeSpouse;
	}
	public void setAnnualIncomeSpouse(String annualIncomeSpouse) {
		this.annualIncomeSpouse = annualIncomeSpouse;
	}
	public String getChildEdu() {
		return childEdu;
	}
	public void setChildEdu(String childEdu) {
		this.childEdu = childEdu;
	}
	public String getServiceLife() {
		return serviceLife;
	}
	public void setServiceLife(String serviceLife) {
		this.serviceLife = serviceLife;
	}
	public String getMainBusinessIncome() {
		return mainBusinessIncome;
	}
	public void setMainBusinessIncome(String mainBusinessIncome) {
		this.mainBusinessIncome = mainBusinessIncome;
	}
	public String getCurrentAssets() {
		return currentAssets;
	}
	public void setCurrentAssets(String currentAssets) {
		this.currentAssets = currentAssets;
	}
	public String getFixedAssets() {
		return fixedAssets;
	}
	public void setFixedAssets(String fixedAssets) {
		this.fixedAssets = fixedAssets;
	}
	public String getShortTermLiab() {
		return shortTermLiab;
	}
	public void setShortTermLiab(String shortTermLiab) {
		this.shortTermLiab = shortTermLiab;
	}
	public String getOwnedEqu() {
		return ownedEqu;
	}
	public void setOwnedEqu(String ownedEqu) {
		this.ownedEqu = ownedEqu;
	}
	public String getAnnualDisIncome() {
		return annualDisIncome;
	}
	public void setAnnualDisIncome(String annualDisIncome) {
		this.annualDisIncome = annualDisIncome;
	}
	public String getOtherImcome() {
		return otherImcome;
	}
	public void setOtherImcome(String otherImcome) {
		this.otherImcome = otherImcome;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}
	public String getTotalLiab() {
		return totalLiab;
	}
	public void setTotalLiab(String totalLiab) {
		this.totalLiab = totalLiab;
	}
	public String getPayPrivateUse() {
		return payPrivateUse;
	}
	public void setPayPrivateUse(String payPrivateUse) {
		this.payPrivateUse = payPrivateUse;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
