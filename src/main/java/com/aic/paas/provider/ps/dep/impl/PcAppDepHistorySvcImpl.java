package com.aic.paas.provider.ps.dep.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.dep.PcAppDepHistorySvc;



public class PcAppDepHistorySvcImpl implements PcAppDepHistorySvc {

	@Autowired
	PcAppDepHistoryDao pcAppDepHistoryDao;

	@Override
	public Long saveOrUpdate(PcAppDepHistory record) {
		return pcAppDepHistoryDao.save(record);
	}

	@Override
	public List<PcAppDepHistory> queryByTaskId(Long taskId) {
		CPcAppDepHistory cPcAppDepHistory = new CPcAppDepHistory();
		cPcAppDepHistory.setTaskId(taskId);
		return pcAppDepHistoryDao.selectList(cPcAppDepHistory, null);
	}
	
	@Override
	public long update(PcAppDepHistory pcAppDepHistory, CPcAppDepHistory cdt) {
		return pcAppDepHistoryDao.updateByCdt(pcAppDepHistory,cdt);
	}
}
