package webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import medicalRecord.LabObservation;
import medicalRecord.Report;
import medicalRecord.ReportType;
import observerPattern.Subject;
import user.Patient;

public class FetchService extends Subject {
	
	private WebService server = new FhirServer();
	
	public void startService() {
		Timer timer = new Timer();
		TimerTask hourlyTask = new TimerTask() {
		    @Override
		    public void run () {
		        notifyObservers();
		    }
		};
		
		timer.schedule(hourlyTask, 0l, 1000*60*60); // 60 milliseconds * 1000 * 60 minutes
	}
	
	
	/**
	 * Fetches the list of patients for the specified clinican id
	 * @param clinicianId
	 * @return
	 */
	public ArrayList<Patient> fetchPatients(String clinicianId){
		ArrayList<Patient> userPatientList = new ArrayList<Patient>();
		
		// get the patient IDs
		ArrayList<String> userPatientIdList = server.getPatientList(clinicianId);
		
		//create the patient objects
		for(String patientId: userPatientIdList) {
			Patient patient = new Patient(patientId);
			
			// fetch the patient reports
			HashMap<String, Report> patientReports = server.getPatientReport(patient.getId());
			
			// add reports to the patient
			patient.setReports(patientReports);
			
			userPatientList.add(patient);
		}
		
		return userPatientList;
	}
	
	/**
	 * Fetches the observation value for type specified report
	 * @param patientList - the list of user's patient objects
	 * @param type - the type of report requested
	 */
	public void refreshLabObservation(ArrayList<Patient> patientList, ReportType type) {
		for(Patient patient: patientList) {
			
			if(patient.getReports().containsKey(type.getAction())) {
			
			Report report = patient.getReport(type);
			
			HashMap<String, LabObservation> observations = new HashMap<String, LabObservation>();

			for(String observationId: report.getObservationId()) {
				LabObservation observation = server.getLabObservation(patient.getId(), observationId);
				
				observations.put(observation.getId(), observation);
			}
			
			report.setObservations(observations);
			}
		}
	}
	
	/**
	 * Fetches observation values for the type specified vitals
	 * @param patientList
	 * @param observationTypes
	 */
	public void refreshVitalObservations(ArrayList<Patient> patientList, ArrayList<String> observationTypes) {
		for(Patient patient: patientList) {
			Report report = patient.getReport(ReportType.Vitals);
			report.setObservationId(observationTypes);
			
			HashMap<String, LabObservation> observations = server.getVitalObservation(patient.getId(), observationTypes);
			
			report.setObservations(observations);
		}
	}
	
	/**
	 * refreshes the stored value for the numerical monitor
	 * @param monitorId
	 * @return updated observation value
	 */
	public String refreshNumericalMonitor(String patientId, String monitorId) {
		LabObservation observation = server.getLabObservation(patientId, monitorId);
		
		return observation.getValue();
	}
	
	/**
	 * refreshes the stored report for a graphical monitor
	 * @param patientId
	 * @param report
	 */
	public Report refreshGraphicalMonitor(String patientId, Report report) {
		ArrayList<String> observationTypeList = report.getObservationId();
		
		HashMap<String, LabObservation> updatedValues = server.getVitalObservation(patientId, observationTypeList);
		
		report.setObservations(updatedValues);
		
		return report;
	}
	
	public HashMap<String, LabObservation> testVitalFetch(String patientId, ArrayList<String> observationTypes) {
		return server.getVitalObservation(patientId, observationTypes);
	}
	
	/**
	 * @deprecated refreshes all the observations for all the patient reports
	 * @param patientReports
	 */
	public void refreshReportObservations(HashMap<String, Report> patientReports) {
		for (String reportId: patientReports.keySet()) {
			
			Report report = patientReports.get(reportId);
			
			HashMap<String, LabObservation> observations = new HashMap<String, LabObservation>();
			
			// fetch the observation for each report
			for(String observationId: report.getObservationId()) {
//				LabObservation observation = server.getObservation(observationId);
//				
//				observations.put(observation.getId(), observation);
			}
			
			// add observations to the patient report
			report.setObservations(observations);
		}
		
	}	
}
