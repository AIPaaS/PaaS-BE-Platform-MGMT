package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppResApply;
import com.aic.paas.provider.ps.bean.PcAppResApply;
import com.aic.paas.provider.ps.db.PcAppResApplyDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 应用资源申请表[PC_APP_RES_APPLY]数据访问对象实现
 */
public class PcAppResApplyDaoImpl extends IBatisDaoTemplate<PcAppResApply, CPcAppResApply> implements PcAppResApplyDao {

	
	
	
	@Override
	public int checkResApply(Long id, Integer status, Long checkerId, String checkerName, String desc) {
		BinaryUtils.checkEmpty(id, "id");
		BinaryUtils.checkEmpty(status, "status");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("checkTime", BinaryUtils.getNumberDateTime());
		map.put("checkDesc", desc);
		map.put("checkerName", checkerName);
		map.put("checkerId", checkerId);
		
		return getSqlMapClientTemplate().update(getTableName()+".checkResApply", map);
	}

	
	
	

}


