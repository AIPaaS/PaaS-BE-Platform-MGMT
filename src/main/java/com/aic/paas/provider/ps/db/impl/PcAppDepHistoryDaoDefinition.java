package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppDepHistory;
import com.aic.paas.provider.ps.bean.PcAppDepHistory;


/**
 * 应用布署历史表[PC_APP_DEP_HISTORY]数据访问对象定义实现
 */
public class PcAppDepHistoryDaoDefinition implements DaoDefinition<PcAppDepHistory, CPcAppDepHistory> {


	@Override
	public Class<PcAppDepHistory> getEntityClass() {
		return PcAppDepHistory.class;
	}


	@Override
	public Class<CPcAppDepHistory> getConditionClass() {
		return CPcAppDepHistory.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_DEP_HISTORY";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppDepHistory record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppDepHistory cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppDepHistory record, String creator) {
		record.setCreator(creator);
	}


	@Override
	public void setModifierValue(PcAppDepHistory record, String modifier) {
		record.setModifier(modifier);
	}


}


