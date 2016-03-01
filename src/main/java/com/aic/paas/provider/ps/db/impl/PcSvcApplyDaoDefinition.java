package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcSvcApply;
import com.aic.paas.provider.ps.bean.PcSvcApply;


/**
 * 平台服务申请表[PC_SVC_APPLY]数据访问对象定义实现
 */
public class PcSvcApplyDaoDefinition implements DaoDefinition<PcSvcApply, CPcSvcApply> {


	@Override
	public Class<PcSvcApply> getEntityClass() {
		return PcSvcApply.class;
	}


	@Override
	public Class<CPcSvcApply> getConditionClass() {
		return CPcSvcApply.class;
	}


	@Override
	public String getTableName() {
		return "PC_SVC_APPLY";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcSvcApply record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcSvcApply cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcSvcApply record, String creator) {
	}


	@Override
	public void setModifierValue(PcSvcApply record, String modifier) {
	}


}


