package com.aic.paas.provider.ps.dep.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppImgSvc;
import com.aic.paas.provider.ps.bean.CPcKvPair;
import com.aic.paas.provider.ps.bean.CPcService;
import com.aic.paas.provider.ps.bean.PcApp;
import com.aic.paas.provider.ps.bean.PcAppImage;
import com.aic.paas.provider.ps.bean.PcAppImgSvc;
import com.aic.paas.provider.ps.bean.PcKvPair;
import com.aic.paas.provider.ps.bean.PcService;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppImageDao;
import com.aic.paas.provider.ps.db.PcAppImgSvcDao;
import com.aic.paas.provider.ps.db.PcKvPairDao;
import com.aic.paas.provider.ps.db.PcServiceDao;
import com.aic.paas.provider.ps.dep.PcServiceSvc;
import com.aic.paas.provider.ps.dep.bean.PcServiceInfo;
import com.aic.paas.provider.ps.util.DepUtil;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;

public class PcServiceSvcImpl implements PcServiceSvc {
	
	
	@Autowired
	PcServiceDao serviceDao;
	
	@Autowired
	PcKvPairDao kvPairDao;
	
	@Autowired
	PcAppDao appDao;
	
	@Autowired
	PcAppImgSvcDao appImgSvcDao;
	
	@Autowired
	PcAppImageDao appImageDao;
	
	@Override
	public Page<PcService> queryPage(Integer pageNum, Integer pageSize, CPcService cdt, String orders) {
		return serviceDao.selectPage(pageNum, pageSize, cdt, orders);
	}
	
	

	@Override
	public List<PcService> queryList(CPcService cdt, String orders) {
		return serviceDao.selectList(cdt, orders);
	}
	
	

	@Override
	public PcService queryById(Long id) {
		return serviceDao.selectById(id);
	}
	
	
	
	private List<PcServiceInfo> fillInfo(List<PcService> ls) {
		List<PcServiceInfo> infos = new ArrayList<PcServiceInfo>();
		if(ls!=null && ls.size()>0) {
			Long[] svcIds = new Long[ls.size()];
			for(int i=0; i<ls.size(); i++) {
				PcService svc = ls.get(i);
				Long svcId = svc.getId();
				PcServiceInfo info = new PcServiceInfo();
				info.setSvc(svc);
				infos.add(info);
				
				svcIds[i] = svcId;
			}
			
			CPcKvPair kvcdt = new CPcKvPair();
			kvcdt.setTypeId(1);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
			kvcdt.setSourceIds(svcIds);
			List<PcKvPair> kvls = kvPairDao.selectList(kvcdt, " SOURCE_ID, KV_KEY ");
			Map<Long, List<PcKvPair>> kvmap = BinaryUtils.toObjectGroupMap(kvls, "sourceId");
			
			for(int i=0; i<infos.size(); i++) {
				PcServiceInfo info = infos.get(i);
				Long svcId = info.getSvc().getId();
				info.setParams(kvmap.get(svcId));
			}
		}
		return infos;
	}
	
	
	
	@Override
	public Page<PcServiceInfo> queryInfoPage(Integer pageNum, Integer pageSize, CPcService cdt, String orders) {
		Page<PcService> page = queryPage(pageNum, pageSize, cdt, orders);
		List<PcService> ls = page.getData();
		List<PcServiceInfo> infols = fillInfo(ls);
		return new Page<PcServiceInfo>(page.getPageNum(), page.getPageSize(), page.getTotalRows(), page.getTotalPages(), infols);
	}


	
	@Override
	public List<PcServiceInfo> queryInfoList(CPcService cdt, String orders) {
		List<PcService> ls = queryList(cdt, orders);
		return fillInfo(ls);
	}

	
	@Override
	public PcServiceInfo queryInfoById(Long id) {
		PcService p = queryById(id);
		if(p != null) {
			List<PcService> ls = new ArrayList<PcService>();
			ls.add(p);
			return fillInfo(ls).get(0);
		}
		return null;
	}
	
	

