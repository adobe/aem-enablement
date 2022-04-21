package com.sample.core.jobs;

import org.apache.sling.event.jobs.JobBuilder;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.event.jobs.ScheduledJobInfo;
import org.osgi.service.component.annotations.*;

import java.util.Collection;

/**
 * A component that schedules the sample job
 */
@Component(immediate = true)
public class SampleJobScheduler {

    @Reference
    private JobManager jobManager;

    @Activate
    @Modified
    protected void activate() {
        removeScheduler();
        startScheduledJob();
    }

    @Deactivate
    protected void deactivate() {
        removeScheduler();
    }

    /**
     * Remove a scheduler based on the job topic
     */
    private void removeScheduler() {
        jobManager.getScheduledJobs( SampleJob.TOPIC, 0, null ).forEach( scheduledJobInfo -> scheduledJobInfo.unschedule());
    }

    private void startScheduledJob(){
        jobManager.createJob(SampleJob.TOPIC).schedule().cron("0 1 0 ? * *" ).add();
    }
}