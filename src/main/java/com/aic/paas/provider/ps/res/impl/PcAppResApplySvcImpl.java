package com.aic.paas.provider.ps.res.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcApp;
import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.CPcAppRes;
import com.aic.paas.provider.ps.bean.CPcAppResApply;
import com.aic.paas.provider.ps.bean.CPsMntRes;
import com.aic.paas.provider.ps.bean.PcAppRes;
import com.aic.paas.provider.ps.bean.PcAppResApply;
import com.aic.paas.provider.ps.bean.PsMntRes;
import com.aic.paas.comm.util.CommUtil;
import com.aic.paas.comm.util.SystemUtil;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppResApplyDao;
import com.aic.paas.provider.ps.db.PcAppResDao;
import com.aic.paas.provider.ps.res.PcAppResApplySvc;
import com.aic.paas.provider.ps.res.PcAppResSvc;
import com.aic.paas.provider.ps.res.PsMntResSvc;
import com.aic.paas.provider.ps.res.bean.PcAppResApplyInfo;
import com.aic.paas.provider.ps.res.bean.ResItems;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcAppResApplySvcImpl implements PcAppResApplySvc {
	
	
	
	@Autowired
	PcAppResApplyDao appResApplyDao;
	
	
	@Autowired
	PsMntResSvc mntResSvc;
	
	
	@Autowired
	PcAppResDao appResDao;
	
	
	@Autowired
	PcAppResSvc appResSvc;

	@Autowired
	PcAppDao appDao;
	
	
	
	@Override
	public Page<PcAppResApply> queryPage(Integer pageNum, Integer pageSize, CPcAppResApply cdt, String orders) {
		return appResApplyDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	
	

	@Override
	public List<PcAppResApply> queryList(CPcAppResApply cdt, String orders) {
		return appResApplyDao.selectList(cdt, orders);
	}

	
	
	@Override
	public PcAppResApply queryById(Long id) {
		return appResApplyDao.selectById(id);
	}
	
	
	
	private List<PcAppResApplyInfo> fillApplyInfo(List<PcAppResApply> ls) {
		List<PcAppResApplyInfo> infos = new ArrayList<PcAppResApplyInfo>();
		if(ls==null || ls.size()>0) {
			Set<Long> appIds = new HashSet<Long>();			
			for(int i=0; i<ls.size(); i++) {
				PcAppResApply apply = ls.get(i);
				PcAppResApplyInfo info = new PcAppResApplyInfo();
				info.setApply(apply);
				infos.add(info);
				
				appIds.add(apply.getAppId());
			}
			
			CPcApp appcdt = new CPcApp();
			appcdt.setIds(appIds.toArray(new Long[0]));
			List<PcApp> appls = appDao.selectList(appcdt, null);
			Map<Long, PcApp> appmap = BinaryUtils.toObjectMap(appls, "id");
			
			for(int i=0; i<infos.size(); i++) {
				PcAppResApplyInfo info = infos.get(i);
				info.setApp(appmap.get(info.getApply().getAppId()));
			}
		}
		return infos;
	}
	

	@Override
	public Page<PcAppResApplyInfo> queryInfoPage(Integer pageNum, Integer pageSize, CPcAppResApply cdt, String orders) {
		Page<PcAppResApply> page = queryPage(pageNum, pageSize, cdt, orders);
		List<PcAppResApply> ls = page.getData();
		List<PcAppResApplyInfo> infols = fillApplyInfo(ls);
		return new Page<PcAppResApplyInfo>(page.getPageNum(), page.getPageSize(), page.getTotalRows(), page.getTotalPages(), infols);
	}




	@Override
	public List<PcAppResApplyInfo> queryInfoList(CPcAppResApply cdt, String orders) {
		List<PcAppResApply> ls = queryList(cdt, orders);
		return fillApplyInfo(ls);
	}




	@Override
	public PcAppResApplyInfo queryInfoById(Long id) {
		PcAppResApply p = queryById(id);
		if(p != null) {
			List<PcAppResApply> ls = new ArrayList<PcAppResApply>();
			ls.add(p);
			return fillApplyInfo(ls).get(0);
		}
		return null;
	}
	
	
	

	
	
	@Override
	public Long saveOrUpdate(PcAppResApply record) {
		BinaryUtils.checkEmpty(record, "record");
		
		Long id = record.getId();
		boolean add = id == null;
		if(add) {
			BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoonId");
			BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
		}else {
			if(record.getMntId()!=null) BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			if(record.getAppId()!=null) BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
			if(record.getDataCenterId()!=null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId()!=null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getNetZoneId()!=null) BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoonId");
			if(record.getCpuCount()!=null) BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			if(record.getMemSize()!=null) BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			if(record.getDiskSize()!=null) BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
			record.setCode(null);
		}
				
		if(record.getApplyTime() == null) {
			record.setApplyTime(BinaryUtils.getNumberDateTime());
		}
		if(record.getAppliorId() == null) {
			User user = SystemUtil.getLoginUser();
			record.setAppliorId(user.getId());
			record.setAppliorName(user.getUserName());
		}
		record.setStatus(0);		//0=待审批  1=审批通过  2=审批退回    9=撤销
		
		if(add) {
			id = appResApplyDao.save(record);
			PcAppResApply up = new PcAppResApply();
			String strid = CommUtil.fillPrefixZero(id, 8);
			if(strid.length() > 8) strid = strid.substring(0, 8);
			String code = BinaryUtils.getNumberDate() + strid;
			up.setCode(Long.valueOf(code));
			appResApplyDao.updateById(up, id);
		}else {
			CPcAppResApply cdt = new CPcAppResApply();
			cdt.setId(id);
			cdt.setStatus(0);
			appResApplyDao.updateByCdt(record, cdt);
		}
		
		return id;
	}
	
	
	
	
	

	@Override
	public void cannelApply(Long id) {
		BinaryUtils.checkEmpty(id, "id");
		
		//0=待审批  1=审批通过  2=审批退回    9=撤销
		int count = appResApplyDao.checkResApply(id, 9, null, null, null);
		if(count == 0) {
			throw new ServiceException(" Apply order does not exist or the status cannot be cannel! ");
		}
	}

	
	
	
	
	@Override
	public void checkApply(Long id, Boolean pass, String checkDesc) {
		BinaryUtils.checkEmpty(id, "id");
		BinaryUtils.checkEmpty(pass, "pass");
		BinaryUtils.checkEmpty(checkDesc, "checkDesc");
		
		User user = SystemUtil.getLoginUser();
		Long checkerId = user.getId();
		String checkName = user.getUserName();
		
		PcAppResApply apply = appResApplyDao.selectById(id);
		if(apply == null) throw new ServiceException(" not found AppResApply by id '"+id+"'! ");
		
		//如果审批通过则
		if(pass.booleanValue()) {
			PcApp app = appDao.selectById(apply.getAppId());
			if(app == null) {
				throw new ServiceException(" not found app by apply '"+apply.getCode()+"'! ");
			}
			
			//查询租户资源
			CPsMntRes mntrescdt = new CPsMntRes();
			mntrescdt.setMntId(apply.getMntId());
			mntrescdt.setDataCenterId(apply.getDataCenterId());
			mntrescdt.setResCenterId(apply.getResCenterId());
			mntrescdt.setNetZoneId(apply.getNetZoneId());
			List<PsMntRes> mntresls = mntResSvc.queryResList(mntrescdt, null);
			if(mntresls.size() == 0) {
				throw new ServiceException(" The mnt does not have the resources at current resource center! ");
			}
			PsMntRes mntres = mntresls.get(0);
			long mntCpuCount = mntres.getCpuCount();
			long mntMemSize = mntres.getMemSize();
			long mntDiskSize = mntres.getDiskSize();
			if(mntres.getCpuCount() > mntCpuCount) throw new ServiceException(" The current mnt resource 'CPU' is not enough! ");
			if(mntres.getMemSize() > mntMemSize) throw new ServiceException(" The current mnt resource 'memory' is not enough! ");
			if(mntres.getDiskSize() > mntDiskSize) throw new ServiceException(" The current mnt resource 'storage' is not enough! ");
			
			ResItems items = new ResItems();
			items.setCpuCount(apply.getCpuCount());
			items.setMemSize(apply.getMemSize());
			items.setDiskSize(apply.getDiskSize());
			
			//扣减租户资源
			int rc = mntResSvc.reduceRes(mntres.getId(), items, checkName, "应用["+app.getAppCode()+"]["+app.getAppName()+"]资源申请单被审批通过，扣减租户资源。", null, true);
			if(rc == 0) {
				throw new ServiceException(" Deducting the mnt resources failed, please check mnt resource adequacy! ");
			}
			
			//补充应用资源
			CPcAppRes apprescdt = new CPcAppRes();
			apprescdt.setMntId(apply.getMntId());
			apprescdt.setAppId(apply.getAppId());
			apprescdt.setNetZoneId(apply.getNetZoneId());
			List<PcAppRes> appresls = appResDao.selectList(apprescdt, null);
			if(appresls.size() == 0) {
				//throw new ServiceException(" not found appRes by appId '"+apply.getAppId()+"'! ");
				//如果应用资源不存在则添加 (不支持幂等操作)
				PcAppRes res = appResSvc.createRes(apply.getAppId(), apply.getMntId(), apply.getDataCenterId(), apply.getResCenterId(), apply.getNetZoneId());
				appresls.add(res);
			}
			if(appresls.size() > 1) throw new ServiceException(" has multiple AppRes by applyId '"+id+"'! ");
			PcAppRes res = appresls.get(0);			
			
			//添加资源
			rc = appResSvc.addRes(res.getId(), items, checkName, "应用["+app.getAppCode()+"]["+app.getAppName()+"]资源申请单被审批通过，增加应用资源。", id);
			if(rc == 0) {
				throw new ServiceException(" Deducting the app resources failed, please check app resource adequacy! ");
			}
		}
		
		int count = appResApplyDao.checkResApply(id, pass.booleanValue()?1:2, checkerId, checkName, checkDesc);
		if(count == 0) {
			throw new ServiceException(" AppResApply's status cannot be check! ");
		}
	}




	
	

}
