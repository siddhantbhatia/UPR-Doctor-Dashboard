package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import medicalRecord.ObservationType;
import medicalRecord.ReportType;
import monitor.Monitor;
import observerPattern.Observer;
import user.Clinician;
import user.Patient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import webservice.FetchService;

import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class App extends JFrame implements Observer {
	
	// models
	public static Clinician user;
	public ReportType newtype;
	
	// services
	public static FetchService api = new FetchService();
	
	// views
	public static JPanel Login = new Login();;
	public static JPanel Dashboard;
	public static JPanel ReportTypeSelectionPage = new ReportTypeSelection(); 
	public static JPanel CombineMonitorSelectionView;
	public static JPanel ListPage;
	public static JPanel grahicalMonitorView;
	public static JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		api.attach(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		contentPane.add(Login, "name_53165299375634");		
		contentPane.add(ReportTypeSelectionPage,"name_53173218062166");
	}
	
	// server fetches
	public static void fetchLabObservation(ReportType type) {
		api.refreshLabObservation(user.getPatientList(), type);
		System.out.println(user.getPatientList());
	}
	
	public static void fetchVitalObservation(ArrayList<String> observationList) {
		api.refreshVitalObservations(user.getPatientList(), observationList);
	}
	
	public static void addMonitorToUser(String patientId, Monitor monitor) {
		user.addMonitor(patientId, monitor);
	}
	public static void removeMonitorFromUser(String patientId, Monitor monitor) {
		user.removeMonitor(patientId, monitor);
		Dashboard.setVisible(false);
		Dashboard.setVisible(true);
	}
	
	// routing methods
	public static void login(String userId) {
		user = new Clinician(userId);
		user.setPatientList(api.fetchPatients(userId));
		System.out.println("Length");
		System.out.print(user.getPatientList().size());
		
		Dashboard = new Dashboard();
		contentPane.add(Dashboard, "name_53211997005490");
		
		Login.setVisible(false);
		Dashboard.setVisible(true);
		
		// start the api refresh service
		api.startService();
	}
	
	public static void showList(ReportType anyType) {
		ListPage = new PatientListView(anyType);
		contentPane.add(ListPage,"name_53173218062168");
		ReportTypeSelectionPage.setVisible(false);
		ListPage.setVisible(true);
	}
	
	public static void showDashboard() {
		contentPane.remove(Dashboard);		
		Dashboard = new Dashboard();
		contentPane.add(Dashboard, "name_53211997005490");
		
		System.out.println(user.getMonitorList());
		
		if(ListPage != null) {
			ListPage.setVisible(false);
		}
		
		if(grahicalMonitorView != null) {
			grahicalMonitorView.setVisible(false);
		}

		Dashboard.setVisible(true);
	}
	
	public static void showReportTypeSelection() {
		Dashboard.setVisible(false);
		ReportTypeSelectionPage.setVisible(true);
	}
	
	public static void showListVitals(ReportType anyType) {
		ListPage = new PatientListView(anyType);
		contentPane.add(ListPage,"name_53173218062168");
		ReportTypeSelectionPage.setVisible(false);
		ListPage.setVisible(true);
	}
	
	public static void showGraphicalMonitorView(Monitor monitor) {
		grahicalMonitorView = new GraphView(monitor);
		contentPane.add(grahicalMonitorView, "graph_748934r");
		
		System.out.println("here");
		
		if(Dashboard.isVisible()) {
			Dashboard.setVisible(false);
		}

		if(CombineMonitorSelectionView != null) {
			contentPane.remove(CombineMonitorSelectionView);
			CombineMonitorSelectionView.setVisible(false);
		}
		
		grahicalMonitorView.setVisible(true);
	}	
	
	public static void showMonitorCombineSelectionView(HashMap<String, ArrayList<Monitor>> userMonitorList) {
		CombineMonitorSelectionView = new CombineMonitorSelectionView(userMonitorList);
		contentPane.add(CombineMonitorSelectionView, "monitorSelectionView_28271645");
		
		Dashboard.setVisible(false);
		CombineMonitorSelectionView.setVisible(true);
	}
	
	public static void refreshDashboard() {
		Boolean isCurrentlyVisible = false;
		if(Dashboard.isVisible()) {
			isCurrentlyVisible = true;
		}
		
		contentPane.remove(Dashboard);		
		Dashboard = new Dashboard();
		contentPane.add(Dashboard, "name_53211997005490");
		
		if(isCurrentlyVisible) {
			Login.setVisible(false);
			Dashboard.setVisible(true);
		}
	}

	@Override
	public void update() {
		refreshDashboard();
		
	}
}
