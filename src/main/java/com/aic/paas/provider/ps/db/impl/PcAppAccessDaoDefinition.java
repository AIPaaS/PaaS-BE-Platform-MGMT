package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppAccess;
import com.aic.paas.provider.ps.bean.PcAppAccess;


/**
 * 应用访问表[PC_APP_ACCESS]数据访问对象定义实现
 */
public class PcAppAccessDaoDefinition implements DaoDefinition<PcAppAccess, CPcAppAccess> {


	@Override
	public Class<PcAppAccess> getEntityClass() {
		return PcAppAccess.class;
	}


	@Override
	public Class<CPcAppAccess> getConditionClass() {
		return CPcAppAccess.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_ACCESS";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppAccess record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppAccess cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppAccess record, String creator) {
		record.setCreator(creator);
	}


	@Override
	public void setModifierValue(PcAppAccess record, String modifier) {
		record.setModifier(modifier);
	}


}


