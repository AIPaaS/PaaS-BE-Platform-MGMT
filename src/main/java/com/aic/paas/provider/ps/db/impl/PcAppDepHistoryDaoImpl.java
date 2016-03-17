package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;
import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;


/**
 * 应用布署历史表[PC_APP_DEP_HISTORY]数据访问对象实现
 */
public class PcAppDepHistoryDaoImpl extends IBatisDaoTemplate<PcAppDepHistory, CPcAppDepHistory> implements PcAppDepHistoryDao {

	
	
	@SuppressWarnings("unchecked")
	@Override
	public PcAppDepHistory selectMaxByAppImageFullName(String appImageFullName) {
		BinaryUtils.checkEmpty(appImageFullName, "appImageFullName");
		appImageFullName = appImageFullName.trim();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("containerFullName", appImageFullName);
		List<PcAppDepHistory> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectMaxByAppImageFullName", map);
		if(list.size() == 0) return null;
		return list.get(0);
	}

	
	
	

}


