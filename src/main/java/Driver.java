import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Driver {

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task("one");

        Set<Thread> allThreads = new HashSet<Thread>();
        final TaskScheduler deferredCallbackExecutor = new TaskScheduler();

        Thread service = new Thread(new Runnable() {
            public void run() {
                try {
                    deferredCallbackExecutor.start();
                } catch (InterruptedException ie) {

                }
            }
        });
        service.start();
        Task task2 = new Task("two", new Date().getTime() + 10000);
        deferredCallbackExecutor.scheduleTask(task);
        deferredCallbackExecutor.scheduleTask(task2);

    }
}
