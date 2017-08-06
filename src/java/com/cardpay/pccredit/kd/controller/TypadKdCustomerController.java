package com.cardpay.pccredit.kd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.LocationInfoForm;
import com.cardpay.pccredit.kd.model.TypadKdCustomer;
import com.cardpay.pccredit.kd.service.TypadKdCustomerServie;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class TypadKdCustomerController {

	@Autowired
	private TypadKdCustomerServie CustomerServi;
	/**
	 * 查询新申请的客户
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/Customer/selectSqCustomer.json", method = { RequestMethod.GET })
	public String selectSqCustomer(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<TypadKdCustomer>result=CustomerServi.selectSqCustomer();
		map.put("result", result);
		map.put("size",result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/Customer/selectSqCustomerImage.json", method = { RequestMethod.GET })
	public String selectSqCustomerImage(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<TypadKdCustomer> Image1=new ArrayList<TypadKdCustomer>();
		List<TypadKdCustomer> Image2=new ArrayList<TypadKdCustomer>();
		List<TypadKdCustomer> Image3=new ArrayList<TypadKdCustomer>();
		List<TypadKdCustomer> Image4=new ArrayList<TypadKdCustomer>();
		List<TypadKdCustomer> result=CustomerServi.selectImageType(request.getParameter("id"));
		for(int i=0;i<result.size();i++){
			if(result.get(i).getUPLOAD_FILE_NAME().equals("身份证")){
				
					Image1.add(Image1.size(), result.get(i));
			}else if(result.get(i).getUPLOAD_FILE_NAME().equals("银行流水")){
				
				Image1.add(Image2.size(), result.get(i));
		}else if(result.get(i).getUPLOAD_FILE_NAME().equals("社保缴纳证明")){
			
			Image1.add(Image3.size(), result.get(i));
	}else if(result.get(i).getUPLOAD_FILE_NAME().equals("缴税证明")){
		
		Image1.add(Image4.size(), result.get(i));
}
		}
		map.put("Image1", Image1);
		map.put("Image2", Image2);
		map.put("Image3", Image3);
		map.put("Image4", Image4);
		map.put("Image1size", Image1.size());
		map.put("Image2size", Image2.size());
		map.put("Image3size", Image3.size());
		map.put("Image4size", Image4.size());
		System.out.println(Image4.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
