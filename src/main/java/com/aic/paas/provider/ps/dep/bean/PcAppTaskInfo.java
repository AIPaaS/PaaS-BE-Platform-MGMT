package com.aic.paas.provider.ps.dep.bean;

import com.aic.paas.provider.ps.bean.PcAppTask;

public class PcAppTaskInfo {

	private PcAppTask pcAppTask;

	private String versionNo;

	public PcAppTask getPcAppTask() {
		return pcAppTask;
	}

	public void setPcAppTask(PcAppTask pcAppTask) {
		this.pcAppTask = pcAppTask;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

}
