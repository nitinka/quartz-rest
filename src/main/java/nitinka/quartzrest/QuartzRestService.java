package nitinka.quartzrest;

import com.fasterxml.jackson.databind.SerializationFeature;
import nitinka.jmetrics.JMetric;
import nitinka.jmetrics.controller.dropwizard.JMetricController;
import nitinka.quartzrest.config.QuartzRestServiceConfiguration;
import nitinka.quartzrest.core.SchedulerFactory;
import nitinka.quartzrest.resource.JobSchedulingResource;
import nitinka.quartzrest.resource.SchedulerResource;
import nitinka.quartzrest.resource.TriggerResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class QuartzRestService extends Service<QuartzRestServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<QuartzRestServiceConfiguration> bootstrap) {
        bootstrap.setName("quartz-interface-service");
        bootstrap.getObjectMapperFactory().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public void run(QuartzRestServiceConfiguration configuration, Environment environment) throws Exception {
        SchedulerFactory.buildSchedulers(configuration.getSchedulerConfigsFolder());

        JMetric.initialize(configuration.getjMetricConfig());
        environment.addResource(new JMetricController());

        environment.addResource(new JobSchedulingResource());
        environment.addResource(new SchedulerResource());
        environment.addResource(new TriggerResource());
    }

    public static void main(String[] args) throws Exception {
        args = new String[]{"server",args[0]};
        new QuartzRestService().run(args);
    }
}
