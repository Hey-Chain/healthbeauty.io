/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.utils;

import java.util.List;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;
import com.thinkgem.jeesite.modules.crm.service.CrmMemberCardService;

/**
 * 内容管理工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CrmUtils {
	
	private static CrmMemberCardService memberCardService = SpringContextHolder.getBean(CrmMemberCardService.class);

	private static final String CRM_CACHE = "crmCache";
	
	/**
	 * 获得会员卡列表
	 * 空卡
	 */
	public static List<CrmMemberCard> getCrmCardList(String useFlag){
		@SuppressWarnings("unchecked")
		List<CrmMemberCard> bizList = (List<CrmMemberCard>)CacheUtils.get(CRM_CACHE, "crmCardList" + useFlag);
		if (bizList == null){
			CrmMemberCard biz = new CrmMemberCard();
			if(useFlag != "") {
				biz.setUseFlag(useFlag);	
			}
			Page<CrmMemberCard> page = new Page<CrmMemberCard>(1, -1);
			page = memberCardService.findPage(page, biz);
			bizList = page.getList();
			CacheUtils.put(CRM_CACHE, "crmCardList" + useFlag, bizList);
		}
		return bizList;
	}
	
	// ============== Cms Cache ==============
	
	public static Object getCache(String key) {
		return CacheUtils.get(CRM_CACHE, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CRM_CACHE, key, value);
	}

	public static void removeCache(String key) {
		CacheUtils.remove(CRM_CACHE, key);
	}
}