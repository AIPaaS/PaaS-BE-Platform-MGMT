package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcService;
import com.aic.paas.provider.ps.bean.PcService;
import com.aic.paas.provider.ps.db.PcServiceDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 服务表[PC_SERVICE]数据访问对象实现
 */
public class PcServiceDaoImpl extends IBatisDaoTemplate<PcService, CPcService> implements PcServiceDao {

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcService> selectListByCode(String code, CPcService cdt, String orders) {
		BinaryUtils.checkEmpty(code, "code");
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		map.put("code", code);
		List<PcService> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectListByCode", map);
		return list;
	}
	
	
	

}


