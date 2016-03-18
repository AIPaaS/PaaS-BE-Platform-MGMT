package com.aic.paas.provider.ps.dep.bean;

/**
 * 映射PcAppAccessSvc中remoteMonitorService()方法参数
 * 
 */
public class ParmDockerImage {
	//docker镜像名
	private String dockerImage;
	//docker实例名
	private String dockerName;
	//时间戳 
	private String timestamp;
	//主机ip
	private String host;
	//状态 
	private String taskStatus;
	//数据中心 
	private String dataCenter;
	//资源中心
	private String sourceCenter ;
	
	public String getDockerImage() {
		return dockerImage;
	}
	public void setDockerImage(String dockerImage) {
		this.dockerImage = dockerImage;
	}
	public String getDockerName() {
		return dockerName;
	}
	public void setDockerName(String dockerName) {
		this.dockerName = dockerName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getDataCenter() {
		return dataCenter;
	}
	public void setDataCenter(String dataCenter) {
		this.dataCenter = dataCenter;
	}
	public String getSourceCenter() {
		return sourceCenter;
	}
	public void setSourceCenter(String sourceCenter) {
		this.sourceCenter = sourceCenter;
	}
	
}
