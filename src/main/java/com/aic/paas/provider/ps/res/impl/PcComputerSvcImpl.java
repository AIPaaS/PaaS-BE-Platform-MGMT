package com.aic.paas.provider.ps.res.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import com.aic.paas.provider.ps.bean.CPcComputer;
import com.aic.paas.provider.ps.bean.CPcComputerTag;
import com.aic.paas.provider.ps.bean.CPcNetZone;
import com.aic.paas.provider.ps.bean.PcComputer;
import com.aic.paas.provider.ps.bean.PcComputerTag;
import com.aic.paas.provider.ps.bean.PcNetZone;
import com.aic.paas.provider.ps.bean.PcResCenter;
import com.aic.paas.provider.ps.db.PcComputerDao;
import com.aic.paas.provider.ps.db.PcComputerTagDao;
import com.aic.paas.provider.ps.db.PcDataCenterDao;
import com.aic.paas.provider.ps.db.PcNetZoneDao;
import com.aic.paas.provider.ps.db.PcResCenterDao;
import com.aic.paas.provider.ps.res.PcComputerSvc;
import com.aic.paas.provider.ps.res.PsResCenterResSvc;
import com.aic.paas.provider.ps.res.bean.ResDetailInfo;
import com.aic.paas.provider.ps.res.bean.ResItems;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcComputerSvcImpl implements PcComputerSvc {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(PcComputerSvcImpl.class);
	
	@Autowired
	PcComputerDao computerDao;
	
	@Autowired
	PcComputerTagDao computerTagDao;
	
	@Autowired
	PsResCenterResSvc resSvc;
	
	@Autowired
	PcNetZoneDao pcNetZoneDao;
	
	@Autowired
	PcResCenterDao pcResCenterDao;
	
	@Autowired
	PcDataCenterDao dataCenterDao;
	
	
	
	

	@Override
	public Page<PcComputer> queryPage(Integer pageNum, Integer pageSize, CPcComputer cdt, String orders) {
		return computerDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	
	@Override
	public List<PcComputer> queryList(CPcComputer cdt, String orders) {
		return computerDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcComputer queryById(Long id) {
		return computerDao.selectById(id);
	}
	
	

	@Override
	public Long saveOrUpdate(PcComputer record) {
		BinaryUtils.checkEmpty(record, "record");
		
		if(record.getId() == null) {
			BinaryUtils.checkEmpty(record.getCode(), "record.code");
			BinaryUtils.checkEmpty(record.getRoomId(), "record.roomId");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoneId");
			BinaryUtils.checkEmpty(record.getIp(), "record.id");
			BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
		}else {
			if(record.getCode()!=null) BinaryUtils.checkEmpty(record.getCode(), "record.code");
			if(record.getRoomId()!=null) BinaryUtils.checkEmpty(record.getRoomId(), "record.roomId");
			if(record.getDataCenterId()!=null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId()!=null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getNetZoneId()!=null) BinaryUtils.checkEmpty(record.getNetZoneId(), "record.netZoneId");
			if(record.getIp()!=null) BinaryUtils.checkEmpty(record.getIp(), "record.id");
			if(record.getCpuCount()!=null) BinaryUtils.checkEmpty(record.getCpuCount(), "record.cpuCount");
			if(record.getMemSize()!=null) BinaryUtils.checkEmpty(record.getMemSize(), "record.memSize");
			if(record.getDiskSize()!=null) BinaryUtils.checkEmpty(record.getDiskSize(), "record.diskSize");
		}
		
		Long id = record.getId();
		boolean add = id == null;
		
		if(record.getCode() != null) {
			String code = record.getCode().trim();
			record.setCode(code);
			
			CPcComputer cdt = new CPcComputer();
			cdt.setCodeEqual(code);
			List<PcComputer> ls = computerDao.selectList(cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		
		PcComputer before = null;
		PcComputer after = null;
		if(!add) {
			before = computerDao.selectById(id);
			if(before == null) throw new ServiceException(" not found computer by id '"+id+"'! ");
		}
		id = computerDao.save(record);
		
		if(!add) after = computerDao.selectById(id);
		
		
		//如果新增服务器, 则在对应数据中心添加资源
		if(add) {
			ResItems items = new ResItems();
			items.setCpuCount(record.getCpuCount().longValue());
			items.setMemSize(record.getMemSize());
			items.setDiskSize(record.getDiskSize());
			//增加总资源
			resSvc.addTotalResByNetZoneId(record.getNetZoneId(), items);
			
			//增加剩余资源
			resSvc.addResByNetZoneId(record.getNetZoneId(), items, Local.getLogUser(), "新增服务器["+id+"]["+record.getCode()+"]["+record.getIp()+"]添加服务器资源。");
		}else {
			//如果服务器资源情况有变化则更新资源
			Long beforeNetZoneId = before.getNetZoneId();
			Integer beforeCpuCount = before.getCpuCount();
			Long beforeMemSize = before.getMemSize();
			Long beforeDiskSize = before.getDiskSize();
			
			Long afterNetZoneId = after.getNetZoneId();
			Integer afterCpuCount = after.getCpuCount();
			Long afterMemSize = after.getMemSize();
			Long afterDiskSize = after.getDiskSize();
			
			//只要有一项资源项目有变化则更新资源
			if((beforeNetZoneId.compareTo(afterNetZoneId)!=0)
					|| (beforeCpuCount.compareTo(afterCpuCount)!=0)
					|| (beforeMemSize.compareTo(afterMemSize)!=0)
					|| (beforeDiskSize.compareTo(afterDiskSize)!=0)) {
				ResItems beforeitems = new ResItems();
				beforeitems.setCpuCount(beforeCpuCount.longValue());
				beforeitems.setMemSize(beforeMemSize);
				beforeitems.setDiskSize(beforeDiskSize);
				
				//扣减更新之前总资源
				resSvc.reduceTotalResByNetZoneId(beforeNetZoneId, beforeitems);
				
				//扣减更新之前剩余资源
				resSvc.reduceResByNetZoneId(beforeNetZoneId, beforeitems, Local.getLogUser(), "更新服务器["+id+"]["+before.getCode()+"]["+before.getIp()+"]扣减之前旧服务器资源。", false);
				
				
				ResItems afteritems = new ResItems();
				afteritems.setCpuCount(afterCpuCount.longValue());
				afteritems.setMemSize(afterMemSize);
				afteritems.setDiskSize(afterDiskSize);
				
				//增加总资源
				resSvc.addTotalResByNetZoneId(afterNetZoneId, afteritems);
				
				//增加剩余资源
				resSvc.addResByNetZoneId(afterNetZoneId, afteritems, Local.getLogUser(), "更新服务器["+id+"]["+after.getCode()+"]["+after.getIp()+"]添加之后新服务器资源。");
			}
		}
		
		return id;
	}
	
	

	
	
	@Override
	public int removeById(Long id) {
		PcComputer c = computerDao.selectById(id);
		if(c == null) return 0;
		
		ResItems items = new ResItems();
		items.setCpuCount(c.getCpuCount().longValue());
		items.setMemSize(c.getMemSize());
		items.setDiskSize(c.getDiskSize());
		
		//扣减总资源
		resSvc.reduceTotalResByNetZoneId(c.getNetZoneId(), items);
		
		//扣减剩余资源
		resSvc.reduceResByNetZoneId(c.getNetZoneId(), items, Local.getLogUser(), "删除服务器["+id+"]["+c.getCode()+"]["+c.getIp()+"]扣减服务器资源。", false);
		
		return computerDao.deleteById(id);
	}


	
	
	@Override
	public List<PcComputerTag> queryComputerTagList(Long computerId, String orders) {
		BinaryUtils.checkEmpty(computerId, "computerId");
		CPcComputerTag cdt = new CPcComputerTag();
		cdt.setComputerId(computerId);
		List<PcComputerTag> tags = computerTagDao.selectList(cdt, orders);
		return tags;
	}

	
	

	@Override
	public void saveComputerTags(Long computerId, List<PcComputerTag> tags) {
		BinaryUtils.checkEmpty(computerId, "computerId");
		removeComputerTags(computerId);
		
		if(tags!=null && tags.size()>0) {
			Set<String> nameset = new HashSet<String>();
			
			for(int i=0; i<tags.size(); i++) {
				PcComputerTag tag = tags.get(i);
				tag.setId(null);
				tag.setComputerId(computerId);
				
				String name = tag.getTagName();
				String value = tag.getTagValue();
				
				if(name==null || (name=name.trim()).length()==0) {
					throw new ServiceException(" the targs["+i+"].name is empty! ");
				}
				if(value==null || (value=value.trim()).length()==0) {
					throw new ServiceException(" the targs["+i+"].value is empty! ");
				}
				if(nameset.contains(name)) {
					throw new ServiceException(" Having the same tag-name '"+name+"'! ");
				}
				
				tag.setTagName(name);
				tag.setTagValue(value);
				
				nameset.add(name);
			}
			
			computerTagDao.insertBatch(tags);
		}
	}

	
	

	@Override
	public void removeComputerTags(Long computerId) {
		BinaryUtils.checkEmpty(computerId, "computerId");
		CPcComputerTag cdt = new CPcComputerTag();
		cdt.setComputerId(computerId);
		computerTagDao.deleteByCdt(cdt);
	}
	
	
	
	
	@Override
	public List<PcComputerTag> queryComputerTagsByNetZoneId(Long netZoneId) {
		BinaryUtils.checkEmpty(netZoneId, "netZoneId");
		return computerTagDao.selectListByNetZoneId(netZoneId);
	}
	
	
	
	@Override
	public ResDetailInfo queryByResCenter(Long resCenterId) {
		if(resCenterId==null){
			LOGGER.info("the param of queryByResCenter is null");
			throw new ServiceException("the param of queryByResCenter is null");
		}
		ResDetailInfo resInfo = new ResDetailInfo();
		
		//该资源信息
		PcResCenter prc = pcResCenterDao.selectById(resCenterId);
		if(prc==null) {
			LOGGER.info(" there are not the resCenter  " +resCenterId);
			throw new ServiceException(" there are not the resCenter  " +resCenterId);
		}
		//该资源中心所属数据中心名称
		String dataCenterName = dataCenterDao.selectById(prc.getDataCenterId()).getName();
		
		resInfo.setResCenterId(resCenterId);
		resInfo.setResCenterName(prc.getResName());
		resInfo.setDataCenterId(prc.getDataCenterId());
		resInfo.setDataCenterName(dataCenterName);
		resInfo.setImagePath(prc.getImagePath());
		resInfo.setDomain(prc.getDomain());
		resInfo.setExternalDomain(prc.getExternalDomain());
		
		//该数据中心的服务器
		CPcComputer cp = new CPcComputer();
		cp.setResCenterId(resCenterId);
		List<PcComputer> list = computerDao.selectList(cp, "NET_ZONE_ID");
		if(list==null || list.size()==0) {
			LOGGER.info(" the resCenter  " +resCenterId+" have none computer!");
			return resInfo;
//			throw new ServiceException(" the resCenter  " +resCenterId+" have none computer!");
		}
		
		CPcNetZone pcNetZone = new CPcNetZone();
		pcNetZone.setResCenterId(resCenterId);
		List<PcNetZone> zoneList =  pcNetZoneDao.selectList(pcNetZone, "id");
		if(zoneList.size()==0||zoneList==null){
			LOGGER.info(" the resCenter  " +resCenterId+" have none netZone!");
			return resInfo;
//			throw new ServiceException(" the resCenter  " +resCenterId+" have none netZone!");
		}
		
		//分区域展示服务器信息
		List<PcComputer> corePartList = new ArrayList<PcComputer>();
		List<PcComputer> visitPartList = new ArrayList<PcComputer>();
		List<PcComputer> slavePartList = new ArrayList<PcComputer>();
		
		Map<Long,String> map = getNetZoneCodeMap(resCenterId);
		for(PcComputer pc :list){
			if("center".equals(map.get(pc.getNetZoneId()))){
				corePartList.add(pc);
			}else if("visit".equals(map.get(pc.getNetZoneId()))){
				visitPartList.add(pc);
			}else{
				slavePartList.add(pc);
			}
		}
		
		resInfo.setComputerList(list);
		resInfo.setCorePartList(corePartList);
		resInfo.setVisitPartList(visitPartList);
		resInfo.setSlavePartList(slavePartList);
		return resInfo;
	}
	
	/**
	 * 获取网络区域id与区域名对应关系
	 * @param resCenterId
	 * @return
	 */
	private Map<Long,String> getNetZoneCodeMap(Long resCenterId){
		Map<Long,String> map = new HashMap<Long, String>();
		CPcNetZone pcNetZone = new CPcNetZone();
		pcNetZone.setResCenterId(resCenterId);
		List<PcNetZone> zoneList =  pcNetZoneDao.selectList(pcNetZone, "id");
		for(PcNetZone pnz : zoneList){
			map.put(pnz.getId(), pnz.getZoneCode());
		}
		return map;
	}
	
	

}