	@Override
	public Long saveOrUpdate(PcService record) {
		BinaryUtils.checkEmpty(record, "record");
		
		boolean isadd = record.getId() == null;
		if(isadd) {
			BinaryUtils.checkEmpty(record.getSvcCode(), "record.svcCode");
			BinaryUtils.checkEmpty(record.getSvcName(), "record.svcName");
			BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			BinaryUtils.checkEmpty(record.getSvcType(), "record.svcType");
			if(record.getSvcType().intValue() != 1) {	//1=平台服务    2=外部服务    3=镜像服务
				BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			}
			if(record.getStatus() == null) record.setStatus(1); 	//1=未部署  2=运行中  3=停止
			
			switch(record.getSvcType().intValue()) {
				case 1 : {
					BinaryUtils.checkEmpty(record.getDutierId(), "record.dutierId");
					BinaryUtils.checkEmpty(record.getDutierName(), "record.dutierName");
					BinaryUtils.checkEmpty(record.getDutierPhone(), "record.dutierPhone");
					BinaryUtils.checkEmpty(record.getDutierEmail(), "record.dutierEmail");
					BinaryUtils.checkEmpty(record.getDepTime(), "record.depTime");
					break;
				}
				case 2 : {
//					BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
					BinaryUtils.checkEmpty(record.getUserId(), "record.userId");
					BinaryUtils.checkEmpty(record.getUserName(), "record.userName");
					break;
				}
				case 3 : {
					BinaryUtils.checkEmpty(record.getAppImageId(), "record.appImageId");
					break;
				}
			}
		}else {
			if(record.getSvcCode() != null) BinaryUtils.checkEmpty(record.getSvcCode(), "record.svcCode");
			if(record.getSvcName() != null) BinaryUtils.checkEmpty(record.getSvcName(), "record.svcName");
			if(record.getDataCenterId() != null) BinaryUtils.checkEmpty(record.getDataCenterId(), "record.dataCenterId");
			if(record.getResCenterId() != null) BinaryUtils.checkEmpty(record.getResCenterId(), "record.resCenterId");
			if(record.getSvcType() != null) BinaryUtils.checkEmpty(record.getSvcType(), "record.svcType");
			if(record.getMntId() != null) BinaryUtils.checkEmpty(record.getMntId(), "record.mntId");
			if(record.getDutierId() != null) BinaryUtils.checkEmpty(record.getDutierId(), "record.dutierId");
			if(record.getDutierName() != null) BinaryUtils.checkEmpty(record.getDutierName(), "record.dutierName");
			if(record.getDutierPhone() != null) BinaryUtils.checkEmpty(record.getDutierPhone(), "record.dutierPhone");
			if(record.getDutierEmail() != null) BinaryUtils.checkEmpty(record.getDutierEmail(), "record.dutierEmail");
			if(record.getDepTime() != null) BinaryUtils.checkEmpty(record.getDepTime(), "record.depTime");
//			if(record.getAppId() != null) BinaryUtils.checkEmpty(record.getAppId(), "record.appId");
			if(record.getUserId() != null) BinaryUtils.checkEmpty(record.getUserId(), "record.userId");
			if(record.getUserName() != null) BinaryUtils.checkEmpty(record.getUserName(), "record.userName");
			if(record.getAppImageId() != null) BinaryUtils.checkEmpty(record.getAppImageId(), "record.appImageId");
		}
		
		Long id = record.getId();
		if(record.getSvcCode() != null) {
			String code = record.getSvcCode().trim();
			record.setSvcCode(code);
			
			CPcService cdt = new CPcService();
			cdt.setMntId(record.getMntId());
			
			List<PcService> ls = serviceDao.selectListByCode(code, cdt, null);
			if(ls.size()>0 && (id==null || ls.size()>1 || ls.get(0).getId().longValue()!=id.longValue())) {
				throw new ServiceException(" is exists code '"+code+"'! ");
			}
		}
		
		return serviceDao.save(record);
	}
	
	

