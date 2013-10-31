package nitinka.quartzrest.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 22/7/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class TriggerInfo {
    private Date startDate;
    private Date endDate;
    private String name;
    private String group;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public TriggerInfo setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public TriggerInfo setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public TriggerInfo setName(String name) {
        this.name = name;
        return this;
    }

    public TriggerInfo setGroup(String group) {
        this.group = group;
        return this;
    }
}
