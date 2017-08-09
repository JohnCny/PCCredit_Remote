package com.cardpay.pccredit.kd.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.JnUserLoginResult;
import com.cardpay.pccredit.kd.model.SupplementaryInvestigationData;
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
	
	
	/**
	 * 快审审批   补充调查详情页面信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/ks/selectSuppleMentInformation.json", method = { RequestMethod.GET })
	public String selectSuppleMentInformation(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<SupplementaryInvestigationData>result = trialLoanApplyServie.selectSuppleMentInformation(request);
		map.put("result", result.get(0));
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/saveBcdc.json")
	public String saveBcdc(@ModelAttribute SupplementarySurveyData supplementarySurveyData,HttpServletRequest request) {
		//JRadReturnMap returnMap = new JRadReturnMap();
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Result result = new  Result();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		
		
		// 验证表单参数 
		// num
		Map<String,String>  map1 = com.cardpay.pccredit.kd.constant.Constant.ATT_NUM;
		Map<String,String>  nummap = returnNumMap(supplementarySurveyData);
		for (String key : nummap.keySet()) {  
			if(!IsNum(nummap.get(key))){
				result.setStatus("fail");
				result.setReason(map1.get(key)+"格式有误,请重新填写！");
				map.put("result",result);
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
			}else{
				result.setStatus("success");
				map.put("result",result);
			}
		}
		
		// double 
		Map<String,String>  map2 = com.cardpay.pccredit.kd.constant.Constant.ATT_DOUBLE;
		Map<String,String>  doublemap = returnDobleMap(supplementarySurveyData);
		for (String key : doublemap.keySet()) {  
			if(!IsDouble(doublemap.get(key))){
				result.setStatus("fail");
				result.setReason(map2.get(key)+"格式有误,请重新填写！");
				map.put("result",result);
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
			}else{
				result.setStatus("success");
				map.put("result",result);
			}
		}  

		// 提交审核审批
		try {
			trialLoanApplyServie.saveBcdc(supplementarySurveyData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	// 判断是否为整数
    public Boolean IsNum(String value){
    	java.util.regex.Pattern pattern = Pattern.compile("[0-9]*");
    	java.util.regex.Matcher isNum = pattern.matcher(value.trim());
    	if(!isNum.matches()){
    		return false;
    	}
    	return true;
    }
    
    //判断 是否是数值型
    public  Boolean IsDouble(String value){
    	// 判断小数点后一位的数字的正则表达式
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); 
    	java.util.regex.Matcher isNum = pattern.matcher(value.trim());
    	if(!isNum.matches()){
    		return false;
    	}
    	return true;
    }
    
    
    public Map<String,String> returnNumMap (SupplementarySurveyData supplementarySurveyData){
    	Map<String,String> ATT_NUM = new HashMap<String,String>();
    	ATT_NUM.put("ownHouses",supplementarySurveyData.getOwnHouses());
    	ATT_NUM.put("mortgageHouses",supplementarySurveyData.getMortgageHouses());
    	ATT_NUM.put("ownVehicles",supplementarySurveyData.getOwnVehicles());
    	ATT_NUM.put("creditOverdueTimes",supplementarySurveyData.getCreditOverdueTimes());
    	ATT_NUM.put("loanOverdueTimes",supplementarySurveyData.getLoanOverdueTimes());
    	ATT_NUM.put("numOfEcoDepend",supplementarySurveyData.getNumOfEcoDepend());
    	ATT_NUM.put("serviceLife",supplementarySurveyData.getServiceLife());
    	return ATT_NUM;
    }
    
    public Map<String,String> returnDobleMap (SupplementarySurveyData supplementarySurveyData){
    	Map<String,String> ATT_DOUBLE = new HashMap<String,String>();
    	ATT_DOUBLE.put("mortgageBalamt",supplementarySurveyData.getMortgageBalamt());
    	ATT_DOUBLE.put("loanBalamt",supplementarySurveyData.getLoanBalamt());
    	ATT_DOUBLE.put("guaranteed",supplementarySurveyData.getGuaranteed());
    	ATT_DOUBLE.put("annualIncomeSpouse",supplementarySurveyData.getAnnualIncomeSpouse());
    	ATT_DOUBLE.put("mainBusinessIncome",supplementarySurveyData.getMainBusinessIncome());
    	ATT_DOUBLE.put("currentAssets",supplementarySurveyData.getCurrentAssets());
    	ATT_DOUBLE.put("fixedAssets",supplementarySurveyData.getFixedAssets());
    	ATT_DOUBLE.put("shortTermLiab",supplementarySurveyData.getShortTermLiab());
    	ATT_DOUBLE.put("ownedEqu",supplementarySurveyData.getOwnedEqu());
    	ATT_DOUBLE.put("annualDisIncome",supplementarySurveyData.getAnnualDisIncome());
    	ATT_DOUBLE.put("otherImcome",supplementarySurveyData.getOtherImcome());
    	ATT_DOUBLE.put("stock",supplementarySurveyData.getStock());
    	ATT_DOUBLE.put("totalAssets",supplementarySurveyData.getTotalAssets());
    	ATT_DOUBLE.put("totalLiab",supplementarySurveyData.getTotalLiab());
    	ATT_DOUBLE.put("payPrivateUse",supplementarySurveyData.getPayPrivateUse());
    	return ATT_DOUBLE;
    }
}
