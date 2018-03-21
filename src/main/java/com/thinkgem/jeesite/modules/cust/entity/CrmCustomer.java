/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cust.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户管理Entity
 * @author HEY-Chain
 * @version 2018-03-21
 */
public class CrmCustomer extends DataEntity<CrmCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String customerName;		// 姓名
	private String phone;		// 手机号码
	private String sourceType;		// 来源
	private String customerGroup;		// 客户组
	private String sex;		// 性别
	private String age;		// 年龄
	private String address;		// 联系地址
	private String wechat;		// 微信号
	private Date birthday;		// 出生日期
	private String membercard;		// 会员卡号
	private String introducer;		// 介绍人
	private String email;		// 邮箱
	private String qq;		// qq号码
	private String job;		// 职业
	private String idcard;		// 证件号码
	private String customerNo;		// 档案号码
	
	public CrmCustomer() {
		super();
	}

	public CrmCustomer(String id){
		super(id);
	}

	@Length(min=1, max=32, message="姓名长度必须介于 1 和 32 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=32, message="手机号码长度必须介于 0 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=1, message="来源长度必须介于 0 和 1 之间")
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	@Length(min=1, max=1, message="客户组长度必须介于 1 和 1 之间")
	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=11, message="年龄长度必须介于 0 和 11 之间")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@Length(min=0, max=255, message="联系地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=32, message="微信号长度必须介于 0 和 32 之间")
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=32, message="会员卡号长度必须介于 0 和 32 之间")
	public String getMembercard() {
		return membercard;
	}

	public void setMembercard(String membercard) {
		this.membercard = membercard;
	}
	
	@Length(min=0, max=32, message="介绍人长度必须介于 0 和 32 之间")
	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}
	
	@Length(min=0, max=50, message="邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=16, message="qq号码长度必须介于 0 和 16 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=16, message="职业长度必须介于 0 和 16 之间")
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Length(min=0, max=16, message="证件号码长度必须介于 0 和 16 之间")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Length(min=0, max=16, message="档案号码长度必须介于 0 和 16 之间")
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
}