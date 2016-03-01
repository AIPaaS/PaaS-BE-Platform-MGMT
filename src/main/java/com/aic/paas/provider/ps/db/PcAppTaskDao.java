package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcAppTask;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.binary.framework.dao.Dao;


/**
 * 应用布署任务表[PC_APP_TASK]数据访问对象
 */
public interface PcAppTaskDao extends Dao<PcAppTask, CPcAppTask> {


	
	
	/**
	 * 查询应用最近一次运行情况
	 * @param appIds
	 * @return
	 */
	public List<PcAppTask> selectLastList(Long[] appIds);
	
	
	
}


