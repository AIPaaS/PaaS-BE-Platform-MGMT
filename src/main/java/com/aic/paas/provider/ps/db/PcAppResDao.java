package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.PcAppRes;
import com.binary.framework.dao.Dao;


/**
 * 应用资源表[PC_APP_RES]数据访问对象
 */
public interface PcAppResDao extends Dao<PcAppRes, CPcAppRes> {

	
	
	/**
	 * 查询按应用ID分组数据
	 * @param appIds
	 * @return 返回字段如下：
	 * 		APP_ID 应用ID
	 * 		CPU_COUNT 应用占CPU总数
	 * 		MEM_SIZE 应用占内存总数
	 * 		DISK_SIZE 应用占硬盘总数
	 */
	public List<PcAppRes> selectAppGroupList(Long[] appIds);
	
	
	
	
	/**
	 * 增加应用资源
	 * @param id
	 * @param cpuCount
	 * @param memSize
	 * @param diskSize
	 * @param validity 是否需要验证扣减有效性, 缺省为false
	 * @return 返回更新记录数
	 */
	public int increResById(Long id, Long cpuCount, Long memSize, Long diskSize, Boolean validity);
	
	
		
	
	/**
	 * 释放资源(删除当前资源)
	 * @param id
	 * @return 返回删除的资源对象
	 */
	public PcAppRes releaseRes(Long id);
	
	

	
	
	
}


