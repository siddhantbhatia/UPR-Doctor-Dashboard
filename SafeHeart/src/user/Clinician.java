package user;

import java.util.ArrayList;
import java.util.HashMap;

import monitor.Monitor;

public class Clinician extends User {
	
//	private ArrayList<Monitor> monitorList = new ArrayList<Monitor>();
	private ArrayList<Patient> patientList = new ArrayList<Patient>();
	
	private HashMap<String, ArrayList<Monitor>> monitorList = new HashMap<String, ArrayList<Monitor>>();

	public Clinician(String userId) {
		super(userId);
	}
	
	public void addMonitor(String patientId, Monitor monitor) {
		if(monitorList.containsKey(patientId)) {
			monitorList.get(patientId).add(monitor);
		}
		else {
			ArrayList<Monitor> monitors = new ArrayList<Monitor>();
			monitors.add(monitor);
			
			monitorList.put(patientId, monitors);
		}

	}
	
	public HashMap<String, ArrayList<Monitor>> getMonitorList() {
		return monitorList;
	}
	
	public void setPatientList(ArrayList<Patient> patientList) {
		this.patientList = patientList;
		
	}
	
	public void removeMonitor(String patientId, Monitor clMonitor) {
		monitorList.get(patientId).remove(clMonitor);
	}
	
	public ArrayList<Patient> getPatientList() {
		return patientList;
	}
	
	public ArrayList<Patient> removePatientList(Patient patient) {
		patientList.remove(patient);
		return patientList;
	}
}
