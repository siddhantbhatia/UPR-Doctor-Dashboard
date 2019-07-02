package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import medicalRecord.ObservationType;
import medicalRecord.ReportType;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ReportTypeSelection extends JPanel {

	/**
	 * Create the application.
	 */
	public ReportTypeSelection() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{206, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{45, 35, 29, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		//create labels
		JLabel label = new JLabel("SafeHeart");
		label.setFont(new Font("Lucida Handwriting", Font.BOLD, 38));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTH;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridwidth = 3;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		JLabel lblPatient = new JLabel("Choose the report type to view");
		lblPatient.setForeground(new Color(0, 0, 128));
		lblPatient.setFont(new Font("Georgia", Font.PLAIN, 30));
		GridBagConstraints gbc_lblPatient = new GridBagConstraints();
		gbc_lblPatient.anchor = GridBagConstraints.NORTH;
		gbc_lblPatient.insets = new Insets(0, 0, 5, 0);
		gbc_lblPatient.gridwidth = 3;
		gbc_lblPatient.gridx = 0;
		gbc_lblPatient.gridy = 1;
		add(lblPatient, gbc_lblPatient);
		
		JButton btnBloodLevelMonitor = new JButton(ReportType.BloodCount.getAction());
		
		btnBloodLevelMonitor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//upon click fetch bloodcount
				App.fetchLabObservation(ReportType.BloodCount);
				App.showList(ReportType.BloodCount);
			}
		});
		
		//offer report selections
		JButton btnCholesterolMonitor = new JButton(ReportType.LipidPanel.getAction());
		
		btnCholesterolMonitor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//upon click, fetch lipidpanel data
				App.fetchLabObservation(ReportType.LipidPanel);
				App.showList(ReportType.LipidPanel);
			}
		});
		
		JLabel lblLabReports = new JLabel("Lab Reports");
		lblLabReports.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblLabReports = new GridBagConstraints();
		gbc_lblLabReports.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabReports.gridx = 0;
		gbc_lblLabReports.gridy = 2;
		add(lblLabReports, gbc_lblLabReports);
		GridBagConstraints gbc_btnCholesterolMonitor = new GridBagConstraints();
		gbc_btnCholesterolMonitor.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnCholesterolMonitor.insets = new Insets(0, 0, 5, 5);
		gbc_btnCholesterolMonitor.gridx = 0;
		gbc_btnCholesterolMonitor.gridy = 3;
		add(btnCholesterolMonitor, gbc_btnCholesterolMonitor);
		
		JButton btnMetabolicMonitor = new JButton(ReportType.MetabolicPanel.getAction());
		
		btnMetabolicMonitor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//upon click, fetch metabolic panel
				App.fetchLabObservation(ReportType.MetabolicPanel);
				App.showList(ReportType.MetabolicPanel);
			}
		});
		
		GridBagConstraints gbc_btnMetabolicMonitor = new GridBagConstraints();
		gbc_btnMetabolicMonitor.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnMetabolicMonitor.insets = new Insets(0, 0, 5, 0);
		gbc_btnMetabolicMonitor.gridx = 2;
		gbc_btnMetabolicMonitor.gridy = 3;
		add(btnMetabolicMonitor, gbc_btnMetabolicMonitor);
		GridBagConstraints gbc_btnBloodLevelMonitor = new GridBagConstraints();
		gbc_btnBloodLevelMonitor.insets = new Insets(0, 0, 5, 0);
		gbc_btnBloodLevelMonitor.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnBloodLevelMonitor.gridwidth = 3;
		gbc_btnBloodLevelMonitor.gridx = 0;
		gbc_btnBloodLevelMonitor.gridy = 4;
		add(btnBloodLevelMonitor, gbc_btnBloodLevelMonitor);
		
		JLabel lblVitals = new JLabel("Vitals");
		lblVitals.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblVitals = new GridBagConstraints();
		gbc_lblVitals.insets = new Insets(0, 0, 5, 5);
		gbc_lblVitals.gridx = 0;
		gbc_lblVitals.gridy = 7;
		add(lblVitals, gbc_lblVitals);
		JCheckBox vital1 = new JCheckBox();
		vital1.setText("Blood Pressure");
		GridBagConstraints gbc_vital1 = new GridBagConstraints();
		gbc_vital1.insets = new Insets(0, 0, 5, 5);
		gbc_vital1.gridx = 0;
		gbc_vital1.gridy = 8;
		add(vital1, gbc_vital1);
		JCheckBox vital3 = new JCheckBox();
		vital3.setText("Body Weight");
		GridBagConstraints gbc_vital3 = new GridBagConstraints();
		gbc_vital3.insets = new Insets(0, 0, 5, 5);
		gbc_vital3.gridx = 1;
		gbc_vital3.gridy = 8;
		add(vital3, gbc_vital3);
		JCheckBox vital2 = new JCheckBox();
		vital2.setText("Body Height");
		GridBagConstraints gbc_vital2 = new GridBagConstraints();
		gbc_vital2.insets = new Insets(0, 0, 5, 5);
		gbc_vital2.gridx = 0;
		gbc_vital2.gridy = 9;
		add(vital2, gbc_vital2);
		JCheckBox vital4 = new JCheckBox();
		vital4.setText("Body Mass Index");
		GridBagConstraints gbc_vital4 = new GridBagConstraints();
		gbc_vital4.insets = new Insets(0, 0, 5, 5);
		gbc_vital4.gridx = 1;
		gbc_vital4.gridy = 9;
		add(vital4, gbc_vital4);
		
		JLabel lblSurveyResults = new JLabel("Survey Results");
		lblSurveyResults.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblSurveyResults = new GridBagConstraints();
		gbc_lblSurveyResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurveyResults.gridx = 0;
		gbc_lblSurveyResults.gridy = 11;
		add(lblSurveyResults, gbc_lblSurveyResults);
		
		JCheckBox chckbxTobacco = new JCheckBox("Tobacco smoking status NHIS");
		GridBagConstraints gbc_chckbxTobacco = new GridBagConstraints();
		gbc_chckbxTobacco.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTobacco.gridx = 0;
		gbc_chckbxTobacco.gridy = 12;
		add(chckbxTobacco, gbc_chckbxTobacco);
		
		JButton btnGetVitals = new JButton("Get Vitals");
		btnGetVitals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> vitalsList = new  ArrayList<String>();
				
				if (vital1.isSelected()) {
					vitalsList.add(vital1.getText());
				}
				if (vital2.isSelected()) {
					vitalsList.add(vital2.getText());
				}
				if (vital3.isSelected()) {
					vitalsList.add(vital3.getText());
				}
				if (vital4.isSelected()) {
					vitalsList.add(vital4.getText());
				}
				if(chckbxTobacco.isSelected()) {
					vitalsList.add(chckbxTobacco.getText());
				}
				
				for(String vital: vitalsList) {
					System.out.println(vital);
				}
				
				App.fetchVitalObservation(vitalsList);
				App.showList(ReportType.Vitals);
			}
		});
		GridBagConstraints gbc_btnGetVitals = new GridBagConstraints();
		gbc_btnGetVitals.gridx = 2;
		gbc_btnGetVitals.gridy = 13;
		add(btnGetVitals, gbc_btnGetVitals);
	}
}
