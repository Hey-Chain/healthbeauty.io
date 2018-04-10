/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 收费管理Entity
 * @author HEY-Chain
 * @version 2018-03-31
 */
public class Bill extends DataEntity<Bill> {
	
	private static final long serialVersionUID = 1L;
	private String billNumber;	//收费单编码
	private String customerId;		// 客户
	private String customerName;	// 客户名称
	private String memberCard;	//会员卡号
	private Date billTime;		// 开单时间
	private String attendanceId;		// 就诊单
	private String isPaid;		// 是否付款
	private List<Billitem> billitemList = Lists.newArrayList();		// 子表列表
	
	public Bill() {
		super();
	}

	public Bill(String id){
		super(id);
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	@Length(min=1, max=64, message="客户长度必须介于 1 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开单时间不能为空")
	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	
	@Length(min=0, max=64, message="就诊单长度必须介于 0 和 64 之间")
	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}
	
	@Length(min=1, max=1, message="是否付款长度必须介于 1 和 1 之间")
	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}
	
	public List<Billitem> getBillitemList() {
		return billitemList;
	}

	public void setBillitemList(List<Billitem> billitemList) {
		this.billitemList = billitemList;
	}
}