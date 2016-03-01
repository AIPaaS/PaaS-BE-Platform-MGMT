package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcKvPair;
import com.aic.paas.provider.ps.bean.PcKvPair;


/**
 * 键值表[PC_KV_PAIR]数据访问对象定义实现
 */
public class PcKvPairDaoDefinition implements DaoDefinition<PcKvPair, CPcKvPair> {


	@Override
	public Class<PcKvPair> getEntityClass() {
		return PcKvPair.class;
	}


	@Override
	public Class<CPcKvPair> getConditionClass() {
		return CPcKvPair.class;
	}


	@Override
	public String getTableName() {
		return "PC_KV_PAIR";
	}


	@Override
	public boolean hasDataStatusField() {
		return false;
	}


	@Override
	public void setDataStatusValue(PcKvPair record, int status) {
	}


	@Override
	public void setDataStatusValue(CPcKvPair cdt, int status) {
	}


	@Override
	public void setCreatorValue(PcKvPair record, String creator) {
	}


	@Override
	public void setModifierValue(PcKvPair record, String modifier) {
	}


}


