package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.parttimehumans.safeheart.Client;

import medicalRecord.LabObservation;
import medicalRecord.ObservationType;
import medicalRecord.Report;
import medicalRecord.ReportType;
import monitor.GraphicalMonitor;
import monitor.Monitor;
import monitor.MonitorType;
import monitor.NumericalMonitor;
import user.Patient;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JScrollBar;

/**
 * Page to display all the observations from the selected report of the patients
 */

public class PatientListView extends JPanel {
	public int allMonCount = 0;
	ReportType viewReportType;

	/**
	 * Create the panel.
	 */
	public PatientListView(ReportType type) {
		this.viewReportType = type;

		this.initialize();
	}
	
	/*
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{188, 0, 204, 0};
		gridBagLayout.rowHeights = new int[]{45, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		//Add labels
		JLabel label_1 = new JLabel("SafeHeart");
		label_1.setFont(new Font("Lucida Handwriting", Font.BOLD, 38));
		
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 0;
		add(label_1, gbc_label_1);
		
		JLabel lblListOfPatients = new JLabel("List Of Patients");
		lblListOfPatients.setForeground(new Color(0, 0, 128));
		lblListOfPatients.setFont(new Font("Georgia", Font.PLAIN, 30));
		GridBagConstraints gbc_lblListOfPatients = new GridBagConstraints();
		gbc_lblListOfPatients.insets = new Insets(0, 0, 5, 5);
		gbc_lblListOfPatients.anchor = GridBagConstraints.WEST;
		gbc_lblListOfPatients.gridx = 0;
		gbc_lblListOfPatients.gridy = 1;
		
		add(lblListOfPatients, gbc_lblListOfPatients);
		
		//button to link to dashboard
		
		System.out.println(App.user.getMonitorList());
		JButton btnMonitor = new JButton("Dashboard");
		GridBagConstraints gbc_btnMonitor = new GridBagConstraints();
		gbc_btnMonitor.insets = new Insets(0, 0, 5, 0);
		gbc_btnMonitor.gridx = 1;
		gbc_btnMonitor.gridy = 1;

			
		btnMonitor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.showDashboard();
			}
		});
		
		add(btnMonitor, gbc_btnMonitor);
		

		//Dynamically create Patient buttons
		int gridcount=3;
		int varcount=0;
		
		ArrayList<Monitor> clinicianMonitors = new  ArrayList<Monitor>(); //Observations that can be added 
		
		for(Patient patient3 : App.user.getPatientList()) {
			//For each patient within the Clinician's list of Patients
			if(patient3.getReports().containsKey(viewReportType.getAction())) {
				
				// Patient ID
				JLabel patientIdLabel = new JLabel(patient3.getId());
				patientIdLabel.setForeground(new Color(255, 0, 0));
				patientIdLabel.setFont(new Font("Georgia", Font.BOLD, 17));
				
				GridBagConstraints patientIdLabelConst = new GridBagConstraints();
				patientIdLabelConst.anchor = GridBagConstraints.WEST;
				patientIdLabelConst.insets = new Insets(0, 0, 5, 5);
				patientIdLabelConst.gridx = 0;
				patientIdLabelConst.gridy = gridcount;
				
				add(patientIdLabel, patientIdLabelConst);
				
				/**
				 * Display Report details
				 */
				Report report = patient3.getReport(viewReportType);
				
				String reportTypeDate = "Report:" + report.getType() + " " + report.getDate();
				
				// Report type
				JLabel reportTypeLabel = new JLabel(reportTypeDate);
				reportTypeLabel.setForeground(new Color(0, 0, 0));
				reportTypeLabel.setFont(new Font("Georgia", Font.PLAIN, 15));
				
				GridBagConstraints reportTypeLabelConst = new GridBagConstraints();
				reportTypeLabelConst.anchor = GridBagConstraints.WEST;
				reportTypeLabelConst.insets = new Insets(0, 0, 5, 5);
				reportTypeLabelConst.gridx = 0;
				reportTypeLabelConst.gridy = gridcount+1;
				
				add(reportTypeLabel, reportTypeLabelConst);
				
				// Graphical Monitor button
				JButton addGraphicalMonitorBtn = new JButton("Add Graphical Monitor");
				
