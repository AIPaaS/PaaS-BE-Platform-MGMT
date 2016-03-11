package com.aic.paas.provider.ps.dep.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.PcAppDepInstance;
import com.aic.paas.provider.ps.db.PcAppDepInstanceDao;
import com.aic.paas.provider.ps.dep.PcAppDepInstanceSvc;

public class PcAppDepInstanceSvcImpl implements PcAppDepInstanceSvc {

	@Autowired
	PcAppDepInstanceDao pcAppDepInstanceDao;

	@Override
	public Long saveOrUpdate(PcAppDepInstance record) {
		return pcAppDepInstanceDao.insert(record);
	}

}
