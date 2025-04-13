package experiment1;

public class Consumer extends Thread {
    private ThreadPool pool;
    private boolean running;

    Consumer(ThreadPool pool) {
        this.pool = pool;
    }

    public void run() {
        while (true) {
            pool.removeTask();
            pool.showCurrentTasks();
        }
    }

    public void shutdown() {
        running = false;
    }
}
