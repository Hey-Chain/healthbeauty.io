/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 收费管理Entity
 * @author HEY-Chain
 * @version 2018-03-31
 */
public class Billitem extends DataEntity<Billitem> {
	
	private static final long serialVersionUID = 1L;
	private Bill bill;		// 收费单据 父类
	private String projectId;		// 项目
	private String projectName;		// 项目名称
	private String doctorId;		// 医生
	private String doctorName;		// 医生名称
	private String quantity;		// 数量
	private String unit;		//单位
	private String originalprice;	// 原价
	private String dealprice;		// 成交价
	
	public Billitem() {
		super();
	}

	public Billitem(String id){
		super(id);
	}

	public Billitem(Bill bill){
		this.bill = bill;
	}

	@Length(min=1, max=64, message="收费单据长度必须介于 1 和 64 之间")
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	@Length(min=1, max=64, message="项目长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="医生长度必须介于 1 和 64 之间")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	
	@Length(min=1, max=64, message="单位长度必须介于 1 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=1, max=64, message="数量长度必须介于 1 和 64 之间")
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Length(min=1, max=64, message="原价长度必须介于 1 和 64 之间")
	public String getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(String originalprice) {
		this.originalprice = originalprice;
	}
	
	@Length(min=1, max=64, message="成交价长度必须介于 1 和 64 之间")
	public String getDealprice() {
		return dealprice;
	}

	public void setDealprice(String dealprice) {
		this.dealprice = dealprice;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
}