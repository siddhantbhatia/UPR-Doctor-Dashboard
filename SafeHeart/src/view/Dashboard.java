package view;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Graphics;
import com.parttimehumans.safeheart.Client;
import monitor.NumericalMonitor;
import monitor.GraphicalMonitor;
import monitor.Monitor;
import monitor.MonitorType;
import user.Clinician;
import user.Patient;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComponent;
import medicalRecord.LabObservation;
import medicalRecord.Report;
import medicalRecord.ReportType;
import java.awt.GridBagLayout;
public class Dashboard extends JPanel {

	/**
	 * Create the panel.
	 */
	int x,y;
	int ax,by;
	public Dashboard() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Application label
		JLabel appLabel = new JLabel("SafeHeart");
		appLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 38));
		
		GridBagConstraints appLabelConst = new GridBagConstraints();
		appLabelConst.anchor = GridBagConstraints.NORTHWEST;
		appLabelConst.insets = new Insets(0, 0, 5, 5);
		appLabelConst.gridx = 0;
		appLabelConst.gridy = 0;
		add(appLabel, appLabelConst);
		
		// Dashboard title label
		JLabel dashboardLabel = new JLabel("Dashboard");
		dashboardLabel.setForeground(new Color(0, 0, 128));
		dashboardLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
		
		GridBagConstraints dashboardLabelConst = new GridBagConstraints();
		dashboardLabelConst.insets = new Insets(0, 0, 0, 5);
		dashboardLabelConst.anchor = GridBagConstraints.WEST;
		dashboardLabelConst.gridx = 0;
		dashboardLabelConst.gridy = 1;
		
		add(dashboardLabel, dashboardLabelConst);
		
		// add monitor button to link to the patient list view page
		JButton addMonitorsButton = new JButton("Add Monitors");
		GridBagConstraints gbc_addMonsButton = new GridBagConstraints();
		
		gbc_addMonsButton.insets = new Insets(0, 0, 0, 5);
		gbc_addMonsButton.gridx = 2;
		gbc_addMonsButton.gridy = 1;
		
		addMonitorsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.showReportTypeSelection();
				System.out.println("add monitor clicked");
			}
		});	
		
		add(addMonitorsButton, gbc_addMonsButton);
		
		if(App.user.getMonitorList().size() > 0) {
			JButton btnCombineMonitor = new JButton("Combine Monitor");
			btnCombineMonitor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					App.showMonitorCombineSelectionView(App.user.getMonitorList());
				}
			});
			GridBagConstraints gbc_btnCombineMonitor = new GridBagConstraints();
			gbc_btnCombineMonitor.gridx = 3;
			gbc_btnCombineMonitor.gridy = 1;
			add(btnCombineMonitor, gbc_btnCombineMonitor);
		}
		
		int monCount =0; //Count of monitors within getMonitorList it access arraylist element
		int gridyVal = 2; //Counter to position content in the y axis
	

		System.out.println(App.user.getMonitorList().size());
		
		for(ArrayList<Monitor> patientMonitors: App.user.getMonitorList().values()) {
			
			for (Monitor eachMon: patientMonitors) {
				
				String monitorDetails = eachMon.getPatientId() + " " + eachMon.getType() + ": ";


				//Patient ID 
				JLabel monitorDetailLabel = new JLabel(monitorDetails);	
				monitorDetailLabel.setForeground(new Color(0, 0, 0));
				monitorDetailLabel.setFont(new Font("Georgia", Font.BOLD, 15));
				
				GridBagConstraints monitorDetailLblConst = new GridBagConstraints();
				monitorDetailLblConst.anchor = GridBagConstraints.WEST;
				monitorDetailLblConst.insets = new Insets(0, 0, 5, 5);
				monitorDetailLblConst.gridx = 1;
				monitorDetailLblConst.gridy = gridyVal;
				
				add(monitorDetailLabel, monitorDetailLblConst);
				
				//Patient ID 
				JLabel monitorValueLabel = new JLabel(eachMon.displayMonitor());	
				monitorValueLabel.setForeground(new Color(0,0,128));
				monitorValueLabel.setFont(new Font("Georgia", Font.BOLD, 13));
				
				GridBagConstraints monitorValueDetailLblConst = new GridBagConstraints();
				monitorValueDetailLblConst.anchor = GridBagConstraints.WEST;
				monitorValueDetailLblConst.insets = new Insets(0, 0, 5, 5);
				monitorValueDetailLblConst.gridx = 2;
				monitorValueDetailLblConst.gridy = gridyVal;
				
				add(monitorValueLabel, monitorValueDetailLblConst);
				
				//Remove Monitor button
				JButton removeMonitorBtn = new JButton("Remove Monitor");
				
				GridBagConstraints removeMonitorBtnConst = new GridBagConstraints();
				removeMonitorBtnConst.anchor = GridBagConstraints.WEST;
				removeMonitorBtnConst.insets = new Insets(0, 0, 5, 5);
				removeMonitorBtnConst.gridx = 3;
				removeMonitorBtnConst.gridy = gridyVal;
				
				removeMonitorBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("remove monitor clicked");
						
						// detach the observer monitor from the subject
						App.api.detach(eachMon);
						
						App.removeMonitorFromUser(eachMon.getPatientId(), eachMon);
						
						App.refreshDashboard();
					}
					
				});
				
				add(removeMonitorBtn, removeMonitorBtnConst);
				
				if(eachMon.getType() == MonitorType.GraphicalMonitor.getAction()) {					
					JButton viewVitalObservation = new JButton("View");
					
					GridBagConstraints viewVitalObservationConst = new GridBagConstraints();
					viewVitalObservationConst.anchor = GridBagConstraints.WEST;
					viewVitalObservationConst.insets = new Insets(0, 0, 5, 5);
					viewVitalObservationConst.gridx = 4;
					viewVitalObservationConst.gridy = gridyVal;
					
					viewVitalObservation.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicalMonitor monitor = (GraphicalMonitor) eachMon;

							App.showGraphicalMonitorView(monitor);
						}
						
					});
					add(viewVitalObservation, viewVitalObservationConst);
				}
				monCount=monCount+1;
				gridyVal += 1;
			}
			
		}
	}
}
