package com.aic.paas.provider.ps.res.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.comm.util.SystemUtil;
import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.CPsMntResFlow;
import com.aic.paas.provider.ps.bean.CPsResCenterRes;
import com.aic.paas.provider.ps.bean.PsMntRes;
import com.aic.paas.provider.ps.bean.PsMntResApply;
import com.aic.paas.provider.ps.bean.PsMntResFlow;
import com.aic.paas.provider.ps.bean.PsResCenterRes;
import com.aic.paas.provider.ps.db.PsMntResApplyDao;
import com.aic.paas.provider.ps.db.PsMntResDao;
import com.aic.paas.provider.ps.db.PsMntResFlowDao;
import com.aic.paas.provider.ps.res.PsMntResSvc;
import com.aic.paas.provider.ps.res.PsResCenterResSvc;
import com.aic.paas.provider.ps.res.bean.ResItems;
import com.aic.paas.provider.ps.res.bean.WsMerchent;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PsMntResSvcImpl implements PsMntResSvc {
	
		
	@Autowired
	PsMntResDao mntResDao;
	

	@Autowired
	PsMntResFlowDao mntResFlowDao;
	
	
	@Autowired
	PsMntResApplyDao mntResFlowApplyDao;
	
	
	@Autowired
	PsResCenterResSvc resCenterResSvc;
	
	

	
	@Override
	public Page<PsMntRes> queryResPage(Integer pageNum, Integer pageSize, CPsMntRes cdt, String orders) {
		return mntResDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	
	@Override
	public List<PsMntRes> queryResList(CPsMntRes cdt, String orders) {
		return mntResDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PsMntRes queryResById(Long id) {
		return mntResDao.selectById(id);
	}
	
	

	@Override
	public Page<PsMntResFlow> queryFlowPage(Integer pageNum, Integer pageSize, CPsMntResFlow cdt, String orders) {
		return mntResFlowDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PsMntResFlow> queryFlowList(CPsMntResFlow cdt, String orders) {
		return mntResFlowDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PsMntResFlow queryFlowById(Long id) {
		return mntResFlowDao.selectById(id);
	}
	
	
	
	@Override
	public PsMntRes createRes(Long mntId, Long dataCenterId, Long resCenterId, Long netZoneId) {
		BinaryUtils.checkEmpty(mntId, "mntId");
		BinaryUtils.checkEmpty(dataCenterId, "dataCenterId");
		BinaryUtils.checkEmpty(resCenterId, "resCenterId");
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");

		CPsMntRes cdt = new CPsMntRes();
		cdt.setMntId(mntId);
		cdt.setNetZoneId(netZoneId);
		List<PsMntRes> ls = mntResDao.selectList(cdt, null);
		if(ls.size() > 0) {
			throw new ServiceException(" the mnt-res is exists by mnt:'"+mntId+"', netZoneId:'"+netZoneId+"'! ");
		}
		
		PsMntRes res = new PsMntRes();
		res.setMntId(mntId);
		res.setDataCenterId(dataCenterId);
		res.setResCenterId(resCenterId);
		res.setNetZoneId(netZoneId);
		res.setTotalCpuCount(0l);
		res.setTotalMemSize(0l);
		res.setTotalDiskSize(0l);
		res.setCpuCount(0l);
		res.setMemSize(0l);
		res.setDiskSize(0l);
		Long resId = mntResDao.insert(res);
		res.setId(resId);
				
		return res;
	}
	
	
	@Override
	public void removeResByMnt(Long mntId, Long netZoneId, WsMerchent mnt) {
		BinaryUtils.checkEmpty(mntId, "mntId");
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		removeRes(mntId, netZoneId, mnt);
	}
	
	
	@Override
	public void removeAllResByMnt(Long mntId, WsMerchent mnt) {
		BinaryUtils.checkEmpty(mntId, "appId");
		removeRes(mntId, null, mnt);
	}
	
	
	
	
	private void removeRes(Long mntId, Long netZoneId, WsMerchent mnt) {
//		WsMerchent mnt = merchentDao.selectById(mntId);
		if(mnt == null) {
			throw new ServiceException(" not found mnt by id '"+mntId+"'! ");
		}
		
		CPsMntRes cdt = new CPsMntRes();
		cdt.setMntId(mntId);
		cdt.setNetZoneId(netZoneId);
		List<PsMntRes> ls = mntResDao.selectList(cdt, null);
		for(int i=0; i<ls.size(); i++) {
			PsMntRes res = ls.get(i);
			releaseRes(mnt, res.getId());
		}
	}
	
	
	private void releaseRes(WsMerchent mnt, Long resId) {
		PsMntRes releaseRes = mntResDao.releaseRes(resId);
		
		if(releaseRes != null) {
			//查询租户资源
			CPsResCenterRes rcrescdt = new CPsResCenterRes();
			rcrescdt.setDataCenterId(releaseRes.getDataCenterId());
			rcrescdt.setResCenterId(releaseRes.getResCenterId());
			rcrescdt.setNetZoneId(releaseRes.getNetZoneId());
			List<PsResCenterRes> rcresls = resCenterResSvc.queryResList(rcrescdt, null);
			
			if(rcresls.size() == 0) {
				throw new ServiceException(" The mnt does not have the resources at current resource center! ");
			}
			PsResCenterRes rcRes = rcresls.get(0);
			
			//返还资源中心资源
			ResItems items = new ResItems();
			items.setCpuCount(releaseRes.getCpuCount());
			items.setMemSize(releaseRes.getMemSize());
			items.setDiskSize(releaseRes.getDiskSize());
			
			User user = SystemUtil.getLoginUser();
			String upor = user.getUserName();
			
			int rc = resCenterResSvc.addResByNetZoneId(rcRes.getNetZoneId(), items, upor, "租户["+mnt.getId()+"]["+mnt.getMntCode()+"]["+mnt.getMntName()+"]释放占用资源，返还资源中心资源。");
			if(rc == 0) {
				throw new ServiceException(" Deducting the resCenter resources failed, please check resCenter resource adequacy! ");
			}
			
			PsMntResFlow flow = new PsMntResFlow();
			flow.setMntId(releaseRes.getMntId());
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
			flow.setUpDesc("租户["+mnt.getId()+"]["+mnt.getMntCode()+"]["+mnt.getMntName()+"]释放占用资源。");
			
			mntResFlowDao.insert(flow);
		}
	}
	
	
	
	

	@Override
	public Integer addTotalRes(Long id, ResItems items) {
		return updateTotalRes(true, id, items);
	}
	
	

	@Override
	public Integer reduceTotalRes(Long id, ResItems items) {
		return updateTotalRes(false, id, items);
	}
	
	
	
	private Integer updateTotalRes(boolean add, Long id, ResItems items) {
		BinaryUtils.checkEmpty(id, "id");
		BinaryUtils.checkEmpty(items, "items");
		
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
		
		return mntResDao.increTotalResById(id, cpuCount, memSize, diskSize);
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
		
		PsMntRes before = mntResDao.selectById(id);
		if(before == null) throw new ServiceException(" not found MntRes by id '"+id+"'! "); 
		
		if(applyId != null) {
			PsMntResApply apply = mntResFlowApplyDao.selectById(applyId);
			if(apply == null) throw new ServiceException(" not found MntResApply by id '"+applyId+"'! "); 
		}
		
		int count = mntResDao.increResById(before.getId(), cpuCount, memSize, diskSize, validity);
		if(count == 0) return 0;
		
		PsMntRes after = mntResDao.selectById(before.getId());
		
		
		PsMntResFlow flow = new PsMntResFlow();
		flow.setMntId(before.getMntId());
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
		
		mntResFlowDao.insert(flow);
		
		return count;
	}

	
	
	
	
	
}
