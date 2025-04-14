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
    }

    public ProcessControlBlock insertPCB(ProcessControlBlock head, ProcessControlBlock newPCB) {
        // 作为头节点的情况
        if (head == null) {
            return newPCB;
        }
        ProcessControlBlock current = head;
        while (current != null) {
            if (current.next == null) {
                current.next = newPCB;
                current = current.next;
            }
        }
        return head;
    }

    public ProcessControlBlock buildQueue(ProcessControlBlock[] pcbs) {
        ProcessControlBlock head = null;
        for (ProcessControlBlock pcb : pcbs) {
            head = insertPCB(head, pcb);
        }
        // 头尾连接
        ProcessControlBlock current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = head;
        return head;
    }

    public void run() {
        init();
        ProcessControlBlock head = buildQueue(pcbs);
        ProcessControlBlock current = head;
        while (current.next != null) {
            System.out.print(current.getName() + " ");
            current = current.next;
        }

    }
}
