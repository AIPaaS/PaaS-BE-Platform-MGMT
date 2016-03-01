package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.CPcAppResFlow;
import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.PcAppRes;
import com.aic.paas.provider.ps.bean.PcAppResApply;
import com.aic.paas.provider.ps.bean.PcAppResFlow;
import com.aic.paas.provider.ps.bean.PsMntRes;
import com.aic.paas.comm.util.SystemUtil;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppResApplyDao;
import com.aic.paas.provider.ps.db.PcAppResDao;
import com.aic.paas.provider.ps.db.PcAppResFlowDao;
import com.aic.paas.provider.ps.res.PcAppResSvc;
import com.aic.paas.provider.ps.res.PsMntResSvc;
import com.aic.paas.provider.ps.res.bean.ResItems;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcAppResSvcImpl implements PcAppResSvc {
	
	@Autowired
	PcAppDao appDao;
	
	
	@Autowired
	PcAppResDao appResDao;
	

	@Autowired
	PcAppResFlowDao appResFlowDao;
	
	
	@Autowired
	PcAppResApplyDao appResFlowApplyDao;
	
	
	@Autowired
	PsMntResSvc mntResSvc;
	
	

	@Override
	public Page<PcAppRes> queryResPage(Integer pageNum, Integer pageSize, CPcAppRes cdt, String orders) {
		return appResDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcAppRes> queryResList(CPcAppRes cdt, String orders) {
		return appResDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcAppRes queryResById(Long id) {
		return appResDao.selectById(id);
	}
	
	
	

	@Override
	public Page<PcAppResFlow> queryFlowPage(Integer pageNum, Integer pageSize, CPcAppResFlow cdt, String orders) {
		return appResFlowDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcAppResFlow> queryFlowList(CPcAppResFlow cdt, String orders) {
		return appResFlowDao.selectList(cdt, orders);
	}

	
	
	
	@Override
	public PcAppResFlow queryFlowById(Long id) {
		return appResFlowDao.selectById(id);
	}

	
	
	@Override
	public PcAppRes createRes(Long appId, Long mntId, Long dataCenterId, Long resCenterId, Long netZoneId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(mntId, "mntId");
		BinaryUtils.checkEmpty(dataCenterId, "dataCenterId");
		BinaryUtils.checkEmpty(resCenterId, "resCenterId");
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");

		CPcAppRes cdt = new CPcAppRes();
		cdt.setAppId(appId);
		cdt.setNetZoneId(netZoneId);
		List<PcAppRes> ls = appResDao.selectList(cdt, null);
		if(ls.size() > 0) {
			throw new ServiceException(" the app-res is exists by app:'"+appId+"', netZoneId:'"+netZoneId+"'! ");
		}
		
		PcAppRes res = new PcAppRes();
		res.setMntId(mntId);
		res.setAppId(appId);
		res.setDataCenterId(dataCenterId);
		res.setResCenterId(resCenterId);
		res.setNetZoneId(netZoneId);
		res.setCpuCount(0l);
		res.setMemSize(0l);
		res.setDiskSize(0l);
		
		Long id = appResDao.insert(res);
		res.setId(id);
		
		return res;
	}
	
	
	@Override
	public void removeResByApp(Long appId, Long netZoneId) {
		BinaryUtils.checkEmpty(appId, "appId");
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		removeRes(appId, netZoneId);
	}

	
	@Override
	public void removeAllResByApp(Long appId) {
		BinaryUtils.checkEmpty(appId, "appId");
		removeRes(appId, null);
	}


	
	private void removeRes(Long appId, Long netZoneId) {
		PcApp app = appDao.selectById(appId);
		if(app == null) {
			throw new ServiceException(" not found app by id '"+appId+"'! ");
		}
		
		CPcAppRes cdt = new CPcAppRes();
		cdt.setAppId(appId);
		cdt.setNetZoneId(netZoneId);
		List<PcAppRes> ls = appResDao.selectList(cdt, null);
		for(int i=0; i<ls.size(); i++) {
			PcAppRes res = ls.get(i);
			releaseRes(app, res.getId());
		}
	}
	
	
	private void releaseRes(PcApp app, Long resId) {
		PcAppRes releaseRes = appResDao.releaseRes(resId);
		
		if(releaseRes != null) {
			//查询租户资源
			CPsMntRes mntrescdt = new CPsMntRes();
			mntrescdt.setMntId(releaseRes.getMntId());
			mntrescdt.setDataCenterId(releaseRes.getDataCenterId());
			mntrescdt.setResCenterId(releaseRes.getResCenterId());
			mntrescdt.setNetZoneId(releaseRes.getNetZoneId());
			List<PsMntRes> mntresls = mntResSvc.queryResList(mntrescdt, null);
			if(mntresls.size() == 0) {
				throw new ServiceException(" The mnt does not have the resources at current resource center! ");
			}
			PsMntRes mntRes = mntresls.get(0);
			
			//返还租户资源
			ResItems items = new ResItems();
			items.setCpuCount(releaseRes.getCpuCount());
			items.setMemSize(releaseRes.getMemSize());
			items.setDiskSize(releaseRes.getDiskSize());
			
			User user = SystemUtil.getLoginUser();
			String upor = user.getUserName();
			
			int rc = mntResSvc.addRes(mntRes.getId(), items, upor, "应用["+app.getId()+"]["+app.getAppCode()+"]["+app.getAppName()+"]释放占用资源，返还租户资源。", null);
			if(rc == 0) {
				throw new ServiceException(" Deducting the mnt resources failed, please check mnt resource adequacy! ");
			}
			
			
			PcAppResFlow flow = new PcAppResFlow();
			flow.setMntId(releaseRes.getMntId());
			flow.setAppId(releaseRes.getAppId());
			flow.setResId(releaseRes.getId());
			flow.setUpTime(BinaryUtils.getNumberDateTime());
			flow.setUpType(2); 		//1=增加    2=扣减
			
			flow.setResApplyId(null);
			flow.setDataCenterId(releaseRes.getDataCenterId());
			flow.setResCenterId(releaseRes.getResCenterId());
			flow.setNetZoneId(releaseRes.getNetZoneId());
			
			flow.setBeforeCpuCount(releaseRes.getCpuCount());
			flow.setBeforeMemSize(releaseRes.getMemSize());
			flow.setBeforeDiskSize(releaseRes.getDiskSize());
			flow.setAfterCpuCount(0l);
			flow.setAfterMemSize(0l);
			flow.setAfterDiskSize(0l);
			flow.setUpor(upor);
			flow.setUpDesc("应用["+app.getId()+"]["+app.getAppCode()+"]["+app.getAppName()+"]释放占用资源。");
			
			appResFlowDao.insert(flow);
		}
	}
	
	
	
	
	
	@Override
	public Integer addRes(Long id, ResItems items, String upor, String remark, Long applyId) {
		return updateRes(true, id, items, upor, remark, applyId, false);
	}

	
	
	@Override
	public Integer reduceRes(Long id, ResItems items, String upor, String remark, Long applyId, Boolean validity) {
		return updateRes(false, id, items, upor, remark, applyId, !Boolean.FALSE.equals(validity));
	}
	
	
	
	
	private Integer updateRes(boolean add, Long id, ResItems items, String upor, String remark, Long applyId, Boolean validity) {
		BinaryUtils.checkEmpty(id, "id");
		BinaryUtils.checkEmpty(items, "items");
		BinaryUtils.checkEmpty(upor, "upor");
		BinaryUtils.checkEmpty(remark, "remark");
		
		Long cpuCount = items.getCpuCount();
		Long memSize = items.getMemSize();
		Long diskSize = items.getDiskSize();
		
		if(cpuCount==null && memSize==null && diskSize==null) {
			throw new ServiceException(" No effective update res's item!  ");
		}
		
		if(cpuCount != null) {
			cpuCount = Math.abs(cpuCount);
			if(!add) cpuCount = -cpuCount;
		}
		if(memSize != null) {
			memSize = Math.abs(memSize);
			if(!add) memSize = -memSize;
		}
		if(diskSize != null) {
			diskSize = Math.abs(diskSize);
			if(!add) diskSize = -diskSize;
		}
		
		PcAppRes before = appResDao.selectById(id);
		if(before == null) throw new ServiceException(" not found AppRes by id '"+id+"'! "); 
		
		if(applyId != null) {
			PcAppResApply apply = appResFlowApplyDao.selectById(applyId);
			if(apply == null) throw new ServiceException(" not found AppResApply by id '"+applyId+"'! "); 
		}
		
		int count = appResDao.increResById(before.getId(), cpuCount, memSize, diskSize, validity);
		if(count == 0) return 0;
		
		PcAppRes after = appResDao.selectById(before.getId());
		
		PcAppResFlow flow = new PcAppResFlow();
		flow.setMntId(before.getMntId());
		flow.setAppId(before.getAppId());
		flow.setResId(before.getId());
		flow.setUpTime(BinaryUtils.getNumberDateTime());
		flow.setUpType(add?1:2); 		//1=增加    2=扣减
		
		flow.setResApplyId(applyId);
		flow.setDataCenterId(before.getDataCenterId());
		flow.setResCenterId(before.getResCenterId());
		flow.setNetZoneId(before.getNetZoneId());
		
		flow.setBeforeCpuCount(before.getCpuCount());
		flow.setBeforeMemSize(before.getMemSize());
		flow.setBeforeDiskSize(before.getDiskSize());
		flow.setAfterCpuCount(after.getCpuCount());
		flow.setAfterMemSize(after.getMemSize());
		flow.setAfterDiskSize(after.getDiskSize());
		flow.setUpor(upor);
		flow.setUpDesc(remark);
		
		appResFlowDao.insert(flow);
		
		return count;
	}



	
	
	

}
