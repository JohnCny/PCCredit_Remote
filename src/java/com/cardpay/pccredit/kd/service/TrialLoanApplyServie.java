package com.cardpay.pccredit.kd.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;











import sun.util.logging.resources.logging;

import com.cardpay.loanrating.widget.StockCredit;
import com.cardpay.pbccrcReport.PbccrcReport;
import com.cardpay.pccredit.kd.constant.Constant;
import com.cardpay.pccredit.kd.constant.CustomerStockForm;
import com.cardpay.pccredit.kd.constant.StockForm;
import com.cardpay.pccredit.kd.dao.TrialLoanApplyDao;
import com.cardpay.pccredit.kd.model.Result;
import com.cardpay.pccredit.kd.model.SupplementaryInvestigationData;
import com.cardpay.pccredit.kd.model.SupplementarySurveyData;
import com.cardpay.pccredit.kd.model.TrialLoanApply;
import com.cardpay.pccredit.kd.model.TrialLoanApy;
import com.cardpay.unstockrating.widget.Unstock;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class TrialLoanApplyServie {
	
	
	@Autowired
	private  CommonDao commonDao;
	@Autowired
	private TrialLoanApplyDao trialLoanApplyDao ;

	public List<TrialLoanApply> selectMetionApply(HttpServletRequest request){
		String chineseName = request.getParameter("chineseName");
		return trialLoanApplyDao.selectMetionApply(chineseName);
	}
	
	public void doUpdateCustomerApply(HttpServletRequest request) throws Exception{
		String auditresult = request.getParameter("status");//结论
		String id = request.getParameter("appId");//提额申请id
		String amt = request.getParameter("amt");//金额
		
		//通过
		if("APPROVE".equals(auditresult)){
			
			//try {
				// repCode
				int responseCode = sendRequest(id,amt);
				
				// success
				if(responseCode == 200){
					// 成功更新申请表LOAN_STATE
					trialLoanApplyDao.doUpdateCustomerApply(id,Constant.LOAN_STATE_3,amt);
				}
			/*} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			
		}else if("REJECT".equals(auditresult)){//拒绝
			trialLoanApplyDao.doUpdateCustomerApply(id,Constant.LOAN_STATE_4,"");
		}else if("SUPINVEST".equals(auditresult)){//补充调查
			trialLoanApplyDao.doUpdateCustomerApply(id,Constant.LOAN_STATE_1,"");
		}
	}
	
	
	// 请求同步额度 方法  获取返回报文
	public static int sendRequest(String appId,String auditAmt) throws IOException {
		String result = "";
		int responseCode  = 500;
		//try {
            //创建连接
            URL url = new URL("http://bsp.qkjr.com.cn/api/quota/pad_update_amount");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.element("quota_id", appId);
            obj.element("updated_quota", auditAmt);
            out.writeBytes(obj.toString());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            result = sb.toString();
            responseCode = connection.getResponseCode();
            
            System.out.println("返回报文："+result);
            System.out.println("返回码："+connection.getResponseCode());
            
            reader.close();
            connection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		return responseCode;
	}
	
	
	/*public static void main(String[] args) {
		System.out.println(sendRequest("1","3000"));
	}*/
	
	public List<SupplementaryInvestigationData> selectSuppleMentInformation(HttpServletRequest request){
		String id = request.getParameter("appId");
		return trialLoanApplyDao.selectSuppleMentInformation(id);
	}

	public void saveBcdc(SupplementarySurveyData supplementarySurveyData) {
		// 评估额度
		int creditAmt = 0;
		// 描述信息
		String msg ="";
		
		// 1.保存补充调查信息
		supplementarySurveyData.setInvestTime(new Date());
		commonDao.insertObject(supplementarySurveyData);
		
		
		// 2.数据模型评估 
		Result result = getCreditAmt(supplementarySurveyData.getCardId(),supplementarySurveyData.getApplyId());
		creditAmt = result.getCreditAmt();
		msg = result.getMsg();
		
		// 3.保存评估额度 到提额申请表
		trialLoanApplyDao.updateCustomerApplyCreditAmt(supplementarySurveyData.getApplyId(),creditAmt,msg);
		
		// 4.提交审批
		trialLoanApplyDao.doUpdateCustomerApply(supplementarySurveyData.getApplyId(),Constant.LOAN_STATE_2,"");
	}
	
	
	
	
	
	// 获取评估额度
	public Result getCreditAmt(String cardId,String appId){
		// 评估金额
		int creditAmt = 0;
		
		// 描述
		String msg = "";
		
		// 1.根据id 查询该客户是否是存量客户数据
		List<CustomerStockForm> list = trialLoanApplyDao.selectCustomerStock(cardId);
		
		if(list!=null && list.size()>0){
			// 存量客户数据
			CustomerStockForm stock = list.get(0);
			StockForm form = returnVo(stock);
			creditAmt =StockCredit.calculate(form.getAge(),
											form.getMarriage(),
											form.getHouseHold(),
											form.getEdu(), 
											form.getHouse(),
											form.getUnitType(), 
											form.getCareer(), 
											form.getWorkYear(), 
											form.getYearIncome(),
											form.getFamilyYearIncome(),
											form.getFamilyNetAssets(),
											form.getTaxStatus(), 
											form.getInsured(), 
											form.getCredit(),
											form.getDisputes());
		}else{
			// 非存量客户
			int kuhxd =0;
			
			// 获取客户好信度
			TrialLoanApply trial = trialLoanApplyDao.selectTrialLoanApplyList(appId).get(0);
			Map<String, String> map = getGoodReliability(trial.getCardId(),trial.getCustomerName(),
														 "25e7d8ad2462481fb2ce11ac3dc069f5",trial.getPhoneNo(),trial.getCardNum());
			
			// 查询成功
			if("0".equals(map.get("ret").toString())){
				kuhxd = Integer.parseInt(map.get("credooScore").toString());//好信度
				msg = "";
			}else{ 
				msg = map.get("msg").toString();
			}
			
			// 根据客户好信度 进行额度评估 
			creditAmt = Unstock.calculate(kuhxd);
		}
		
		// set Result
		Result result = new Result();
		result.setCreditAmt(creditAmt);
		result.setMsg(msg);
 		return result;
	}
	
	 // return vo
	public StockForm returnVo(CustomerStockForm stock){
		StockForm form = new StockForm();
		
		// 1.年龄
		if("未知".equals( stock.getAge())){
			form.setAge(-2);
		}else{
			form.setAge(Integer.parseInt(stock.getAge()));
		}
		
		// 2.marriage：婚姻状况
		if("未婚".equals(stock.getMarrige())){
			form.setMarriage(1);
		}else if("已婚".equals(stock.getMarrige())){
			form.setMarriage(2);
		}else if("离异有小孩".equals(stock.getMarrige())){
			form.setMarriage(3);
		}else if("离异无小孩".equals(stock.getMarrige())){
			form.setMarriage(4);
		}else if("丧偶".equals(stock.getMarrige())){
			form.setMarriage(-1);
		}else if("其他".equals(stock.getMarrige())){
			form.setMarriage(-2);
		}else if("未知".equals(stock.getMarrige())){
			
		}
		
		// 3.本地居住年数
		if("本地人".equals(stock.getHr())){
			form.setHouseHold(30);
		}else if("未知".equals(stock.getHr())){
			form.setHouseHold(-2);
		}else {
			form.setHouseHold((int)Float.parseFloat(stock.getHr()));
		}
		
		
		// 4.学历
		if("研究生".equals(stock.getEdu())){
			form.setEdu(1);
		}else if("本科".equals(stock.getEdu())){
			form.setEdu(2);
		}else if("专科".equals(stock.getEdu())){
			form.setEdu(3);
		}else if("中专、技术学校或高中".equals(stock.getEdu())){
			form.setEdu(4);
		}else if("初中及以下".equals(stock.getEdu())){
			form.setEdu(5);
		}else if("未知".equals(stock.getEdu())){
			form.setEdu(-2);
		}
		
		//5.house：住房状况，int
		if("自有住房(债务已还清)".equals(stock.getHs())){
			form.setHouse(1);
		}else if("自有住房(债务未还清)".equals(stock.getHs())){
			form.setHouse(2);
		}else if("无房".equals(stock.getHs())){
			form.setHouse(3);
		}else if("未知".equals(stock.getHs())){
			form.setHouse(-2);
		}
		
		//6.unitType：单位性质，int，取值见下表
		if("国家机关、事业单位、社会团体".equals(stock.getUp())){
			form.setUnitType(1);
		}else if("央企".equals(stock.getUp())){
			form.setUnitType(2);
		}else if("国内外上市公司".equals(stock.getUp())){
			form.setUnitType(3);
		}else if("非上市大型企业".equals(stock.getUp())){
			form.setUnitType(4);
		}else if("非上市中小型企业".equals(stock.getUp())){
			form.setUnitType(5);
		}else if("微型企业".equals(stock.getUp())){
			form.setUnitType(6);
		}else if("自由职业者".equals(stock.getUp())){
			form.setUnitType(7);
		}else if("未知".equals(stock.getUp())){
			form.setUnitType(-2);
		}

		
		// 7.career：职业性质，int，取值见下表
		if("公务员、事业单位工作人员".equals(stock.getPn())){
			form.setCareer(1);
		}else if("教师、医师、护士、体育、文艺专业人员".equals(stock.getPn())){
			form.setCareer(2);
		}else if("专业技术人员".equals(stock.getPn())){
			form.setCareer(3);
		}else if("会计师、律师、评估师、税务师、建筑设计师".equals(stock.getPn())){
			form.setCareer(4);
		}else if("专业管理人员".equals(stock.getPn())){
			form.setCareer(5);
		}else if("工厂或民生行业一般工作人员或服务人员".equals(stock.getPn())){
			form.setCareer(6);
		}else if("小业主".equals(stock.getPn())){
			form.setCareer(7);
		}else if("农业从业人员".equals(stock.getPn())){
			form.setCareer(8);
		}else if("学生".equals(stock.getPn())){
			form.setCareer(9);
		}else if("其他".equals(stock.getPn())){
			form.setCareer(-1);
		}else if("未知".equals(stock.getPn())){
			form.setCareer(-2);
		}
		
		// 8.workYear：工作年数，int，当未知时设置为-2。
		if("未知".equals(stock.getWorklife())){
			form.setWorkYear(-2);
		}else{
			form.setWorkYear((int)Float.parseFloat(stock.getWorklife()));
		}
		
		//9 yearIncome：本人年收入（单位：元），float。当未知时，取值为-2。
		if("未知".equals(stock.getAnnualIncome())){
			form.setYearIncome(-2);
		}else{
			form.setYearIncome(Float.parseFloat(stock.getAnnualIncome()));
		}
		
		// 10 familyYearIncome：家庭年收入（单位：元），float。当未知时，取值为-2。
		if("未知".equals(stock.getHouseholdIncome())){
			form.setFamilyYearIncome(-2);
		}else{
			form.setFamilyYearIncome((int)Float.parseFloat(stock.getHouseholdIncome()));
		}
		
		// 11 familyNetAssets：家庭净资产（单位：元），float。当未知时，取值为-2。
		if("未知".equals(stock.getHouseholdNetAssets())){
			form.setFamilyNetAssets(-2);
		}else{
			form.setFamilyNetAssets((int)Float.parseFloat(stock.getHouseholdNetAssets()));
		}
		
		
		// 12 taxStatus：纳税情况，int
		if("按时足额纳税".equals(stock.getTaxSituation())){
			form.setTaxStatus(1);
		}else if("有应纳未纳税款".equals(stock.getTaxSituation())){
			form.setTaxStatus(2);
		}else if("无纳税".equals(stock.getTaxSituation())){
			form.setTaxStatus(3);
		}else if("未知".equals(stock.getTaxSituation())){
			form.setTaxStatus(-2);
		}
		
		//13.insured：投保情况，int。取值如下表：
		if("有商业保险及社会保险".equals(stock.getInsuranceSituation())){
			form.setInsured(1);
		}else if("仅有社会保险或农村合作医疗".equals(stock.getInsuranceSituation())){
			form.setInsured(2);
		}else if("仅有商业保险".equals(stock.getInsuranceSituation())){
			form.setInsured(3);
		}else if("无任何保险".equals(stock.getInsuranceSituation())){
			form.setInsured(4);
		}else if("未知".equals(stock.getInsuranceSituation())){
			form.setInsured(-2);
		}
		
		//14 credit：信用记录，int
		if("有银行贷款或信用卡逾期记录".equals(stock.getCreditRecord())){
			form.setCredit(1);
		}else if("有商业征信机构逾期记录".equals(stock.getCreditRecord())){
			form.setCredit(2);
		}else if("无逾期记录".equals(stock.getCreditRecord())){
			form.setCredit(3);
		}else if("未知".equals(stock.getCreditRecord())){
			form.setCredit(-2);
		}
		
		// 15 disputes：民事与行政纠纷，int
		if("有".equals(stock.getCaad())){
			form.setDisputes(1);
		}else if("无".equals(stock.getCaad())){
			form.setDisputes(2);
		}else if("未知".equals(stock.getCaad())){
			form.setDisputes(-2);
		}
		return form;
	}
	
	
//==========================================================================================================//
	// 读取存量客户信息模板数据插入存量客户信息表
	public  void insert() {
		InputStream is = null;

		try {
			File sourcefile = new File("C://Users//songchen//Desktop//新建文件夹//评估模型数据.xlsx");
			is = new FileInputStream(sourcefile);
			Workbook wb = WorkbookFactory.create(is);
			for (int j = 0; j < wb.getNumberOfSheets(); j++) {
				if (wb instanceof XSSFWorkbook) {
					CustomerStockForm visitregistledgerform = new CustomerStockForm();
					int i = 1;
					while (true) {
						Sheet st1 = wb.getSheetAt(j);
						Row row1 = st1.getRow(i);
						if (row1 == null) {
							break;
						}

						Cell cell0 = row1.getCell(0);
						String str0 = getCellValue(cell0);
						visitregistledgerform.setLyDesc(str0);// 来源

						Cell cell1 = row1.getCell(1);
						String str1 = getCellValue(cell1);
						visitregistledgerform.setCardId(str1);// 身份证号码

						Cell cell2 = row1.getCell(2);
						String str2 = getCellValue(cell2);
						visitregistledgerform.setAge(str2);// 年龄

						Cell cell3 = row1.getCell(3);
						String str3 = getCellValue(cell3);
						visitregistledgerform.setMarrige(str3);// 婚姻状况

						Cell cell4 = row1.getCell(4);
						String str4 = getCellValue(cell4);
						visitregistledgerform.setHr(str4);// 户籍及居住状况

						Cell cell5 = row1.getCell(5);
						String str5 = getCellValue(cell5);
						visitregistledgerform.setEdu(str5);// 学历

						Cell cell6 = row1.getCell(6);
						String str6 = getCellValue(cell6);
						visitregistledgerform.setHs(str6);// 住房状况

						Cell cell7 = row1.getCell(7);
						String str7 = getCellValue(cell7);
						visitregistledgerform.setUp(str7);// 单位性质

						Cell cell8 = row1.getCell(8);
						String str8 = getCellValue(cell8);
						visitregistledgerform.setPn(str8);// 职业性质

						Cell cell9 = row1.getCell(9);
						String str9 = getCellValue(cell9);
						visitregistledgerform.setWorklife(str9);// 工作年限

						Cell cell10 = row1.getCell(10);
						String str10 = getCellValue(cell10);
						visitregistledgerform.setAnnualIncome(str10);// 年收入

						Cell cell11 = row1.getCell(11);
						String str11 = getCellValue(cell11);
						visitregistledgerform.setHouseholdIncome(str11);// 家庭收入

						Cell cell12 = row1.getCell(12);
						String str12 = getCellValue(cell12);
						visitregistledgerform.setHouseholdNetAssets(str12);// 家庭净资产

						Cell cell13 = row1.getCell(13);
						String str13 = getCellValue(cell13);
						visitregistledgerform.setTaxSituation(str13);// 纳税情况

						Cell cell14 = row1.getCell(14);
						String str14 = getCellValue(cell14);
						visitregistledgerform.setInsuranceSituation(str14);// 投保情况

						Cell cell15 = row1.getCell(15);
						String str15 = getCellValue(cell15);
						visitregistledgerform.setCreditRecord(str15);// 信用记录

						Cell cell16 = row1.getCell(16);
						String str16 = getCellValue(cell16);
						visitregistledgerform.setCaad(str16);// 民事与行政纠纷

						//System.out.println(visitregistledgerform.toString());
						commonDao.insertObject(visitregistledgerform);
						i++;
					}
				} else if (wb instanceof HSSFWorkbook) {
					int i = 1;
					while (true) {
						CustomerStockForm visitregistledgerform = new CustomerStockForm();
						Sheet st1 = wb.getSheetAt(j);
						Row row1 = st1.getRow(i);
						if (row1 == null) {
							break;
						}

						Cell cell0 = row1.getCell(0);
						String str0 = getCellValue(cell0);
						visitregistledgerform.setLyDesc(str0);// 来源

						Cell cell1 = row1.getCell(1);
						String str1 = getCellValue(cell1);
						visitregistledgerform.setCardId(str1);// 身份证号码

						Cell cell2 = row1.getCell(2);
						String str2 = getCellValue(cell2);
						visitregistledgerform.setAge(str2);// 年龄

						Cell cell3 = row1.getCell(3);
						String str3 = getCellValue(cell3);
						visitregistledgerform.setMarrige(str3);// 婚姻状况

						Cell cell4 = row1.getCell(4);
						String str4 = getCellValue(cell4);
						visitregistledgerform.setHr(str4);// 户籍及居住状况

						Cell cell5 = row1.getCell(5);
						String str5 = getCellValue(cell5);
						visitregistledgerform.setEdu(str5);// 学历

						Cell cell6 = row1.getCell(6);
						String str6 = getCellValue(cell6);
						visitregistledgerform.setHs(str6);// 住房状况

						Cell cell7 = row1.getCell(7);
						String str7 = getCellValue(cell7);
						visitregistledgerform.setUp(str7);// 单位性质

						Cell cell8 = row1.getCell(8);
						String str8 = getCellValue(cell8);
						visitregistledgerform.setPn(str8);// 职业性质

						Cell cell9 = row1.getCell(9);
						String str9 = getCellValue(cell9);
						visitregistledgerform.setWorklife(str9);// 工作年限

						Cell cell10 = row1.getCell(10);
						String str10 = getCellValue(cell10);
						visitregistledgerform.setAnnualIncome(str10);// 年收入

						Cell cell11 = row1.getCell(11);
						String str11 = getCellValue(cell11);
						visitregistledgerform.setHouseholdIncome(str11);// 家庭收入

						Cell cell12 = row1.getCell(12);
						String str12 = getCellValue(cell12);
						visitregistledgerform.setHouseholdNetAssets(str12);// 家庭净资产

						Cell cell13 = row1.getCell(13);
						String str13 = getCellValue(cell13);
						visitregistledgerform.setTaxSituation(str13);// 纳税情况

						Cell cell14 = row1.getCell(14);
						String str14 = getCellValue(cell14);
						visitregistledgerform.setInsuranceSituation(str14);// 投保情况

						Cell cell15 = row1.getCell(15);
						String str15 = getCellValue(cell15);
						visitregistledgerform.setCreditRecord(str15);// 信用记录

						Cell cell16 = row1.getCell(16);
						String str16 = getCellValue(cell16);
						visitregistledgerform.setCaad(str16);// 民事与行政纠纷
						commonDao.insertObject(visitregistledgerform);
						//System.out.println(visitregistledgerform.toString());
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getCellValue(Cell cell) {
		String result = new String();
		if (cell == null || cell.equals("")
				|| cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			result = null;
		} else {
			try {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
				case Cell.CELL_TYPE_FORMULA:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat sdf = null;
						if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
								.getBuiltinFormat("h:mm")) {
							sdf = new SimpleDateFormat("HH:mm");
						} else {
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						Date date = cell.getDateCellValue();
						result = sdf.format(date);
					} else if (cell.getCellStyle().getDataFormat() == 14
							|| cell.getCellStyle().getDataFormat() == 20
							|| cell.getCellStyle().getDataFormat() == 31
							|| cell.getCellStyle().getDataFormat() == 32
							|| cell.getCellStyle().getDataFormat() == 57
							|| cell.getCellStyle().getDataFormat() == 58) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy/MM/dd");
						double value = cell.getNumericCellValue();
						Date date = org.apache.poi.ss.usermodel.DateUtil
								.getJavaDate(value);
						result = sdf.format(date);
					} else {
						double value = cell.getNumericCellValue();
						DecimalFormat format = new DecimalFormat();
						result = format.format(value);
					}
					break;

				case Cell.CELL_TYPE_STRING:
					result = cell.getStringCellValue().toString();
					break;
				case Cell.CELL_TYPE_BLANK:
					result = null;
					break;
				default:
					result = null;
					break;
				}
			} catch (Exception e) {
				System.out.println("第" + (cell.getRowIndex() + 1) + "行"
						+ (cell.getColumnIndex() + 1) + "列");
			}
		}
		return getNewString(result);
	}

	public static String getNewString(String str) {
		if (str != null) {
			if (str.indexOf(",") != -1) {
				str = str.replace(",", "");
			}
		}
		return str;
	}
	
//==========================================================================================================//
			
   /**
    *   获取好信度
    * @param personId
    * @param name
    * @param apiKey
    * @param mobileNum
    * @param cardId
    * @return
    */
   public Map<String, String> getGoodReliability(String personId,String name,String apiKey,String mobileNum,String cardId){
	   		Map<String, String> map = new HashMap<String, String>();
	   		
	   		// 正常报文
	   		String result =getHttpsResult(personId,name,apiKey,mobileNum,cardId);
	   		
	   		// 测试 报文
			//String result =   "{\"data\":{\"actionScore\":\"36\",\"bseInfoScore\":\"34\",\"credooScore\":\"564\",\"finRequireScore\":\"36\",\"payAbilityScore\":\"36\",\"performScore\":\"36\",\"trendScore\":\"36\",\"updateTime\":\"2017-08-11T16:59:01Z\",\"virAssetScore\":\"36\"},\"msg\":\"接口调用成功\",\"ret\":0}";
			//String result = "{\"msg\":\"无数据\",\"ret\":10009}";//失败报文
			org.json.JSONObject model;
			try {
				model = new org.json.JSONObject(result);
			// 0-查询成功
			if("0".equals(model.get("ret").toString())){
				map.put("ret", model.get("ret").toString());
				map.put("msg", model.get("msg").toString());
				//map.put("credooScore", model.get("credooScore").toString());
				map.put("credooScore", model.getJSONObject("data").get("credooScore").toString());
				
			}else{// 其他为异常
				map.put("ret", model.get("ret").toString());
				map.put("msg", model.get("msg").toString());
			}
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return map;
   }

   

   
   // get result
   public  String getHttpsResult(String personId,String name,String apiKey,String mobileNum,String cardId){
	   String result = "";
	   
	   String url = "https://www.kuaixin360.com/api/1.0/personal/credoo?personalId="+personId+"&name="+name+"&apiKey="+apiKey+"&mobileNum="+mobileNum+"&cardId="+cardId+"";
	   System.out.println(url);
	   
	   try {
		   // 创建URL对象
		   URL reqURL = new URL(url); 
		   HttpsURLConnection httpsConn = (HttpsURLConnection)reqURL.openConnection();

		   // 取得该连接的输入流，以读取响应内容 
		   InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());

		   // 读取服务器的响应内容并显示
		   int respInt = insr.read();
		   
		   while( respInt != -1){
			   result += (char)respInt;
			   respInt = insr.read();
		   }
		   System.out.println("返回报文:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	   return result;
   }
   
   
   
    // 保存提额申请记录
    public void saveApply(HttpServletRequest request){
    	TrialLoanApy apy = new TrialLoanApy();
    	apy.setId(request.getParameter("id"));
    	apy.setCustomerName(request.getParameter("customerName"));
    	apy.setCardId(request.getParameter("sfzh"));
    	apy.setPhoneNo(request.getParameter("phoneNo"));
    	apy.setSex(request.getParameter("sex"));
    	apy.setAge(request.getParameter("age"));
    	apy.setCardNum(request.getParameter("cardNum"));
    	apy.setApplyAmt(request.getParameter("applyAmt"));
    	apy.setLoanTerm(request.getParameter("loanTerm"));
    	apy.setApplyTime(request.getParameter("applyTime"));
    	trialLoanApplyDao.insertCustomerLoanApply(apy);
    }
    
    
    /**
     * 通过快信接口获取 评估额度
     */
    
    public Result getCreditAmtChannelOfWeChat(String sfzh,String customerName,String phoneNo,String cardNum){

		// 评估金额
		int creditAmt = 0;

		// 描述
		String msg = "";

		// 1.根据id 查询该客户是否是存量客户数据
		List<CustomerStockForm> list = trialLoanApplyDao.selectCustomerStock(sfzh);

		if (list != null && list.size() > 0) {
			// 存量客户数据
			CustomerStockForm stock = list.get(0);
			StockForm form = returnVo(stock);
			creditAmt = StockCredit.calculate(form.getAge(),
					form.getMarriage(), form.getHouseHold(), form.getEdu(),
					form.getHouse(), form.getUnitType(), form.getCareer(),
					form.getWorkYear(), form.getYearIncome(),
					form.getFamilyYearIncome(), form.getFamilyNetAssets(),
					form.getTaxStatus(), form.getInsured(), form.getCredit(),
					form.getDisputes());
		} else {
			// 非存量客户
			int kuhxd = 0;

			// 获取客户好信度
			Map<String, String> map = getGoodReliability(sfzh,
														 customerName,
														 "25e7d8ad2462481fb2ce11ac3dc069f5",
														 phoneNo,
														 cardNum);

			// 查询成功
			if ("0".equals(map.get("ret").toString())) {
				kuhxd = Integer.parseInt(map.get("credooScore").toString());// 好信度
				msg = "";
			} else {
				msg = map.get("msg").toString();
			}

			// 根据客户好信度 进行额度评估
			creditAmt = Unstock.calculate(kuhxd);
		}

		// set Result
		Result result = new Result();
		result.setCreditAmt(creditAmt);
		result.setMsg(msg);
		return result;
	}
    
}
