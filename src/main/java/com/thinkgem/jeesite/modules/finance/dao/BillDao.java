/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.finance.entity.Bill;

/**
 * 收费管理DAO接口
 * @author HEY-Chain
 * @version 2018-03-31
 */
@MyBatisDao
public interface BillDao extends CrudDao<Bill> {
	
}