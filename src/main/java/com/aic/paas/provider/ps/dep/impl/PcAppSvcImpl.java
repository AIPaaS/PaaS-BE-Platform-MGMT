package com.aic.paas.provider.ps.dep.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcApp;
import com.aic.paas.provider.ps.bean.CPcAppImage;
import com.aic.paas.provider.ps.bean.CPcAppMgr;
import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.CPcAppVersion;
import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.aic.paas.provider.ps.bean.PcAppMgr;
import com.aic.paas.provider.ps.bean.PcAppRes;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.aic.paas.provider.ps.bean.PcAppVersion;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppImageDao;
import com.aic.paas.provider.ps.db.PcAppMgrDao;
import com.aic.paas.provider.ps.db.PcAppResDao;
import com.aic.paas.provider.ps.db.PcAppTaskDao;
import com.aic.paas.provider.ps.db.PcAppVersionDao;
import com.aic.paas.provider.ps.dep.PcAppSvc;
import com.aic.paas.provider.ps.dep.bean.AppInfo;
import com.aic.paas.provider.ps.dep.bean.AppResInfo;
import com.aic.paas.provider.ps.dep.bean.AppZoneResInfo;
import com.aic.paas.provider.ps.res.PcAppResSvc;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcAppSvcImpl implements PcAppSvc {
	
	
	@Autowired
	PcAppDao appDao;
	
	@Autowired
	PcAppVersionDao appVnoDao;
	
	@Autowired
	PcAppMgrDao appMgrDao;
	
	@Autowired
	PcAppResSvc appResSvc;
	
	
	@Autowired
	PcAppResDao appResDao;
	
	
	@Autowired
	PcAppImageDao appImageDao;
	
	@Autowired
	PcAppTaskDao appTaskDao;
	
	

	@Override
	public Page<PcApp> queryPage(Integer pageNum, Integer pageSize, CPcApp cdt, String orders) {
		return appDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcApp> queryList(CPcApp cdt, String orders) {
		return appDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcApp queryById(Long id) {
		return appDao.selectById(id);
	}
	
	
	
	
	private List<AppInfo> fillMgrInfo(List<PcApp> ls) {
		List<AppInfo> infos = new ArrayList<AppInfo>();
		if(ls!=null && ls.size()>0) {
			Long[] pIds = new Long[ls.size()];
			
			for(int i=0; i<ls.size(); i++) {
				PcApp p = ls.get(i);
				AppInfo info = new AppInfo();
				info.setApp(p);
				infos.add(info);
				
				pIds[i] = p.getId();
			}
			
			
			CPcAppMgr cdt = new CPcAppMgr();
			cdt.setAppIds(pIds);
			List<PcAppMgr> pmgrs = appMgrDao.selectList(cdt, null);
			
			//key=appId, value=mgrIds
			Map<Long, List<Long>> mgrmap = new HashMap<Long, List<Long>>();
			
			for(int i=0; i<pmgrs.size(); i++) {
				PcAppMgr pm = pmgrs.get(i);
				Long pId = pm.getAppId();
				Long mgrId = pm.getUserId();
				
				List<Long> os = mgrmap.get(pId);
				if(os == null) {
					os = new ArrayList<Long>();
					mgrmap.put(pId, os);
				}
				os.add(mgrId);
			}
			
			CPcAppVersion appvnocdt = new CPcAppVersion();
			appvnocdt.setAppIds(pIds);
			List<PcAppVersion> appVnoList = appVnoDao.selectList(appvnocdt, " APP_ID, VERSION_NO DESC ");
			Map<Long, List<PcAppVersion>> appvnomap = BinaryUtils.toObjectGroupMap(appVnoList, "appId");
			
			
			for(int i=0; i<infos.size(); i++) {
				AppInfo info = infos.get(i);
				Long appId = info.getApp().getId();
				
				info.setAppVnos(appvnomap.get(appId));
				
				List<Long> mgrIds = mgrmap.get(appId);
				if(mgrIds != null) {
					info.setMgrIds(mgrIds.toArray(new Long[0]));
				}
			}
		}
		return infos;
	}
	
	

	@Override
	public Page<AppInfo> queryInfoPage(Integer pageNum, Integer pageSize, CPcApp cdt, String orders) {
		Page<PcApp> page = queryPage(pageNum, pageSize, cdt, orders);
		List<PcApp> ls = page.getData();
		List<AppInfo> infols = fillMgrInfo(ls);
		return new Page<AppInfo>(page.getPageNum(), page.getPageSize(), page.getTotalRows(), page.getTotalPages(), infols);
	}
	
	
	
	

	@Override
	public List<AppInfo> queryInfoList(CPcApp cdt, String orders) {
		List<PcApp> ls = queryList(cdt, orders);
		return fillMgrInfo(ls);
	}

	@Override
	public AppInfo queryInfoById(Long id) {
		PcApp p = queryById(id);
		if(p != null) {
			List<PcApp> ls = new ArrayList<PcApp>();
			ls.add(p);
			return fillMgrInfo(ls).get(0);
		}
		return null;
	}
	

	
	
	@Override
	public Page<PcApp> queryMgrPage(Integer pageNum, Integer pageSize, Long mgrId, CPcApp cdt, String orders) {
		BinaryUtils.checkEmpty(mgrId, "mgrId");
		return appDao.selectMgrPage(pageNum, pageSize, mgrId, cdt, orders);
	}
	
	
	

	@Override
	public List<PcApp> queryMgrList(Long mgrId, CPcApp cdt, String orders) {
		BinaryUtils.checkEmpty(mgrId, "mgrId");
		return appDao.selectMgrList(mgrId, cdt, orders);
	}
	
	
	@Override
	public Page<PcAppMgr> queryAppMgrPage(Integer pageNum, Integer pageSize, CPcAppMgr cdt, String orders) {
		return appMgrDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	
	@Override
	public List<PcAppMgr> queryAppMgrPage2(Integer pageNum, Integer pageSize, CPcAppMgr cdt, String orders) {
		return appMgrDao.selectList(pageNum, pageSize, cdt, orders);
	}


	@Override
	public List<PcAppMgr> queryAppMgrList(CPcAppMgr cdt, String orders) {
		return appMgrDao.selectList(cdt, orders);
	}
	
	
	@Override
	public Page<PcAppVersion> queryAppVersionPage(Integer pageNum, Integer pageSize, CPcAppVersion cdt, String orders) {
		return appVnoDao.selectPage(pageNum, pageSize, cdt, orders);
	}


	@Override
	public List<PcAppVersion> queryAppVersionPage2(Integer pageNum, Integer pageSize, CPcAppVersion cdt, String orders) {
		return appVnoDao.selectList(pageNum, pageSize, cdt, orders);
	}
	
	
	@Override
	public List<PcAppVersion> queryAppVersionList(CPcAppVersion cdt, String orders) {
		return appVnoDao.selectList(cdt, orders);
	}
	
	
	
	private List<AppResInfo> fillResInfo(List<PcApp> ls) {
		List<AppResInfo> infos = new ArrayList<AppResInfo>();
		if(ls!=null && ls.size()>0) {
			Map<Long, AppResInfo> infomap = new HashMap<Long, AppResInfo>();
			Long[] appIds = new Long[ls.size()];
			Long[] vnoIds = new Long[ls.size()];
			
			for(int i=0; i<ls.size(); i++) {
				PcApp p = ls.get(i);
				AppResInfo info = new AppResInfo();
				info.setApp(p);
				infos.add(info);
				
				appIds[i] = p.getId();
				vnoIds[i] = p.getVersionId();
				infomap.put(appIds[i], info);
			}
			
			List<PcAppRes> resls = appResDao.selectAppGroupList(appIds);
			for(int i=0; i<resls.size(); i++) {
				PcAppRes res = resls.get(i);
				infomap.get(res.getAppId()).setTotalRes(res);
			}
			
			List<PcAppImage> imgls = appImageDao.selectAppGroupList(appIds, vnoIds, null);
			for(int i=0; i<imgls.size(); i++) {
				PcAppImage img = imgls.get(i);
				infomap.get(img.getAppId()).setImageTotal(img);
			}
			
			List<PcAppTask> taskls = appTaskDao.selectLastList(appIds);
			Map<Long, PcAppTask> taskmap = BinaryUtils.toObjectMap(taskls, "appId");
			
			CPcAppVersion appvnocdt = new CPcAppVersion();
			appvnocdt.setAppIds(appIds);
			List<PcAppVersion> appVnoList = appVnoDao.selectList(appvnocdt, " APP_ID, VERSION_NO DESC ");
			Map<Long, List<PcAppVersion>> appvnomap = BinaryUtils.toObjectGroupMap(appVnoList, "appId");
			
			
			for(int i=0; i<infos.size(); i++) {
				AppResInfo info = infos.get(i);
				Long appId = info.getApp().getId();
				
				info.setResidueRes(getResidueRes(info.getTotalRes(), info.getImageTotal()));
				info.setLastTask(taskmap.get(appId));
				info.setAppVnos(appvnomap.get(appId));
			}
		}
		
		return infos;
	}
	
	
	
	
	@Override
	public Page<AppResInfo> queryResInfoPage(Integer pageNum, Integer pageSize, CPcApp cdt, String orders) {
		Page<PcApp> page = queryPage(pageNum, pageSize, cdt, orders);
		List<PcApp> ls = page.getData();
		List<AppResInfo> infols = fillResInfo(ls);
		return new Page<AppResInfo>(page.getPageNum(), page.getPageSize(), page.getTotalRows(), page.getTotalPages(), infols);
	}



	@Override
	public List<AppResInfo> queryResInfoList(CPcApp cdt, String orders) {
		List<PcApp> ls = queryList(cdt, orders);
		return fillResInfo(ls);
	}
	
	
	
	@Override
	public AppResInfo queryResInfoById(Long appId) {
		PcApp p = queryById(appId);
		if(p != null) {
			List<PcApp> ls = new ArrayList<PcApp>();
			ls.add(p);
			return fillResInfo(ls).get(0);
		}
		return null;
	}



	@Override
	public Page<AppResInfo> queryMgrResInfoPage(Integer pageNum, Integer pageSize, Long mgrId, CPcApp cdt, String orders) {
		Page<PcApp> page = queryMgrPage(pageNum, pageSize, mgrId, cdt, orders);
		List<PcApp> ls = page.getData();
		List<AppResInfo> infols = fillResInfo(ls);
		return new Page<AppResInfo>(page.getPageNum(), page.getPageSize(), page.getTotalRows(), page.getTotalPages(), infols);
	}



	@Override
	public List<AppResInfo> queryMgrResInfoList(Long mgrId, CPcApp cdt, String orders) {
		List<PcApp> ls = queryMgrList(mgrId, cdt, orders);
		return fillResInfo(ls);
	}
	
	
	
	@Override
	public List<AppZoneResInfo> queryAppNetZoneResInfo(Long appId, Long appVnoId, Long netZoneId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(appVnoId, "appVnoId");
		
		CPcAppRes rescdt = new CPcAppRes();
		rescdt.setAppId(appId);
		rescdt.setNetZoneId(netZoneId);
		List<PcAppRes> resls = appResDao.selectList(rescdt, "NET_ZONE_ID");
		
		List<AppZoneResInfo> infos = new ArrayList<AppZoneResInfo>();
		
		if(resls.size() > 0) {
			for(int i=0; i<resls.size(); i++) {
				PcAppRes res = resls.get(i);
				AppZoneResInfo info = new AppZoneResInfo();
				info.setTotalRes(res);
				infos.add(info);
			}
			
			CPcAppImage appimgcdt = new CPcAppImage();
			appimgcdt.setAppId(appId);
			appimgcdt.setAppVnoId(appVnoId);
			appimgcdt.setNetZoneId(netZoneId);
			List<PcAppImage> imageList = appImageDao.selectList(appimgcdt, null);
			if(imageList.size() > 0) {
				List<PcAppImage> imageGroups = appImageDao.selectAppNetZoneGroupList(appId, appVnoId, netZoneId);
				
				//key=netZoneId
				Map<Long, List<PcAppImage>> imageListMap = BinaryUtils.toObjectGroupMap(imageList, "netZoneId");
				Map<Long, PcAppImage> imageGroupMap = BinaryUtils.toObjectMap(imageGroups, "netZoneId");
				
				for(int i=0; i<infos.size(); i++) {
					AppZoneResInfo info = infos.get(i);
					PcAppRes totalRes = info.getTotalRes();
					Long zoneId = totalRes.getNetZoneId();
					PcAppImage imageTotal = imageGroupMap.get(zoneId);
					info.setImageTotal(imageTotal);
					info.setImageList(imageListMap.get(zoneId));				
				}
			}
			
			for(int i=0; i<infos.size(); i++) {
				AppZoneResInfo info = infos.get(i);
				info.setResidueRes(getResidueRes(info.getTotalRes(), info.getImageTotal()));
			}
		}
		
		return infos;
	}

	
	
	
	private PcAppRes getResidueRes(PcAppRes appRes, PcAppImage imageRes) {
		PcAppRes res = new PcAppRes();
		if(appRes == null) {
			res.setCpuCount(0l);
			res.setMemSize(0l);
			res.setDiskSize(0l);
		}else {
			if(imageRes == null) {
				res.setCpuCount(appRes.getCpuCount());
				res.setMemSize(appRes.getMemSize());
				res.setDiskSize(appRes.getDiskSize());
			}else {
				res.setCpuCount(appRes.getCpuCount()-imageRes.getCpuCount());
				res.setMemSize(appRes.getMemSize()-imageRes.getMemSize());
				res.setDiskSize(appRes.getDiskSize()-imageRes.getDiskSize());
			}
		}
		return res;
	}
	
	
	
	@Override
	public PcAppRes queryAppResidueRes(Long appId, Long appVnoId, Long netZoneId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		
		CPcAppRes rescdt = new CPcAppRes();
		rescdt.setAppId(appId);
		rescdt.setNetZoneId(netZoneId);
		List<PcAppRes> resls = appResDao.selectList(rescdt, "NET_ZONE_ID");
		if(resls.size() == 0) {
			return getResidueRes(null, null);
		}
		
		List<PcAppImage> imageGroups = appImageDao.selectAppNetZoneGroupList(appId, appVnoId, netZoneId);
		if(imageGroups.size() == 0) {
			return getResidueRes(resls.get(0), null);
		}
		return getResidueRes(resls.get(0), imageGroups.get(0));
	}
	
	
	
	
	@Override
	public Long saveOrUpdate(PcApp record) {
		BinaryUtils.checkEmpty(record, "record");
		BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
		
		boolean isadd = record.getId() == null;
		if(isadd) {
			BinaryUtils.checkEmpty(record.getAppCode(), "record.appCode");
			BinaryUtils.checkEmpty(record.getAppName(), "record.appName");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getVersionNo(), "record.versionNo");
			if(record.getStatus() == null) record.setStatus(1); 	//1=未部署  2=运行中  3=停止
		}else {
			if(record.getAppCode() != null) BinaryUtils.checkEmpty(record.getAppCode(), "record.appCode");
			if(record.getAppName() != null) BinaryUtils.checkEmpty(record.getAppName(), "record.appName");
			if(record.getDataCenterId() != null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId() != null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getVersionNo() != null) BinaryUtils.checkEmpty(record.getVersionNo(), "record.versionNo");
			record.setStatus(null);
		}
		
		Long id = record.getId();
		if(record.getAppCode() != null) {
			String code = record.getAppCode().trim();
			record.setAppCode(code);
			
			CPcApp cdt = new CPcApp();
			cdt.setMntId(record.getMntId());
			cdt.setAppCodeEqual(code);
			List<PcApp> ls = appDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		
		
		boolean newVersion = false;
		if(isadd) {
			record.setVersionNo(record.getVersionNo().trim());
			id = appDao.insert(record);
			newVersion = true;
		}else {
			PcApp old = appDao.selectById(id);
			if(old == null) throw new ServiceException(" app ["+id+"] not exists! ");
//			int status = old.getStatus();
//			if(status == 2) {		//1=未部署  2=运行中  3=停止
//				if((record.getAppCode()!=null && !record.getAppCode().equals(old.getAppCode()))
//						|| (record.getAppName()!=null && !record.getAppCode().equals(old.getAppName()))
//						|| (record.getDataCenterId()!=null && !record.getDataCenterId().equals(old.getDataCenterId()))
//						|| (record.getResCenterId()!=null && !record.getResCenterId().equals(old.getResCenterId()))) {
//					throw new ServiceException(" Application is running, cannot be modified! ");
//				}
//			}
			
			if(record.getResCenterId()!=null && !record.getResCenterId().equals(old.getResCenterId())) {
				CPcAppImage cdt = new CPcAppImage();
				cdt.setAppId(id);
				long count = appImageDao.selectCount(cdt);
				
				if(count > 0) {
					throw new ServiceException(" The application ["+old.getAppCode()+"] has containers, application's resource center cannot be modified! ");
				}
			}
			
			
			if(record.getVersionNo() != null) {
				String verno = record.getVersionNo();
				record.setVersionNo(verno=verno.trim());
				
				//如果版本号与之前不一致
				if(!verno.equals(old.getVersionNo())) {
					CPcAppVersion cdt = new CPcAppVersion();
					cdt.setAppId(id);
					cdt.setVersionNoEqual(verno);
					List<PcAppVersion> ls = appVnoDao.selectList(cdt, null);
					if(ls.size() > 0) {
						throw new ServiceException(" is exists version '"+verno+"'! ");
					}
					
					newVersion = true;
				}
			}
			
			appDao.updateById(record, id);
		}
		
		
		//需要添加版本
		if(newVersion) {
			PcAppVersion appVno = new PcAppVersion();
			appVno.setMntId(record.getMntId());
			appVno.setAppId(id);
			appVno.setVersionNo(record.getVersionNo());
			appVno.setSetupStatus(0);		//1=已完成    0=未完成
			appVno.setStatus(1);			//1=未部署  2=运行中  3=停止
			Long versionId = appVnoDao.insert(appVno);
			
			PcApp upapp = new PcApp();
			upapp.setVersionId(versionId);
			appDao.updateById(upapp, id);
		}
		
		return id;
	}
	
	
	
	

	@Override
	public int removeById(Long id) {
		PcApp app = appDao.selectById(id);
		if(app == null) return 0;
		
		//删除应用管理员
		CPcAppMgr cdt = new CPcAppMgr();
		cdt.setAppId(id);
		appMgrDao.deleteByCdt(cdt);
		
		//删除释放资源
		appResSvc.removeAllResByApp(id);
		
		return appDao.deleteById(id);
	}

	
	
	@Override
	public void setAppMgrs(Long appId, Long[] mgrIds) {
		BinaryUtils.checkEmpty(appId, "appId");
		
		//删除产品对应管理员
		CPcAppMgr oprolecdt = new CPcAppMgr();
		oprolecdt.setAppId(appId);
		appMgrDao.deleteByCdt(oprolecdt);
		
		if(mgrIds!=null && mgrIds.length>0) {
			List<PcAppMgr> records = new ArrayList<PcAppMgr>();
			for(int i=0; i<mgrIds.length; i++) {
				PcAppMgr r = new PcAppMgr();
				r.setAppId(appId);
				r.setUserId(mgrIds[i]);
				records.add(r);
			}
			
			appMgrDao.insertBatch(records);
		}
	}



	

}
