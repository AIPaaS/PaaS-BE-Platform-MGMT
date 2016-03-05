package com.aic.paas.provider.ps.dep;

import com.aic.paas.provider.ps.bean.PcAppTask;

public interface PcAppTaskSvc {
	
	/**
	 * 保存获更新，判断主键ID[id]是否存在, 存在则更新, 不存在则插入
	 * @param record : PcAppTask数据记录
	 * @return 当前记录主键[id]值
	 */
	public Long saveOrUpdate(PcAppTask record);
}
