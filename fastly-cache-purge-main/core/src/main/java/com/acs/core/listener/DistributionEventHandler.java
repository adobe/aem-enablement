package com.acs.core.listener;


import com.acs.core.job.FastlyPurgeJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.distribution.event.DistributionEventProperties;
import org.apache.sling.distribution.event.DistributionEventTopics;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Arrays;
import java.util.Map;

@Component(immediate = true, service = EventHandler.class, property = {
        Constants.SERVICE_DESCRIPTION + "= This event handler listens to the events on Distribution",
        EventConstants.EVENT_TOPIC + "=" + DistributionEventTopics.AGENT_PACKAGE_DISTRIBUTED,
        EventConstants.EVENT_FILTER + "=(distribution.paths=/content/*)" })
@Slf4j
public class DistributionEventHandler implements EventHandler {

    private static final String FILTER_PATH = "/content";

    @Reference
    JobManager jobManager;


    @Override
    public void handleEvent(Event event) {
        log.debug("DistributionEventHandler event: {}", event);
        // Get the payload path from the event
        final String[] distributionPaths = (String[]) event.getProperty(DistributionEventProperties.DISTRIBUTION_PATHS);
        Arrays.stream(distributionPaths)
                .filter(path -> path.startsWith(FILTER_PATH))
                .forEach(path -> {
                    // fastly purge
                    Map<String, Object> fastlyJobProperties = Map.of(FastlyPurgeJob.PATH, path);
                    this.jobManager.addJob(FastlyPurgeJob.JOB_TOPIC, fastlyJobProperties);
                });

    }


}

