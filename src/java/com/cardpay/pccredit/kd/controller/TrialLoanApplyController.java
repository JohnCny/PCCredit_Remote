package com.cardpay.pccredit.kd.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.web.AmountAdjustmentForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.kd.model.SupplementarySurveyData;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.cardpay.pccredit.kd.service.TrialLoanApplyServie;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;

@Controller
public class TrialLoanApplyController {

	@Autowired
	private TrialLoanApplyServie trialLoanApplyServie;
	/**
	 * 查询快审中的提额申请信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/ks/selectMetionApply.json", method = { RequestMethod.GET })
	public String selectSqCustomer(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<TrialLoanApply>result = trialLoanApplyServie.selectMetionApply(request);
		map.put("result", result);
		map.put("size",result.size());
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 快审审批
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/ks/update.json")
	@JRadOperation(JRadOperation.APPROVE)
	public String update(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				trialLoanApplyServie.doUpdateCustomerApply(request);
				returnMap.put("message","提交成功");
			} catch (Exception e) {
				returnMap.put("message","提交失败");
			}
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/saveBcdc.json")
	public String saveBcdc(@ModelAttribute SupplementarySurveyData supplementarySurveyData,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		
		try {
			trialLoanApplyServie.saveBcdc(supplementarySurveyData);
			returnMap.put("message","提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("message","提交失败");
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
}
