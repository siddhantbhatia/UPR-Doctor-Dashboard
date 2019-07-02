package medicalRecord;

import java.util.List;

public interface LabObservation {
	public String getId();
	public String getType();	
	public String getValue();	
	public String getUnit();
	public List<String[]> getHistoricValues();
	
	
	public void setValue(String value);
	public void setUnit(String unit);
	public void setHistoricValues(List<String[]> values);
}
