package nitinka.quartzrest.domain;

public class SimpleSchedulerInfo {
    private boolean repeatForever;
    private long interval;
    private int repeatCount;
    private int misfireInstruction = 4;

    public boolean isRepeatForever() {
        return repeatForever;
    }

    public long getInterval() {
        return interval;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public SimpleSchedulerInfo setRepeatForever(boolean repeatForever) {
        this.repeatForever = repeatForever;
        return this;
    }

    public SimpleSchedulerInfo setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    public SimpleSchedulerInfo setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public SimpleSchedulerInfo setMisfireInstruction(int misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
        return this;
    }

    public int getMisfireInstruction() {
        return misfireInstruction;
    }
}
