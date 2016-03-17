package com.aic.paas.provider.ps.dep.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.PcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepInstance;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.db.PcAppDepInstanceDao;
import com.aic.paas.provider.ps.dep.PcAppDepInstanceSvc;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;

public class PcAppDepInstanceSvcImpl implements PcAppDepInstanceSvc {

	@Autowired
	PcAppDepInstanceDao pcAppDepInstanceDao;
	
	@Autowired
	PcAppDepHistoryDao depHistoryDao;
	
	

	@Override
	public Long saveOrUpdate(PcAppDepInstance record) {
		return pcAppDepInstanceDao.insert(record);
	}
	
	

	@Override
	public Long addDepInstanceByAppImgFullName(String appImgFullName, PcAppDepInstance record) {
		BinaryUtils.checkEmpty(appImgFullName, "appImgFullName");
		BinaryUtils.checkEmpty(record, "record");
		
		BinaryUtils.checkEmpty(record.getServerIp(), "record.serverIp");
		BinaryUtils.checkEmpty(record.getInstanceName(), "record.instanceName");
		BinaryUtils.checkEmpty(record.getTime(), "record.time");
		
		PcAppDepHistory his = depHistoryDao.selectMaxByAppImageFullName(appImgFullName);
		if(his == null) {
			throw new ServiceException(" not found history by containerFullName '"+appImgFullName+"'! ");
		}
		
		record.setAppDepHistoryId(his.getId());
		record.setStatus(1);
		
		return pcAppDepInstanceDao.insert(record);
	}
	
	

	
	
	@Override
	public Integer disableDepInstanceByInstanceName(String instanceName) {
		BinaryUtils.checkEmpty(instanceName, "instanceName");
		instanceName = instanceName.trim();
		return pcAppDepInstanceDao.disableDepInstanceByInstanceName(instanceName);
	}
	
	
	

}
