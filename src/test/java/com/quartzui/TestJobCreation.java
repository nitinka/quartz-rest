package com.quartzui;

import nitinka.quartzrest.exception.JobAlreadyExistsException;
import org.quartz.SchedulerException;

public class TestJobCreation {
    public static void main(String[] args) throws ClassNotFoundException, JobAlreadyExistsException, SchedulerException, InterruptedException {
/*
        SchedulerHelper schedulerHelper = SchedulerHelper.instance();
        schedulerHelper.removeJob(new JobKey("Name1","Group1"));
        JobInfo jobInfo = new JobInfo().
                setJobClass("AddJob").
                setJobParams(new HashMap<String, Object>()).
                setDescription("Sample Add Job").
                setName("Name1").
                setGroup("Group1").
                setStoreDurably(true);

        SimpleSchedulerInfo schedulerInfo = new SimpleSchedulerInfo().
                setInterval(30000).
                setRepeatCount(5).
                setRepeatForever(false);


        TriggerInfo triggerInfo = new TriggerInfo().
                setName("Name1").
                setGroup("Group1");

        schedulerHelper.scheduleJob(JobHelper.buildJobDetail(jobInfo),
                JobHelper.buildTrigger(triggerInfo,
                        JobHelper.buildSimpleSchedulerBuilder(schedulerInfo)));


        System.out.println("****************"+ schedulerHelper.listAllJobs()+"********************");
        Thread.sleep(1000000);
*/

    }
}
