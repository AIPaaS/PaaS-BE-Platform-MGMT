package com.aic.paas.provider.ps.dep.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppImage;
import com.aic.paas.provider.ps.bean.CPcAppImgSvc;
import com.aic.paas.provider.ps.bean.CPcKvPair;
import com.aic.paas.provider.ps.bean.CPcService;
import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.aic.paas.provider.ps.bean.PcAppImgSvc;
import com.aic.paas.provider.ps.bean.PcAppRes;
import com.aic.paas.provider.ps.bean.PcAppVersion;
import com.aic.paas.provider.ps.bean.PcKvPair;
import com.aic.paas.provider.ps.bean.PcService;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppImageDao;
import com.aic.paas.provider.ps.db.PcAppImgSvcDao;
import com.aic.paas.provider.ps.db.PcAppVersionDao;
import com.aic.paas.provider.ps.db.PcKvPairDao;
import com.aic.paas.provider.ps.db.PcServiceDao;
import com.aic.paas.provider.ps.dep.PcAppImageSvc;
import com.aic.paas.provider.ps.dep.PcAppSvc;
import com.aic.paas.provider.ps.dep.PcServiceSvc;
import com.aic.paas.provider.ps.dep.bean.AppImageCallServiceRlt;
import com.aic.paas.provider.ps.dep.bean.AppImageSettings;
import com.aic.paas.provider.ps.dep.bean.AppImageSetup;
import com.aic.paas.provider.ps.dep.bean.AppImageSvcInfo;
import com.aic.paas.provider.ps.dep.bean.AppZoneResInfo;
import com.aic.paas.provider.ps.util.DepUtil;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcAppImageSvcImpl implements PcAppImageSvc {

	
	@Autowired
	PcAppImageDao appImageDao;
	
//	@Autowired
//	PcImageDao imageDao;
	
	@Autowired
	PcServiceDao svcDao;
	
	@Autowired
	PcKvPairDao kvPairDao;
	
	@Autowired
	PcAppImgSvcDao appImgSvcDao;
	
	@Autowired
	PcServiceSvc serviceSvc;
	
	@Autowired
	PcAppDao appDao;
	
	@Autowired
	PcAppVersionDao appVnoDao;
	
	@Autowired
	PcAppSvc appSvc;
	
	
	@Override
	public Page<PcAppImage> queryPage(Integer pageNum, Integer pageSize, CPcAppImage cdt, String orders) {
		return appImageDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	
	
	@Override
	public List<PcAppImage> queryList(CPcAppImage cdt, String orders) {
		return appImageDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcAppImage queryById(Long id) {
		return appImageDao.selectById(id);
	}

	
	
	
	
	
	@Override
	public Long saveOrUpdate(PcAppImage record) {
		BinaryUtils.checkEmpty(record, "record");
		BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
		BinaryUtils.checkEmpty(record.getAppVnoId(), "record.appVnoId");
		BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
		
		boolean isadd = record.getId() == null;
		if(isadd) {
			BinaryUtils.checkEmpty(record.getImageId(), "record.imageId");
			BinaryUtils.checkEmpty(record.getContainerName(), "record.containerName");
			BinaryUtils.checkEmpty(record.getContainerFullName(), "record.containerFullName");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoneId");
			BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
			BinaryUtils.checkEmpty(record.getInstanceCount(), "record.instanceCount");
			BinaryUtils.checkEmpty(record.getIsSupportFlex(), "record.isSupportFlex");
			if(record.getIsSupportFlex().intValue() == 1) {
				BinaryUtils.checkEmpty(record.getCpuFlexUpperLimit(), "record.cpuFlexUpperLimit");
				BinaryUtils.checkEmpty(record.getCpuFlexLowerLimit(), "record.cpuFlexLowerLimit");
				BinaryUtils.checkEmpty(record.getMaxInstanceCount(), "record.maxInstanceCount");
				BinaryUtils.checkEmpty(record.getMinInstanceCount(), "record.minInstanceCount");
			}
//			BinaryUtils.checkEmpty(record.getLogMpPath(), "record.logMpPath");
//			BinaryUtils.checkEmpty(record.getDataMpPath(), "record.dataMpPath");
			BinaryUtils.checkEmpty(record.getIsUniform(), "record.isUniform");
		}else {
			if(record.getImageId() != null) BinaryUtils.checkEmpty(record.getImageId(), "record.imageId");
			if(record.getContainerName() != null) BinaryUtils.checkEmpty(record.getContainerName(), "record.containerName");
			if(record.getContainerFullName() != null) BinaryUtils.checkEmpty(record.getContainerFullName(), "record.containerFullName");
			if(record.getDataCenterId() != null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId() != null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getNetZoneId() != null) BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoneId");
			if(record.getCpuCount() != null) BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			if(record.getMemSize() != null) BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			if(record.getDiskSize() != null) BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
			if(record.getInstanceCount() != null) BinaryUtils.checkEmpty(record.getInstanceCount(), "record.instanceCount");
			if(record.getIsSupportFlex() != null) BinaryUtils.checkEmpty(record.getIsSupportFlex(), "record.isSupportFlex");
			if(record.getCpuFlexUpperLimit() != null) BinaryUtils.checkEmpty(record.getCpuFlexUpperLimit(), "record.cpuFlexUpperLimit");
			if(record.getCpuFlexLowerLimit() != null) BinaryUtils.checkEmpty(record.getCpuFlexLowerLimit(), "record.cpuFlexLowerLimit");
			if(record.getMaxInstanceCount() != null) BinaryUtils.checkEmpty(record.getMaxInstanceCount(), "record.maxInstanceCount");
			if(record.getMinInstanceCount() != null) BinaryUtils.checkEmpty(record.getMinInstanceCount(), "record.minInstanceCount");
//			if(record.getLogMpPath() != null) BinaryUtils.checkEmpty(record.getLogMpPath(), "record.logMpPath");
//			if(record.getDataMpPath() != null) BinaryUtils.checkEmpty(record.getDataMpPath(), "record.dataMpPath");
			if(record.getIsUniform() != null) BinaryUtils.checkEmpty(record.getIsUniform(), "record.isUniform");
		}
				
		Long id = record.getId();
		if(record.getContainerFullName() != null) {
			String code = record.getContainerFullName().trim();
			record.setContainerFullName(code);
			List<PcAppImage> ls = appImageDao.selectListByFullName(code, null, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists containerName '"+code+"'! ");
			}
			
			if(!isadd){
				//将镜像提供的服务名与容器名一至
				CPcService svccdt = new CPcService();
				svccdt.setSvcType(3); 		//1=平台服务    2=外部服务    3=镜像服务
				svccdt.setAppImageId(id);
				
				PcService svcup = new PcService();
				svcup.setSvcCode(code);
				svcDao.updateByCdt(svcup, svccdt);
			}
		}
		
		id = appImageDao.save(record);
		updateSetupNum(record.getAppId(), record.getAppVnoId(), id, AppImageSetup.CONTAINER);
		
		return id;
	}

	
	
	@Override
	public Integer removeById(Long id) {
		//删除镜像作为的服务
		int count = appImageDao.deleteById(id);
		if(count > 0) {
			CPcService svccdt = new CPcService();
			svccdt.setSvcType(3); 		//1=平台服务    2=外部服务    3=镜像服务
			svccdt.setAppImageId(id);
			svcDao.deleteByCdt(svccdt);			
		}
		return count;
	}


	
	
	@Override
	public AppImageSvcInfo getAppImageOpenService(Long appImageId) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		CPcService svccdt = new CPcService();
		svccdt.setSvcType(3); 		//1=平台服务    2=外部服务    3=镜像服务
		svccdt.setAppImageId(appImageId);
		List<PcService> ls = svcDao.selectList(svccdt, null);
		
		if(ls.size() == 0) return null;
		
		PcService svc = ls.get(0);
		
		CPcKvPair cdtkv = new CPcKvPair();
		cdtkv.setTypeId(1); 		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdtkv.setSourceId(svc.getId());
		List<PcKvPair> params = kvPairDao.selectList(cdtkv, null);
		
		AppImageSvcInfo info = new AppImageSvcInfo();
		info.setSvc(ls.get(0));
		info.setParams(params);
		
		return info;
	}
	
	
	
	
	
	@Override
	public Long saveAppImageOpenService(Integer isOpen, Long appImageId, PcService svc, List<PcKvPair> params) {
		BinaryUtils.checkEmpty(isOpen, "isOpen");
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		PcAppImage appImg = appImageDao.selectById(appImageId);
		if(appImg == null) throw new ServiceException(" not found appImage by id '"+appImageId+"'! ");
		
		PcApp app = appDao.selectById(appImg.getAppId());
		if(app == null) throw new ServiceException(" not found app by id '"+appImg.getAppId()+"'! ");
		
		PcAppVersion appVno = appVnoDao.selectById(appImg.getAppVnoId());
		if(appVno == null) throw new ServiceException(" not found app-verion by id '"+appImg.getAppVnoId()+"'! ");
		
		Long serviceId = null;
		if(isOpen.intValue() == 1) {	//1=开放    0=不开放
			BinaryUtils.checkEmpty(svc, "appImageService");
			
			svc.setDataCenterId(appImg.getDataCenterId());
			svc.setResCenterId(appImg.getResCenterId());
			svc.setSvcCode(appImg.getContainerFullName());
			svc.setSvcName("应用["+app.getAppCode()+"]镜像["+appImg.getContainerName()+"]服务");
			svc.setSvcType(3);		//1=平台服务    2=外部服务    3=镜像服务
			svc.setMntId(app.getMntId());
			svc.setStatus(1); 		//1=有效  0=无效
			svc.setAppId(appImg.getAppId());
			svc.setAppImageId(appImageId);
			
			serviceId = serviceSvc.saveOrUpdate(svc);
			serviceSvc.resetParams(serviceId, params);
		}else {
			//如果不开放则删除之前定义的服务
			CPcService svccdt = new CPcService();
			svccdt.setSvcType(3); 		//1=平台服务    2=外部服务    3=镜像服务
			svccdt.setAppImageId(appImageId);
			svcDao.deleteByCdt(svccdt);
		}
		
		updateSetupNum(app.getId(), appVno.getId(), appImageId, AppImageSetup.OPEN_SERVICE);
		
		return serviceId;
	}



	@Override
	public List<PcAppImage> getAppImageDependImages(Long appImageId) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		CPcAppImgSvc cdt = new CPcAppImgSvc();
		cdt.setAppImgId(appImageId);
		cdt.setSvcType(4); 		//1=平台服务    2=外部服务    3=镜像服务    4=非服务镜像
		cdt.setCallType(2); 	//1=调用    2=依赖
		List<PcAppImgSvc> ls = appImgSvcDao.selectList(cdt, null);
		
		List<PcAppImage> depends = new ArrayList<PcAppImage>();
		
		if(ls.size() > 0) {
			Set<Long> dependAppImageIds = new HashSet<Long>();
			for(int i=0; i<ls.size(); i++) {
				PcAppImgSvc svc = ls.get(i);
				dependAppImageIds.add(svc.getSvcId());
			}
			
			CPcAppImage appimgcdt = new CPcAppImage();
			appimgcdt.setIds(dependAppImageIds.toArray(new Long[0]));
			depends = appImageDao.selectList(appimgcdt, " CONTAINER_NAME ");
		}
		
		return depends;
	}



	@Override
	public void saveAppImageDependImages(Long appImageId, Long[] dependAppImageIds) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		CPcAppImgSvc cdt = new CPcAppImgSvc();
		cdt.setAppImgId(appImageId);
		cdt.setSvcType(4); 		//1=平台服务    2=外部服务    3=镜像服务    4=非服务镜像
		cdt.setCallType(2); 	//1=调用    2=依赖
		appImgSvcDao.deleteByCdt(cdt);
		
		if(dependAppImageIds!=null && dependAppImageIds.length>0) {
			List<PcAppImgSvc> records = new ArrayList<PcAppImgSvc>();
			for(int i=0; i<dependAppImageIds.length; i++) {
				PcAppImgSvc record = new PcAppImgSvc();
				record.setAppImgId(appImageId);
				record.setSvcId(dependAppImageIds[i]);
				record.setSvcType(4);
				record.setCallType(2);
				records.add(record);
			}
			appImgSvcDao.insertBatch(records);
		}
	}


	

	@Override
	public List<AppImageSvcInfo> getAppImageCallService(Long appImageId) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		CPcAppImgSvc cdt = new CPcAppImgSvc();
		cdt.setAppImgId(appImageId);
		cdt.setSvcTypes(new Integer[]{1,2,3}); 		//1=平台服务    2=外部服务    3=镜像服务    4=非服务镜像
		List<PcAppImgSvc> ls = appImgSvcDao.selectList(cdt, null);
		
		List<AppImageSvcInfo> svcinfos = new ArrayList<AppImageSvcInfo>();
		
		if(ls.size() > 0) {
			//key=svcId
			Map<Long, PcAppImgSvc> imgsvcmap = new HashMap<Long, PcAppImgSvc>();
			Long[] svcIds = new Long[ls.size()];
			Long[] rltIds = new Long[ls.size()];
			
			for(int i=0; i<ls.size(); i++) {
				PcAppImgSvc svc = ls.get(i);
				rltIds[i] = svc.getId();
				svcIds[i] = svc.getSvcId();
				imgsvcmap.put(svcIds[i], svc);
			}
			
			CPcService svccdt = new CPcService();
			svccdt.setIds(svcIds);
			List<PcService> svcls = svcDao.selectList(svccdt, " SVC_TYPE,SVC_CODE ");
			
			CPcKvPair kvcdt = new CPcKvPair();
			kvcdt.setTypeId(3);			//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
			kvcdt.setSourceIds(rltIds);
			List<PcKvPair> kvls = kvPairDao.selectList(kvcdt, " SOURCE_ID, KV_KEY"); 
			Map<Long, List<PcKvPair>> kvmap = BinaryUtils.toObjectGroupMap(kvls, "sourceId");
			
			for(int i=0; i<svcls.size(); i++) {
				PcService svc = svcls.get(i);
				Long svcId = svc.getId();
				
				PcAppImgSvc rlt = imgsvcmap.get(svcId);
				AppImageSvcInfo info = new AppImageSvcInfo();
				info.setAppImgSvc(rlt);
				info.setSvc(svc);
				info.setParams(kvmap.get(rlt.getId()));
				svcinfos.add(info);
			}
		}
		
		return svcinfos;
	}



	
	@Override
	public void saveAppImageCallService(Long appImageId, List<AppImageCallServiceRlt> rlts) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		if(rlts != null) {
			List<PcKvPair> allps = new ArrayList<PcKvPair>();
			for(int i=0; i<rlts.size(); i++) {
				AppImageCallServiceRlt rlt = rlts.get(i);
				List<PcKvPair> ps = rlt.getParams();
				if(ps != null) {
					allps.addAll(ps);
				}
			}
			DepUtil.verifyParamsRepeated(allps, true);
		}
		
		
		PcAppImage appImg = appImageDao.selectById(appImageId);
		if(appImg == null) throw new ServiceException(" not found appImage by id '"+appImageId+"'! ");
		
		PcApp app = appDao.selectById(appImg.getAppId());
		if(app == null) throw new ServiceException(" not found app by id '"+appImg.getAppId()+"'! ");
		
		PcAppVersion appVno = appVnoDao.selectById(appImg.getAppVnoId());
		if(appVno == null) throw new ServiceException(" not found app-verion by id '"+appImg.getAppVnoId()+"'! ");
		
		
		CPcAppImgSvc cdt = new CPcAppImgSvc();
		cdt.setAppImgId(appImageId);
		cdt.setSvcTypes(new Integer[]{1,2,3}); 		//1=平台服务    2=外部服务    3=镜像服务    4=非服务镜像
		List<PcAppImgSvc> ls = appImgSvcDao.selectList(cdt, null);
		
		//先删
		if(ls.size() > 0) {
			Long[] rltIds = new Long[ls.size()];
			for(int i=0; i<ls.size(); i++) {
				PcAppImgSvc svc = ls.get(i);
				rltIds[i] = svc.getId();
			}
			
			CPcKvPair kvcdt = new CPcKvPair();
			kvcdt.setTypeId(3);			//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
			kvcdt.setSourceIds(rltIds);
			kvPairDao.deleteByCdt(kvcdt);
			appImgSvcDao.deleteByCdt(cdt);
		}
		
		if(rlts!=null && rlts.size()>0) {
			List<PcKvPair> kvrecords = new ArrayList<PcKvPair>();
			
			for(int i=0; i<rlts.size(); i++) {
				AppImageCallServiceRlt rlt = rlts.get(i);
				Long svcId = rlt.getSvcId();
				Integer svcType = rlt.getSvcType();
				Integer callType = rlt.getCallType();
				List<PcKvPair> params = rlt.getParams();
				
				BinaryUtils.checkEmpty(svcId, " rltList["+i+"].svcId ");
				BinaryUtils.checkEmpty(svcType, " rltList["+i+"].svcType ");
				BinaryUtils.checkEmpty(callType, " rltList["+i+"].callType ");
				
				PcAppImgSvc rltrecord = new PcAppImgSvc();
				rltrecord.setAppImgId(appImageId);
				rltrecord.setSvcId(svcId);
				rltrecord.setSvcType(svcType);
				rltrecord.setCallType(callType);
				Long rltId = appImgSvcDao.insert(rltrecord);
				
				if(params!=null && params.size()>0) {
					for(int j=0; j<params.size(); j++) {
						PcKvPair kv = params.get(j);
						kv.setTypeId(3);
						kv.setSourceId(rltId);
						kvrecords.add(kv);
					}
				}
			}
			
			if(kvrecords.size() > 0) {
				kvPairDao.insertBatch(kvrecords);
			}
		}
		
		updateSetupNum(app.getId(), appVno.getId(), appImageId, AppImageSetup.DEPEND_SERVICE);
	}
	
	
	

	@Override
	public List<PcKvPair> getAppImageParams(Long appImageId) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		
		CPcKvPair cdt = new CPcKvPair();
		cdt.setTypeId(2);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdt.setSourceId(appImageId);
		return kvPairDao.selectList(cdt, " KV_KEY ");
	}



	@Override
	public void saveAppImageParams(Long appImageId, List<PcKvPair> params) {
		BinaryUtils.checkEmpty(appImageId, "appImageId");
		DepUtil.verifyParamsRepeated(params, false);
		
		PcAppImage appImg = appImageDao.selectById(appImageId);
		if(appImg == null) throw new ServiceException(" not found appImage by id '"+appImageId+"'! ");
		
		PcApp app = appDao.selectById(appImg.getAppId());
		if(app == null) throw new ServiceException(" not found app by id '"+appImg.getAppId()+"'! ");
		
		PcAppVersion appVno = appVnoDao.selectById(appImg.getAppVnoId());
		if(appVno == null) throw new ServiceException(" not found app-verion by id '"+appImg.getAppVnoId()+"'! ");
		
		CPcKvPair cdt = new CPcKvPair();
		cdt.setTypeId(2);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdt.setSourceId(appImageId);
		kvPairDao.deleteByCdt(cdt);
		
		if(params!=null && params.size()>0) {
			for(int i=0; i<params.size(); i++) {
				PcKvPair kv = params.get(i);
				kv.setTypeId(2);
				kv.setSourceId(appImageId);
			}
			kvPairDao.insertBatch(params);
		}
		
		updateSetupNum(app.getId(), appVno.getId(), appImageId, AppImageSetup.CONTAINER_PARAMS);
	}



	
	@Override
	public AppImageSettings getAppImageSettings(Long appImageId) {
//		PcAppImageInfo info = queryInfoById(appImageId);
//		if(info == null) return null;
		PcAppImage appImage = queryById(appImageId);
		if(appImage == null) return null;
		
		AppImageSettings settings = new AppImageSettings();
		settings.setAppImage(appImage);
//		settings.setImage(info.getImage());
		settings.setOpenSvc(getAppImageOpenService(appImageId));
		settings.setDependImages(getAppImageDependImages(appImageId));
		settings.setCallServices(getAppImageCallService(appImageId));
		settings.setParams(getAppImageParams(appImageId));
		
		return settings;
	}

	
	
	@Override
	public List<AppImageSettings> getAppImageSettingsList(Long appId, Long appVnoId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(appVnoId, "appVnoId");
		
		CPcAppImage cdt = new CPcAppImage();
		cdt.setAppId(appId);
		cdt.setAppVnoId(appVnoId);
		List<PcAppImage> images = queryList(cdt, null);
		
		List<AppImageSettings> settingsList = new ArrayList<AppImageSettings>();
		for(int i=0; i<images.size(); i++) {
			PcAppImage img = images.get(i);
			AppImageSettings settings = getAppImageSettings(img.getId());
			settingsList.add(settings);
		}
		
		return settingsList;
	}
	
	


	@Override
	public void finishAppImageSettings(Long appImageId) {
		PcAppImage appImg = appImageDao.selectById(appImageId);
		if(appImg == null) throw new ServiceException(" not found appImage by id '"+appImageId+"'! ");
		
		PcApp app = appDao.selectById(appImg.getAppId());
		if(app == null) throw new ServiceException(" not found app by id '"+appImg.getAppId()+"'! ");
		
		PcAppVersion appVno = appVnoDao.selectById(appImg.getAppVnoId());
		if(appVno == null) throw new ServiceException(" not found app-verion by id '"+appImg.getAppVnoId()+"'! ");
		
		Long appId = app.getId();
		Long appVnoId = appImg.getAppVnoId();
		Long netZoneId = appImg.getNetZoneId();
		
		//验证资源
		Integer cpuCount = appImg.getCpuCount();
		Long memSize = appImg.getMemSize();
		Integer instanceCount = appImg.getInstanceCount();
		if(cpuCount==null || cpuCount.intValue()<=0) throw new ServiceException(" the appImage.cpuCount is wrong '"+cpuCount+"'! ");
		if(memSize==null || memSize.intValue()<=0) throw new ServiceException(" the appImage.memSize is wrong '"+memSize+"'! ");
		if(instanceCount==null || instanceCount.intValue()<=0) throw new ServiceException(" the appImage.instanceCount is wrong '"+instanceCount+"'! ");
		
		List<AppZoneResInfo> appresls = appSvc.queryAppNetZoneResInfo(appId, appVnoId, netZoneId);
		if(appresls.size() == 0) throw new ServiceException(" Don't have the resources app ["+appId+"] in net-zone ["+netZoneId+"]. ");
		
		AppZoneResInfo info = appresls.get(0);
		PcAppRes totalRes = info.getTotalRes();
		PcAppRes residueRes = info.getResidueRes();
		if(totalRes==null) throw new ServiceException(" Don't have the resources app ["+appId+"] in net-zone ["+netZoneId+"]. ");
		
		long residueCpuCount = residueRes.getCpuCount();
		long residueMemSize = residueRes.getMemSize();
		long residueDiskSize = residueRes.getDiskSize();
		if(residueCpuCount < 0) throw new ServiceException(" Application of CPU resources is not enough! ");
		if(residueMemSize < 0) throw new ServiceException(" Application of memory resources is not enough! ");
		if(residueDiskSize < 0) throw new ServiceException(" Application of storage resources is not enough! ");
		
		
		//验证参数
		CPcAppImgSvc cdt = new CPcAppImgSvc();
		cdt.setAppImgId(appImageId);
		cdt.setSvcTypes(new Integer[]{1,2,3}); 		//1=平台服务    2=外部服务    3=镜像服务    4=非服务镜像
		List<PcAppImgSvc> ls = appImgSvcDao.selectList(cdt, null);
		
		List<PcKvPair> params = new ArrayList<PcKvPair>();
		
		if(ls.size() > 0) {
			Long[] rltIds = new Long[ls.size()];
			
			for(int i=0; i<ls.size(); i++) {
				PcAppImgSvc svc = ls.get(i);
				rltIds[i] = svc.getId();
			}
			
			CPcKvPair kvcdt = new CPcKvPair();
			kvcdt.setTypeId(3);			//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
			kvcdt.setSourceIds(rltIds);
			List<PcKvPair> ps = kvPairDao.selectList(kvcdt, " SOURCE_ID, KV_KEY");
			if(ps.size() > 0) params.addAll(ps);
		}
		
		CPcKvPair kvcdt = new CPcKvPair();
		kvcdt.setTypeId(2);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		kvcdt.setSourceId(appImageId);
		List<PcKvPair> imgps = kvPairDao.selectList(kvcdt, " KV_KEY ");
		if(imgps.size() > 0) {
			for(int i=0; i<imgps.size(); i++) {
				PcKvPair kv = imgps.get(i);
				kv.setKeyAlias(kv.getKvKey());
				params.add(kv);
			}
		}
		DepUtil.verifyParamsRepeated(params, true);
		
		updateSetupNum(app.getId(), appVno.getId(), appImageId, AppImageSetup.FINISHED);
	}


	
	
	/**
	 * 1=镜像定义  2=开放服务  3=依赖服务  4=其它参数   9=完成
	 * @param appImageId
	 * @param num
	 */
	private void updateSetupNum(Long appId, Long appVnoId, Long appImageId, AppImageSetup setup) {
		PcAppImage record = new PcAppImage();
		record.setSetupNum(setup.getValue());
		appImageDao.updateById(record, appImageId);
		
		PcAppVersion appVno = new PcAppVersion();
		appVno.setSetupStatus(0);
		if(setup == AppImageSetup.FINISHED) {
			boolean allfed = appImageDao.isFinishAllAppImage(appId);
			if(allfed) {
				appVno.setSetupStatus(1);
			}
		}
		appVnoDao.updateById(appVno, appId);
	}
	
	
	
	
}
