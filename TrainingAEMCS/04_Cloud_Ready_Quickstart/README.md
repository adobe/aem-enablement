# Working with the Cloud Ready Quickstart JAR

## Table of Contents
* [Senario Overview](#scenario-overview)
* [Lesson 1 - Download & Install Cloud Ready Quickstart](#lesson-1---Download-&-Install-Cloud-Ready-Quickstart)
* [Lesson 2 - Deploy an AEM Project for the Cloud Ready Quickstart ](#lesson-2---Create-AEM-Project-for-Cloud-Ready-Quickstart)

## Scenario Overview
AEM Cloud as a Cloud Service will be upgrading very frequently with daily patches,feature releases and more. Developers should get in the habit of downloading the quickstart JAR file daily to be sure that the local environment is in sync with the cloud environment. Adobe will release new JAR files on a daily basis. 

The quictstart jar version is subject to change very frequently. There is a corresponding global-apis jar which must be used as a dependency in the AEM Project pom files for compile time,  and will deploy the built artifact into the local quickstart based AEM instance for testing. Remember, there is no substitute for pushing the code to the GIT connected with Cloud Manager which will deploy the code to AEM as a Cloud Service. 

### Key Takeaways

* Understand how to setup the local development Environment
* Use Cloud Ready Quickstart Global API for projects

### Pre-requisites

* Maven setup with setting.xml ponting to Adobe Repository
* License File For AEM

### Step 1. Download and install the cloud ready quickstart JAR file

### Lesson Context
In this lesson we will learn how to download and setup the local AEM Environment using the Cloud Ready Quickstart JAR.

1. Go to [Cloud Ready Quickstart ](https://downloads.experiencecloud.adobe.com/content/software-distribution/en/general.html)

2. Find the latest version released (It should be at end of the page) and click to see list of available jars, docs, files etc for the version.
3. Download the zip file which should be named as *AEM  v2020.02.2291.20200219T233521Z-200130* - Version name will be different
    > AEM SDK contains Quickstart Jar, Dispatcher Tools for Unix, and Dispatcher Tools for Windows
4. Extract the zip file and put the downloaded jar file and license file in one folder.
    > For simplicity, rename the jar file to aemcs-author-p4502.jar or aemcs-publish-p4503.jar
5. Go to http://localhost:4502 to verify that AEM is running. ( Port may change if 4502 is already occupied, you may check logs files to know the current port on which AEM is running.)


### Step 2. Deploy an AEM Project for the Cloud Ready Quickstart

### Lesson Context
In this lesson we will install All Demos for AEM as a Cloud Service


1. Download (com.adobe.aem.demos.all-demos.x.x.zip)[https://internal.adobedemo.com/content/demo-hub/en/demos/external/aem-all-demos.html#]
        
2. Upload and Install, the zip file via Package Manager.

3. Go to AEM > Sites and verify your newly created site in AEM.

