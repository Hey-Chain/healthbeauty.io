/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员卡管理Entity
 * @author HEY-Chain
 * @version 2018-05-13
 */
public class CrmMemberCard extends DataEntity<CrmMemberCard> {
	
	private static final long serialVersionUID = 1L;
	private String cardNumber;		// 卡号
	private String useFlag;		// 使用标记
	
	public CrmMemberCard() {
		super();
	}

	public CrmMemberCard(String id){
		super(id);
	}

	@Length(min=1, max=32, message="卡号长度必须介于 1 和 32 之间")
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Length(min=1, max=1, message="使用标记长度必须介于 1 和 1 之间")
	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	
}