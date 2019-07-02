package com.parttimehumans.safeheart;

import ca.uhn.fhir.context.FhirContext;

import webservice.*;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import medicalRecord.Report;
import medicalRecord.ReportType;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/**
 * @deprecated
 * @author Sid
 *
 */
public class main {

	public static void main(String[] args) {
		
		Client client = new Client("8");
		
//		WebService myServer = new FhirServer();
//		
//		ArrayList<Report> reports = myServer.getPatientReport("18600");
//		
//		System.out.println(reports.toString());
//		
//		System.out.println(reports.get(0).getType());
//		System.out.println(reports.get(0).getObservations().get(3).getType());
		
		
//		mySpike();
    }
	
	public static void mySpike() {
		//SPIKE START IGGNORE
		System.out.println("I am groot");
		
		FhirContext ctx = FhirContext.forDstu3();
		
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3/";

        // increase timeouts since the server might be powered down
        // see http://hapifhir.io/doc_rest_client_http_config.html
        ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

        // create the RESTful client to work with our FHIR server
        // see http://hapifhir.io/doc_rest_client.html
        IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

        System.out.println("Press Enter to send to server: " + serverBaseUrl);
//        System.in.read();
        
        ArrayList<String> patientList = new ArrayList<String>();
        
        final String cholesterolMonitor = "Lipid Panel";

        
        //getting list of patients
        try {
            // perform a search for Patients with last name 'Fhirman'
            // see http://hapifhir.io/doc_rest_client.html#SearchQuery_-_Type
//            Bundle response = client.search()
//                    .forResource(Patient.class)
//                    .where(Patient.FAMILY.matches().values("Fhirman"))
//                    .returnBundle(Bundle.class)
//                    .execute();
        	
        	Bundle response = client.search()
        			.forResource(Encounter.class)
        			.where(Encounter.PRACTITIONER.hasId("3252"))
        			.returnBundle(Bundle.class)
        			.execute();

            System.out.println("Found " + response.getTotal()
                    + " Fhirman patients. Their logical IDs are:");
            response.getEntry().forEach((entry) -> {
                // within each entry is a resource - print its logical ID
//                System.out.println(entry.getResource().getIdElement().getIdPart());
                
            	// each entry is an Encounter object
                Encounter anEncounter = (Encounter) entry.getResource();
            	

                System.out.println(anEncounter.getSubject().getReference());
                patientList.add(anEncounter.getSubject().getReference());  

                
            });
                         
            //getting the distinct elements
             HashSet hs = new HashSet();
             
             hs.addAll(patientList);
             patientList.clear();
             patientList.addAll(hs);
             
             System.out.println(patientList);
             
             // getting the patient record in diagnostic report
             Bundle response2 = client.search()
             		.forResource(DiagnosticReport.class)
             		.where(new ReferenceClientParam("patient").hasId("131656"))
             		.returnBundle(Bundle.class)
             		.execute();
             
            //list of diagnostic reports
             ArrayList<String> patientReports = new ArrayList<String>();
             
            System.out.println("Found" + response2.getTotal());
            response2.getEntry().forEach((entry) -> {
            	DiagnosticReport dr = (DiagnosticReport) entry.getResource();
            	
            	System.out.println(dr.getId());
            	
            	// getting the cholesterol report out of all the reports
            	if(dr.getCode().getText().equals(cholesterolMonitor)) {
            		System.out.println(dr.getCode().getText());
            		List<Reference> results = dr.getResult();
            		
            		System.out.println(results.get(0).getReference());
            		
            		// add observation corresponding to each cholesterol report
            		patientReports.add(results.get(0).getReference());
            	}            	            	            	            	
            	
            });
            
            
            
            //get the cholesterol value
            Observation response3 = client.read()
            		.resource(Observation.class)
            		.withUrl(serverBaseUrl + "Observation/3324")
            		.execute();
            
            
            System.out.println(response3.getValueQuantity().getValue());
            
         
            
            
            
            
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }



        System.out.println("Press Enter to end.");
//        System.in.read();

	}

		
	}

