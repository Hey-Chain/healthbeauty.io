/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 金额增加流水Entity
 * 因为客户没有会员卡，所以要同时记入客户Id
 * @author HEY-Chain
 * @version 2018-05-22
 */
public class CrmBalanceInOut extends DataEntity<CrmBalanceInOut> {
	
	private static final long serialVersionUID = 1L;
	private String memberCardId;		// 会员卡Id
	private Integer operateType;			// 操作类型
	private Double changeAmount;		// 增加金额
	
	private String customerId;			// 客户Id
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 0:减少,1:增加
	 * @return 操作类型
	 */
	public Integer getOperateType() {
		return operateType;
	}
	
	/**
	 * 0:减少,1:增加
	 * @param 操作类型
	 */
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	
	public CrmBalanceInOut() {
		super();
	}

	public CrmBalanceInOut(String id){
		super(id);
	}

	@Length(min=1, max=64, message="会员卡Id长度必须介于 1 和 64 之间")
	public String getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(String memberCardId) {
		this.memberCardId = memberCardId;
	}
	
	@NotNull(message="增加金额不能为空")
	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}
	
}