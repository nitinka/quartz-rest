package nitinka.quartzrest.domain;

public class CronSchedulerInfo {
    private String cronExpression;
    private int misfireInstruction = 2;

    public String getCronExpression() {
        return cronExpression;
    }

    public CronSchedulerInfo setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public CronSchedulerInfo setMisfireInstruction(int misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
        return this;
    }

    public int getMisfireInstruction() {
        return misfireInstruction;
    }
}
