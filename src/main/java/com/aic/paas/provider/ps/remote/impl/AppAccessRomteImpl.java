package com.aic.paas.provider.ps.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.comm.util.PropertiesPool;
import com.aic.paas.provider.ps.remote.IAppAccess;
import com.aic.paas.provider.ps.remote.model.AppAccessModel;
import com.aic.paas.provider.ps.util.HttpClientUtil;
import com.binary.json.JSON;

public class AppAccessRomteImpl implements IAppAccess {

	@Autowired
	PropertiesPool propertiesPool;
	
	@Override
	public String addAccess(AppAccessModel param) {
		String addr = propertiesPool.get("beAmUrl");
		addr += "/appaccess/manage/add";
		return HttpClientUtil.send(addr, JSON.toString(param));
	}

	@Override
	public String updateAccess(AppAccessModel param) {
		String addr = propertiesPool.get("beAmUrl");
		addr += "/appaccess/manage/modify";
		return HttpClientUtil.send(addr, JSON.toString(param));
	}

	@Override
	public String removeAccess(AppAccessModel param) {
		String addr = propertiesPool.get("beAmUrl");
		addr += "/appaccess/manage/delete";
		return HttpClientUtil.send(addr, JSON.toString(param));
	}

}
