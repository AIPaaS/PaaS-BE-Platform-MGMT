package com.aic.paas.provider.ps.db.impl;


import com.binary.framework.dao.DaoDefinition;

import com.aic.paas.provider.ps.bean.CPcCompRoom;
import com.aic.paas.provider.ps.bean.PcCompRoom;


/**
 * 服务器机房表[PC_COMP_ROOM]数据访问对象定义实现
 */
public class PcCompRoomDaoDefinition implements DaoDefinition<PcCompRoom, CPcCompRoom> {


	@Override
	public Class<PcCompRoom> getEntityClass() {
		return PcCompRoom.class;
	}


	@Override
	public Class<CPcCompRoom> getConditionClass() {
		return CPcCompRoom.class;
	}


	@Override
	public String getTableName() {
		return "PC_COMP_ROOM";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcCompRoom record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcCompRoom cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcCompRoom record, String creator) {
	}


	@Override
	public void setModifierValue(PcCompRoom record, String modifier) {
	}


}


