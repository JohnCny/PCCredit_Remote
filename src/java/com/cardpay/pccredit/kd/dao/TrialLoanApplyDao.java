package com.cardpay.pccredit.kd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.kd.model.LoanUploadData;
import com.cardpay.pccredit.kd.model.SupplementaryInvestigationData;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
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
	
}
