package experiment3.first;

import java.util.HashMap;
import java.util.Scanner;

// Since 2025/4/15 by CZ
public class Run {
    private int numOfPages = 7;
    private int sizeOfBlock = 128;
    private int[] memoryIDs = {5, 8, 9, 1, -1, -1, -1};
    private int[] locationsOfDisk = {11, 12, 13, 21, 22, 23, 121};
    private Page[] pages = new Page[numOfPages];
    HashMap<Integer, Integer> operations = new HashMap<>();

    public void init() {
        for (int i = 0; i < numOfPages; i++) {
            pages[i] = new Page(i, locationsOfDisk[i]);
            if (memoryIDs[i] != -1) {
                pages[i].setMemoryID(memoryIDs[i]);
            }
        }
        System.out.println("初始状态：（-1表示不在主存中）");
        System.out.println("页号 | 标志 | 主存块号 | 在磁盘上的位置");
        for (int i = 0; i < numOfPages; i++) {
            System.out.println(pages[i]);
        }
        System.out.println();
    }

    public void saveOperation() {
        int numOfOperations = 0;
        Scanner in = new Scanner(System.in);
        System.out.print("请输入指令数：");
        numOfOperations = in.nextInt();
        System.out.println("请输入页号和其单元号：");
        for (int i = 0; i < numOfOperations; i++) {
            operations.put(in.nextInt(), in.nextInt());
        }
        in.close();
    }

    public void findPageInMemory(Page page) {
        System.out.print("(" + page.getPageID() + ", " + operations.get(page.getPageID()) + "): ");
        // 如果在主存中
        if (page.isInMemory()) {
            System.out.print("Page 在主存中，绝对地址：");
            System.out.print(page.getMemoryID() + " * " + sizeOfBlock + " + " + operations.get(page.getPageID()) + " = ");
            System.out.println(page.getMemoryID()*sizeOfBlock + operations.get(page.getPageID()));
        }
        // 如果不在主存中
        else {
            System.out.println("Page 不在主存中，页号：" + page.getPageID());
        }
    }

    public void run() {
        init();
        saveOperation();
        for (int key : operations.keySet()) {
            findPageInMemory(pages[key]);
        }
    }

}
