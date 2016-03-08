package com.aic.paas.provider.ps.res.bean;

import java.util.List;

import com.aic.paas.provider.ps.bean.PcComputer;

public class ResDetailInfo {
	Long resCenterId;
	String resCenterName;
	Long dataCenterId;
	String dataCenterName;
	String imagePath;
	String domain;
	String externalDomain;
	List<PcComputer> corePartList;
	List<PcComputer> visitPartList;
	List<PcComputer> slavePartList;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getExternalDomain() {
		return externalDomain;
	}
	public void setExternalDomain(String externalDomain) {
		this.externalDomain = externalDomain;
	}
	public Long getResCenterId() {
		return resCenterId;
	}
	public void setResCenterId(Long resCenterId) {
		this.resCenterId = resCenterId;
	}
	public String getResCenterName() {
		return resCenterName;
	}
	public void setResCenterName(String resCenterName) {
		this.resCenterName = resCenterName;
	}
	public Long getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(Long dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public String getDataCenterName() {
		return dataCenterName;
	}
	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}
	public List<PcComputer> getCorePartList() {
		return corePartList;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public void setCorePartList(List<PcComputer> corePartList) {
		this.corePartList = corePartList;
	}
	public List<PcComputer> getVisitPartList() {
		return visitPartList;
	}
	public void setVisitPartList(List<PcComputer> visitPartList) {
		this.visitPartList = visitPartList;
	}
	public List<PcComputer> getSlavePartList() {
		return slavePartList;
	}
	public void setSlavePartList(List<PcComputer> slavePartList) {
		this.slavePartList = slavePartList;
	}
	
	
}
