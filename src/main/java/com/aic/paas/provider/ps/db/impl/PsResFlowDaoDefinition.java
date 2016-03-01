package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPsResFlow;
import com.aic.paas.provider.ps.bean.PsResFlow;


/**
 * 资源流水表[PS_RES_FLOW]数据访问对象定义实现
 */
public class PsResFlowDaoDefinition implements DaoDefinition<PsResFlow, CPsResFlow> {


	@Override
	public Class<PsResFlow> getEntityClass() {
		return PsResFlow.class;
	}


	@Override
	public Class<CPsResFlow> getConditionClass() {
		return CPsResFlow.class;
	}


	@Override
	public String getTableName() {
		return "PS_RES_FLOW";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PsResFlow record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPsResFlow cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PsResFlow record, String creator) {
	}


	@Override
	public void setModifierValue(PsResFlow record, String modifier) {
	}


}


