package nitinka.quartzrest.domain;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 25/7/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleInfo {
    private SimpleSchedulerInfo simpleScheduleInfo;
    private CronSchedulerInfo cronScheduleInfo;
    private TriggerInfo triggerInfo;
    private boolean simpleSchedule = true;

    public SimpleSchedulerInfo getSimpleScheduleInfo() {
        return simpleScheduleInfo;
    }

    public ScheduleInfo setSimpleScheduleInfo(SimpleSchedulerInfo simpleScheduleInfo) {
        this.simpleScheduleInfo = simpleScheduleInfo;
        return this;
    }
    public boolean isSimpleSchedule() {
        return simpleSchedule;
    }

    public ScheduleInfo setSimpleSchedule(boolean simpleSchedule) {
        this.simpleSchedule = simpleSchedule;
        return this;
    }

    public CronSchedulerInfo getCronScheduleInfo() {
        return cronScheduleInfo;
    }

    public ScheduleInfo setCronScheduleInfo(CronSchedulerInfo cronScheduleInfo) {
        this.cronScheduleInfo = cronScheduleInfo;
        return this;
    }

    public TriggerInfo getTriggerInfo() {
        return triggerInfo;
    }

    public ScheduleInfo setTriggerInfo(TriggerInfo triggerInfo) {
        this.triggerInfo = triggerInfo;
        return this;
    }
}
