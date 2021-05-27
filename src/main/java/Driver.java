import java.util.Date;

public class Driver {

    public static void main(String[] args) throws InterruptedException {
        TaskScheduler taskScheduler = new TaskScheduler();
        Task task = new Task("one");

        Task task2 = new Task("two", new Date().getTime() + 10000);
        taskScheduler.scheduleTask(task);
        taskScheduler.scheduleTask(task2);

    }
}
