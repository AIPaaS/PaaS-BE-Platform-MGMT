package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcNetZone;
import com.aic.paas.provider.ps.bean.PcNetZone;


/**
 * 网络区域表[PC_NET_ZONE]数据访问对象定义实现
 */
public class PcNetZoneDaoDefinition implements DaoDefinition<PcNetZone, CPcNetZone> {


	@Override
	public Class<PcNetZone> getEntityClass() {
		return PcNetZone.class;
	}


	@Override
	public Class<CPcNetZone> getConditionClass() {
		return CPcNetZone.class;
	}


	@Override
	public String getTableName() {
		return "PC_NET_ZONE";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcNetZone record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcNetZone cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcNetZone record, String creator) {
	}


	@Override
	public void setModifierValue(PcNetZone record, String modifier) {
	}


}


