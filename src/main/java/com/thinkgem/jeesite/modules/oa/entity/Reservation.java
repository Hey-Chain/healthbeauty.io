/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 预约管理Entity
 * @author HEY-Chain
 * @version 2018-03-24
 */
public class Reservation extends DataEntity<Reservation> {
	
	private static final long serialVersionUID = 1L;
	private String customerid;		// 客户
	private String customerName;	// 客户名称
	private String doctorid;		// 医生
	private String doctorName;	// 客户名称
	private Date reservationTime;		// 预约时间
	private String projectid;		// 预约项目
	private String projectName;	// 客户名称
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Reservation() {
		super();
	}

	public Reservation(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户长度必须介于 1 和 64 之间")
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	@Length(min=0, max=64, message="医生长度必须介于 0 和 64 之间")
	public String getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}
	
	@Length(min=1, max=64, message="预约项目长度必须介于 1 和 64 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
}