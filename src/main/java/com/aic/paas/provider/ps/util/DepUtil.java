package com.aic.paas.provider.ps.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aic.paas.provider.ps.bean.PcKvPair;
import com.binary.framework.exception.ServiceException;

public abstract class DepUtil {
	
	
	
	/**
	 * 验证参数名是否重复
	 * @param params
	 * @param alias true表示验证别名, false表示验证键名
	 */
	public static void verifyParamsRepeated(List<PcKvPair> params, boolean alias) {
		if(params==null || params.size()==0) return ;
		Set<String> set = new HashSet<String>();
		
		for(int i=0; i<params.size(); i++) {
			PcKvPair kv = params.get(i);
			String name = alias ? kv.getKeyAlias() : kv.getKvKey();
			if(name==null || (name=name.trim()).length()==0) {
				throw new ServiceException(" the params["+i+"].name is empty! ");
			}
			
			if(set.contains(name)) {
				throw new ServiceException(" is repeated the params.name '"+name+"'! ");
			}
			set.add(name);
			
			if(alias) {
				kv.setKeyAlias(name);
			}else {
				kv.setKvKey(name);
			}
		}
	}

}
