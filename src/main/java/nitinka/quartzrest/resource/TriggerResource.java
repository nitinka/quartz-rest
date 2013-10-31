package nitinka.quartzrest.resource;

import nitinka.quartzrest.core.SchedulerHelper;
import nitinka.quartzrest.util.ResponseBuilder;
import org.quartz.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Http End Point that handles Job Scheduling
 */

@Path("/triggers")
public class TriggerResource {

    @Path("/{group}.{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Trigger getTrigger(@PathParam("group") String group, @PathParam("name") String name) throws SchedulerException {
        return triggerExistsOrException(group, name);
    }

    @Path("/{group}.{name}/job")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JobDetail getJob(@PathParam("group") String group, @PathParam("name") String name) throws SchedulerException {
        Trigger trigger = triggerExistsOrException(group, name);
        return scheduler().getQuartzScheduler().getJobDetail(trigger.getJobKey());
    }

    private Trigger triggerExistsOrException(String group, String name) throws SchedulerException {
        Trigger trigger = scheduler().getTrigger(group, name);
        if(trigger == null)
            throw new WebApplicationException(ResponseBuilder.resourceNotFound("Trigger", group + "." + name));
        return trigger;
    }

    public SchedulerHelper scheduler() {
        return nitinka.quartzrest.core.SchedulerFactory.getDefaultScheduler();
    }
}

