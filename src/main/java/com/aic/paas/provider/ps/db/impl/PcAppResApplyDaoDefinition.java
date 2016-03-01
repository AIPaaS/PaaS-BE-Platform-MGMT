package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcAppResApply;
import com.aic.paas.provider.ps.bean.PcAppResApply;


/**
 * 应用资源申请表[PC_APP_RES_APPLY]数据访问对象定义实现
 */
public class PcAppResApplyDaoDefinition implements DaoDefinition<PcAppResApply, CPcAppResApply> {


	@Override
	public Class<PcAppResApply> getEntityClass() {
		return PcAppResApply.class;
	}


	@Override
	public Class<CPcAppResApply> getConditionClass() {
		return CPcAppResApply.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_RES_APPLY";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppResApply record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppResApply cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppResApply record, String creator) {
	}


	@Override
	public void setModifierValue(PcAppResApply record, String modifier) {
	}


}


