import java.util.Date;

public class Task implements Runnable {

    String name;
    long date;
    public Task(String name) {
        this.name = name;
    }

    public Task(String name, long date) {
        this.name = name;
        this.date = date;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("My task started.. " + name);

        System.out.println("My task End.." + new Date().getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
