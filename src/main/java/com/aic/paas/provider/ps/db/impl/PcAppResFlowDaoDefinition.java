package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppResFlow;
import com.aic.paas.provider.ps.bean.PcAppResFlow;


/**
 * 应用资源流水表[PC_APP_RES_FLOW]数据访问对象定义实现
 */
public class PcAppResFlowDaoDefinition implements DaoDefinition<PcAppResFlow, CPcAppResFlow> {


	@Override
	public Class<PcAppResFlow> getEntityClass() {
		return PcAppResFlow.class;
	}


	@Override
	public Class<CPcAppResFlow> getConditionClass() {
		return CPcAppResFlow.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_RES_FLOW";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppResFlow record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppResFlow cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppResFlow record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppResFlow record, String modifier) {
	}


}


