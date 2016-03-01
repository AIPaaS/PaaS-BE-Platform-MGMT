package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcDataCenter;
import com.aic.paas.provider.ps.bean.PcDataCenter;


/**
 * 数据中心表[PC_DATA_CENTER]数据访问对象定义实现
 */
public class PcDataCenterDaoDefinition implements DaoDefinition<PcDataCenter, CPcDataCenter> {


	@Override
	public Class<PcDataCenter> getEntityClass() {
		return PcDataCenter.class;
	}


	@Override
	public Class<CPcDataCenter> getConditionClass() {
		return CPcDataCenter.class;
	}


	@Override
	public String getTableName() {
		return "PC_DATA_CENTER";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcDataCenter record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcDataCenter cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcDataCenter record, String creator) {
	}


	@Override
	public void setModifierValue(PcDataCenter record, String modifier) {
	}


}


