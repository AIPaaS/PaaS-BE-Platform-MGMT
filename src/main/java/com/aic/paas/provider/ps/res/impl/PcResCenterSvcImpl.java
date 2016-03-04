package com.aic.paas.provider.ps.res.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcImageRepository;
import com.aic.paas.provider.ps.bean.CPcResCenter;
import com.aic.paas.provider.ps.bean.PcImageRepository;
import com.aic.paas.provider.ps.bean.PcResCenter;
import com.aic.paas.provider.ps.db.PcImageRepositoryDao;
import com.aic.paas.provider.ps.db.PcResCenterDao;
import com.aic.paas.provider.ps.res.PcResCenterSvc;
import com.aic.paas.provider.ps.res.bean.PcResCenterInfo;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcResCenterSvcImpl implements PcResCenterSvc {
	
	
	@Autowired
	PcResCenterDao pcResCenterDao;
	
	@Autowired
	PcImageRepositoryDao imageRespDao;
	
	

	@Override
	public Page<PcResCenter> queryPage(Integer pageNum, Integer pageSize, CPcResCenter cdt, String orders) {
		return pcResCenterDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	
	@Override
	public List<PcResCenter> queryPage2(Integer pageNum, Integer pageSize, CPcResCenter cdt, String orders) {
		return pcResCenterDao.selectList(pageNum, pageSize, cdt, orders);
	}
	
	
	

	@Override
	public List<PcResCenter> queryList(CPcResCenter cdt, String orders) {
		return pcResCenterDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcResCenter queryById(Long id) {
		return pcResCenterDao.selectById(id);
	}
	
	
	
	private List<PcResCenterInfo> fillResCenterInfo(List<PcResCenter> ls) {
		List<PcResCenterInfo> infos = new ArrayList<PcResCenterInfo>();
		if(ls!=null && ls.size()>0) {
			Set<Long> respIds = new HashSet<Long>();
			
			for(int i=0; i<ls.size(); i++) {
				PcResCenter rc = ls.get(i);
				PcResCenterInfo info = new PcResCenterInfo();
				info.setResCenter(rc);
				infos.add(info);
				
				Long respId = rc.getImgRespId();
				if(respId != null) {
					respIds.add(respId);
				}
			}
			
			if(respIds.size() > 0) {
				CPcImageRepository cdt = new CPcImageRepository();
				cdt.setIds(respIds.toArray(new Long[0]));
				List<PcImageRepository> respList = imageRespDao.selectList(cdt, null);
				Map<Long, PcImageRepository> respMap = BinaryUtils.toObjectMap(respList, "id");
				
				for(int i=0; i<infos.size(); i++) {
					PcResCenterInfo info = infos.get(i);
					Long respId = info.getResCenter().getImgRespId();
					if(respId != null) {
						info.setImageResp(respMap.get(respId));
					}
				}
			}
		}
		return infos;
	}
	
	
	
	
	@Override
	public Page<PcResCenterInfo> queryInfoPage(Integer pageNum, Integer pageSize, CPcResCenter cdt, String orders) {
		Page<PcResCenter> page = queryPage(pageNum, pageSize, cdt, orders);
		List<PcResCenter> list = page.getData();
		List<PcResCenterInfo> data = fillResCenterInfo(list);
		return new Page<PcResCenterInfo>(page.getPageNum(), page.getPageSize(), page.getTotalRows(), page.getTotalPages(), data);
	}



	@Override
	public List<PcResCenterInfo> queryInfoList(CPcResCenter cdt, String orders) {
		List<PcResCenter> list = queryList(cdt, orders);
		return fillResCenterInfo(list);
	}



	@Override
	public PcResCenterInfo queryInfoById(Long id) {
		PcResCenter record = queryById(id);
		if(record == null) return null;
		
		List<PcResCenter> ls = new ArrayList<PcResCenter>();
		ls.add(record);
		return fillResCenterInfo(ls).get(0);
	}
	
	
	

	@Override
	public Long saveOrUpdate(PcResCenter record) {
		BinaryUtils.checkEmpty(record, "record");
		
		if(record.getId() == null) {
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCode(), "record.resCode");
			BinaryUtils.checkEmpty(record.getResName(), "record.resName");
			BinaryUtils.checkEmpty(record.getEnvType(), "record.envType");
		}else {
			if(record.getDataCenterId()!=null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCode()!=null) BinaryUtils.checkEmpty(record.getResCode(), "record.resCode");
			if(record.getResName()!=null) BinaryUtils.checkEmpty(record.getResName(), "record.resName");
			if(record.getEnvType()!=null) BinaryUtils.checkEmpty(record.getEnvType(), "record.envType");
		}
		
		Long id = record.getId();
		
		if(record.getResCode() != null) {
			String code = record.getResCode().trim();
			record.setResCode(code);
			
			CPcResCenter cdt = new CPcResCenter();
			cdt.setResCodeEqual(code);
			List<PcResCenter> ls = pcResCenterDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
				
		id = pcResCenterDao.save(record);
		
		return id;
	}
	
	

	
	
	@Override
	public int removeById(Long id) {
		return pcResCenterDao.deleteById(id);
	}



	
	
	
	

}
