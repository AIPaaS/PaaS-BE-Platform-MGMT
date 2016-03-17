package com.aic.paas.provider.ps.remote;

import com.aic.paas.provider.ps.remote.model.AppAccessModel;

public interface IAppAccess {
	String addAccess(AppAccessModel param);

	String updateAccess(AppAccessModel param);

	String removeAccess(AppAccessModel param);

}
