package nitinka.quartzrest.domain;

/**
 * Domain Interacted by JobScheduling Resource
 */
public class JobSchedulingInfo {
    private JobInfo jobInfo;
    private ScheduleInfo scheduleInfo;

    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public JobSchedulingInfo setJobInfo(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
        return this;
    }

    public ScheduleInfo getScheduleInfo() {
        return scheduleInfo;
    }

    public JobSchedulingInfo setScheduleInfo(ScheduleInfo scheduleInfo) {
        this.scheduleInfo = scheduleInfo;
        return this;
    }

/*
    public static void main(String[] args) throws IOException {
        JobSchedulingInfo jobSchedulingInfo = new JobSchedulingInfo().
                setJobInfo(new JobInfo().setJobClass("AddJob").
                        setJobParams(new HashMap<String, Object>()).
                        setDescription("Sample Add Job").
                        setName("Name1").
                        setGroup("Group1").
                        setStoreDurably(true)).
                setTriggerInfo(new TriggerInfo().
                        setName("Name1").
                        setGroup("Group1")).
                setSimpleScheduleInfo(new SimpleSchedulerInfo().
                        setInterval(30000).
                        setRepeatCount(5).
                        setRepeatForever(false)).
                setCronScheduleInfo(new CronSchedulerInfo().
                        setCronExpression("0/5 * * * * *")).
                setSimpleSchedule(false);
        System.out.println(new ObjectMapper().defaultPrettyPrintingWriter().writeValueAsString(jobSchedulingInfo));
    }
*/
}
