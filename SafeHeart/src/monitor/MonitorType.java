package monitor;

public enum MonitorType {
	
	NumericalMonitor("Numerical Monitor"),
	GraphicalMonitor("Graphical Monitor");

	
	private String action;
	
	public String getAction() {
		return this.action;
	}
	
	private MonitorType(String action) {
		this.action = action;
	}
}
