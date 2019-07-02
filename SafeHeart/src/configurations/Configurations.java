package configurations;

public enum Configurations {
	BaseURL("[FHIR Server URL]");
	
	private String action;
	
	public String getAction() {
		return this.action;
	}
	
	private Configurations(String action) {
		this.action = action;
	}
}
