package com.aic.paas.provider.ps.db;


import com.binary.framework.dao.Dao;
import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;


/**
 * 应用布署历史表[PC_APP_DEP_HISTORY]数据访问对象
 */
public interface PcAppDepHistoryDao extends Dao<PcAppDepHistory, CPcAppDepHistory> {

	
	
	
	/**
	 * 跟据容器全名查询最新布署容器
	 * @param appImageFullName
	 * @return
	 */
	public PcAppDepHistory selectMaxByAppImageFullName(String appImageFullName);
	
	
	

}


