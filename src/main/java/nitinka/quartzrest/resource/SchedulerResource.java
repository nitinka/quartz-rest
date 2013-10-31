package nitinka.quartzrest.resource;

import nitinka.quartzrest.core.SchedulerFactory;
import nitinka.quartzrest.core.SchedulerHelper;
import nitinka.quartzrest.util.ResponseBuilder;
import org.quartz.SchedulerException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Http End Point that handles Job Scheduling
 */

@Path("/schedulers")
public class SchedulerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, SchedulerHelper> getSchedulers(){
        return SchedulerFactory.schedulers();
    }

    @Path("/{schedulerName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SchedulerHelper getScheduler(@PathParam("schedulerName") String schedulerName){
        return schedulerExistsOrException(schedulerName);
    }

    @Path("/{schedulerName}/standby")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public SchedulerHelper standbyScheduler(@PathParam("schedulerName") String schedulerName) throws SchedulerException {
        return schedulerExistsOrException(schedulerName).standby();
    }

    @Path("/{schedulerName}/start")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public SchedulerHelper startScheduler(@PathParam("schedulerName") String schedulerName) throws SchedulerException {
        return schedulerExistsOrException(schedulerName).start();
    }

    @Path("/{schedulerName}/shutdown")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void shutdownScheduler(@PathParam("schedulerName") String schedulerName) throws SchedulerException {
        schedulerExistsOrException(schedulerName).getQuartzScheduler().shutdown();
    }

    /**
     * Set the scehduler as default. Would be used by JobSchedulingResource
     * @param schedulerName
     * @return
     * @throws SchedulerException
     */
    @Path("/{schedulerName}/defaultScheduler")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void defaultScheduler(@PathParam("schedulerName") String schedulerName) throws SchedulerException {
        SchedulerFactory.setDefaultScheduler(schedulerExistsOrException(schedulerName));
    }

    private SchedulerHelper schedulerExistsOrException(String schedulerName) {
        SchedulerHelper schedulerHelper = SchedulerFactory.getScheduler(schedulerName);
        if(schedulerHelper == null)
            throw new WebApplicationException(ResponseBuilder.resourceNotFound("Scheduler", schedulerName));
        return schedulerHelper;
    }
}

