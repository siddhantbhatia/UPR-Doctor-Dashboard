package view;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class spikegraph extends JPanel {

	/**
	 * Create the panel.
	 */
	public spikegraph() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		LineChart_AWT(
				"School Vs Years" ,
				"Numer of Schools vs years");

	}
	
	public void LineChart_AWT( String applicationTitle , String chartTitle ) {
		JFreeChart lineChart = ChartFactory.createLineChart(
				         chartTitle,
				         "Years","Number of Schools",
				         createDataset(),
				         PlotOrientation.VERTICAL,
				         true,true,false);
		
		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		
		
		GridBagConstraints dashboardLabelConst = new GridBagConstraints();
		dashboardLabelConst.insets = new Insets(0, 0, 5, 5);
		dashboardLabelConst.anchor = GridBagConstraints.WEST;
		dashboardLabelConst.gridx = 1;
		dashboardLabelConst.gridy = 1;
		
		add( chartPanel, dashboardLabelConst );
		}
	
	private DefaultCategoryDataset createDataset( ) {
		      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		      dataset.addValue( 15 , "schools" , "1970" );
		      dataset.addValue( 30 , "schools" , "1980" );
		      dataset.addValue( 60 , "schools" ,  "1990" );
		      dataset.addValue( 120 , "schools" , "2000" );
		      dataset.addValue( 240 , "schools" , "2010" );
		      dataset.addValue( 300 , "schools" , "2014" );
		      
		      dataset.addValue( 23 , "abc" , "1940" );
		      dataset.addValue( 526 , "abc" , "1980" );
		      dataset.addValue( 45 , "abc" ,  "2003" );
		      dataset.addValue( 34 , "abc" , "2010" );
		      dataset.addValue( 4 , "abc" , "2010" );
		      dataset.addValue( 34 , "abc" , "2005" );
		      return dataset;
		   }


}
