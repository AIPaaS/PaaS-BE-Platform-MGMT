package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppDepKvHistory;
import com.aic.paas.provider.ps.bean.PcAppDepKvHistory;


/**
 * 应用布署键值历史表[PC_APP_DEP_KV_HISTORY]数据访问对象定义实现
 */
public class PcAppDepKvHistoryDaoDefinition implements DaoDefinition<PcAppDepKvHistory, CPcAppDepKvHistory> {


	@Override
	public Class<PcAppDepKvHistory> getEntityClass() {
		return PcAppDepKvHistory.class;
	}


	@Override
	public Class<CPcAppDepKvHistory> getConditionClass() {
		return CPcAppDepKvHistory.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_DEP_KV_HISTORY";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcAppDepKvHistory record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcAppDepKvHistory cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcAppDepKvHistory record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppDepKvHistory record, String modifier) {
	}


}


