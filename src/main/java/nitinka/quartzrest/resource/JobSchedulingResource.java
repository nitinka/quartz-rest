package nitinka.quartzrest.resource;

import nitinka.quartzrest.core.SchedulerFactory;
import nitinka.quartzrest.core.SchedulerHelper;
import nitinka.quartzrest.domain.JobSchedulingInfo;
import nitinka.quartzrest.domain.RunTimeJobDetail;
import nitinka.quartzrest.domain.ScheduleInfo;
import nitinka.quartzrest.exception.BadCronExpressionException;
import nitinka.quartzrest.exception.JobAlreadyExistsException;
import nitinka.quartzrest.util.JobHelper;
import nitinka.quartzrest.util.ResponseBuilder;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Http End Point that handles Job Scheduling
 */

@Path("/jobs")
public class JobSchedulingResource {

    /**
     * {
     "jobInfo" : {
       "name" : "Name1",
       "jobClass" : "AddJob",
       "group" : "Group1",
       "description" : "Sample Add Job",
       "storeDurably" : true,
       "jobParams" : {
     }
     },scheduleInfo {
       "triggerInfo" : {
         "name" : "Name1",
         "group" : "Group1",
         "startDate" : null,
         "endDate" : null
       },
       "simpleScheduleInfo" : {
         "repeatForever" : false,
         "interval" : 30000,
         "repeatCount" : 5
       },
       "cronScheduleInfo" : {
         "cronExpression" : "0/5 * * * * ?"
       },
       "simpleSchedule" : false
      }
     }

     * @param jobSchedulingInfo
     * @throws ClassNotFoundException
     * @throws JobAlreadyExistsException
     * @throws SchedulerException
     * @throws ParseException
     */
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public RunTimeJobDetail scheduleJob(JobSchedulingInfo jobSchedulingInfo) throws ClassNotFoundException, JobAlreadyExistsException, SchedulerException {
        try {
            return scheduler().scheduleJob(JobHelper.buildJobDetail(jobSchedulingInfo.getJobInfo()),
                    JobHelper.buildTrigger(jobSchedulingInfo.getScheduleInfo().getTriggerInfo(),
                            JobHelper.buildSchedulerBuilder(jobSchedulingInfo.getScheduleInfo())));
        } catch (BadCronExpressionException e) {
            throw new WebApplicationException(ResponseBuilder.badRequest(e.getLocalizedMessage()));
        }
    }

    @Path("/{group}.{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RunTimeJobDetail getJob(@PathParam("group") String group, @PathParam("name") String name) throws SchedulerException {
        return jobExistsOrException(group, name);
    }

    @Path("/{group}.{name}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteJob(@PathParam("group") String group, @PathParam("name") String name) throws SchedulerException {
        jobExistsOrException(group, name);
        scheduler().removeJob(new JobKey(name, group));
        return ResponseBuilder.resourceDeleted("Job", "{Group:" + group + ", name:" + name + "}");
    }

    @Path("/{group}.{name}/triggers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<? extends Trigger> getJobTriggers(@PathParam("group") String group, @PathParam("name") String name) throws SchedulerException {
        return jobExistsOrException(group, name).getTriggers();
    }

    /**
     *
     {
       "triggerInfo" : {
         "name" : "Name1",
         "group" : "Group1",
         "startDate" : null,
         "endDate" : null
       },
       "simpleScheduleInfo" : {
         "repeatForever" : false,
         "interval" : 30000,
         "repeatCount" : 5
       },
       "cronScheduleInfo" : {
         "cronExpression" : "0/5 * * * * ?"
       },
       "simpleSchedule" : false
     }

     * @param group
     * @param name
     * @param scheduleInfo
     * @return
     * @throws SchedulerException
     */
    @Path("/{group}.{name}/triggers")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RunTimeJobDetail addJobTrigger(@PathParam("group") String group, @PathParam("name") String name, ScheduleInfo scheduleInfo) throws SchedulerException {
        RunTimeJobDetail runTimeJobDetail = jobExistsOrException(group, name);
        try {
            scheduler().getQuartzScheduler().scheduleJob(JobHelper.buildTriggerForJob(scheduleInfo.getTriggerInfo(),
                    JobHelper.buildSchedulerBuilder(scheduleInfo), runTimeJobDetail.getJobDetail()));
            return jobExistsOrException(group, name);
        } catch (BadCronExpressionException e) {
            throw new WebApplicationException(ResponseBuilder.badRequest(e.getLocalizedMessage()));
        } catch (ObjectAlreadyExistsException e) {
            throw new WebApplicationException(ResponseBuilder.
                    resourceAlreadyExists("Trigger", scheduleInfo.getTriggerInfo().getGroup()+"."+scheduleInfo.getTriggerInfo().getName()));
        }
    }

    @Path("/{jobGroup}.{jobName}/triggers/{triggerGroup}.{triggerName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Trigger getJobTrigger(@PathParam("jobGroup") String jobGroup,
                                          @PathParam("jobName") String jobName,
                                          @PathParam("triggerGroup") String triggerGroup,
                                          @PathParam("triggerName") String triggerName) throws SchedulerException {
        return jobExistsOrException(jobGroup, jobName).
                getTrigger(triggerGroup, triggerName);
    }

    @Path("/{jobGroup}.{jobName}/triggers/{triggerGroup}.{triggerName}/unSchedule")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void unScheduleJobTrigger(@PathParam("jobGroup") String jobGroup,
                                    @PathParam("jobName") String jobName,
                                    @PathParam("triggerGroup") String triggerGroup,
                                    @PathParam("triggerName") String triggerName) throws SchedulerException {
        Trigger trigger = jobExistsOrException(jobGroup, jobName).
                getTrigger(triggerGroup, triggerName);

        if(trigger != null)
            scheduler().getQuartzScheduler().unscheduleJob(trigger.getKey());
    }

    /**
     *
     * @param groupExp Regular Expression for Job Group
     * @param nameExp  Regular Expression for Job Name
     * @return
     * @throws SchedulerException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RunTimeJobDetail> searchJobs(@QueryParam("group") @DefaultValue(".+") String groupExp,
                                             @QueryParam("name") @DefaultValue(".+") String nameExp) throws SchedulerException {
        try{
            return scheduler().searchJobs(groupExp, nameExp);
        }
        catch(PatternSyntaxException e) {
            throw new WebApplicationException(ResponseBuilder.badRequest(e.getLocalizedMessage()));
        }
    }

    @DELETE
    public void deleteAllJobs() throws SchedulerException {
        scheduler().removeAllJobs();
    }

    private RunTimeJobDetail jobExistsOrException(String group, String name) throws SchedulerException {
        RunTimeJobDetail runTimeJobDetail = scheduler().getJobDetails(group, name);
        if(runTimeJobDetail == null)
            throw new WebApplicationException(ResponseBuilder.resourceNotFound("Job", "{Group:" + group + ", name:" + name + "}"));
        return runTimeJobDetail;
    }

    public SchedulerHelper scheduler() {
        return SchedulerFactory.getDefaultScheduler();
    }
}

