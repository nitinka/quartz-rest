package nitinka.quartzrest.domain;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 22/7/13
 * Time: 7:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobInfo {
    private String jobClass;
    private String name;
    private String group;
    private String description;
    private boolean storeDurably;
    private boolean requestRecovery;
    private Map<String, Object> jobParams;

    public String getJobClass() {
        return jobClass;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStoreDurably() {
        return storeDurably;
    }

    public Map<String, Object> getJobParams() {
        return jobParams;
    }

    public JobInfo setJobClass(String jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    public JobInfo setName(String name) {
        this.name = name;
        return this;
    }

    public JobInfo setGroup(String group) {
        this.group = group;
        return this;
    }

    public JobInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public JobInfo setStoreDurably(boolean storeDurably) {
        this.storeDurably = storeDurably;
        return this;
    }

    public JobInfo setJobParams(Map<String, Object> jobParams) {
        this.jobParams = jobParams;
        return this;
    }

    public JobInfo setRequestRecovery(boolean requestRecovery) {
        this.requestRecovery = requestRecovery;
        return this;
    }

    public boolean isRequestRecovery() {
        return requestRecovery;
    }
}
