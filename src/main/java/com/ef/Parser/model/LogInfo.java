package com.ef.Parser.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_info")
public class LogInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "log_date", nullable = false)
	private Date logDate;

	@Column(name = "ip_address", length = 255, nullable = true)
	private String iPAddress;

	@Column(name = "http_request", length = 255, nullable = true)
	private String httpRequest;

	@Column(name = "http_status", length = 255, nullable = true)
	private String httpStatus;

	@Column(name = "user_agent", length = 255, nullable = true)
	private String userAgent;

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return logDate;
	}

	public void setDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getIPAddress() {
		return iPAddress;
	}

	public void setIPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}

	public String getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(String httpRequest) {
		this.httpRequest = httpRequest;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public LogInfo() {

	}

	public LogInfo(Date logDate, String iPAddress, String httpRequest, String httpStatus, String userAgent) {
		super();
		this.logDate = logDate;
		this.iPAddress = iPAddress;
		this.httpRequest = httpRequest;
		this.httpStatus = httpStatus;
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "LogInfo{" + "id=" + id + ", date='" + logDate.toString() + '\'' + ", ipAddress='" + iPAddress + '\''
				+ ", request='" + httpRequest + '\'' + ", httpStatus='" + httpStatus + '\'' + ", userAgent='"
				+ userAgent + '\'' + '}';
	}

}
