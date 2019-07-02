package user;

import java.util.ArrayList;
import java.util.HashMap;

import com.parttimehumans.safeheart.Client;

import medicalRecord.Report;
import medicalRecord.ReportType;

public class Patient extends User {
	
	private HashMap<String, Report> diagnosticReports;

	public Patient(String userId) {
		super(userId);
	}
	
	public HashMap<String, Report> getReports(){
		return this.diagnosticReports;
	}
	
	public void setReports(HashMap<String, Report> reports) {
		this.diagnosticReports = reports;
		
	}
	
	public Report getReport(ReportType type){
		return this.diagnosticReports.get(type.getAction());
	}
}
