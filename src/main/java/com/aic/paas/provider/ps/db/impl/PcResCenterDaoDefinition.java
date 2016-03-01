package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcResCenter;
import com.aic.paas.provider.ps.bean.PcResCenter;


/**
 * 资源中心表[PC_RES_CENTER]数据访问对象定义实现
 */
public class PcResCenterDaoDefinition implements DaoDefinition<PcResCenter, CPcResCenter> {


	@Override
	public Class<PcResCenter> getEntityClass() {
		return PcResCenter.class;
	}


	@Override
	public Class<CPcResCenter> getConditionClass() {
		return CPcResCenter.class;
	}


	@Override
	public String getTableName() {
		return "PC_RES_CENTER";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcResCenter record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcResCenter cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcResCenter record, String creator) {
	}


	@Override
	public void setModifierValue(PcResCenter record, String modifier) {
	}


}


