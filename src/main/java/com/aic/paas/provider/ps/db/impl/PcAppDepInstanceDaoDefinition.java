package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppDepInstance;
import com.aic.paas.provider.ps.bean.PcAppDepInstance;


/**
 * 应用布署实例表[PC_APP_DEP_INSTANCE]数据访问对象定义实现
 */
public class PcAppDepInstanceDaoDefinition implements DaoDefinition<PcAppDepInstance, CPcAppDepInstance> {


	@Override
	public Class<PcAppDepInstance> getEntityClass() {
		return PcAppDepInstance.class;
	}


	@Override
	public Class<CPcAppDepInstance> getConditionClass() {
		return CPcAppDepInstance.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_DEP_INSTANCE";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppDepInstance record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppDepInstance cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppDepInstance record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppDepInstance record, String modifier) {
	}


}


