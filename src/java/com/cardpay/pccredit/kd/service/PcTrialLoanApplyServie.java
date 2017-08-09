package com.cardpay.pccredit.kd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.kd.dao.PcTrialLoanApplyDao;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class PcTrialLoanApplyServie {
	@Autowired
	private PcTrialLoanApplyDao PcTrialLoanApplydao;
	
	public QueryResult<TrialLoanApply> findCustomerApplicationIntopieceDecison(
			TrialLoanApply filter) {
		// TODO Auto-generated method stub
		
		List<TrialLoanApply> listCAI = PcTrialLoanApplydao.findCustomerApplicationIntopieceDecisionForm(filter);
		int size = PcTrialLoanApplydao.findCustomerApplicationIntopieceDecisionCountForm(filter);
		QueryResult<TrialLoanApply> qs = new QueryResult<TrialLoanApply>(size, listCAI);
		return qs;
	}


}
