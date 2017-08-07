package com.cardpay.pccredit.kd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.kd.model.TypadKdCustomer;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface TypadKdCustomerDao {
List<TypadKdCustomer>selectSqCustomer();
List<TypadKdCustomer> selectImageType(@Param(value = "id") String id);
void inserIma (TypadKdCustomer t);
TypadKdCustomer selectImaById(@Param(value = "id") String id);
TypadKdCustomer selectHistory(@Param(value = "id") String id);
TypadKdCustomer selectSqCustomerBcl(@Param(value = "id") String id);
void deleteIma(@Param(value = "id") String id);
List<TypadKdCustomer>selectImageByType(@Param(value = "id") String id,@Param(value = "REMARKS") String REMARKS);
}
