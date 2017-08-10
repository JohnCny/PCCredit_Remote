package com.cardpay.pccredit.kd.dao;


import java.util.List;

import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface PcTrialLoanApplyDao {

	List<TrialLoanApply> findCustomerApplicationIntopieceDecisionForm(
			TrialLoanApply filter);

	int findCustomerApplicationIntopieceDecisionCountForm(TrialLoanApply filter);

}
