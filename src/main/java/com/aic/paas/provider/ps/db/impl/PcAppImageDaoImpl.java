package com.aic.paas.provider.ps.db.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aic.paas.provider.ps.bean.CPcAppImage;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.aic.paas.provider.ps.db.PcAppImageDao;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.dao.support.tpl.IBatisDaoTemplate;
import com.binary.jdbc.JdbcOperator;


/**
 * 应用镜像表[PC_APP_IMAGE]数据访问对象实现
 */
public class PcAppImageDaoImpl extends IBatisDaoTemplate<PcAppImage, CPcAppImage> implements PcAppImageDao {

	
	
	
	@Override
	public List<PcAppImage> selectAppGroupList(Long[] appIds, Long[] appVnoIds, Long netZoneId) {
		BinaryUtils.checkEmpty(appIds, "appIds");
		BinaryUtils.checkEmpty(appVnoIds, "appVnoIds");
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select APP_ID appId, sum(CPU_COUNT) cpuCount, sum(MEM_SIZE) memSize, sum(DISK_SIZE) diskSize, ")
			.append("			sum(INSTANCE_COUNT) instanceCount, count(1)  custom1 ")
			.append("		from PC_APP_IMAGE ")
			.append("		where DATA_STATUS=1 and APP_ID in ("+Conver.toString(appIds)+") and APP_VNO_ID in ("+Conver.toString(appVnoIds)+") ");
		if(netZoneId != null) {
			sb.append(" and NET_ZONE_ID=").append(netZoneId).append(" ");
		}
		sb.append("		group by APP_ID ")
			.append("		order by APP_ID ");
		
		JdbcOperator jo = getJdbcOperator();
		List<PcAppImage> ls = jo.executeQuery(sb.toString(), PcAppImage.class);
		return ls;
	}
	
	
	
	@Override
	public List<PcAppImage> selectAppNetZoneGroupList(Long appId, Long appVnoId, Long netZoneId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(appVnoId, "appVnoId");
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select APP_ID appId, NET_ZONE_ID netZoneId, ")
			.append("			sum(CPU_COUNT) cpuCount, sum(MEM_SIZE) memSize, sum(DISK_SIZE) diskSize, ")
			.append("			sum(INSTANCE_COUNT) instanceCount, count(1)  custom1 ")
			.append("		from PC_APP_IMAGE ")
			.append("		where DATA_STATUS=1 and APP_ID="+appId+" and APP_VNO_ID="+appVnoId+"  ");
		if(netZoneId != null) {
			sb.append(" and NET_ZONE_ID=").append(netZoneId).append(" ");
		}
		sb.append("		group by APP_ID,NET_ZONE_ID ")
			.append("		order by APP_ID,NET_ZONE_ID ");
		
		JdbcOperator jo = getJdbcOperator();
		List<PcAppImage> ls = jo.executeQuery(sb.toString(), PcAppImage.class);
		return ls;
	}

	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcAppImage> selectListByFullName(String fullName, CPcAppImage cdt, String orders) {
		BinaryUtils.checkEmpty(fullName, "fullName");
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		map.put("fullName", fullName);
		List<PcAppImage> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectListByFullName", map);
		return list;
	}
	
	
	
	
	@Override
	public boolean isFinishAllAppImage(Long appId, Long appVnoId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(appVnoId, "appVnoId");
		
		String sql = " select avg(SETUP_NUM) SN from PC_APP_IMAGE  where DATA_STATUS=1 and APP_ID="+appId+" and APP_VNO_ID="+appVnoId;
		
		JdbcOperator jo = getJdbcOperator();
		Map<String, Object> first = jo.selectFirst(sql);
		if(first == null) return false;
		
		Integer sn = Conver.to(first.get("SN"), Integer.class);
		
		return sn!=null &&sn.intValue()==9;
	}

}


