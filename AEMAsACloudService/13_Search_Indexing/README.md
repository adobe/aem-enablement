# Search & Indexing in AEM Cloud 

## Table of Contents
- [Search & Indexing in AEM Cloud](#search--indexing-in-aem-cloud)
  - [Table of Contents](#table-of-contents)
  - [Scenario Overview](#scenario-overview)
  - [Changes Compared to AEM 6.5](#changes-compared-to-aem-65)
    - [Key Takeaways](#key-takeaways)
    - [Prerequisites](#prerequisites)
  

## Scenario Overview/Lesson Context

AEM supports running queries against the repository, very similar to running queries against a relational database. We also support fulltext queries. Customers can write their own queries and define their own indexes, and most of them do this.

Changing index definitions is needed to speed up queries. With blue-green deployments,
the challenge is that multiple application version can exist at the same time, but each
version might require a different index configuration.

With AEM as a Cloud Servivce, instead of configuring and maintaining indices on single AEM instances, the index configuration has to be specified before an actual deployment.


## Changes Compared to AEM 6.5

* Users will not have access to the Index Manager (http://localhost:4502/libs/granite/operations/content/diagnosistools/indexManager.html) of a single AEM Instance anymore to debug, configure or maintain indexing. It is only used for local development and on-prem deployments.

* Users will not change Indexes on a single AEM Instance anymore nor will they have to worry about consistency checks, reindexing etc.

* In general, index changes are initiated before going to production to not circumvent quality gateways in Cloud Manager CI/CD pipelines and not impact Business KPI's in production.

* All related metrics including search performance in production will be available for customer users at runtime to provide the holistic view on the topics of Search and Indexing

* Customers will be able to set up alerts to their needs 

* SRE's are monitoring system health 24/7 and take action as needed and as early as possible

* Index configuration is changed via deployments. Index definition changes are configured like other content changes.

* At a high level, on AEM as a Cloud Service, using the the blue-green deployment model, means there are two sets of indexes: one set for the old version (blue), and one set for the new version (green). At which version an index is used, it is configured using flags in the index definitions ("useIfExist"). An index may be used in only one version of the application (e.g. only blue, or only green), or in both versions. 
  
* Customers can see whether the indexing job is complete on the Cloud Manager build page and will receive a notification when the new version is ready to take traffic. Note that there is no progress bar to show how much time remains for the indexing job. There is no plan currently to provide a UI in the cloud manager to view or edit index definitions.

### Key Takeaways

* Search & Indexing Changes in AEM Cloud 
* Prepare,Add & Change Index definition in AEM CLoud

### Prerequisites

* AEM Cloud Instance
* AEM Project

### Step 1. Prepare New Index Defintion
1. Download [Custom Index Definition](./.content.xml).
2. Copy .content.xml to `ui.content` project in Cloud Manager Git Project. Project path should look like \ui.content\src\main\content\jcr_root\_oak_index.
3. Modify the ` filter.xml ` to include the ` _oak_index ` folder
4. Add ` <allowIndexDefinitions>true</allowIndexDefinitions> ` to ` filevault-package-maven-plugin ` plugin configuration in Parent `pom.xml`.
5. Do a local maven build and deploy.
6. Commit the new code with index definitions to Cloud Manager Git.







## Additional Resources
* [KT- AEM Cloud Service - Search & Indexing](https://docs.adobe.com/content/help/en/experience-manager-cloud-service/implementing/developing/indexing.html)
