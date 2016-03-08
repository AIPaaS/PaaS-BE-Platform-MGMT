package com.aic.paas.provider.ps.dep.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppVersion;
import com.aic.paas.provider.ps.bean.PcAppVersion;
import com.aic.paas.provider.ps.db.PcAppVersionDao;
import com.aic.paas.provider.ps.dep.PcAppVersionSvc;

public class PcAppVersionSvcImpl implements PcAppVersionSvc {

	@Autowired
	PcAppVersionDao pcAppVersionDao;

	@Override
	public List<PcAppVersion> getAppVersion(Long appId, int status) {
		CPcAppVersion cPcAppVersion = new CPcAppVersion();
		cPcAppVersion.setAppId(appId);
		cPcAppVersion.setStatus(status);
		return pcAppVersionDao.selectList(cPcAppVersion, null);
	}

	@Override
	public Long getRunningAppVersionId(Long appId) {
		List<PcAppVersion> list = getAppVersion(appId, VersionStatus.RUNNING.getValue());
		if (CollectionUtils.isEmpty(list))
			return null;
		else
			return list.get(0).getId();
	}

	@Override
	public Long getStopedAppVersionId(Long appId) {
		List<PcAppVersion> list = getAppVersion(appId, VersionStatus.STOP.getValue());
		if (CollectionUtils.isEmpty(list))
			return null;
		else
			return list.get(0).getId();
	}

	@Override
	public void updateAppVersionStatus(Long appId, String version, int status) {
		CPcAppVersion cPcAppVersion = new CPcAppVersion();
		cPcAppVersion.setAppId(appId);
		cPcAppVersion.setVersionNo(version);
		List<PcAppVersion> appVersions = pcAppVersionDao.selectList(cPcAppVersion, null);
		for (PcAppVersion pcAppVersion : appVersions)
			pcAppVersion.setStatus(status);
		pcAppVersionDao.updateBatch(appVersions);
	}
}
