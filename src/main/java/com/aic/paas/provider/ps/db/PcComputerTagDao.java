package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcComputerTag;
import com.aic.paas.provider.ps.bean.PcComputerTag;
import com.binary.framework.dao.Dao;


/**
 * 服务器标签[PC_COMPUTER_TAG]数据访问对象
 */
public interface PcComputerTagDao extends Dao<PcComputerTag, CPcComputerTag> {


	
	
	/**
	 * 查询指定网络区域下服务器标签
	 * @param netZoneId
	 * @return
	 */
	public List<PcComputerTag> selectListByNetZoneId(Long netZoneId);
	
	
	
	
}


