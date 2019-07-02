package webservice;

import java.util.ArrayList;
import java.util.HashMap;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import configurations.Configurations;
import medicalRecord.*;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.DiagnosticReport;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Observation.ObservationComponentComponent;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.exceptions.FHIRException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class FhirServer implements WebService {
	
	private FhirContext ctx = FhirContext.forDstu3();
	private String baseUrl = Configurations.BaseURL.getAction();
	private IGenericClient client = null;
		
	public FhirServer() {
        // increase timeouts since the server might be powered down
        // see http://hapifhir.io/doc_rest_client_http_config.html
        ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);
        
        // create the RESTful client to work with our FHIR server
        // see http://hapifhir.io/doc_rest_client.html
        client = ctx.newRestfulGenericClient(baseUrl);
	}

	@Override
	public ArrayList<String> getPatientList(String practitionerId) {
		ArrayList<String> patientList = new ArrayList<String>();
		
		try {
        	Bundle response = client.search()
        			.forResource(Encounter.class)
        			.where(Encounter.PRACTITIONER.hasId(practitionerId))
        			.returnBundle(Bundle.class)
        			.execute();  
        	
            response.getEntry().forEach((entry) -> {
                // within each entry is a resource - print its logical ID
                System.out.println(entry.getResource().getIdElement().getIdPart());
                
            	// each entry is an Encounter object, so casting it to Encounter is safe
                Encounter anEncounter = (Encounter) entry.getResource();
            	
                
                String patientId = anEncounter.getSubject().getReference();
                
             
                patientList.add(patientId);
           		System.out.println(patientId);    
            });
            
            
            // remove duplicate patient IDs
            HashSet tempHs = new HashSet();
            
            tempHs.addAll(patientList);
            patientList.clear();
            patientList.addAll(tempHs); 
            
           
    		ArrayList<String> tempPatientList = new ArrayList<String>();
    		
    		for(int i = 0; i < patientList.size(); i++) {
    			String[] str = patientList.get(i).split("/", 2);
    			tempPatientList.add(str[1]);
    		}
            
            return tempPatientList;
        	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * Fetches the Diagnostic Report for the provided patient id
	 */
	@Override
	public HashMap<String, Report> getPatientReport(String patientId) {
		
	
		System.out.println("Test getpatientreport");
		
		HashMap<String, Report> patientReports = new HashMap<String, Report>();
		
		try {
			Bundle response = client.search()
					.forResource(DiagnosticReport.class)
					.where(new ReferenceClientParam("patient").hasId(patientId))
					.sort().descending(DiagnosticReport.ISSUED)
					.returnBundle(Bundle.class)
					.execute();
			
			// iterating through the array of entries
			response.getEntry().forEach((entry) -> {
				
				// each entry is Diagnostic Report object, so casting it to Diagnostic Report is safe
				DiagnosticReport serverReport = (DiagnosticReport) entry.getResource();
				
				// Check if the particular type of report exists
				if(!patientReports.containsKey(serverReport.getCode().getText())) {
					
					// creating the Patient Report Instance
					Report report = new PatientReport(serverReport.getId(), 
							serverReport.getIssued().toString(), 
							serverReport.getCode().getText());
					
					// list of observation ids for this report
					ArrayList<String> observationIdList = new ArrayList<String>();
					
					// list of observations from the server
					List<Reference> results = serverReport.getResult();
					
					for(int i = 0; i < results.size(); i++) {
						
						observationIdList.add(results.get(i).getDisplay());
					}
					
					// set the observation id list for this report
					report.setObservationId(observationIdList);
					
					// add report to the hash map with the type as the key
					patientReports.put(report.getType(), report);
				}
			});
			
			Report vitalReport = new PatientReport(ReportType.Vitals.getAction(), "", ReportType.Vitals.getAction());
			patientReports.put(vitalReport.getType(),  vitalReport);
			
			
			return patientReports;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Fetches the lab observation value for the provided patient id and the observation id
	 */
	@Override
	public LabObservation getLabObservation(String patientId, String obsId) {
		String htmlSpaceEncoder = "%20";
		String htmlPatientParameter = "&subject=Patient/";
		String htmlDateSortParameter = "&_sort=-date";
		String searchUrl = baseUrl + "/Observation?code:text=";
		
		String[] str = obsId.split(" ", 3);
		
		// if the observation has two words
		if(str.length > 1) {
			searchUrl = searchUrl + str[0] + htmlSpaceEncoder + str[1];
		}
		else {
			searchUrl = searchUrl + str[0];
		}
		
		searchUrl = searchUrl + htmlPatientParameter + patientId + htmlDateSortParameter;
		

		
		try {
			Bundle response = client.search()
					.byUrl(searchUrl)
					.returnBundle(Bundle.class)
					.execute();
			
			List<String[]> obsValuesList = new ArrayList<String[]>();
			List<String> unit = new ArrayList<String>();
			
			response.getEntry().forEach((entry) -> {
				
				//each entry is an Observation object, so casting it into Observation is safe
				Observation obs = (Observation) entry.getResource();
				
				
				try {
					String[] timeAndValue = {obs.getIssued().toString(), 
						obs.getValueQuantity().getValue().toString()};
					unit.add(obs.getValueQuantity().getUnit());
					
					obsValuesList.add(timeAndValue);
				} 
				catch(FHIRException e) 
				{
					e.printStackTrace();
					}
			});
			
			
			LabObservation observation = new PatientLabObservation(obsId, obsId);
			
			System.out.println(obsValuesList.get(0)[1]);
			
			observation.setValue(obsValuesList.get(0)[1]);
			observation.setUnit(unit.get(0));
			observation.setHistoricValues(obsValuesList);
	
			return observation;
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * fetches the vital observation for the provided patient id and list of vital observation types
	 */
	@Override
	public HashMap<String, LabObservation> getVitalObservation(String patientId, ArrayList<String> observationTypes) {
		
		String htmlSpaceEncoder = "%20";
		String htmlPatientParameter = "&subject=Patient/";
		String htmlDateSortParameter = "&_sort=-date";
		
		
		HashMap<String, LabObservation> patientObservations = new HashMap<String, LabObservation>();
		
		
		// iterate through every obsevation type
		for(String observationType: observationTypes) {
			String searchUrl = baseUrl + "/Observation?code:text=";
			
			String[] str = observationType.split(" ", 3);
			
			// if the observation has two words
			if(str.length > 1) {
				searchUrl = searchUrl + str[0] + htmlSpaceEncoder + str[1];
			}
			else {
				searchUrl = searchUrl + str[0];
			}
			
			searchUrl = searchUrl + htmlPatientParameter + patientId + htmlDateSortParameter;
			System.out.println(searchUrl);
			
			// get all the observations corresponding to the observation text using url.
			try {
				Bundle response = client.search()
						.byUrl(searchUrl)
						.returnBundle(Bundle.class)
						.execute();
				
				
				int observationCtr = 0;
				List<List<String[]>> obsValuesList = new ArrayList<List<String[]>>();
				
				Observation testObs = (Observation) response.getEntryFirstRep().getResource();
				
				if(testObs.getComponent().size() > 0) {
					for(ObservationComponentComponent obsComponent: testObs.getComponent()) {
						obsValuesList.add(new ArrayList<String[]>());
					}
				}
				else {
					obsValuesList.add(new ArrayList<String[]>());
				}
				
				List<String> unit = new ArrayList<String>();
				
				response.getEntry().forEach((entry) -> {
					
					//each entry is an Observation object, so casting it into Observation is safe
					Observation vitalObs = (Observation) entry.getResource();
					
					// some observations like Blood Pressure have sub components like systolic and diastolic
					if(vitalObs.getComponent().size() > 0) {
						int obsComponentCtr = 0;
						
						for(ObservationComponentComponent obsComponent: vitalObs.getComponent()) {
							String obsId = obsComponent.getCode().getText();
							
							try {
								String[] timeAndValue = {vitalObs.getIssued().toString(), 
									obsComponent.getValueQuantity().getValue().toString()};
								
								obsValuesList.get(obsComponentCtr).add(timeAndValue);
								unit.add(obsComponent.getValueQuantity().getUnit());
								
								obsComponentCtr += 1;
							} 
							catch(FHIRException e) 
							{
								e.printStackTrace();
								}
						}
					}
					else {

						try {
							String[] timeAndValue;
							
							// to accommodate for differently stored observation structures
							try {
								timeAndValue = new String[]{vitalObs.getIssued().toString(),
										vitalObs.getValueCodeableConcept().getText()};
								
								unit.add("");
								
							}catch(Exception e) {
								timeAndValue = new String[]{vitalObs.getIssued().toString(), 
										vitalObs.getValueQuantity().getValue().toString()};
								unit.add(vitalObs.getValueQuantity().getUnit());
								
							}

							obsValuesList.get(0).add(timeAndValue);
							
						} 
						catch(FHIRException e) 
						{
							e.printStackTrace();
							}
					}
				});
				
				
				/**
				 * Adding the subcomponents of the observation with the observation type set
				 * to component text
				 */
				if(testObs.getComponent().size() > 0) {
					
					int componentCtr = 0;
					
					for(List<String[]> obsValues: obsValuesList) {
						
						// get the observatino component display name
						String obsType = testObs
								.getComponent().get(componentCtr)
								.getCode()
								.getText();
						
						LabObservation observation = new PatientLabObservation(observationType, obsType);
						observation.setHistoricValues(obsValues);
						
						observation.setValue(obsValues.get(0)[1]);
						observation.setUnit(unit.get(0));
						
						// add the observation to the observations hash map
						patientObservations.put(observation.getType(), observation);
						
						componentCtr += 1;
					}
				}
				else {
					LabObservation observation = new PatientLabObservation(observationType, observationType);
					observation.setHistoricValues(obsValuesList.get(0));
					observation.setValue(obsValuesList.get(0).get(0)[1]);
					observation.setUnit(unit.get(0));

					
					// add the observation to the observations hash map
					patientObservations.put(observation.getType(), observation);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return patientObservations;
	}	
}
