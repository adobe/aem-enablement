package com.acs.core.job;

import com.acs.core.services.FastlyPurger;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component(immediate = true, service = JobConsumer.class, property = {JobConsumer.PROPERTY_TOPICS + "=" + FastlyPurgeJob.JOB_TOPIC})
@Slf4j
public class FastlyPurgeJob implements JobConsumer {

    public static final String JOB_TOPIC = "acs/fastly-cache-purge";

    // path to purge.
    public static final String PATH = "path";


    @Reference
    FastlyPurger fastlyPurger;

    /**
     * run purge
     *
     * @param job
     * @return
     */
    @Override
    public JobResult process(final Job job) {
        // TODO: transform this path to the actual path on Fastly. Conetnt paths may not be what is published, but a shorter version of the path.
        // Example, your content maybe at /content/mycom/test, but on fastly it maybe at /test. Fastly will only invalidate /test.
        final String path = job.getProperty(PATH, String.class);
        log.info("Fastly Purge Started for Path: {} " , path);
        this.fastlyPurger.purgeAsync(path)
            .thenAcceptAsync((HttpResponse<String> purgeResult)-> {
                String purgeUrl = Optional.ofNullable(purgeResult)
                        .map(HttpResponse::uri)
                        .map(URI::toString)
                        .orElse(null);
                log.info("Fastly Purge Complete. Result: [url: {}] [status: {}] [response body: {}]", purgeUrl, purgeResult.statusCode(), purgeResult.body());
            });
        return JobResult.OK;
    }
}


