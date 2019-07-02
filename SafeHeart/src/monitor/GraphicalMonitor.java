package monitor;
import medicalRecord.LabObservation;
import medicalRecord.Report;

import java.sql.Timestamp;
import java.util.List;

public class GraphicalMonitor extends Monitor {
	
	Report patientReport;
	List<String[]> hist;
	public GraphicalMonitor(String id, String patientId, String type) {
		super(id, patientId, type);
		this.refreshLastUpdatedTime();
	}
	
	public void setReport(Report report) {
		this.patientReport = report;
	}
	
	public Report getReport() {
		return this.patientReport;
	}
	
	public String displayMonitor() {
		for(LabObservation obs: this.patientReport.getObservations().values()) {
			System.out.println("Observation: \n");
			for(String[] timeValueArray : obs.getHistoricValues()) {
				System.out.println("----");
				for(String value : timeValueArray) {
					System.out.println(value);
				}
				
			}
		}
		
		return this.id;
	}

	@Override
	public void update() {
		this.patientReport = this.fetchService.refreshGraphicalMonitor(patientId, patientReport);
		this.refreshLastUpdatedTime();
	}

}
