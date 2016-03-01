package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcApp;
import com.aic.paas.provider.ps.bean.PcApp;


/**
 * 应用表[PC_APP]数据访问对象定义实现
 */
public class PcAppDaoDefinition implements DaoDefinition<PcApp, CPcApp> {


	@Override
	public Class<PcApp> getEntityClass() {
		return PcApp.class;
	}


	@Override
	public Class<CPcApp> getConditionClass() {
		return CPcApp.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcApp record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcApp cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcApp record, String creator) {
		record.setCreator(creator);
	}


	@Override
	public void setModifierValue(PcApp record, String modifier) {
		record.setModifier(modifier);
	}


}


