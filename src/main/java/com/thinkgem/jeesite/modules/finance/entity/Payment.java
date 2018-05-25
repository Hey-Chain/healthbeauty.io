/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 付款管理Entity
 * @author HEY-Chain
 * @version 2018-04-02
 */
public class Payment extends DataEntity<Payment> {
	
	private static final long serialVersionUID = 1L;
	private String billId;		// 收费单
	private String billNumber;	//收费单编码
	private String customerName;//客户名改
	private String memberCardId;	//会员卡Id
	private String memberCard;	//会员卡号
	private Date paymentTime;		// 付款时间
	private String paymentNumber;	//付款编码
	private String paymentType;		// 付款方式
	private Double payable;		// 应付金额
	private Double amount;		// 付款金额
	
	public Payment() {
		super();
	}

	public Payment(String id){
		super(id);
	}

	@Length(min=1, max=64, message="收费单长度必须介于 1 和 64 之间")
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(String memberCardId) {
		this.memberCardId = memberCardId;
	}
	
	public String getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	@Length(min=0, max=64, message="付款方式长度必须介于 0 和 64 之间")
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public Double getPayable() {
		return payable;
	}

	public void setPayable(Double payable) {
		this.payable = payable;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}