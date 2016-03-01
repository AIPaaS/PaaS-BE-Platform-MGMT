package com.aic.paas.provider.ps.db;


import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.PsMntRes;
import com.binary.framework.dao.Dao;


/**
 * 租户资源表[PS_MNT_RES]数据访问对象
 */
public interface PsMntResDao extends Dao<PsMntRes, CPsMntRes> {

	
	
	/**
	 * 增加用户总资源
	 * @param id
	 * @param cpuCount
	 * @param memSize
	 * @param diskSize
	 * @return 返回更新记录数
	 */
	public int increTotalResById(Long id, Long cpuCount, Long memSize, Long diskSize);
	
	
	
	
	/**
	 * 增加用户资源
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
	public PsMntRes releaseRes(Long id);
	
	
	
	
}


