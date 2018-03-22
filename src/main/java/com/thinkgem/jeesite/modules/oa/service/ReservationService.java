/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.Reservation;
import com.thinkgem.jeesite.modules.oa.dao.ReservationDao;

/**
 * 预约管理Service
 * @author HEY-Chain
 * @version 2018-03-23
 */
@Service
@Transactional(readOnly = true)
public class ReservationService extends CrudService<ReservationDao, Reservation> {

	public Reservation get(String id) {
		return super.get(id);
	}
	
	public List<Reservation> findList(Reservation reservation) {
		return super.findList(reservation);
	}
	
	public Page<Reservation> findPage(Page<Reservation> page, Reservation reservation) {
		return super.findPage(page, reservation);
	}
	
	@Transactional(readOnly = false)
	public void save(Reservation reservation) {
		super.save(reservation);
	}
	
	@Transactional(readOnly = false)
	public void delete(Reservation reservation) {
		super.delete(reservation);
	}
	
}