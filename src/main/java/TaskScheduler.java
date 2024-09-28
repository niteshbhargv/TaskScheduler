import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler {

    PriorityQueue<Task> priorityQueue;
    ReentrantLock lock = new ReentrantLock();
    Condition newCallbackArrived = lock.newCondition();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    public TaskScheduler() {
        this.priorityQueue = new PriorityQueue<>(Comparator.comparing(Task::getDate));
    }

    public void start() throws InterruptedException {
        long sleepFor = 0;
        while (true) {

            lock.lock();

            while (priorityQueue.size() == 0) {
                newCallbackArrived.await();
            }

            while (priorityQueue.size() != 0) {
                sleepFor = findSleepDuration();

                if(sleepFor <=0)
                    break;

                newCallbackArrived.await(sleepFor, TimeUnit.MILLISECONDS);
            }

            Task cb = priorityQueue.poll();
            System.out.println(
                    "Executed at " + System.currentTimeMillis()/1000 + " required at " + cb.getDate()/1000
                            + ": message:" + cb.name);
            cb.run();

            lock.unlock();
        }

    }



    private long findSleepDuration() {
        long currentTime = System.currentTimeMillis();
        return priorityQueue.peek().getDate() - currentTime;
    }

    public void scheduleTask(Task task) {
        lock.lock();
        priorityQueue.add(task);
        newCallbackArrived.signal();
        lock.unlock();
    }
}
