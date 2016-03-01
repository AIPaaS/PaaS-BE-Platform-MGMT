package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppVersion;
import com.aic.paas.provider.ps.bean.PcAppVersion;


/**
 * 应用版本表[PC_APP_VERSION]数据访问对象定义实现
 */
public class PcAppVersionDaoDefinition implements DaoDefinition<PcAppVersion, CPcAppVersion> {


	@Override
	public Class<PcAppVersion> getEntityClass() {
		return PcAppVersion.class;
	}


	@Override
	public Class<CPcAppVersion> getConditionClass() {
		return CPcAppVersion.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_VERSION";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcAppVersion record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcAppVersion cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcAppVersion record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppVersion record, String modifier) {
	}


}


