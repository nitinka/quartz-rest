package nitinka.quartzrest.config;

import com.yammer.dropwizard.config.Configuration;

public class QuartzInterfaceServiceConfiguration extends Configuration{
    private String schedulerConfigsFolder;

    public String getSchedulerConfigsFolder() {
        return schedulerConfigsFolder;
    }

    public QuartzInterfaceServiceConfiguration setSchedulerConfigsFolder(String schedulerConfigsFolder) {
        this.schedulerConfigsFolder = schedulerConfigsFolder;
        return this;
    }
}
