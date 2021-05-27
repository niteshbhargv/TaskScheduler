import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    PriorityQueue<Task> priorityQueue;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    public TaskScheduler() {
        this.priorityQueue = new PriorityQueue<>(Comparator.comparing(Task::getDate));
    }

    public void scheduleTask(Task myTask) throws InterruptedException {
        if(myTask!=null) {
            priorityQueue.add(myTask);
            System.out.println("Adding task: "+ myTask.getName());
        }
        while(!priorityQueue.isEmpty() && priorityQueue.peek().getDate() <= new Date().getTime()) {
            Task task = priorityQueue.poll();
            executor.execute(task);
        }
        if(priorityQueue.isEmpty()) {
            return;
        } else {
            TimeUnit.MICROSECONDS.sleep(priorityQueue.peek().getDate()-new Date().getTime());
            scheduleTask(null);
        }
    }
}
