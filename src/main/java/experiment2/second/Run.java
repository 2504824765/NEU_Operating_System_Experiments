package experiment2.second;

import java.util.ArrayList;
import java.util.Scanner;

public class Run {
    int numOfProcesses;
    private ArrayList<ProcessControlBlock> pcbsList = new ArrayList<>();
    private ArrayList<Integer> requestTimes = new ArrayList<>();
    private ProcessControlBlock[] pcbs;

    public void init() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        numOfProcesses = in.nextInt();
        System.out.print("Enter request times of each process: ");
        for (int i = 0; i < numOfProcesses; i++) {
            requestTimes.add(in.nextInt());
        }
        for (int i = 0; i < numOfProcesses; i++) {
            pcbsList.add(new ProcessControlBlock("Q" + (i+1), requestTimes.get(i)));
        }
        pcbs = pcbsList.toArray(new ProcessControlBlock[0]);
        System.out.println("INPUTNAME | NEEDTIME");
        for (ProcessControlBlock pcb : pcbs) {
            System.out.println(pcb.getName() + "\t\t" + pcb.getRequestTime());
        }
        System.out.println("OUTPUT:");
        System.out.println();
    }

    /**
     * 从队列中移除节点
     * @param head 头节点
     * @param toRemove 要移除的节点
     * @return 返回移除后的队列头节点
     */
    public ProcessControlBlock removePCB(ProcessControlBlock head, ProcessControlBlock toRemove) {
        if (head == null || toRemove == null) {
            return null;
        }
        // 如果只有一个节点
        if (head == toRemove && head.next == head) {
            return null;
        }
        // 要移除的是头节点
        if (head == toRemove) {
            // 移动到头节点的前一个节点
            ProcessControlBlock current = head;
            while (current.next != head) {
                current = current.next;
            }
            // 移除头节点
            current.next = head.next;
            head = head.next;
            return head;
        }
        // 要移除的不是头节点
        // 获得要删除节点的前一个节点
        ProcessControlBlock current = head;
        while (current.next != toRemove) {
            current = current.next;
            // 若回到头节点说明没有要删除的节点
            if (current.next == head) {
                System.out.println("ERROR: Node doesn't exist");
                return head;
            }
        }
        // 删除操作
        current.next = toRemove.next;
        return head;
    }

    public ProcessControlBlock insertPCB(ProcessControlBlock head, ProcessControlBlock newPCB) {
        // 作为头节点的情况
        if (head == null) {
            return newPCB;
        }
        ProcessControlBlock current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newPCB;
        return head;
    }

    public ProcessControlBlock buildQueue(ProcessControlBlock[] pcbs) {
        if (pcbs == null) {
            return null;
        }
        // 构建队列
        ProcessControlBlock head = pcbs[0];
        ProcessControlBlock current = head;
        for (int i = 1; i < pcbs.length; i++) {
            current.next = pcbs[i];
            current = current.next;
        }
        // 头尾连接
        current.next = head;
        return head;
    }

    public void run() {
        init();
        ProcessControlBlock readyQueue = buildQueue(pcbs);

        int cpuTime = 0;
        ProcessControlBlock current = readyQueue;
        while (readyQueue != null) {
            // 运行一次
            current.setStatus(Status.Working);
            current.setWorkedTime(current.getWorkedTime() + 1);
            current.setRoundTime(current.getRoundTime() + 1);
            for (ProcessControlBlock pcb : pcbs) {
                if (pcb.getStatus() == Status.Ready) {
                    pcb.setWaitingTime(pcb.getWaitingTime() + 1);
                }
            }
            System.out.println("CPUTime: " + cpuTime);
            System.out.println("NAME | REQUESTTIME | WORKEDTIME | STATE");
            for (ProcessControlBlock pcb : pcbs) {
                System.out.println(pcb.getName() + "\t\t" + pcb.getRequestTime() + "\t\t" + pcb.getWorkedTime() + "\t\t" + pcb.getStatus());
            }
            System.out.println();
            // If 'current' is done
            if (current.getWorkedTime() >= current.getRequestTime()) {
                // Remove 'current' from queue
                current.setStatus(Status.Finish);
                readyQueue = removePCB(readyQueue, current);
                // If readyQueue is null, end the loop
                if (readyQueue == null) {
                    break;
                }
                // If readyQueue is not null, continue the loop
                else {
                    current = current.next;
                }
            }
            // if 'current' is not done
            else {
                current.setStatus(Status.Ready);
                current.setRoundTime(current.getRoundTime() + 1);
                current = current.next;
            }
            cpuTime++;
        }
        System.out.println("All processes finished");
        System.out.println("NAME | RoundTime | WaitingTime");
        for (ProcessControlBlock pcb : pcbs) {
            System.out.println(pcb.getName() + "\t\t" + pcb.getRoundTime() + "\t\t" + pcb.getWaitingTime());
        }
    }
}
