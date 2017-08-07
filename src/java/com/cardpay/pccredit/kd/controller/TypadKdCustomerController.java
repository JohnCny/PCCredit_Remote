package com.cardpay.pccredit.kd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.LocationInfoForm;
import com.cardpay.pccredit.kd.model.TypadKdCustomer;
import com.cardpay.pccredit.kd.service.TypadKdCustomerServie;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

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
			if(result.get(i).getREMARKS().equals("身份证")){
				
					Image1.add(Image1.size(), result.get(i));
			}else if(result.get(i).getREMARKS().equals("银行流水")){
				
				Image2.add(Image2.size(), result.get(i));
		}else if(result.get(i).getREMARKS().equals("社保缴纳证明")){
			
			Image3.add(Image3.size(), result.get(i));
	}else if(result.get(i).getREMARKS().equals("缴税证明")){
		
		Image4.add(Image4.size(), result.get(i));
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
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 上传文件
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/sqcustomer/imagesImport.json")
	public Map<String, Object>  imagesImport(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=gbk");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
		CustomerServi.uploadYxzlFileBySpring(file, request.getParameter("id"),  request.getParameter("name"));
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	/**
	 * 查看图片
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/TypadImageBrowse/downLoadYxzlJn.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlJn(HttpServletRequest request,HttpServletResponse response){
		try {
			String s =request.getParameter("id");
			CustomerServi.downLoadYxzlJn(response,s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询更新后的图片
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/Customer/selectAllIma.json", method = { RequestMethod.GET })
	public String selectAllIma(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<TypadKdCustomer>result=CustomerServi.selectAllIma( request.getParameter("id"), request.getParameter("name"));
		map.put("result", result);
		map.put("size",result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查看审批历史
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/Customer/selectHistory.json", method = { RequestMethod.GET })
	public String selectHistory(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		TypadKdCustomer result=CustomerServi.selectHistory( request.getParameter("id"));
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 查看补充调查资料
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/Customer/selectHistoryBC.json", method = { RequestMethod.GET })
	public String selectHistoryBC(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		TypadKdCustomer result=CustomerServi.selectSqCustomerBcl( request.getParameter("id"));
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 删除照片
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/Customer/deleteIma.json", method = { RequestMethod.GET })
	public String deleteIma(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String ID= request.getParameter("id");
		String imaId[]=ID.split(",");
		for(int i=0;i<imaId.length;i++){
			System.out.println(imaId[i]);
			CustomerServi.deleteIma(imaId[i]);
		}
		map.put("message", "删除成功");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
