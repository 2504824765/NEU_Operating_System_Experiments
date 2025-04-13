package experiment1;

import java.util.LinkedList;

public class ThreadPool {
//    ✅ 插入删除更快
//    你在模拟线程“产生和消失”时，可能频繁地添加和移除线程对象。
//    LinkedList 在首尾插入/删除都是 O(1)，性能好于 ArrayList 的 O(n)。
//            ✅ 可当作队列使用
//    LinkedList 实现了 Deque 接口，可以当成队列用，如 addLast()、removeFirst()。
//    非常适合用作任务队列或线程对象列表。
    private LinkedList<Product> tasks;
    private int maxSize;
    private int currentSize;

    ThreadPool(int size) {
        this.maxSize = size;
        this.tasks = new LinkedList<>();
    }

    public synchronized String showCurrentTasks() {
//        System.out.print("Current task: ");
        if (currentSize == 0) {
            return "EMPTY";
        }
        StringBuilder str = new StringBuilder();
        for (Product task : tasks) {
            str.append(task.getId()).append(" ");
        }
        return str.toString();
//        System.out.println();
    }

    public synchronized void addNewTask(Product product) {
        if (currentSize == maxSize) {
            System.out.println("Producer is waiting because Threads Pool is FULL");
        }
        // 这里用 while，不是 if —— 是为了防止被错误唤醒（spurious wakeup），唤醒后要重新检查条件
        while (currentSize >= maxSize) {
            try {
                /*
                 * wait方法必须持有锁才可以调用。
                 * 调用后会让当前进程释放所占有的锁并进入wait状态
                 */
                this.wait(); // Wait when pool is full
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*
         * notify和notifyAll也必须持有锁才可以调用。
         * 调用后会重新唤醒等待当前锁的线程并让他们重新竞争锁
         * <p>
         * 例如：
         * 线程A调用addNewTask方法，但是线程池满了，线程A进入等待状态
         * 线程B运行之后有空间了，会告诉其他在等待状态中的线程（线程A），让他们重新竞争锁
         */
        notifyAll();
        // If pull is not full
        tasks.addLast(product);
        currentSize++;
        if (currentSize < maxSize) {
            System.out.println("Current pool: " + showCurrentTasks() + " | " + "Current size: " + currentSize);
        } else {
            System.out.println("Current pool: " + showCurrentTasks() + " | " + "Now pool is FULL");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void removeTask() {
        // If pool is empty
        if (currentSize == 0) {
            System.out.println("Consumer is waiting because Threads Pool is EMPTY");
        }
        while (currentSize <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Pool is not empty
        notifyAll(); // Wake up other process that the pool status is updated
        tasks.removeFirst();
        currentSize--;
        if (currentSize != maxSize) {
            System.out.println("Current pool: " + showCurrentTasks() + " | " + "Current size: " + currentSize);
        } else {
            System.out.println("Current pool: " + showCurrentTasks() + " | " + "Now pool is EMPTY");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
