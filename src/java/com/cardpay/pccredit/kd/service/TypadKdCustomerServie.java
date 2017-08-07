package com.cardpay.pccredit.kd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.kd.dao.TypadKdCustomerDao;
import com.cardpay.pccredit.kd.model.TypadKdCustomer;

@Service
public class TypadKdCustomerServie {
	@Autowired
	private TypadKdCustomerDao commonDao;
	public List<TypadKdCustomer>selectSqCustomer(){
		return commonDao.selectSqCustomer();
		
	}
	public List<TypadKdCustomer> selectImageType( String id){
		
		return commonDao.selectImageType(id);
	}
}
