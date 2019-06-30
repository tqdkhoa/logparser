package com.ef.Parser;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="loginfo")
public class LogInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column
    private Long id;
	@Column
	private Date date;
	@Column(length = 50, nullable = true)
	private String iPAddress;
	@Column(length = 255, nullable = true)
	private String request;
	@Column(length = 255, nullable = true)
	private String httpStatus;
	@Column(length = 255, nullable = true)
	private String userAgent;
	
	public Long getId() {
        return id;
    }

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIPAddress() {
		return iPAddress;
	}
	public void setIPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
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
	
	public LogInfo(Date date, String iPAddress, String request, String httpStatus, String userAgent) {
		super();
		this.date = date;
		this.iPAddress = iPAddress;
		this.request = request;
		this.httpStatus = httpStatus;
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "LogInfo{" +
                "id=" + id +
                ", date='" + date.toString() + '\'' +
                ", ipAddress='" + iPAddress + '\'' +
                ", request='" + request + '\'' +
                ", httpStatus='" + httpStatus + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
	}
	
	
}
