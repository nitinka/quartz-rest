package nitinka.quartzrest;

import com.fasterxml.jackson.databind.SerializationFeature;
import nitinka.quartzrest.config.QuartzInterfaceServiceConfiguration;
import nitinka.quartzrest.core.SchedulerFactory;
import nitinka.quartzrest.resource.JobSchedulingResource;
import nitinka.quartzrest.resource.SchedulerResource;
import nitinka.quartzrest.resource.TriggerResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class QuartzInterfaceService extends Service<QuartzInterfaceServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<QuartzInterfaceServiceConfiguration> bootstrap) {
        bootstrap.setName("quartz-interface-service");
        bootstrap.getObjectMapperFactory().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public void run(QuartzInterfaceServiceConfiguration configuration, Environment environment) throws Exception {
        SchedulerFactory.buildSchedulers(configuration.getSchedulerConfigsFolder());
        environment.addResource(new JobSchedulingResource());
        environment.addResource(new SchedulerResource());
        environment.addResource(new TriggerResource());
    }

    public static void main(String[] args) throws Exception {
        args = new String[]{"server",args[0]};
        new QuartzInterfaceService().run(args);
    }
}
