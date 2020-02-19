# Working with the Cloud Ready Quickstart JAR

## Table of Contents
* [Senario Overview](#scenario-overview)
* [Lesson 1 - Download & Install Cloud Ready Quickstart](#lesson-1---Download-&-Install-Cloud-Ready-Quickstart)
* [Lesson 2 - Create Project & Deploy to AEM ](#lesson-2---Create-AEM-Project-for-Cloud-Ready-Quickstart)

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

1. Go to [Software Distribution Portal](https://downloads.experiencecloud.adobe.com/)

2. Find the latest version released (It should be at end of the page) and click to see list of available jars, docs, files etc for the version.
3. Download the jar file which should be named as *cq-quickstart-cloudready-V16068.jar* - Version name will be different
4. Put the downloaded jar file and license file in one folder.
5.  Run the following command to unpack and start your local cloud ready development environment
   ```
   $> java -jar cq-quickstart-cloudready-V16061.jar -unpack
   $> cd crx-quickstart/bin
   $> ./start
   $> tail -f ../logs/error.log

   ```     
6. Go to http://localhost:4502 to verify that AEM is running. ( Port may change if 4502 is already occupied, you may check logs files to know the current port on which AEM is running.)


### Step 2. Create an AEM Project for the Cloud Ready Quickstart

### Lesson Context
In this lesson we will create an AEM project and update it for the Cloud Ready Quickstart.


1. Create a new project using the latest Archetype
    ```
    mvn archetype:generate \
        -DarchetypeGroupId=com.adobe.granite.archetypes \
        -DarchetypeArtifactId=aem-project-archetype \
        -DarchetypeVersion=20
        ```
2. Once the project is generated , Go to parent pom.xml and add the following dependencies.

```
<dependency>
    <groupId>com.day.cq</groupId>
    <artifactId>cq-quickstart-cloudready</artifactId>
    <version>V16061</version>
    <classifier>global-apis</classifier>
    <scope>provided</scope>
</dependency>

```
3. Deploy the project
   ```
   $> cd projectfoldername
   $> mvn clean install -PautoInstallPackage

   ```

4. Go to AEM > Sites and verify your newly created site in AEM.

