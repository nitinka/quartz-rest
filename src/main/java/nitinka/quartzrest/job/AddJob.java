package nitinka.quartzrest.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 22/7/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date().toString() + " 2 + 2 : 4");
    }
}
