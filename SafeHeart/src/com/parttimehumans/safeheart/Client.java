package com.parttimehumans.safeheart;

import java.util.ArrayList;
import java.util.HashMap;

import medicalRecord.LabObservation;
import medicalRecord.Report;
import medicalRecord.ReportType;
import user.Clinician;
import user.Patient;
import webservice.*;

/**
 * @deprecated
 * @author Sid
 *
 */
public class Client {
	
	public static FetchService api = new FetchService();
	//public static Clinician user;
	
	public Client(String userId) {	
		
		ArrayList<String> observationTypes = new ArrayList<String>();
		observationTypes.add("Tobacco");
		observationTypes.add("Body Weight");
		

		
		HashMap<String, LabObservation> obs = api.testVitalFetch("130911", observationTypes);
		
		System.out.println(obs.get("Body Weight").getValue());
		
		
	}
}
