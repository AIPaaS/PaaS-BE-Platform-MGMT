package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPsMntResFlow;
import com.aic.paas.provider.ps.bean.PsMntResFlow;


/**
 * 租户资源流水表[PS_MNT_RES_FLOW]数据访问对象定义实现
 */
public class PsMntResFlowDaoDefinition implements DaoDefinition<PsMntResFlow, CPsMntResFlow> {


	@Override
	public Class<PsMntResFlow> getEntityClass() {
		return PsMntResFlow.class;
	}


	@Override
	public Class<CPsMntResFlow> getConditionClass() {
		return CPsMntResFlow.class;
	}


	@Override
	public String getTableName() {
		return "PS_MNT_RES_FLOW";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PsMntResFlow record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPsMntResFlow cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PsMntResFlow record, String creator) {
	}


	@Override
	public void setModifierValue(PsMntResFlow record, String modifier) {
	}


}


