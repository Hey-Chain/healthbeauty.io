/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 就诊管理Entity
 * @author HEY-Chain
 * @version 2018-03-27
 */
public class Attendance extends DataEntity<Attendance> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户
	private String customerName;	// 客户名称
	private Date attendanceTime;		// 就诊时间
	private String attendanceType;		// 就诊类型
	private String reservationId;		// 预约编号
	private String reservationNumber;		// 预约编号

	public Attendance() {
		super();
	}

	public Attendance(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户长度必须介于 1 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="就诊时间不能为空")
	public Date getAttendanceTime() {
		return attendanceTime;
	}

	public void setAttendanceTime(Date attendanceTime) {
		this.attendanceTime = attendanceTime;
	}
	
	@Length(min=1, max=1, message="就诊类型长度必须介于 1 和 1 之间")
	public String getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}
	
	@Length(min=0, max=64, message="预约编号长度必须介于 0 和 64 之间")
	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
}