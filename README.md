# UPR - Universal Patient Record 

<img width="1074" alt="Monitor Dashboard" src="https://user-images.githubusercontent.com/30483239/60526947-e8ffb880-9d23-11e9-86aa-0915dad0823a.png">

This application displays universal patient records stored in the FHIR SERVER. The term **universal** means that there is a single record for each patient which can be accessed by any authorized doctor. This will eliminate the time spent to conduct redundant tests, such as cholesterol and blood type. 

Plus, medical reports are often reported in esoteric, hard-to-read lab reports. This application displays the observation over time-series graphs to quickly identify the changes over the past.

![graphical reports](https://user-images.githubusercontent.com/30483239/60530089-98d82480-9d2a-11e9-952d-9e6ae5c6bb7e.png)

## Getting Started
1. Change the value of the `BaseURL` in the [Configuration Enum](https://github.com/siddhantbhatia/java-patient-record/blob/master/SafeHeart/src/configurations/Configurations.java) to the desired FHIR server.
2. Run [*App.Java*](https://github.com/siddhantbhatia/java-patient-record/blob/master/SafeHeart/src/view/App.java)
