import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Application {
    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(JobImplementation.class).build();
        Trigger trig = TriggerBuilder.newTrigger().withIdentity("CroneTrigger").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(02).repeatForever()).build();
        Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
        sched.start();
        sched.scheduleJob(
                job, trig
        );
    }
}
