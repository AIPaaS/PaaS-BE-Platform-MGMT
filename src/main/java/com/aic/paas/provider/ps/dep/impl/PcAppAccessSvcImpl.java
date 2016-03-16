package com.aic.paas.provider.ps.dep.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppAccess;
import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.PcAppAccess;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.aic.paas.provider.ps.db.PcAppAccessDao;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppImageDao;
import com.aic.paas.provider.ps.dep.PcAppAccessSvc;
import com.aic.paas.provider.ps.dep.bean.PcAppAccessInfo;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcAppAccessSvcImpl implements PcAppAccessSvc{
	
	@Autowired
	PcAppAccessDao appAccessDao;
	
	@Autowired
	PcAppImageDao appImageDao;
	
	@Autowired
	PcAppDao appDao;

	@Override
	public Page<PcAppAccess> queryPage(Integer pageNum, Integer pageSize, CPcAppAccess cdt, String orders) {
		return appAccessDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	@Override
	public List<PcAppAccess> queryList(CPcAppAccess cdt, String orders) {
		return appAccessDao.selectList(cdt, orders);
	}

	@Override
	public PcAppAccess queryById(Long id) {
		return appAccessDao.selectById(id);
	}

	@Override
	public Long saveOrUpdate(PcAppAccess record) {
		BinaryUtils.checkEmpty(record, "record");
		
		boolean isadd = record.getId() == null;
		if(isadd) {
			BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
			BinaryUtils.checkEmpty(record.getAccessCode(), "record.accessCode");
			BinaryUtils.checkEmpty(record.getAppImageId(), "record.appImageId");
			BinaryUtils.checkEmpty(record.getProtocol(), "record.protocol");
//			BinaryUtils.checkEmpty(record.getAccessUrl(), "record.accessUrl");
			BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getStatus() == null) record.setStatus(1); 
			if(record.getDataStatus() == null) record.setDataStatus(1); 
//			BinaryUtils.checkEmpty(record.getCreator(), "record.creator");
//			BinaryUtils.checkEmpty(record.getCreateTime(), "record.createTime");
//			BinaryUtils.checkEmpty(record.getModifier(), "record.modifier");
//			BinaryUtils.checkEmpty(record.getModifyTime(), "record.modifyTime");
//			BinaryUtils.checkEmpty(record.getRemark(), "record.remark");
		}else {
			if(record.getAppId() != null) BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
			if(record.getAccessCode() != null) BinaryUtils.checkEmpty(record.getAccessCode(), "record.accessCode");
			if(record.getAppImageId() != null) BinaryUtils.checkEmpty(record.getAppImageId(), "record.appImageId");
			if(record.getProtocol() != null) BinaryUtils.checkEmpty(record.getProtocol(), "record.protocol");
			if(record.getAccessUrl() != null) BinaryUtils.checkEmpty(record.getAccessUrl(), "record.accessUrl");
			if(record.getMntId() != null) BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			if(record.getRemark() != null) BinaryUtils.checkEmpty(record.getRemark(), "record.remark");
			if(record.getDataStatus() != null) BinaryUtils.checkEmpty(record.getDataStatus(), "record.dataStatus");
			if(record.getCreator()!= null) BinaryUtils.checkEmpty(record.getCreator(), "record.creator");
			if(record.getCreateTime() != null) BinaryUtils.checkEmpty(record.getCreateTime(), "record.createTime");
			if(record.getModifier() != null) BinaryUtils.checkEmpty(record.getModifier(), "record.modifier");
			if(record.getModifyTime() != null) BinaryUtils.checkEmpty(record.getModifyTime(), "record.modifyTime");
			if(record.getProtocol() != null) BinaryUtils.checkEmpty(record.getProtocol(), "record.Protocol");
		}
		
		Long id = record.getId();
		if(record.getAccessCode() != null) {
			String code = record.getAccessCode().trim();
			record.setAccessCode(code);
			
			CPcAppAccess cdt = new CPcAppAccess();
			cdt.setMntId(record.getMntId());
			
			List<PcAppAccess> ls = appAccessDao.selectListByCode(code, cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		return appAccessDao.save(record);
	}

	@Override
	public int removeById(Long id) {
		PcAppAccess access = appAccessDao.selectById(id);
		PcAppImage record = new PcAppImage();
		record.setId(access.getAppImageId());
		record.setCustom1(0l);
		record.setCustom2(0l);
		appImageDao.save(record);
		return appAccessDao.deleteById(id);
	}

	private List<PcAppAccessInfo> fillInfo(List<PcAppAccess> ls) {
		List<PcAppAccessInfo> infos = new ArrayList<PcAppAccessInfo>();
		if(ls!=null && ls.size()>0) {
			for(int i=0; i<ls.size(); i++) {
				PcAppAccess svc = ls.get(i);
				PcAppImage pai = appImageDao.selectById(svc.getAppImageId());
				PcApp app  = appDao.selectById(svc.getAppId());
				PcAppAccessInfo info = new PcAppAccessInfo();
				info.setAccess(svc);
				if(pai!=null)
					info.setImgName(pai.getContainerFullName());
				if(app!=null)
					info.setAppName(app.getAppName());
				infos.add(info);
			}
		}
		return infos;
	}
	
	@Override
	public Page<PcAppAccessInfo> queryPage4Info(Integer pageNum, Integer pageSize, CPcAppAccess cdt, String orders) {
		Page<PcAppAccess> page = queryPage(pageNum, pageSize, cdt, orders);
		List<PcAppAccess> list = page.getData();
		List<PcAppAccessInfo> infos = fillInfo(list);
		return new Page<PcAppAccessInfo>(page.getPageNum(),page.getPageSize(),page.getTotalRows(),page.getTotalPages(),infos);
	}


	@Override
	public List<PcAppAccessInfo> queryInfoList(CPcAppAccess cdt, String orders) {
		List<PcAppAccess> list = queryList(cdt, orders);
		return fillInfo(list);
	}

	@Override
	public PcAppAccessInfo queryInfoById(Long id) {
		PcAppAccess p = queryById(id);
		if(p != null){
			List<PcAppAccess> list = new ArrayList<PcAppAccess>();
			list.add(p);
			return fillInfo(list).get(0);
		}
		return null;
	}

	


}
