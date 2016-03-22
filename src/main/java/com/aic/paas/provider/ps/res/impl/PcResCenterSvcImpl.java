package com.aic.paas.provider.ps.res.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.comm.util.PropertiesPool;
import com.aic.paas.provider.ps.bean.CPcImageRepository;
import com.aic.paas.provider.ps.bean.CPcNetZone;
import com.aic.paas.provider.ps.bean.CPcResCenter;
import com.aic.paas.provider.ps.bean.PcComputer;
import com.aic.paas.provider.ps.bean.PcImageRepository;
import com.aic.paas.provider.ps.bean.PcNetZone;
import com.aic.paas.provider.ps.bean.PcResCenter;
import com.aic.paas.provider.ps.db.PcComputerDao;
import com.aic.paas.provider.ps.db.PcImageRepositoryDao;
import com.aic.paas.provider.ps.db.PcNetZoneDao;
import com.aic.paas.provider.ps.db.PcResCenterDao;
import com.aic.paas.provider.ps.res.PcComputerSvc;
import com.aic.paas.provider.ps.res.PcResCenterSvc;
import com.aic.paas.provider.ps.res.bean.PcResCenterInfo;
import com.aic.paas.provider.ps.res.bean.ResDetailInfo;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcResCenterSvcImpl implements PcResCenterSvc {
	
	
	@Autowired
	PcResCenterDao pcResCenterDao;
	
	@Autowired
	PcImageRepositoryDao imageRespDao;
	
	@Autowired
	PcComputerDao pcComputerDao;
	
	@Autowired
	PcComputerSvc pcComputerSvc;
	
	@Autowired
	PcNetZoneDao pcNetZoneDao;
	
	@Autowired
	PropertiesPool propertiesPool;
	
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




	@Override
	public Map<String, Object> getInitParam(Long resCenterId,Boolean useAgent,Boolean loadOnly) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		ResDetailInfo  resinfo = pcComputerSvc.queryByResCenter(resCenterId);

		map.put("clusterId", resinfo.getResCenterId()+"");
		map.put("clusterName", resinfo.getResCenterName());
		//镜像地址
		map.put("imagePath", resinfo.getImagePath());
		map.put("useAgent",useAgent);
		//agentid为资源中心id
		map.put("aid", resinfo.getResCenterId()+"");
//		map.put("aid", "dev");
		
		map.put("attributesList", getZoneParam(resCenterId));
		
		map.put("dataCenter", resinfo.getDataCenterName());
		map.put("domain", resinfo.getDomain());
		map.put("externalDomain", resinfo.getExternalDomain());
		map.put("loadVirtualIP", propertiesPool.get("loadVirtulIP"));
				
		map.put("mesosMaster", getMasterParam(resinfo));
		map.put("mesosSlave", getSlaveParam(resinfo));
		
		map.put("webHaproxy", getWebHaproxyParam(resinfo,loadOnly));
		return map;
	}

	@Override
	public Map<String, Object> getCancelParam(Long resCenterId,Boolean useAgent,Boolean loadOnly) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		ResDetailInfo  resinfo = pcComputerSvc.queryByResCenter(resCenterId);

		map.put("clusterId", resinfo.getResCenterId()+"");
		//镜像地址
		map.put("imagePath", resinfo.getImagePath());
		map.put("useAgent",useAgent);
		//agentid为资源中心id
		map.put("aid", resinfo.getResCenterId()+"");
