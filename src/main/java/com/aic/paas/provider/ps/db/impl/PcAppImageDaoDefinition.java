package com.aic.paas.provider.ps.db.impl;


import com.aic.paas.provider.ps.bean.CPcAppImage;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.binary.framework.dao.DaoDefinition;


/**
 * 应用镜像表[PC_APP_IMAGE]数据访问对象定义实现
 */
public class PcAppImageDaoDefinition implements DaoDefinition<PcAppImage, CPcAppImage> {


	@Override
	public Class<PcAppImage> getEntityClass() {
		return PcAppImage.class;
	}


	@Override
	public Class<CPcAppImage> getConditionClass() {
		return CPcAppImage.class;
	}


	@Override
	public String getTableName() {
		return "PC_APP_IMAGE";
	}


	@Override
	public boolean hasDataStatusField() {
		return true;
	}


	@Override
	public void setDataStatusValue(PcAppImage record, int status) {
		record.setDataStatus(status);
	}


	@Override
	public void setDataStatusValue(CPcAppImage cdt, int status) {
		cdt.setDataStatus(status);
	}


	@Override
	public void setCreatorValue(PcAppImage record, String creator) {
		record.setCreator(creator);
	}


	@Override
	public void setModifierValue(PcAppImage record, String modifier) {
		record.setModifier(modifier);
	}


}


