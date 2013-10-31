package nitinka.quartzrest.domain;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.util.List;

/**
 * Wrapper Around Job Details exposed by quartz
 */
public class RunTimeJobDetail {
    private JobDetail jobDetail;
    private List<? extends Trigger> triggers;

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public RunTimeJobDetail setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
        return this;
    }

    public List<? extends Trigger> getTriggers() {
        return triggers;
    }

    public RunTimeJobDetail setTriggers(List<? extends Trigger> triggers) {
        this.triggers = triggers;
        return this;
    }

    public String getJobId() {
        return jobDetail.getKey().getGroup()+"."+jobDetail.getKey().getName();
    }

    public Trigger getTrigger(String triggerGroup, String triggerName) {
        if(triggers!= null) {
            for(Trigger trigger : triggers) {
                if(trigger.getKey().getGroup().equals(triggerGroup) && trigger.getKey().getName().equals(triggerName)) {
                    return trigger;
                }
            }
        }
        return null;
    }
}
