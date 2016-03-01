package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppTask;
import com.aic.paas.provider.ps.bean.PcAppTask;


/**
 * 应用布署任务表[PC_APP_TASK]数据访问对象定义实现
 */
public class PcAppTaskDaoDefinition implements DaoDefinition<PcAppTask, CPcAppTask> {


	@Override
	public Class<PcAppTask> getEntityClass() {
		return PcAppTask.class;
	}


	@Override
	public Class<CPcAppTask> getConditionClass() {
		return CPcAppTask.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_TASK";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppTask record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppTask cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppTask record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppTask record, String modifier) {
	}


}


