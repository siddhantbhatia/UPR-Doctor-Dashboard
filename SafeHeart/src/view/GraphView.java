package view;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import medicalRecord.LabObservation;
import medicalRecord.ObservationType;
import medicalRecord.PatientReport;
import medicalRecord.Report;
import monitor.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

public class GraphView extends JPanel {
	
	GraphicalMonitor monitor;

	/**
	 * Create the panel.
	 */
	public GraphView(Monitor monitor) {
		this.monitor = (GraphicalMonitor) monitor;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		String surveyResult;
		
		if(this.monitor.getReport().getObservations().containsKey(ObservationType.Tobacco.getAction())) {
		surveyResult = this.monitor.getReport()
				.getObservations()
				.get(ObservationType.Tobacco.getAction())
				.getValue();
		}
		else {
			surveyResult = "No survey results for this report";
		}
		
		// Survey result label
		JLabel surveyResultLabel = new JLabel(ObservationType.Tobacco.getAction() + ": " + surveyResult);
		surveyResultLabel.setForeground(new Color(0, 0, 128));
		surveyResultLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		GridBagConstraints surveyResultLabelConst = new GridBagConstraints();
		surveyResultLabelConst.insets = new Insets(0, 0, 5, 5);
		surveyResultLabelConst.anchor = GridBagConstraints.WEST;
		surveyResultLabelConst.gridx = 1;
		surveyResultLabelConst.gridy = 0;
		
		add(surveyResultLabel, surveyResultLabelConst);
		
		// Close view button
		JButton closeButton = new JButton("Close");
		GridBagConstraints gbc_closeButton = new GridBagConstraints();
		gbc_closeButton.gridx = 2;
		gbc_closeButton.gridy = 0;
		add(closeButton, gbc_closeButton);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.showDashboard();
			}
		});	
		
		// last refresh date label
		JLabel lastRefreshDetailLabel = new JLabel("Last refreshed: " + this.monitor.getLastUpdatedTime());	
		lastRefreshDetailLabel.setForeground(new Color(0, 0, 128));
		lastRefreshDetailLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		GridBagConstraints lastRefreshDetailLblConst = new GridBagConstraints();
		lastRefreshDetailLblConst.anchor = GridBagConstraints.CENTER;
		lastRefreshDetailLblConst.insets = new Insets(0, 0, 5, 5);
		lastRefreshDetailLblConst.gridx = 1;
		lastRefreshDetailLblConst.gridy = 3;
		
		add(lastRefreshDetailLabel, lastRefreshDetailLblConst);
		
		
		// adding label to show signs of hypertensivity
		String warningText = "";
		if(this.monitor.getReport().getObservations().containsKey(ObservationType.SystolicBloodPressure.getAction())) {
			double systolicBpVal = Double.parseDouble(this.monitor
					.getReport()
					.getObservations().get(ObservationType.SystolicBloodPressure.getAction())
					.getValue());
			
			if (systolicBpVal > 180) {
				warningText += "Systolic Blood Pressure > 180";
			}
		}
		if(this.monitor.getReport().getObservations().containsKey(ObservationType.DiastolicBloodPressure.getAction())) {
			double diastolicBpVal = Double.parseDouble(this.monitor
					.getReport()
					.getObservations().get(ObservationType.DiastolicBloodPressure.getAction())
					.getValue());
			
			if (diastolicBpVal > 120) {
				warningText += "  Diastolic Blood Pressure > 120";
			}
		}

		// last refresh date label
		JLabel warningLabel = new JLabel(warningText);	
		warningLabel.setForeground(new Color(255, 0, 0));
		warningLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		GridBagConstraints warningLabelConst = new GridBagConstraints();
		warningLabelConst.anchor = GridBagConstraints.CENTER;
		warningLabelConst.insets = new Insets(0, 0, 5, 5);
		warningLabelConst.gridx = 1;
		warningLabelConst.gridy = 2;
		
		add(warningLabel, warningLabelConst);
		
		
		plotTimeSeriesGraph();
	}
	
	public void plotTimeSeriesGraph() {
		JFreeChart lineChart = ChartFactory.createLineChart(
				        "",
				         "Date","Value",
				         createDataset(),
				         PlotOrientation.VERTICAL,
				         true,true,false);
		
		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension(1060 , 467) );
		
		
		GridBagConstraints dashboardLabelConst = new GridBagConstraints();
		dashboardLabelConst.anchor = GridBagConstraints.WEST;
		dashboardLabelConst.gridx = 1;
		dashboardLabelConst.gridy = 2;
		
		add( chartPanel, dashboardLabelConst );
		}
	
	private DefaultCategoryDataset createDataset( ) {
		      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		      
		      Report report = this.monitor.getReport();
		      
		      for(String obsType: report.getObservations().keySet()) {
		    	  
		    	  if(obsType == ObservationType.Tobacco.getAction()) {
		    		  continue;
		    	  }
		    	  
		    	  LabObservation obs = report.getObservations().get(obsType);
		    	  
		    	  List<String[]> observationValues = obs.getHistoricValues();
		    	  
		    	  for(int i = observationValues.size() - 1; i>=0; i--) {
		    		  String[] timeAndValue = observationValues.get(i);
		    		  
						String[] timeSplit = timeAndValue[0].split(" ");
						String time = timeSplit[2]+"-"+timeSplit[1]+"-"+timeSplit[5];
						
						
						dataset.addValue(Double.parseDouble(timeAndValue[1]), obsType, time);
						
						
						System.out.println("----");
						System.out.println(obsType);
						System.out.println(timeAndValue[0]);
						System.out.println(timeAndValue[1]);
		    		  
		    	  }		    	  		    	  
		      }
		      return dataset;
		   }


}
