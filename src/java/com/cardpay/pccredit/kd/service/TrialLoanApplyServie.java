package com.cardpay.pccredit.kd.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






import com.cardpay.pccredit.kd.constant.Constant;
import com.cardpay.pccredit.kd.dao.TrialLoanApplyDao;
import com.cardpay.pccredit.kd.model.SupplementarySurveyData;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class TrialLoanApplyServie {
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private TrialLoanApplyDao trialLoanApplyDao ;

	public List<TrialLoanApply> selectMetionApply(HttpServletRequest request){
		String chineseName = request.getParameter("chineseName");
		return trialLoanApplyDao.selectMetionApply(chineseName);
	}
	
	public void doUpdateCustomerApply(HttpServletRequest request){
		String auditresult = request.getParameter("status");//结论
		String id = request.getParameter("appId");//提额申请id
		String amt = request.getParameter("amt");//金额
		
		if("APPROVE".equals(auditresult)){//通过
			trialLoanApplyDao.doUpdateCustomerApply(id,Constant.LOAN_STATE_3,amt);
		}else if("REJECT".equals(auditresult)){//拒绝
			trialLoanApplyDao.doUpdateCustomerApply(id,Constant.LOAN_STATE_4,"");
		}else if("SUPINVEST".equals(auditresult)){//补充调查
			trialLoanApplyDao.doUpdateCustomerApply(id,Constant.LOAN_STATE_1,"");
		}
	}

	public void saveBcdc(SupplementarySurveyData supplementarySurveyData) {
		commonDao.insertObject(supplementarySurveyData);
		String sql = "update customer_loan_apply set LOAN_STATE='1' where id='"+supplementarySurveyData.getApplyId()+"'";
		commonDao.queryBySql(sql, null);
	}
}
