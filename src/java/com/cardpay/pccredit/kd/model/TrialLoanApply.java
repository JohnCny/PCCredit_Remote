package com.cardpay.pccredit.kd.model;

import java.util.Date;

/**
 * 提额申请信息
 */
public class TrialLoanApply {
	  private String id;
	  private String customerName;
	  private String cardId;
	  private String phoneNo;
	  private String applyAmt;
	  private String loanTerm;
	  private String applyTime;
	  private String loanState;//0-待调查 1-补充调查 2-快审中 3-通过4-拒绝
	  private String remarks;
	  
	  private String AuditAmt;//审批通过金额
	  private String AuditTime;//审批时间
	  
	  
	
	public String getAuditAmt() {
		return AuditAmt;
	}
	public void setAuditAmt(String auditAmt) {
		AuditAmt = auditAmt;
	}
	public String getAuditTime() {
		return AuditTime;
	}
	public void setAuditTime(String auditTime) {
		AuditTime = auditTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getApplyAmt() {
		return applyAmt;
	}
	public void setApplyAmt(String applyAmt) {
		this.applyAmt = applyAmt;
	}
	public String getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getLoanState() {
		return loanState;
	}
	public void setLoanState(String loanState) {
		this.loanState = loanState;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
