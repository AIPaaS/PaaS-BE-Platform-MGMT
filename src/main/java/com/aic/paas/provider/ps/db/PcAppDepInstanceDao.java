package com.aic.paas.provider.ps.db;


import com.binary.framework.dao.Dao;
import com.aic.paas.provider.ps.bean.CPcAppDepInstance;
import com.aic.paas.provider.ps.bean.PcAppDepInstance;


/**
 * 应用布署实例表[PC_APP_DEP_INSTANCE]数据访问对象
 */
public interface PcAppDepInstanceDao extends Dao<PcAppDepInstance, CPcAppDepInstance> {

	
	
	
	
	/**
	 * 跟据实例名称删除实例
	 * @param instanceName 实例名称
	 * @return 记录条数
	 */
	public int disableDepInstanceByInstanceName(String instanceName);

	
	
	
	
}


