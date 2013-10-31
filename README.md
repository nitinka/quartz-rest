A quartz-rest is a dropwizard based rest interface for all your quartz deployments. Simply add your quartz configuration in config folder, start quartz-rest app and your all your quartz instances controlled via rest end points.
<br>
<br>
<b>Getting Started<b>
1) Create quartz configuration, say <b>quartz-1.properties</b>, file for your quartz instance
<pre>
#DataSource Configurations
org.quartz.dataSource.myDS.driver = com.mysql.jdbc.Driver
org.quartz.dataSource.myDS.URL = jdbc:mysql://localhost:3306/quartz
org.quartz.dataSource.myDS.user = root
org.quartz.dataSource.myDS.password =
org.quartz.dataSource.myDS.maxConnections = 30

#Clustering Configuration
org.quartz.jobStore.isClustered = true
org.quartz.scheduler.instanceName = MyScheduler
org.quartz.scheduler.instanceId = AUTO
org.quartz.jobStore.clusterCheckinInterval = 20000

#SchedulerHelper Thread Pool Configuration
org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount = 25
org.quartz.threadPool.threadPriority = 5

#SchedulerHelper Configuration
org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
org.quartz.jobStore.tablePrefix = QRTZ_
org.quartz.jobStore.dataSource = myDS
</pre>
<br>

2) Drop this file on <b>config/schedulerConfigs</b> folder :
<pre>
config/schedulerConfigs/quartz-1.properties  
</pre>

3) Start quartz-rest : <b>mvn clean compile exec:java</b><br>
You would see following rest end point to interface with your quartz deployment : <br>
<pre>
DELETE  /quartz-interface/jobs (nitinka.quartzrest.resource.JobSchedulingResource)
    DELETE  /quartz-interface/jobs/{group}.{name} (nitinka.quartzrest.resource.JobSchedulingResource)
    GET     /quartz-interface/jobs (nitinka.quartzrest.resource.JobSchedulingResource)
    GET     /quartz-interface/jobs/{group}.{name} (nitinka.quartzrest.resource.JobSchedulingResource)
    GET     /quartz-interface/jobs/{group}.{name}/triggers (nitinka.quartzrest.resource.JobSchedulingResource)
    GET     /quartz-interface/jobs/{jobGroup}.{jobName}/triggers/{triggerGroup}.{triggerName} (nitinka.quartzrest.resource.JobSchedulingResource)
    POST    /quartz-interface/jobs (nitinka.quartzrest.resource.JobSchedulingResource)
    POST    /quartz-interface/jobs/{group}.{name}/triggers (nitinka.quartzrest.resource.JobSchedulingResource)
    PUT     /quartz-interface/jobs/{jobGroup}.{jobName}/triggers/{triggerGroup}.{triggerName}/unSchedule (nitinka.quartzrest.resource.JobSchedulingResource)
    GET     /quartz-interface/schedulers (nitinka.quartzrest.resource.SchedulerResource)
    GET     /quartz-interface/schedulers/{schedulerName} (nitinka.quartzrest.resource.SchedulerResource)
    PUT     /quartz-interface/schedulers/{schedulerName}/defaultScheduler (nitinka.quartzrest.resource.SchedulerResource)
    PUT     /quartz-interface/schedulers/{schedulerName}/shutdown (nitinka.quartzrest.resource.SchedulerResource)
    PUT     /quartz-interface/schedulers/{schedulerName}/standby (nitinka.quartzrest.resource.SchedulerResource)
    PUT     /quartz-interface/schedulers/{schedulerName}/start (nitinka.quartzrest.resource.SchedulerResource)
    GET     /quartz-interface/triggers/{group}.{name} (nitinka.quartzrest.resource.TriggerResource)
    GET     /quartz-interface/triggers/{group}.{name}/job (nitinka.quartzrest.resource.TriggerResource)
</pre>
