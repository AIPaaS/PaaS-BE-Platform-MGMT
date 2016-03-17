package com.aic.paas.provider.ps.remote.model;

import java.io.Serializable;

public class AppAccessModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11354545L;
	//容器名称
	private String container;
	private String dns;
	//协议 1http 2tcp
	private int protocol;
	//新的应用访问名称
	private String accessCode;
	//旧的应用访问名称
	private String accessCodeOld;
	//资源中心，集群ID
	private int resCenterId;
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getAccessCodeOld() {
		return accessCodeOld;
	}
	public void setAccessCodeOld(String accessCodeOld) {
		this.accessCodeOld = accessCodeOld;
	}
	public int getResCenterId() {
		return resCenterId;
	}
	public void setResCenterId(int resCenterId) {
		this.resCenterId = resCenterId;
	}
	
	
	
	
}
