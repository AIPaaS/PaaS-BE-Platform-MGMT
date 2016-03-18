package com.aic.paas.provider.ps.dep;

import java.util.List;

import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;

public interface PcAppDepHistorySvc {
	/**
	 * 保存获更新，判断主键ID[id]是否存在, 存在则更新, 不存在则插入
	 * @param record : PcAppDepHistory数据记录
	 * @return 当前记录主键[id]值
	 */
	public Long saveOrUpdate(PcAppDepHistory record);
	
	/**
	 * 根据任务id获取app部署历史信息
	 * @param taskId : Long任务ID
	 * @return 任务[id]值
	 */
	public List<PcAppDepHistory> queryByTaskId(Long taskId);
	/**
	 * 根据CDT更新部署历史信息
	 * @param pcAppDepHistory
	 * @param cdt
	 */
	public long update(PcAppDepHistory pcAppDepHistory, CPcAppDepHistory cdt);
}
