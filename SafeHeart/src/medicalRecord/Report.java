package medicalRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface Report {
	public String getId();
	
	public String getType();
	
	public String getDate();
	
	public void setObservations(HashMap<String, LabObservation> observationList);
	public HashMap<String, LabObservation> getObservations();
	
	public void setObservationId(ArrayList<String> observationIdList);
	public ArrayList<String> getObservationId();
	
	public void setSurveyResult(HashMap<String, String> surveyResults);
	public HashMap<String, String> getSurveyResult(); 
}
