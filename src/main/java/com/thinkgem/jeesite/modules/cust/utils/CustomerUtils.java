/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cust.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.cust.dao.CrmCustomerDao;
import com.thinkgem.jeesite.modules.cust.entity.CrmCustomer;

/**
 * 就诊管理Service
 * @author HEY-Chain
 * @version 2018-03-27
 */
public class CustomerUtils {

	private static CrmCustomerDao customerDao = SpringContextHolder.getBean(CrmCustomerDao.class);

	public static final String CUSTOMER_CACHE = "customerCache";
	public static final String CUSTOMER_CACHE_ID_ = "custid_";
	public static final String CUSTOMER_CACHE_LIST_BY_GROUP_KEY_ = "groupkey_";
	
	/**
	 * 根据ID获取客户
	 * @param id
	 * @return 取不到返回null
	 */
	public static CrmCustomer get(String id){
		CrmCustomer customer = (CrmCustomer)CacheUtils.get(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + id);
		if (customer ==  null){
			customer = customerDao.get(id);
			if (customer == null){
				return null;
			}
			
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + customer.getId(), customer);
		}
		return customer;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param customerGroupId
	 * @return 取不到返回null
	 */
	public static List<CrmCustomer> getByCustomerGroupKey(String customerGroupKey){
		@SuppressWarnings("unchecked")
		List<CrmCustomer> customerList = (List<CrmCustomer>)getCache(CUSTOMER_CACHE_LIST_BY_GROUP_KEY_ + customerGroupKey);
		if (customerList == null){
			CrmCustomer customer = new CrmCustomer();
			customer.setCustomerGroup(customerGroupKey);
			customer.setDelFlag("0");
			customerList = customerDao.findList(customer);
			
			if (customerList == null){
				return null;
			}
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_LIST_BY_GROUP_KEY_ + customerGroupKey, customerList);
		}
		return customerList;
	}
	
	/**
	 * 清除当前客户缓存
	 */
	public static void clearCache(){
		removeCache(CUSTOMER_CACHE);
	}
	
	/**
	 * 清除指定客户缓存
	 * @param customer
	 */
	public static void clearCache(CrmCustomer customer){
		CacheUtils.remove(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + customer.getId());
		if (customer != null && customer.getCustomerGroup() != null){
			CacheUtils.remove(CUSTOMER_CACHE, CUSTOMER_CACHE_LIST_BY_GROUP_KEY_ + customer.getCustomerGroup());
		}
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}

		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== Customer Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		getSession().removeAttribute(key);
	}
	
}