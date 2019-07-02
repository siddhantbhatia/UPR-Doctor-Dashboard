package medicalRecord;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientReport implements Report{
	
	private String id;
	private String dateIssued;
	private String type;
	private HashMap<String, LabObservation> labObservations;
	private ArrayList<String> labObservationIds;
	private HashMap<String, String> surveyResults;
	
	public PatientReport(String id, String dateIssued, String type) {
		this.id = id;
		this.dateIssued = dateIssued;
		this.type = type;
	}
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setObservations(HashMap<String, LabObservation>observationList) {
		this.labObservations = observationList;
		
	}

	@Override
	public HashMap<String, LabObservation> getObservations() {
		// TODO Auto-generated method stub
		return this.labObservations;
	}

	@Override
	public String getDate() {
		return this.dateIssued;
	}

	@Override
	public void setObservationId(ArrayList<String> observationIdList) {
		this.labObservationIds = observationIdList;
		
	}

	@Override
	public ArrayList<String> getObservationId() {
		
		return this.labObservationIds;
	}

	@Override
	public void setSurveyResult(HashMap<String, String> surveyResults) {
		this.surveyResults = surveyResults;
		
	}

	@Override
	public HashMap<String, String> getSurveyResult() {
		return this.surveyResults;
	}
}
