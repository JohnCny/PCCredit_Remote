package com.cardpay.pccredit.kd.model;

import java.util.Date;

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
	private String maritalStatus; // 个人信息-婚姻状况(0-未婚 1-已婚 2-离婚 3-再婚)',
	private String highEdu; // 个人信息-最高学位学历(0-初中及以下 1-高中或技校 2-大学或以上)',
	private String domicile; // 个人信息-户籍所在地(0-本省 1-本省外地 2-外地)',
	private String ownHouses; // '家庭资产-自有房产数量',
	private String mortgageHouses; // '家庭资产-按揭房产数量',
	private String mortgageBalamt; // '家庭资产-按揭贷款余额',
	private String ownVehicles; // '家庭资产-自有车辆数量',
	private String creditStatus; // '征信状况-信用状况(0-正常 1-不正常 2-无记录)',
	private String creditOverdueTimes; // '征信状况-信用逾期次数',
	private String loanOverdueTimes; // '征信状况-贷款逾期次数',
	private String loanBalamt; // '征信状况-贷款余额',
	private String guaranteed; // '征信状况-担保余额',
	private String numOfEcoDepend; // '家庭状况-经济上依赖的人数',
	private String annualIncomeSpouse; // '家庭状况-配偶年收入',
	private String childEdu; // '家庭状况-子女教育情况(0-无子女 1-上学 2-学龄前 3-工作)',
	private String serviceLife; // '经营及财务状况-业务年限',
	private String mainBusinessIncome; // '经营及财务状况-主营业务收入',
	private String currentAssets; // '经营及财务状况-流动资产',
	private String fixedAssets; // '经营及财务状况-固定资产',
	private String shortTermLiab; // '经营及财务状况-短期负债',
	private String ownedEqu; // '经营及财务状况-所有者权益',
	private String annualDisIncome; // '经营及财务状况-年可支配收入',
	private String otherImcome; // '经营及财务状况-其他工作收入',
	private String stock; // '经营及财务状况-存货',
	private String totalAssets; // '经营及财务状况-资产总计',
	private String totalLiab; // '经营及财务状况-负债总计',
	private String payPrivateUse; // '经营及财务状况-私人用途分期付款',
	private Date investTime;//调查时间
	private String remarks; // '备注'                                             
	                                                                
	
	
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
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
