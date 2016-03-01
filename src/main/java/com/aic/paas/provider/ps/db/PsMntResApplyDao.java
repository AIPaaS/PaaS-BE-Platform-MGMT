package com.aic.paas.provider.ps.db;


import com.aic.paas.provider.ps.bean.CPsMntResApply;
import com.aic.paas.provider.ps.bean.PsMntResApply;
import com.binary.framework.dao.Dao;


/**
 * 租户资源申请表[PS_MNT_RES_APPLY]数据访问对象
 */
public interface PsMntResApplyDao extends Dao<PsMntResApply, CPsMntResApply> {


	
	
	/**
	 * 审批申请单
	 * @param id 申请单ID
	 * @param status 审批状态
	 * @param checkerId 审批人ID
	 * @param checkerName 审批人姓名
	 * @param desc 审批描述
	 * @return
	 */
	public int checkResApply(Long id, Integer status, Long checkerId, String checkerName, String desc);
	
	
	
}


