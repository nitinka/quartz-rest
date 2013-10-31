package nitinka.quartzrest.config;

import com.yammer.dropwizard.config.Configuration;
import nitinka.jmetrics.JMetricConfig;

public class QuartzRestServiceConfiguration extends Configuration{
    private String schedulerConfigsFolder;
    private JMetricConfig jMetricConfig;

    public String getSchedulerConfigsFolder() {
        return schedulerConfigsFolder;
    }

    public QuartzRestServiceConfiguration setSchedulerConfigsFolder(String schedulerConfigsFolder) {
        this.schedulerConfigsFolder = schedulerConfigsFolder;
        return this;
    }

    public JMetricConfig getjMetricConfig() {
        return jMetricConfig;
    }

    public void setjMetricConfig(JMetricConfig jMetricConfig) {
        this.jMetricConfig = jMetricConfig;
    }

}
