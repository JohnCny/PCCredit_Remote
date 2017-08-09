package com.cardpay.pccredit.kd.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
  
	//提额申请状态0-待调查 1-补充调查 2-快审中 3-通过4-拒绝
	public static String LOAN_STATE_O = "0";
	public static String LOAN_STATE_1 = "1";
	public static String LOAN_STATE_2 = "2";
	public static String LOAN_STATE_3 = "3";
	public static String LOAN_STATE_4 = "4";
	
	
	public static Map<String,String> ATT_NUM = new HashMap<String,String>(){{
			put("ownHouses","自有房产数量");
			put("mortgageHouses","按揭房产数量");
			put("ownVehicles","自有车辆数量");
			put("creditOverdueTimes","信用逾期次数");
			put("loanOverdueTimes","贷款逾期次数");
			put("numOfEcoDepend","经济上依赖的人数");
			put("serviceLife","业务年限");
	}};
	
	public static Map<String,String> ATT_DOUBLE = new HashMap<String,String>(){{
		put("mortgageBalamt","按揭贷款余额");
		put("loanBalamt","贷款余额");
		put("guaranteed","担保余额");
		put("annualIncomeSpouse","配偶年收入");
		put("mainBusinessIncome","主营业务收入");
		
		put("currentAssets","流动资产");
		put("fixedAssets","固定资产");
		put("shortTermLiab","短期负债");
		put("ownedEqu","所有者权益");
		put("annualDisIncome","年可支配收入");
		
		put("otherImcome","其他工作收入");
		put("stock","存货");
		put("totalAssets","资产总计");
		put("totalLiab","负债总计");
		put("payPrivateUse","私人用途分期付款");
	}};
		
}
