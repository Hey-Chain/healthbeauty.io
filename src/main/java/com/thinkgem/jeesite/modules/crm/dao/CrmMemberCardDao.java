/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;

/**
 * 会员卡管理DAO接口
 * @author HEY-Chain
 * @version 2018-05-13
 */
@MyBatisDao
public interface CrmMemberCardDao extends CrudDao<CrmMemberCard> {
}