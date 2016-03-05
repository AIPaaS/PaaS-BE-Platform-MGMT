package com.aic.paas.provider.ps.dep.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.db.PcAppTaskDao;
import com.aic.paas.provider.ps.dep.PcAppTaskSvc;
import com.binary.core.util.BinaryUtils;

public class PcAppTaskSvcImpl implements PcAppTaskSvc {

	@Autowired
	PcAppTaskDao pcAppTaskDao;
	@Autowired
	PcAppDao pcAppDao;
	@Autowired
	PcAppDepHistoryDao pcAppDepHistoryDao;

	@Override
	public Long saveOrUpdate(PcAppTask record) {

		BinaryUtils.checkEmpty(record, "record");
		BinaryUtils.checkEmpty(record.getAppId(), "record.appId");

		boolean isadd = record.getId() == null;

		Long id = record.getId();
		if (isadd) {
			id = pcAppTaskDao.insert(record);

			PcApp pcApp = pcAppDao.selectById(record.getAppId());
			PcAppDepHistory pcAppDepHistory = new PcAppDepHistory();
			//TODO: copy PcApp to PcAppDepHistory
			pcAppDepHistoryDao.insert(pcAppDepHistory);
		}
		return id;
	}

}
