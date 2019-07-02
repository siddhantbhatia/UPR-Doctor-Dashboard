package medicalRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientLabObservation implements LabObservation {
	
	private String id;
	private String type;
	
	private String value;
	private String unit;
	
	private List<String[]> historicValues;
	
	public PatientLabObservation(String id, String type) {
		this.id = id;
		this.type = type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getUnit() {
		return unit;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
		
	}

	@Override
	public void setUnit(String unit) {
		this.unit = unit;
		
	}

	@Override
	public List<String[]> getHistoricValues() {
		return this.historicValues;
	}

	@Override
	public void setHistoricValues(List<String[]> values) {
		this.historicValues = values;
		
	}
}
