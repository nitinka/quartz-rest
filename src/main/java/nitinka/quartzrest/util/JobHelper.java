package nitinka.quartzrest.util;

import nitinka.quartzrest.domain.*;
import nitinka.quartzrest.exception.BadCronExpressionException;
import org.quartz.*;

import java.text.ParseException;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 23/7/13
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobHelper {

    /**
     * Build Simple SchedulerHelper
     * @param schedulerInfo
     * @return
     */
    public static ScheduleBuilder buildSimpleSchedulerBuilder(SimpleSchedulerInfo schedulerInfo) {
        SimpleScheduleBuilder scheduleBuilder = simpleSchedule();
        if(schedulerInfo.isRepeatForever()) {
            scheduleBuilder.repeatForever();
        }

        scheduleBuilder.withIntervalInMilliseconds(schedulerInfo.getInterval());
        if(schedulerInfo.getRepeatCount() > 0) {
            scheduleBuilder.withRepeatCount(schedulerInfo.getRepeatCount());
        }

        switch(schedulerInfo.getMisfireInstruction()) {
            case SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW:
                scheduleBuilder.withMisfireHandlingInstructionFireNow();
                break;
            case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT:
                scheduleBuilder.withMisfireHandlingInstructionNowWithExistingCount();
                break;
            case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT:
                scheduleBuilder.withMisfireHandlingInstructionNowWithRemainingCount();
                break;
            case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT:
                scheduleBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
                break;
            case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT:
                scheduleBuilder.withMisfireHandlingInstructionNextWithExistingCount();
                break;
        }

        return scheduleBuilder;
    }

    /**
     * 0/5 * * * * *    ->  Every Five Seconds
     * 0 0 12 * * ?    ->  Fire at 12pm (noon) every day
     * 0 15 10 * * ? *    ->  Fire at 10:15am every day
     * 0 0/5 14 * * ?    ->  Fire every 5 minutes starting at 2pm and ending at 2:55pm, every day
     * Check Examples here http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger
     *
     * @param schedulerInfo
     * @return
     */
    public static ScheduleBuilder buildCronSchedulerBuilder(CronSchedulerInfo schedulerInfo) throws BadCronExpressionException {
        CronScheduleBuilder scheduleBuilder = null;
        try {
            CronExpression cronExpression = new CronExpression(schedulerInfo.getCronExpression());
            scheduleBuilder = cronSchedule(cronExpression);

            switch(schedulerInfo.getMisfireInstruction()) {
                case CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                    scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                    break;
                case CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING:
                    scheduleBuilder.withMisfireHandlingInstructionDoNothing();
                    break;
                case CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                    scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new BadCronExpressionException(e.getLocalizedMessage());
        }
        return scheduleBuilder;
    }

    /**
     * Create Builder based on builder type
     *
     * @param scheduleInfo
     * @return
     */
    public static ScheduleBuilder buildSchedulerBuilder(ScheduleInfo scheduleInfo) throws BadCronExpressionException {
        if(scheduleInfo.isSimpleSchedule())
            return buildSimpleSchedulerBuilder(scheduleInfo.getSimpleScheduleInfo());
        return buildCronSchedulerBuilder(scheduleInfo.getCronScheduleInfo());
    }

    private static TriggerBuilder<? extends Trigger> buildTriggerBuilder(TriggerInfo triggerInfo, ScheduleBuilder scheduleBuilder) {
        TriggerBuilder triggerBuilder = newTrigger();
        triggerBuilder.withSchedule(scheduleBuilder).
                startNow().
                withIdentity(triggerInfo.getName(), triggerInfo.getGroup());

        // Set Start Date
        if(triggerInfo.getStartDate() != null) {
            triggerBuilder.startAt(triggerInfo.getStartDate());
        }

        // Set End Date
        if(triggerInfo.getEndDate() != null) {
            triggerBuilder.endAt(triggerInfo.getEndDate());
        }

        return triggerBuilder;
    }

    /**
     * Build Trigger using provided SchedulerBuilder
     * @param triggerInfo
     * @param scheduleBuilder
     * @return
     */
    public static Trigger buildTrigger(TriggerInfo triggerInfo, ScheduleBuilder scheduleBuilder) {
        return buildTriggerBuilder(triggerInfo, scheduleBuilder).build();
    }


    /**
     * Build Trigger using provided SchedulerBuilder
     * @param triggerInfo
     * @param scheduleBuilder
     * @return
     */
    public static Trigger buildTriggerForJob(TriggerInfo triggerInfo, ScheduleBuilder scheduleBuilder, JobDetail jobDetail) {
        return buildTriggerBuilder(triggerInfo, scheduleBuilder).forJob(jobDetail).build();
    }

    public static JobDetail buildJobDetail(JobInfo jobInfo) throws ClassNotFoundException {
        org.quartz.JobBuilder jobBuilder = newJob();

        JobDataMap jobDataMap = new JobDataMap();
        Map<String, Object> jobParams = jobInfo.getJobParams();
        for(String paramKey : jobParams.keySet()) {
            jobDataMap.put(paramKey, jobParams.get(paramKey));
        }

        jobBuilder.ofType((Class<? extends Job>) Class.forName(jobInfo.getJobClass())).
                withIdentity(jobInfo.getName(), jobInfo.getGroup()).
                withDescription(jobInfo.getDescription()).
                storeDurably(jobInfo.isStoreDurably()).
                requestRecovery(jobInfo.isRequestRecovery()).
                usingJobData(jobDataMap);

        return jobBuilder.build();
    }

}
