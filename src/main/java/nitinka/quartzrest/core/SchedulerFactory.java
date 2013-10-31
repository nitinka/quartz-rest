package nitinka.quartzrest.core;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SchedulerFactory {
    private static Map<String, SchedulerHelper> schedulers = new HashMap<String, SchedulerHelper>();
    private static SchedulerHelper defaultScheduler;

    public static void buildSchedulers(String schedulerConfigsFolder) throws SchedulerException {
        File folder = new File(schedulerConfigsFolder);
        for(File scheduleConfigFile : folder.listFiles()) {
            StdSchedulerFactory sf = new StdSchedulerFactory();
            sf.initialize(scheduleConfigFile.getAbsolutePath());
            Scheduler scheduler = sf.getScheduler();
            scheduler.start();
            schedulers.put(scheduler.getSchedulerName(), new SchedulerHelper(scheduler));

            if(defaultScheduler == null)
                defaultScheduler = schedulers.get(scheduler.getSchedulerName());
        }
    }

    public static SchedulerHelper getDefaultScheduler() {
        return defaultScheduler;
    }

    public static SchedulerHelper getScheduler(String schedulerName) {
        return schedulers.get(schedulerName);
    }

    public static Map<String, SchedulerHelper> schedulers() {
        return schedulers;
    }

    public static void setDefaultScheduler(SchedulerHelper scheduler) {
        SchedulerFactory.defaultScheduler = scheduler;
    }
}
