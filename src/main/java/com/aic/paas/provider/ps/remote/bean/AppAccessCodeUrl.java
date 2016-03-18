package com.aic.paas.provider.ps.remote.bean;

import java.io.Serializable;
/**
 * 映射能力后场的 [AppAccessRomteImpl]方法返回值
 *
 */
public class AppAccessCodeUrl implements Serializable{
	//编码
	private String code;
	//应用访问地址
	private String accessUrl;
	//信息
	private String msg;
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	
	
}
