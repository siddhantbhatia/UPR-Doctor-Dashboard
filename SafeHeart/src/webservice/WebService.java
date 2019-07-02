package webservice;

import java.util.ArrayList;
import java.util.HashMap;

import medicalRecord.*;




public interface WebService {
	public ArrayList<String> getPatientList(String practitionerId);
	
	public HashMap<String, Report> getPatientReport(String patientId);
	
	public LabObservation getLabObservation(String patientId, String observationId);
	
	public HashMap<String, LabObservation> getVitalObservation(String patientId, ArrayList<String> observationTypes);
}
