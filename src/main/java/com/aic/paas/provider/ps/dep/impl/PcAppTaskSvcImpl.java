package com.aic.paas.provider.ps.dep.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppTask;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.db.PcAppTaskDao;
import com.aic.paas.provider.ps.dep.PcAppTaskSvc;
import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.Page;

public class PcAppTaskSvcImpl implements PcAppTaskSvc {

	@Autowired
	PcAppTaskDao pcAppTaskDao;
	@Autowired
	PcAppDao pcAppDao;
	@Autowired
	PcAppDepHistoryDao pcAppDepHistoryDao;

	@Override
	public void save(PcAppTask record) {

		BinaryUtils.checkEmpty(record, "record");
		BinaryUtils.checkEmpty(record.getAppId(), "record.appId");

		pcAppTaskDao.insert(record);
	}

	@Override
	public PcAppTask queryById(Long id) {
		return pcAppTaskDao.selectById(id);
	}

	@Override
	public Page<PcAppTask> queryPage(Integer pageNum, Integer pageSize, CPcAppTask cdt, String orders) {
		return null;
	}

}
