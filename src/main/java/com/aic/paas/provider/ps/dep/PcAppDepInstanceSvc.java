package com.aic.paas.provider.ps.dep;

import com.aic.paas.provider.ps.bean.PcAppDepInstance;

public interface PcAppDepInstanceSvc {
	
	/**
	 * 保存获更新，判断主键ID[id]是否存在, 存在则更新, 不存在则插入
	 * @param record : PcAppDepInstance数据记录
	 * @return 当前记录主键[id]值
	 */
	public Long saveOrUpdate(PcAppDepInstance record);
	
	
	
	
	/**
	 * 添加应用实例, 所属容器(快照中)ID根据应用实例全名获取
	 * @param record 实例对象
	 * @return 新添加记录ID
	 */
	public Long addDepInstanceByAppImgFullName(String appImgFullName, PcAppDepInstance record);
	
	
	
	
	/**
	 * 跟据实例名设置应用实例无效
	 * @param instanceName 实例名
	 * @return 删除的记录数
	 */
	public Integer disableDepInstanceByInstanceName(String instanceName);
	
	
	
	
	
	
}
