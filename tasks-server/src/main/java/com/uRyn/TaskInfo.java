package com.uRyn;

import java.util.Date;

public class TaskInfo {
	public TaskInfo(String title, Date date, long id) {
		this.title = title;
		this.date = date;	
		this.id = id;
		this.status = false;
	}
	
	private String title;
	private Date date;
	private boolean status;
	private long id;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Title: " + title + ", date: " + Utilities.formatedDate(date) + ", Status: " + (status ? "done" : "not done") + ", id: " + id;
	}
}