//		map.put("aid", "dev");
				
		map.put("mesosMaster", getMasterParam(resinfo));
		map.put("mesosSlave", getSlaveParam(resinfo));
		
		map.put("webHaproxy", getWebHaproxyParam(resinfo,loadOnly));
		return map;
	}
	
	
	private List<Map<String,Object>> getZoneParam(Long resCenterId){
		
		CPcNetZone cpnz = new CPcNetZone();
		cpnz.setResCenterId(resCenterId);
		cpnz.setStatus(1);
		List<PcNetZone> zoneist = pcNetZoneDao.selectList(cpnz, "id");
		
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();

		for(PcNetZone pcnz : zoneist){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("zone", pcnz.getZoneCode());
			map.put("network", pcnz.getNetSegExp());
			list.add(map);
		}
		
		return list;
	}

	private List<Map<String,Object>> getMasterParam(ResDetailInfo resinfo){
		if(resinfo.getCorePartList()==null||resinfo.getCorePartList().size()<3)
			throw new ServiceException(" the center-netZone of resCenter haven't enough computer! ");
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//所有id对应的区域名
		CPcNetZone cpnz = new CPcNetZone();
		cpnz.setResCenterId(resinfo.getResCenterId());
		List<PcNetZone> zoneist = pcNetZoneDao.selectList(cpnz, "id");
		Map<Long,String> idName = new HashMap<Long, String>();
		for(PcNetZone pnz : zoneist) {
			idName.put(pnz.getId(), pnz.getZoneCode());
		}
		
		//配master参数
		for(int i=0;i<resinfo.getCorePartList().size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			PcComputer pc = resinfo.getCorePartList().get(i);
			map.put("id", i+1);
			map.put("ip", pc.getIp());
			map.put("root", pc.getLoginName());
			map.put("passwd", pc.getLoginPwd());
			map.put("zone", idName.get(pc.getNetZoneId()));
			list.add(map);
		}
		return list;
	}
	

	private List<Map<String,Object>> getSlaveParam(ResDetailInfo resinfo){
		if(resinfo.getSlavePartList()==null)
			throw new ServiceException("there aran't enough slave computer");
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//所有id对应的区域名
		CPcNetZone cpnz = new CPcNetZone();
		cpnz.setResCenterId(resinfo.getResCenterId());
		List<PcNetZone> zoneist = pcNetZoneDao.selectList(cpnz, "id");
		Map<Long,String> idName = new HashMap<Long, String>();
		for(PcNetZone pnz : zoneist) {
			idName.put(pnz.getId(), pnz.getZoneCode());
		}
		
		for(int i=0;i<resinfo.getSlavePartList().size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			PcComputer pc = resinfo.getSlavePartList().get(i);
			map.put("id", i+1);
			map.put("ip", pc.getIp());
			map.put("root", pc.getLoginName());
			map.put("passwd", pc.getLoginPwd());
			map.put("zone", idName.get(pc.getNetZoneId()));
			
			String attributes = "ds:" + pc.getDataCenterId() + ";jf:"
					+ pc.getRoomId() + ";rack:" + pc.getLocation() + ";ex:"
					+ pc.getExSwitch() + ";cpu:" + pc.getCpuModel() + ";mem:"
					+ pc.getMemSize() + ";disk:" + pc.getDiskSize()
					+ ";netband:" + pc.getBandWidth();
			map.put("attributes", attributes);
			map.put("cpuTotal", pc.getCpuCount());
			map.put("cpuOffer", pc.getCpuOffer());
			map.put("memTotal", pc.getMemSize());
			map.put("memOffer", pc.getMemOffer());
			list.add(map);
		}
		
		
		return list;
	}
	
	private Map<String,Object> getWebHaproxyParam(ResDetailInfo resinfo,Boolean loadOnly){
		if(resinfo.getVisitPartList()==null||resinfo.getVisitPartList().size()<2)
			throw new ServiceException("the computer of Haproxy haven't enough!");
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<PcComputer> pcList = resinfo.getVisitPartList();
		for(int i=0;i<pcList.size();i++){
			Map<String,Object> hostsMap = new HashMap<String, Object>();
			PcComputer pc = pcList.get(i);
			hostsMap.put("id", i+1);
			hostsMap.put("ip", pc.getIp());
			hostsMap.put("root", pc.getLoginName());
			hostsMap.put("passwd", pc.getLoginPwd());
			list.add(hostsMap);
		}
		
		map.put("loadOnly", loadOnly);
		map.put("virtualIp", propertiesPool.get("virtulIP"));
		map.put("hosts", list);
		return map;
	}
}
