package com.aic.paas.provider.ps.db;


import com.binary.framework.dao.Dao;
import com.aic.paas.provider.ps.bean.CPsResCenterRes;
import com.aic.paas.provider.ps.bean.PsResCenterRes;


/**
 * 资源中心资源表[PS_RES_CENTER_RES]数据访问对象
 */
public interface PsResCenterResDao extends Dao<PsResCenterRes, CPsResCenterRes> {

	
	
	
	/**
	 * 增加资源中心总资源
	 * @param resCenterId
	 * @param cpuCount
	 * @param memSize
	 * @param diskSize
	 * @return 返回更新记录数
	 */
	public int increTotalResById(Long id, Long cpuCount, Long memSize, Long diskSize);
	
	
	
	
	/**
	 * 增加资源中心总资源
	 * @param netZoneId
	 * @param cpuCount
	 * @param memSize
	 * @param diskSize
	 * @return 返回更新记录数
	 */
	public int increTotalResByNetZoneId(Long netZoneId, Long cpuCount, Long memSize, Long diskSize);
	
	
	
	
	
	/**
	 * 增加资源中心资源
	 * @param resCenterId
	 * @param cpuCount
	 * @param memSize
	 * @param diskSize
	 * @param validity 是否需要验证扣减有效性, 缺省为false
	 * @return 返回更新记录数
	 */
	public int increResById(Long id, Long cpuCount, Long memSize, Long diskSize, Boolean validity);
	
	
	
	
	/**
	 * 增加资源中心资源
	 * @param netZoneId
	 * @param cpuCount
	 * @param memSize
	 * @param diskSize
	 * @param validity 是否需要验证扣减有效性, 缺省为false
	 * @return 返回更新记录数
	 */
	public int increResByNetZoneId(Long netZoneId, Long cpuCount, Long memSize, Long diskSize, Boolean validity);
	
	
	
	
	

}


