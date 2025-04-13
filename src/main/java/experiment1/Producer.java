package experiment1;

public class Producer extends Thread {
    public static int counter = 0; // Record the number of Products
    private ThreadPool pool;
    private boolean running = true;

    Producer(ThreadPool pool) {
        this.pool = pool;
    }

    public void run() {
        while (running) {
            // 要对该类进行加锁，如果同时有多个Producer对counter进行操作需要进行加锁确保线程安全
            synchronized (Producer.class) {
                counter++;
                pool.addNewTask(new Product(counter));
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}
