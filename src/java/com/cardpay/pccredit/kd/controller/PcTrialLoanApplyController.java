package com.cardpay.pccredit.kd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.kd.dao.TrialLoanApplyDao;
import com.cardpay.pccredit.kd.model.LoanUploadData;
import com.cardpay.pccredit.kd.model.SupplementaryInvestigationData;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.cardpay.pccredit.kd.model.TypadKdCustomer;
import com.cardpay.pccredit.kd.service.PcTrialLoanApplyServie;
import com.cardpay.pccredit.kd.service.TrialLoanApplyServie;
import com.cardpay.pccredit.kd.service.TypadKdCustomerServie;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/ipad/ks/*")
@JRadModule("ipad.ks")
public class PcTrialLoanApplyController extends BaseController {
	@Autowired
	private PcTrialLoanApplyServie trialLoanApplyServie;
	@Autowired
	private TypadKdCustomerServie padKdCustomerServie;

	@Autowired
	private TypadKdCustomerServie CustomerServi;
	
	@Autowired
	private TrialLoanApplyDao trialLoanApplyDao;
	
	@Autowired
	private  TrialLoanApplyServie  triservice;
	
	
	    // 审贷决议
		@ResponseBody
		@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView browse(@ModelAttribute TrialLoanApply filter,HttpServletRequest request) {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user == null ){
				JRadModelAndView mv = new JRadModelAndView("/abnormal/404", request);
				return mv;
			}
			filter.setRequest(request);
			QueryResult<TrialLoanApply> result = trialLoanApplyServie.findCustomerApplicationIntopieceDecison(filter);
			JRadPagedQueryResult<TrialLoanApply> pagedResult = new JRadPagedQueryResult<TrialLoanApply>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/kd/dkintopieces_browse", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
		
		// 快贷审批
		@ResponseBody
		@RequestMapping(value = "spbrowse.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView spbrowse(@ModelAttribute TrialLoanApply filter,HttpServletRequest request) {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user == null ){
				JRadModelAndView mv = new JRadModelAndView("/abnormal/404", request);
				return mv;
			}
			filter.setRequest(request);
			String appId=request.getParameter("appId");
			String quotaId=request.getParameter("quotaId");
			filter.setId(appId);
			QueryResult<TrialLoanApply> result = trialLoanApplyServie.findCustomerApplicationIntopieceDecison(filter);
			JRadPagedQueryResult<TrialLoanApply> pagedResult = new JRadPagedQueryResult<TrialLoanApply>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/kd/dkinput_decision", request);
			TrialLoanApply applylist=result.getItems().get(0);
			
			//查询显示评估结果
			mv.addObject("applylist", applylist);
			mv.addObject("appId", appId);
			mv.addObject("quotaId", quotaId);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
		
		// 显示图片页面
		@ResponseBody
		@RequestMapping(value = "showIma.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView showIma(HttpServletRequest request) {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user == null ){
				JRadModelAndView mv = new JRadModelAndView("/abnormal/404", request);
				return mv;
			}
			String appId=request.getParameter("appId");
			List<TypadKdCustomer> result=padKdCustomerServie.selectImageType(appId);
			
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			List<TypadKdCustomer> Image1=new ArrayList<TypadKdCustomer>();
			List<TypadKdCustomer> Image2=new ArrayList<TypadKdCustomer>();
			List<TypadKdCustomer> Image3=new ArrayList<TypadKdCustomer>();
			List<TypadKdCustomer> Image4=new ArrayList<TypadKdCustomer>();
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getREMARKS().equals("身份证")) {
					Image1.add(Image1.size(), result.get(i));
				} else if (result.get(i).getREMARKS().equals("银行流水")) {
					Image2.add(Image2.size(), result.get(i));
				} else if (result.get(i).getREMARKS().equals("社保缴纳证明")) {
					Image3.add(Image3.size(), result.get(i));
				} else if (result.get(i).getREMARKS().equals("纳税证明")) {
					Image4.add(Image4.size(), result.get(i));
				}
			}
			JRadModelAndView mv = new JRadModelAndView("/kd/showImages", request);
			mv.addObject("result", result);
			mv.addObject("appId", appId);
			mv.addObject("Image1size", Image1.size());
			mv.addObject("Image2size", Image2.size());
			mv.addObject("Image3size", Image3.size());
			mv.addObject("Image4size", Image4.size());
			return mv;
		}
		
		// 下载图片
		@ResponseBody
		@RequestMapping(value = "downLoadYxzlJn.json",method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.EXPORT)
		public AbstractModelAndView downLoadYxzlJn(HttpServletRequest request,HttpServletResponse response){
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user == null ){
				JRadModelAndView mv = new JRadModelAndView("/abnormal/404", request);
				return mv;
			}
			try {
				String s =request.getParameter("id");
				CustomerServi.downLoadYxzlJn(response,s);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		// 显示补充调查页面
		@ResponseBody
		@RequestMapping(value = "showBcDc.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView showBcDc(HttpServletRequest request) {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user == null ){
				JRadModelAndView mv = new JRadModelAndView("/abnormal/404", request);
				return mv;
			}
			String appId=request.getParameter("appId");
			List<SupplementaryInvestigationData> list = trialLoanApplyDao.selectSuppleMentInformation(appId);
			JRadModelAndView mv = new JRadModelAndView("/kd/decision_Form", request);
			mv.addObject("supinvestdata", list.get(0));
			return mv;
		}
		
		
		@ResponseBody
		@RequestMapping(value = "updateAll.json")
		public JRadReturnMap updateAll(HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user==null){
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE, "您没有操作权限");
				return returnMap;
			}
			if (returnMap.isSuccess()) {
				try {
					triservice.doUpdateCustomerApply(request);
					returnMap.addGlobalMessage("保存成功");
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
			}

			return returnMap;
		}
		
		
		//查看已上传图片列表
		@ResponseBody
		@RequestMapping(value = "findPageView.page", method = { RequestMethod.GET })
		public AbstractModelAndView findPageView(HttpServletRequest request) {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user == null ){
				JRadModelAndView mv = new JRadModelAndView("/abnormal/404", request);
				return mv;
			}
			JRadModelAndView mv = new JRadModelAndView("/kd/picture_view_browse", request);
			String appId = request.getParameter("id");//申请id
			String type = request.getParameter("type");//身份证等等
			//triservice.insert();
			List<LoanUploadData> list = trialLoanApplyDao.selectLoanUploadDataList(appId,type);
			mv.addObject("QzApplnAttachmentDetail", list);
			return mv;
		}
}
