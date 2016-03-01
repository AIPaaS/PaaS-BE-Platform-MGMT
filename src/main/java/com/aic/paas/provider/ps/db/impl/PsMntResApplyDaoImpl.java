package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPsMntResApply;
import com.aic.paas.provider.ps.bean.PsMntResApply;
import com.aic.paas.provider.ps.db.PsMntResApplyDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 租户资源申请表[PS_MNT_RES_APPLY]数据访问对象实现
 */
public class PsMntResApplyDaoImpl extends IBatisDaoTemplate<PsMntResApply, CPsMntResApply> implements PsMntResApplyDao {

	
	
	
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


