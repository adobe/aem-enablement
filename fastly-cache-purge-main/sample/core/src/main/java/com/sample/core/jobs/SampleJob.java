package com.sample.core.jobs;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobExecutionContext;
import org.apache.sling.event.jobs.consumer.JobExecutionResult;
import org.apache.sling.event.jobs.consumer.JobExecutor;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;


@Component(
        service = JobExecutor.class,
        immediate = true,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Sample Job",
                JobExecutor.PROPERTY_TOPICS + "=" + SampleJob.TOPIC
        })
public class SampleJob implements JobExecutor {

    public static final String TOPIC = "sample/job/topic";

    public JobExecutionResult process(final Job job, JobExecutionContext context) {
        context.log("Job Started");
        context.log("Job has Topic: " + job.getTopic());
        context.log("Job not doing anything at all, just logging these messages :)");
        context.log("Job finished.");
        return context.result().message("Job well done!").succeeded();
    }
}