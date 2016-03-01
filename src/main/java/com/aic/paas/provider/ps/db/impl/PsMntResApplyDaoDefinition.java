package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPsMntResApply;
import com.aic.paas.provider.ps.bean.PsMntResApply;


/**
 * 租户资源申请表[PS_MNT_RES_APPLY]数据访问对象定义实现
 */
public class PsMntResApplyDaoDefinition implements DaoDefinition<PsMntResApply, CPsMntResApply> {


	@Override
	public Class<PsMntResApply> getEntityClass() {
		return PsMntResApply.class;
	}


	@Override
	public Class<CPsMntResApply> getConditionClass() {
		return CPsMntResApply.class;
	}


	@Override
	public String getTableName() {
		return "PS_MNT_RES_APPLY";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PsMntResApply record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPsMntResApply cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PsMntResApply record, String creator) {
	}


	@Override
	public void setModifierValue(PsMntResApply record, String modifier) {
	}


}


