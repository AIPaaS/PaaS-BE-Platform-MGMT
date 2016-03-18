package com.aic.paas.provider.ps.db;


import java.util.List;

import com.aic.paas.provider.ps.bean.CPcAppImage;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.binary.framework.dao.Dao;


/**
 * 应用镜像表[PC_APP_IMAGE]数据访问对象
 */
public interface PcAppImageDao extends Dao<PcAppImage, CPcAppImage> {


	
	
	
	/**
	 * 查询应用下所有容器占用资源合计
	 * @param appIds
	 * @param appVnoIds
	 * @param netZoneId 指定网络区域, 可以为空, 为空则查询应用所在所有网络区域
	 * @return 返回字段如下：
	 * 		APP_ID 应用ID
	 * 		CPU_COUNT 应用占CPU总数
	 * 		MEM_SIZE 应用占内存总数
	 * 		DISK_SIZE 应用占硬盘总数
	 * 		INSTANCE_COUNT 应用起实例总数
	 * 		CUSTOM_1 应用包含容器数
	 */
	public List<PcAppImage> selectAppGroupList(Long[] appIds, Long[] appVnoIds, Long netZoneId);
	
	
	
	
	
	/**
	 * 查询指定应用下所有容器在各网络区域占用资源情况
	 * @param appId
	 * @param appVnoId
	 * @param netZoneId 指定网络区域, 可以为空, 为空则查询应用所在所有网络区域
	 * @return 返回字段如下：
	 * 		APP_ID 应用ID
	 * 		NET_ZONE_ID 网络区域ID
	 * 		CPU_COUNT 应用占CPU总数
	 * 		MEM_SIZE 应用占内存总数
	 * 		DISK_SIZE 应用占硬盘总数
	 * 		INSTANCE_COUNT 应用起实例总数
	 * 		CUSTOM_1 应用包含容器数
	 */
	public List<PcAppImage> selectAppNetZoneGroupList(Long appId, Long appVnoId, Long netZoneId);
	
	
	
	
	/**
	 * 跟据镜像全名查询镜像
	 * @param fullName
	 * @param cdt
	 * @param orders
	 * @return
	 */
	public List<PcAppImage> selectListByFullName(String fullName, CPcAppImage cdt, String orders);
	
	
	
	
	/**
	 * 判断指定APP_ID下所有容器是否都配置完成
	 * @param appId
	 * @return
	 */
	public boolean isFinishAllAppImage(Long appId, Long appVnoId);
	
	
	
}


