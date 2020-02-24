
# Maintenance Tasks

### Scenario Roadmap

In 6.x, the admin UI's Maintenance card (Tools > Operations > Maintenance) provided a means to configure Maintenance Tasks. It linked directly to ` /system/console/configMgr `, which persisted various configurations to the repository. For 
Adobe Experience Manager, the Maintenance card will not be accessible and so configuration should be committed to source control and deployed via Cloud Manager. 

At this point, Maintenance Tasks, can be divided into two main categories: 

1. Adobe Managed Maintenance Tasks i.e. Adobe fully manages MTs that don't require any customer decisioning. For example, Datastore Garbage Collection, Lucene Binaries Cleanup, Revision Cleanup.

2. Shared Maintenance Tasks i.e. Maintenance Tasks configurations that can be shared between Adobe and the customer. The customer owns a subset of properties (e.g. number of versions) and the maintenance window is owned by Adobe. For example, version purge, project purge, workflow purge.

| 6.5 Maintenance Task         | Who owns configuration | How to manage configuration       |
|------------------------------|------------------------|-----------------------------------|
| Datastore garbage collection | Adobe                  | N/A - Adobe Owned                 |
| Version Purge MT             | Shared                 | UI removed so must be done in git |
| Audit Log Purge              | Shared                 | UI removed so must be done in git |
| Lucene Binaries cleanup      | Removed from Cloud     | N/A - removed                     |
| Ad-hoc Task Purge            | Shared                 | UI removed so must be done in git |
| Workflow Purge               | Shared                 | UI removed so must be done in git |
| Project Purge                | Shared                 | UI removed so must be done in git |


#### Lesson Context

In this scenario we will configure and schedule Maintenance Tasks for an AEM as a Cloud Service Author Instance. We will use:
* The Cloud Ready Quickstart to create the configuration and verify the execution of the Maintenance Task. 
* Filevault to synchronize changes between the JCR and the code base. 

##### Step 1. Set up the Schedule for Maintenance Tasks : 

1. Launch the Cloud Ready Quick Start.
2. Navigate to CRXDE Lite.
3. By default, maintenance schedule is maintained at ` /apps/settings/granite/operations/maintenance `.  Maintenance tasks are defined under ` /libs/settings/granite/operations/maintenance/granite_daily `  & ` /libs/settings/granite/operations/maintenance/granite_weekly `  
4. Goto ` /conf/global/settings ` and create the following node:
    > Node Name : ` granite ` <br>
    > Node Type: ` sling:folder`
5. Goto ` /conf/global/settings/granite ` and create the following node: 
    > Node Name : ` operations ` <br>
    > Node Type: ` sling:folder`
6. Copy the ` maintenance ` node from ` /apps/settings/granite/operations/maintenance ` and paste it underneath ` /conf/global/settings/granite/operations `

    > This will allow us to maintain the schedule for maintenance task as a part of content (mutable) package  

7. Select ` granite_daily ` and reset the ` windowEndTime ` and ` windowStartTime ` to an appropriate duration
8. Select ` granite_weekly ` and reset the ` windowEndTime ` and ` windowStartTime ` to an appropriate duration
9. Tail the error.log, messages like below should be logged:
```
15.10.2019 14:25:00.010 *INFO* [pool-21-thread-1] com.adobe.granite.maintenance.crx.impl.RevisionCleanupTask Full GC scheduled to run on Sun. Today is Tue.
15.10.2019 14:25:00.012 *INFO* [pool-21-thread-1] com.adobe.granite.maintenance.impl.MaintenanceJobsManagerImpl Name='RevisionCleanupTask', Status='ACTIVE', Result='', Details='{"created":1571163900012,"started":1571163900012}'
15.10.2019 14:25:00.042 *INFO* [pool-21-thread-1] com.adobe.granite.maintenance.crx.impl.RevisionCleanupTask Starting RevisionGC with compaction type tail
15.10.2019 14:25:00.048 *INFO* [TarMK revision gc [C:\Users\vmitra\Documents\AEM\skyline\crx-quickstart\repository\segmentstore]] org.apache.jackrabbit.oak.segment.file.FileStore TarMK GC #1: started
```
##### Step 2. Set up Version Purge Maintenance Task

1. Go to CRX DE Lite on local AEM Instance.
2. Navigate to ` /apps/<project-name>/config.author
3. Create an OSGI configuration for Day CQ WCM Version Purge Task with the following/similar properties:
    >  {
         "jcr:primaryType": "sling:OsgiConfig",<br>
  "jcr:createdBy": "admin",
  "versionpurge.recursive": true,
  "jcr:created": "Sun Feb 23 2020 17:03:10 GMT+0000",
  "versionpurge.minVersions": 0,
  "versionpurge.maxAgeDays": 50,
  "versionpurge.maxVersions": 5,
  "versionpurge.paths": "/content"
}
4. Repeat the above steps to create Adobe Granite Workflow Purge Configuration.

##### Step 3. Synchronize the Maintenance Tasks with Source Code

1. Navigate to ` <git-project-name>\ui.content\src\main\content\META-INF\vault `
2. Open the ` filter.xml ` file and add the following filter definition: 
    > ` <filter root="/conf/global/settings/granite/operations/maintenance" mode="merge"/> `

    > ` <filter root="/apps/<your-project>/config.author `
3. Navigate to ` ./../../jcr_root ` using command prompt 
4. Use Filevault to serialize the maintenance task configuration to Source Code 
    >  ` vlt --credentials admin:admin co http://localhost:4502/crx --force `
5. Commit the updated source code to cloud manager GitHub.
---

```
In the next scenario we will deploy the maintenance task configuration to AEM as a Cloud Service
