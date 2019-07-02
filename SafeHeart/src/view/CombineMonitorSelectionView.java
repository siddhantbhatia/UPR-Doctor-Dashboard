package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import medicalRecord.LabObservation;
import medicalRecord.PatientReport;
import medicalRecord.Report;
import medicalRecord.ReportType;
import monitor.GraphicalMonitor;
import monitor.Monitor;
import monitor.MonitorType;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class CombineMonitorSelectionView extends JPanel {
	
	HashMap<String, ArrayList<Monitor>> userMonitorList;

	/**
	 * Create the panel.
	 */
	public CombineMonitorSelectionView(HashMap<String, ArrayList<Monitor>> userMonitorList) {
		this.userMonitorList = userMonitorList;
		
		// Standard labels
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMonitorCombineSelection = new JLabel("Combine Monitor Selection");
		lblMonitorCombineSelection.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		GridBagConstraints gbc_lblMonitorCombineSelection = new GridBagConstraints();
		gbc_lblMonitorCombineSelection.insets = new Insets(0, 0, 5, 0);
		gbc_lblMonitorCombineSelection.gridx = 1;
		gbc_lblMonitorCombineSelection.gridy = 1;
		add(lblMonitorCombineSelection, gbc_lblMonitorCombineSelection);
		
		JLabel lblOnlyChooseMonitor = new JLabel("** Only choose monitor from same patient!");
		lblOnlyChooseMonitor.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblOnlyChooseMonitor = new GridBagConstraints();
		gbc_lblOnlyChooseMonitor.gridx = 1;
		gbc_lblOnlyChooseMonitor.gridy = 2;
		add(lblOnlyChooseMonitor, gbc_lblOnlyChooseMonitor);
		
		
		int patientGridY = 4;
		
		for(String patientId : userMonitorList.keySet()) {
			
			// patient label
			JLabel patientLabel = new JLabel(patientId);
			patientLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
			GridBagConstraints gbc_lblPatient = new GridBagConstraints();
			gbc_lblPatient.gridx = 1;
			gbc_lblPatient.gridy = patientGridY;
			add(patientLabel, gbc_lblPatient);
			
			ArrayList<Monitor> patientMonitorList = userMonitorList.get(patientId);
			ArrayList<JCheckBox> monitorCheckList = new ArrayList<JCheckBox>();
			
			if(patientMonitorList.size() == 0) {
				continue;
			}
			
			int monitorGridY = patientGridY+1;
			int serialno = 1;
			
			for(int j = 0; j < patientMonitorList.size(); j+=1) {
				Monitor monitor = patientMonitorList.get(j);
				
				if(monitor.getType() == MonitorType.GraphicalMonitor.getAction()) {
					JCheckBox monitorCheck = new JCheckBox(serialno + ". " + monitor.getId());
					
					GridBagConstraints gbc_monitorCheck = new GridBagConstraints();
					gbc_monitorCheck.insets = new Insets(0, 0, 5, 5);
					gbc_monitorCheck.gridx = 1;
					gbc_monitorCheck.gridy = monitorGridY;
					add(monitorCheck, gbc_monitorCheck);
					
					monitorCheckList.add(monitorCheck);
					
					monitorGridY += 1;
				}
				
				serialno += 1;
			}
			
			patientGridY = monitorGridY;
			
			JButton combineMonitorBtn = new JButton("Combine");
			GridBagConstraints gbc_btnCombineMonitor = new GridBagConstraints();
			gbc_btnCombineMonitor.insets = new Insets(0, 0, 5, 5);
			gbc_btnCombineMonitor.gridx = 2;
			gbc_btnCombineMonitor.gridy = patientGridY;
			
			combineMonitorBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<String> selectedMonitorTextList = new ArrayList<String>();
					List<Monitor> selectedMonitorList = new ArrayList<Monitor>();
					
					// get the selection index
					for(JCheckBox chkBox: monitorCheckList) {

						if(chkBox.isSelected()) {
							System.out.println(chkBox.getText()+"-");
							String[] index = chkBox.getText().split(". ");
							
							int ind = Integer.parseInt(index[0]) - 1;
							
							selectedMonitorList.add(patientMonitorList.get(Integer.parseInt(index[0]) - 1));
							
							selectedMonitorTextList.add(chkBox.getText());
						}
					}
					
					// convert the selected monitor id to monitor list index
					for(String monitor : selectedMonitorTextList) {
						System.out.print(monitor+"\n");
					}
					
					Monitor combinedMonitor = prepareCommonMonitor(selectedMonitorList);
					
					App.showGraphicalMonitorView(combinedMonitor);
				}
			});
			
			add(combineMonitorBtn, gbc_btnCombineMonitor);

		}
	}
	
	/**
	 * Creates the temporary monitor with the combined reports to be displayed on common graph
	 * @param selectedMonitorList
	 * @return
	 */
	private Monitor prepareCommonMonitor(List<Monitor> selectedMonitorList) {
		// create the common monitor
		GraphicalMonitor commonMonitor = new GraphicalMonitor("common", "common", MonitorType.GraphicalMonitor.getAction());
		
		HashMap<String, LabObservation> combinedObs = new HashMap<String, LabObservation>();
		
		for(Monitor monitor: selectedMonitorList) {
			GraphicalMonitor gMonitor = (GraphicalMonitor) monitor;
			
			Report patientReport = gMonitor.getReport();
			
			// create the cobined report
			combinedObs.putAll(patientReport.getObservations());
		}
		
		Report combinedReport = new PatientReport("_", "", ReportType.CombinedCommonReport.getAction());
		combinedReport.setObservations(combinedObs);
		
		commonMonitor.setReport(combinedReport);
		
		return commonMonitor;
	}
	
	

}
