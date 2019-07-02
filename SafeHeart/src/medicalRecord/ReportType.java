package medicalRecord;

public enum ReportType {
	
	/**
	 * the following values are from the server.
	 * in case, where the server types changes, only these values need to be changed 
	 * and it will be reflected elsewhere
	 */
	BloodCount("Complete blood count (hemogram) panel - Blood by Automated count"),
	LipidPanel("Lipid Panel"),
	MetabolicPanel("Basic Metabolic Panel"),
	Vitals("Vitals"),
	CombinedCommonReport("Combined Common Report");
	
	private String action;
	
	public String getAction() {
		return this.action;
	}
	
	private ReportType(String action) {
		this.action = action;
	}
}
