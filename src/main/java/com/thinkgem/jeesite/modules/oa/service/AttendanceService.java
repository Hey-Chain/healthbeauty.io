/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.Attendance;
import com.thinkgem.jeesite.modules.oa.entity.Reservation;
import com.thinkgem.jeesite.modules.oa.dao.AttendanceDao;

/**
 * 就诊管理Service
 * @author HEY-Chain
 * @version 2018-03-27
 */
@Service
@Transactional(readOnly = true)
public class AttendanceService extends CrudService<AttendanceDao, Attendance> {

	@Autowired
	private ReservationService reservationService;
	
	public Attendance get(String id) {
		return super.get(id);
	}
	
	public List<Attendance> findList(Attendance attendance) {
		return super.findList(attendance);
	}
	
	public Page<Attendance> findPage(Page<Attendance> page, Attendance attendance) {
		return super.findPage(page, attendance);
	}
	
	@Transactional(readOnly = false)
	public void save(Attendance attendance) {

		if(attendance.getIsNewRecord() && StringUtils.isNotBlank(attendance.getReservationId())) {
			Reservation reservation = reservationService.get(attendance.getReservationId());
			reservation.setStatus("1");
			reservationService.save(reservation);
		}
		
		super.save(attendance);
	}
	
	@Transactional(readOnly = false)
	public void delete(Attendance attendance) {
		super.delete(attendance);
	}
	
}