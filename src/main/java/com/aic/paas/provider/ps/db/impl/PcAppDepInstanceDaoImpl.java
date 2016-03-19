package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppDepInstance;
import com.aic.paas.provider.ps.bean.PcAppDepInstance;
import com.aic.paas.provider.ps.db.PcAppDepInstanceDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;
import com.binary.framework.ibatis.IBatisUtils;


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

	
	
	
	
	@SuppressWarnings("unchecked")
	private List<PcAppDepInstance> selectByInstanceName(String instanceName, String orders) {
		BinaryUtils.checkEmpty(instanceName, "instanceName");
		instanceName = instanceName.trim();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instanceName", instanceName);
		map.put("orders", orders);
		
		List<PcAppDepInstance> ls = getSqlMapClientTemplate().queryForList(getTableName()+".selectByInstanceName", map);
		return ls;
	}
	
	
	
	/**
	 * 实insert支持幂等
	 */
	@Override
	public long insert(PcAppDepInstance record) {
		IBatisUtils.validateEntityEmpty(record);
		BinaryUtils.checkEmpty(record.getInstanceName(), "record.instanceName");
		
		String instanceName = record.getInstanceName().trim();
		List<PcAppDepInstance> ls = selectByInstanceName(instanceName, "ID");
		if(ls.size() > 0) {
			return ls.get(0).getId();
		}
		
		super.insert(record);
		Local.commit();
		
		ls = selectByInstanceName(instanceName, "ID");
		if(ls.size() > 1) {
			Long[] ids = new Long[ls.size()-1];
			for(int i=1; i<ls.size(); i++) {
				ids[i] = ls.get(i).getId();
			}
			
			CPcAppDepInstance cdt = new CPcAppDepInstance();
			cdt.setIds(ids);
			deleteByCdt(cdt);
			Local.commit();
		}
		return ls.get(0).getId();
	}

	
	
	
	
	

}


