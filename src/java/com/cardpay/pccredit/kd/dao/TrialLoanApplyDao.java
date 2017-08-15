package com.cardpay.pccredit.kd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.kd.constant.CustomerStockForm;
import com.cardpay.pccredit.kd.model.LoanUploadData;
import com.cardpay.pccredit.kd.model.SupplementaryInvestigationData;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.cardpay.pccredit.kd.model.TrialLoanApy;
import com.cardpay.pccredit.kd.model.TypadKdCustomer;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface TrialLoanApplyDao {
	List<TrialLoanApply> selectMetionApply(@Param(value = "chineseName") String chineseName);
	
	public void doUpdateCustomerApply(@Param(value = "id") String id,
									  @Param(value = "state") String state,
									  @Param(value = "amt") String amt);
	
	public List<SupplementaryInvestigationData> selectSuppleMentInformation(@Param(value = "id") String id);
	
	
	public List<LoanUploadData> selectLoanUploadDataList(@Param(value = "appId") String appId,
			@Param(value = "type") String type);
	//int selectLoanUploadDataCount(@Param(value = "batchId") String batchId);
	
	// 根据 身份证号查询存量客户数据
	public List<CustomerStockForm> selectCustomerStock(@Param(value = "cardId") String cardId);
	
	// 根据 appId更新评估额度到提额申请表中
	
	public void updateCustomerApplyCreditAmt(@Param(value = "id") String id,
			  @Param(value = "amt") int amt,@Param(value = "msg") String msg);
	
	public List<TrialLoanApply> selectTrialLoanApplyList(@Param(value = "appId") String appId);
	
	void insertCustomerLoanApply(TrialLoanApy t);
}
