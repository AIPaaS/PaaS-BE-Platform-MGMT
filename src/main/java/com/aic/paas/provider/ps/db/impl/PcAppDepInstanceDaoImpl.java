package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppDepInstance;
import com.aic.paas.provider.ps.bean.PcAppDepInstance;
import com.aic.paas.provider.ps.db.PcAppDepInstanceDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 应用布署实例表[PC_APP_DEP_INSTANCE]数据访问对象实现
 */
public class PcAppDepInstanceDaoImpl extends IBatisDaoTemplate<PcAppDepInstance, CPcAppDepInstance> implements PcAppDepInstanceDao {

	
	
	
	@Override
	public int disableDepInstanceByInstanceName(String instanceName) {
		BinaryUtils.checkEmpty(instanceName, "instanceName");
		
		long time = BinaryUtils.getNumberDateTime();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modifyTime", time);
		map.put("instanceName", instanceName);
		
		int count = getSqlMapClientTemplate().update(getTableName()+".disableDepInstanceByInstanceName", map);
		return count;
	}

	
	
	
	
	

}


