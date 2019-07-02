package medicalRecord;

public enum ObservationType {
	
	/**
	 * the following values are from the server.
	 * in case, where the server types changes, only these values need to be changed 
	 * and it will be reflected elsewhere
	 */
	BloodPressure("Blood Pressure"),
	SystolicBloodPressure("Systolic Blood Pressure"),
	DiastolicBloodPressure("Diastolic Blood Pressure"),
	BodyWeight("Body Weight"),
	BodyHeight("Body Height"),
	BMS("Body Mass Index"),
	Tobacco("Tobacco smoking status NHIS");

	
	private String action;
	
	public String getAction() {
		return this.action;
	}
	
	private ObservationType(String action) {
		this.action = action;
	}

}