				GridBagConstraints addGraphicalMonitorBtnConsnt = new GridBagConstraints();
				addGraphicalMonitorBtnConsnt.anchor = GridBagConstraints.WEST;
				addGraphicalMonitorBtnConsnt.insets = new Insets(0, 0, 5, 5);
				addGraphicalMonitorBtnConsnt.gridx = 1;
				addGraphicalMonitorBtnConsnt.gridy = gridcount+1;
				
				addGraphicalMonitorBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// create the monitor class
						GraphicalMonitor monitor = new GraphicalMonitor(report.getType(), 
								patient3.getId(), 
								MonitorType.GraphicalMonitor.getAction());
						
						monitor.setReport(report);
						monitor.setValue("");
						monitor.setUnit("");
						
						// set the observer fetch service
						monitor.setFetchService(App.api);
						
						// attach monitor to the subject -- fetchService
						App.api.attach(monitor);
						
						HashMap<String, LabObservation> observ = report.getObservations();

						App.addMonitorToUser(patient3.getId() ,monitor);
						
						System.out.println("Add graphical monitor pressed");
					}
					
				});
				
				add(addGraphicalMonitorBtn, addGraphicalMonitorBtnConsnt);
				
				
				/**
				 * Display Observations belonging to the report
				 */
				HashMap<String, LabObservation> observ = report.getObservations();
				
				int obsgrid=2;
				int obsCount = 0;
				
				for(LabObservation obsDet: observ.values()) {
					System.out.println(obsgrid);
					// get the observation
					
					String observation = obsDet.getType() + ": " ;
					
					// Observation 
					JLabel observationLabel = new JLabel(observation);
					observationLabel.setForeground(new Color(0, 0, 0));
					observationLabel.setFont(new Font("Georgia", Font.BOLD, 13));
					
					GridBagConstraints observationLabelConst = new GridBagConstraints();
					observationLabelConst.anchor = GridBagConstraints.WEST;
					observationLabelConst.insets = new Insets(0, 0, 5, 5);
					observationLabelConst.gridx = 0;
					observationLabelConst.gridy = gridcount+obsgrid;
					
					add(observationLabel, observationLabelConst);
					
					// Observation 
					JLabel observationValLabel = new JLabel(obsDet.getValue() + " " + obsDet.getUnit());
					observationValLabel.setForeground(new Color(0, 0, 0));
					observationValLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
					
					GridBagConstraints observationValLabelConst = new GridBagConstraints();
					observationValLabelConst.anchor = GridBagConstraints.WEST;
					observationValLabelConst.insets = new Insets(0, 0, 5, 5);
					observationValLabelConst.gridx = 1;
					observationValLabelConst.gridy = gridcount+obsgrid;
					
					add(observationValLabel, observationValLabelConst);
					
					// Numerical Monitor button
					JButton addNumericalMonitorBtn = new JButton("Add Numerical Monitor");
					
					GridBagConstraints addNumMonitorBtnConst = new GridBagConstraints();
					addNumMonitorBtnConst.anchor = GridBagConstraints.WEST;
					addNumMonitorBtnConst.insets = new Insets(0, 0, 5, 5);
					addNumMonitorBtnConst.gridx = 2;
					addNumMonitorBtnConst.gridy = gridcount+obsgrid;
					
					addNumericalMonitorBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							// create the monitor class
							NumericalMonitor monitor = new NumericalMonitor(obsDet.getId(), 
									patient3.getId(), 
									MonitorType.NumericalMonitor.getAction());
							
							monitor.setValue(obsDet.getValue());
							monitor.setUnit(obsDet.getUnit());
							monitor.setFetchService(App.api);
							
							// attach monitor to the subject -- fetchService
							App.api.attach(monitor);
							
							// add monitor to practitioner list
							App.addMonitorToUser(patient3.getId() ,monitor);
							
							System.out.println("Add numerical monitor pressed");
						}
						
					});
					
					add(addNumericalMonitorBtn, addNumMonitorBtnConst);
					
					obsgrid += 1;
					obsCount += 1;
				}
				gridcount += obsCount + 2;
			}
		}
	}
}
