package monitor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import medicalRecord.Report;
import observerPattern.Observer;
import webservice.FetchService;


public abstract class Monitor implements Observer {
	String id;
	String patientId;
	String type;
	
	String value;
	String unit;
	FetchService fetchService;
	
	String lastUpdated;
	
	public Monitor(String id, String patientId, String type) {
		this.id = id;
		this.patientId = patientId;
		this.type = type;
	}
	
	//getters
	public String getId() {
		return this.id;
	};
	public String getPatientId() {
		return this.patientId;
	};
	public String getType() {
		return this.type;
	};
	public String getValue() {
		return this.value;
	}
	public String getLastUpdatedTime() {
		return this.lastUpdated;
	}
	
	//setters
	public void setValue(String value) {
		this.value = value;
	}
	public void setFetchService(FetchService service) {
		this.fetchService = service;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	protected void refreshLastUpdatedTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		
		this.lastUpdated = timestamp.toString();
	}
	
	public abstract String displayMonitor();
}
