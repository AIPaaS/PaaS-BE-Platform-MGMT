package com.aic.paas.provider.ps.dep.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.provider.ps.bean.CPcAppTask;
import com.aic.paas.provider.ps.bean.PcAppTask;
import com.aic.paas.provider.ps.bean.PcAppVersion;
import com.aic.paas.provider.ps.db.PcAppDao;
import com.aic.paas.provider.ps.db.PcAppDepHistoryDao;
import com.aic.paas.provider.ps.db.PcAppTaskDao;
import com.aic.paas.provider.ps.db.PcAppVersionDao;
import com.aic.paas.provider.ps.dep.PcAppTaskSvc;
import com.aic.paas.provider.ps.dep.bean.PcAppTaskInfo;
import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.Page;

public class PcAppTaskSvcImpl implements PcAppTaskSvc {

	@Autowired
	PcAppTaskDao pcAppTaskDao;
	@Autowired
	PcAppDao pcAppDao;
	@Autowired
	PcAppDepHistoryDao pcAppDepHistoryDao;
	@Autowired
	PcAppVersionDao pcAppVersionDao;

	@Override
	public void save(PcAppTask record) {

		BinaryUtils.checkEmpty(record, "record");
		BinaryUtils.checkEmpty(record.getAppId(), "record.appId");

		pcAppTaskDao.insert(record);
	}

	@Override
	public PcAppTask queryById(Long id) {
		return pcAppTaskDao.selectById(id);
	}

	@Override
	public Page<PcAppTask> queryPage(Integer pageNum, Integer pageSize, CPcAppTask cdt, String orders) {
		return pcAppTaskDao.selectPage(pageNum, pageSize, cdt, orders);
	}

	@Override
	public void update(PcAppTask record) {
		pcAppTaskDao.updateById(record, record.getId());
	}

	@Override
	public Page<PcAppTaskInfo> queryPcAppTaskInfo(Integer pageNum, Integer pageSize, CPcAppTask cdt, String orders) {
		Page<PcAppTask> pageTask = pcAppTaskDao.selectPage(pageNum, pageSize, cdt, orders);
		List<PcAppTaskInfo> pcAppTaskInfoList = new ArrayList<>();
		for (PcAppTask task : pageTask.getData()) {
			PcAppTaskInfo pcAppTaskInfo = new PcAppTaskInfo();
			pcAppTaskInfo.setPcAppTask(task);
			PcAppVersion pcAppVersion = pcAppVersionDao.selectById(task.getAppVnoId());
			pcAppTaskInfo.setVersionNo(pcAppVersion.getVersionNo());
			pcAppTaskInfoList.add(pcAppTaskInfo);
		}
		Page<PcAppTaskInfo> result = new Page<>();
		result.setData(pcAppTaskInfoList);
		result.setPageNum(pageTask.getPageNum());
		result.setPageSize(pageTask.getPageSize());
		result.setTotalPages(pageTask.getTotalPages());
		result.setTotalRows(pageTask.getTotalRows());
		return result;
	}

}
