package experiment1;

public class Main {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(10);
        Producer producer1 = new Producer(pool);
        Producer producer2 = new Producer(pool);
        Consumer consumer1 = new Consumer(pool);
        Consumer consumer2 = new Consumer(pool);
        Thread producerThread1 = new Thread(producer1, "producerThread1");
        Thread producerThread2 = new Thread(producer2, "producerThread2");
        Thread consumerThread1 = new Thread(consumer1, "consumerThread1");
        Thread consumerThread2 = new Thread(consumer2, "consumerThread2");

        producerThread1.setPriority(Thread.MAX_PRIORITY);
        consumerThread2.setPriority(Thread.MIN_PRIORITY);

        producerThread1.start();
        producerThread2.start();
//        consumerThread1.start();
        consumerThread2.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producer1.shutdown();
        producer2.shutdown();
//        consumer1.shutdown();
        consumer2.shutdown();
    }
}
