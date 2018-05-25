/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.crm.entity.CrmBalanceInOut;

/**
 * 金额增加流水DAO接口
 * @author HEY-Chain
 * @version 2018-05-22
 */
@MyBatisDao
public interface CrmBalanceInOutDao extends CrudDao<CrmBalanceInOut> {
	public int deleteByMemberCard(CrmBalanceInOut memberCard);
}