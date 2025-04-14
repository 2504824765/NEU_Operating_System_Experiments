package experiment2.first;

import java.util.ArrayList;
import java.util.Scanner;

public class Run {
    private ArrayList<ProcessControlBlock> pcbsArrayList = new ArrayList<>();
//    private final int[] requestTimes = {2, 3, 1, 2, 4};
//    private final int[] priorities = {1, 5, 3, 4, 2};
    private ArrayList<Integer> requestTimes = new ArrayList<>();
    private ArrayList<Integer> priorities = new ArrayList<>();
    private int numOfProcesses = 5;

    private ProcessControlBlock[] pcbs = new ProcessControlBlock[] {};

    private ProcessControlBlock buildQueue(ProcessControlBlock[] pcbs) {
        ProcessControlBlock head = null;
        for (ProcessControlBlock pcb : pcbs) {
            head = insertPCB(head, pcb);
        }
        return head;
    }

    Run() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        numOfProcesses = in.nextInt();
        System.out.print("Enter number of request time of each process: ");
        for (int i = 0; i < numOfProcesses; i++) {
            requestTimes.add(in.nextInt());
        }
        System.out.print("Enter priorities of each process: ");
        for (int i = 0; i < numOfProcesses; i++) {
            priorities.add(in.nextInt());
        }
        in.close();
        System.out.println();
        for (int i = 0; i < numOfProcesses; i++) {
            pcbsArrayList.add(new ProcessControlBlock("P" + (i+1), requestTimes.get(i), priorities.get(i)));
        }
        pcbs = pcbsArrayList.toArray(new ProcessControlBlock[0]);
    }

    /**
     * 根据优先级构建指针
     * @param head 头节点
     * @param pcb 新加入的节点
     * @return 返回链表的头节点
     */
    private ProcessControlBlock insertPCB(ProcessControlBlock head, ProcessControlBlock pcb) {
        // 作为头节点插入的情况
        if (head == null || pcb.getPriority() > head.getPriority()) {
            pcb.next = head;
            return pcb;
        }
        ProcessControlBlock current = head;
        // 如果当前节点不为空并且当前节点的下一个节点的优先级高于要加入的优先级
        while (current.next != null && current.next.getPriority() > pcb.getPriority()) {
            current = current.next;
        }
        // 已经移动到要插入到节点，将当前节点的下一个节点替换为新加的节点
        pcb.next = current.next;
        current.next = pcb;
        return head;
    }

    public void printPCBs(ProcessControlBlock head, ProcessControlBlock[] pcbs, int cpuTime) {
        System.out.print("CPUTime: " + cpuTime + " | ");
        printReadyQueue(head);
        System.out.println("NAME CPUTIME NEEDTIME PRIORITY STATUS");
        for (ProcessControlBlock pcb : pcbs) {
            System.out.println(pcb);
        }
        System.out.println();
    }

    public void run() {
        showInitInfo();
        ProcessControlBlock readyQueue = buildQueue(pcbs);
        int cpuTime = 0;
        // 当准备队列不为空时循环
        while (readyQueue != null) {
            ProcessControlBlock current = readyQueue;
            current.setStatus(Status.Working);
            // Run once
            current.plusWorkingTime();
            for (ProcessControlBlock pcb : pcbs) {
                if (pcb.getStatus() != Status.Finish) {
                    pcb.plusRoundTime();
                }
            }
            current.runOnce();
            printPCBs(readyQueue, pcbs, cpuTime);
            // Remove from list
            readyQueue = readyQueue.next;
            current.next = null;
            // If requestTime != 0, insert in list again
            if (current.getRequestTime() != 0) {
                current.setStatus(Status.READY);
                readyQueue = insertPCB(readyQueue, current);
            }
            // If requestTime == 0, change STATUS and do not insert
            else {
                current.setStatus(Status.Finish);
            }
            cpuTime++;
        }
        System.out.println("NAME RoundTime WaitingTime");
        for (ProcessControlBlock pcb : pcbs) {
            System.out.println(pcb.getProcessName() + "\t" + pcb.getRoundTime() + "\t" + (pcb.getRoundTime() - pcb.getWorkingTime()));
        }
    }

    private void showInitInfo() {
        System.out.println("INPUTNAME NEEDTIME PRIORITY");
        for (ProcessControlBlock pcb : pcbs) {
            System.out.println(pcb.getProcessName() + "\t" + pcb.getRequestTime() + "\t" + pcb.getPriority());
        }
        System.out.println();
    }

    private static void printReadyQueue(ProcessControlBlock readyQueue) {
        System.out.print("ReadyQueue: ");
        ProcessControlBlock temp = readyQueue;
        while (temp != null) {
            System.out.print(temp.getProcessName() + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
