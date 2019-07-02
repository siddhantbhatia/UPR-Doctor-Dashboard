package monitor;

import java.sql.Timestamp;

public class NumericalMonitor extends Monitor {

	public NumericalMonitor(String id, String patientId, String type) {
		super(id, patientId, type);
		this.refreshLastUpdatedTime();
	}

	@Override
	public String displayMonitor() {
		return this.value + " " + this.unit;
	}

	@Override
	public void update() {
		this.value = this.fetchService.refreshNumericalMonitor(this.patientId, this.id);
		this.refreshLastUpdatedTime();
	}
}
