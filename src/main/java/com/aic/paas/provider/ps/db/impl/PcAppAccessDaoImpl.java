package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppAccess;
import com.aic.paas.provider.ps.bean.PcAppAccess;
import com.aic.paas.provider.ps.db.PcAppAccessDao;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;


/**
 * 应用访问表[PC_APP_ACCESS]数据访问对象实现
 */
public class PcAppAccessDaoImpl extends IBatisDaoTemplate<PcAppAccess, CPcAppAccess> implements PcAppAccessDao {

	@Override
	public List<PcAppAccess> selectListByCode(String code, CPcAppAccess cdt, String orders) {
		BinaryUtils.checkEmpty(code, "code");
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		map.put("code", code);
		List<PcAppAccess> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectListByCode", map);
		return list;
	}


}


