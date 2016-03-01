package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcComputer;
import com.aic.paas.provider.ps.bean.PcComputer;


/**
 * 服务器登记表[PC_COMPUTER]数据访问对象定义实现
 */
public class PcComputerDaoDefinition implements DaoDefinition<PcComputer, CPcComputer> {


	@Override
	public Class<PcComputer> getEntityClass() {
		return PcComputer.class;
	}


	@Override
	public Class<CPcComputer> getConditionClass() {
		return CPcComputer.class;
	}


	@Override
	public String getTableName() {
		return "PC_COMPUTER";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcComputer record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcComputer cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcComputer record, String creator) {
	}


	@Override
	public void setModifierValue(PcComputer record, String modifier) {
	}


}


