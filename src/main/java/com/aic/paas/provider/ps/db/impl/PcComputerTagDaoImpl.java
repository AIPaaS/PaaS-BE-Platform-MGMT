package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;
import com.aic.paas.provider.ps.bean.CPcComputerTag;
import com.aic.paas.provider.ps.bean.PcComputerTag;
import com.aic.paas.provider.ps.db.PcComputerTagDao;


/**
 * 服务器标签[PC_COMPUTER_TAG]数据访问对象实现
 */
public class PcComputerTagDaoImpl extends IBatisDaoTemplate<PcComputerTag, CPcComputerTag> implements PcComputerTagDao {

	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcComputerTag> selectListByNetZoneId(Long netZoneId) {
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("netZoneId", netZoneId);
		List<PcComputerTag> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectListByNetZoneId", map);
		return list;
	}

	
	
	

}