	@Override
	public int removeById(Long id) {
		CPcKvPair cdt = new CPcKvPair();
		cdt.setTypeId(1);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdt.setSourceId(id);
		kvPairDao.deleteByCdt(cdt);
		return serviceDao.deleteById(id);
	}
	
	
	
	@Override
	public List<PcKvPair> queryParams(Long svcId, String orders) {
		BinaryUtils.checkEmpty(svcId, "svcId");
		
		CPcKvPair cdt = new CPcKvPair();
		cdt.setTypeId(1);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdt.setSourceId(svcId);
		return kvPairDao.selectList(cdt, orders);
	}

	
	
	

	@Override
	public void resetParams(Long svcId, List<PcKvPair> params) {
		BinaryUtils.checkEmpty(svcId, "svcId");
		DepUtil.verifyParamsRepeated(params, false);
		
		CPcKvPair cdt = new CPcKvPair();
		cdt.setTypeId(1);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdt.setSourceId(svcId);
		kvPairDao.deleteByCdt(cdt);
		
		if(params!=null && params.size()>0) {
			for(int i=0; i<params.size(); i++) {
				PcKvPair kv = params.get(i);
				kv.setTypeId(1);
				kv.setSourceId(svcId);
			}
			kvPairDao.insertBatch(params);
		}		
	}



	


	@Override
	public void removeParams(Long svcId) {
		BinaryUtils.checkEmpty(svcId, "svcId");
		
		CPcKvPair cdt = new CPcKvPair();
		cdt.setTypeId(1);		//1=服务定义参数  2=镜像环境变量  3=镜像调用服务参数
		cdt.setSourceId(svcId);
		kvPairDao.deleteByCdt(cdt);
	}


	/**
	 * 镜像服务
	 * 提供者 应用名称
	 * 引用者 容器名称  描述
	 */
	@Override
	public Page<PcServiceInfo> queryPage4Info(Integer pageNum,
			Integer pageSize, CPcService cdt, String orders) {
		//有效
		cdt.setStatus(1);
		Page<PcService> pcServices = queryPage(pageNum, pageSize, cdt, orders);
		List<PcService> datas = pcServices.getData();
		List<PcServiceInfo> dataDes = new ArrayList<PcServiceInfo>();
		Page<PcServiceInfo> res = new Page<PcServiceInfo>(pcServices.getPageNum(),
				pcServices.getPageSize(),pcServices.getTotalRows(),pcServices.getTotalPages(),dataDes);
		for(int i=0;i<datas.size();i++){
			PcService service = datas.get(i);
			PcServiceInfo des = new PcServiceInfo();
			if(service.getAppId()!=null){
				PcApp app = appDao.selectById(service.getAppId());
				if(app!=null)
					des.setProvider(app.getAppName());
			}
			setConsumerInfo(service,des);
			des.setSvc(service);
			dataDes.add(des);
		}
		res.setData(dataDes);
		return res;
	}
	
	private void setConsumerInfo(PcService service, PcServiceInfo des) {
		CPcAppImgSvc cdt = new CPcAppImgSvc();
		cdt.setSvcId(service.getId());
		List<PcAppImgSvc> imgSvcs = appImgSvcDao.selectList(cdt, "ID");
		if(imgSvcs!=null && imgSvcs.size()>0){
			List<Long> consumerIds = new ArrayList<Long>();
			List<String> consumers = new ArrayList<String>();
			for(PcAppImgSvc imgSvc:imgSvcs){
				consumerIds.add(imgSvc.getAppImgId());
				PcAppImage img = appImageDao.selectById(imgSvc.getAppImgId());
				consumers.add(img.getContainerName());
			}
			des.setConsumerIds(consumerIds);
			des.setConsumers(consumers);
			des.setConsumerDes(consumers.toString());
		}
	}
	
	
	
	
	
	
}




