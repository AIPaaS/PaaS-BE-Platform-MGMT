package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppTaskLog;
import com.aic.paas.provider.ps.bean.PcAppTaskLog;


/**
 * 应用布署任务日志表[PC_APP_TASK_LOG]数据访问对象定义实现
 */
public class PcAppTaskLogDaoDefinition implements DaoDefinition<PcAppTaskLog, CPcAppTaskLog> {


	@Override
	public Class<PcAppTaskLog> getEntityClass() {
		return PcAppTaskLog.class;
	}


	@Override
	public Class<CPcAppTaskLog> getConditionClass() {
		return CPcAppTaskLog.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_TASK_LOG";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcAppTaskLog record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcAppTaskLog cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcAppTaskLog record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppTaskLog record, String modifier) {
	}


}


