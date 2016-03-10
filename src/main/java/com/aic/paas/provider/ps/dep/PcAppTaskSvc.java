package com.aic.paas.provider.ps.dep;

import com.aic.paas.provider.ps.bean.CPcAppTask;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.binary.jdbc.Page;

public interface PcAppTaskSvc {
	
	/**
	 * 保存获更新，判断主键ID[id]是否存在, 存在则更新, 不存在则插入
	 * @param record : PcAppTask数据记录
	 */
	public void save(PcAppTask record);
	
	public PcAppTask queryById(Long id);
	
	public Page<PcAppTask> queryPage(Integer pageNum, Integer pageSize, CPcAppTask cdt, String orders);
}
