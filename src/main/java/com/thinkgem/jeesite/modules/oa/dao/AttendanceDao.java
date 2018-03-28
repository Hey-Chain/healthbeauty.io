/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.Attendance;

/**
 * 就诊管理DAO接口
 * @author HEY-Chain
 * @version 2018-03-27
 */
@MyBatisDao
public interface AttendanceDao extends CrudDao<Attendance> {
	
}