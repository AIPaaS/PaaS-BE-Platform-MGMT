package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppDepSvcHistory;
import com.aic.paas.provider.ps.bean.PcAppDepSvcHistory;


/**
 * 应用布署服务历史表[PC_APP_DEP_SVC_HISTORY]数据访问对象定义实现
 */
public class PcAppDepSvcHistoryDaoDefinition implements DaoDefinition<PcAppDepSvcHistory, CPcAppDepSvcHistory> {


	@Override
	public Class<PcAppDepSvcHistory> getEntityClass() {
		return PcAppDepSvcHistory.class;
	}


	@Override
	public Class<CPcAppDepSvcHistory> getConditionClass() {
		return CPcAppDepSvcHistory.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_DEP_SVC_HISTORY";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppDepSvcHistory record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppDepSvcHistory cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppDepSvcHistory record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppDepSvcHistory record, String modifier) {
	}


}


