package experiment3.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

// Since 2025/4/15 by CZ
public class Run {
    private int numOfPages = 7;
    private int sizeOfBlock = 128;
    //    private int[] memoryIDs = {5, 8, 9, 1, -1, -1, -1};
    private int[] memoryIDs = {-1, -1, -1, -1, -1, -1, -1};
    private int[] locationsOfDisk = {11, 12, 13, 21, 22, 23, 121};
    //    private boolean[] changed = {true, false, false, false, false, false, false, false};
    private Page[] pages = new Page[numOfPages];
    private ArrayList<Command> operations = new ArrayList<Command>();
    private LinkedList<Page> queue = new LinkedList<>();
    private int sizeOfQueue = 4; // 主存中的空闲块

    private final boolean isDefault = true;

    // 新增：主存块池和页号到主存块号的映射
    private int[] memoryBlockPool = {5, 8, 9, 1};
    private HashMap<Integer, Integer> pageToBlockMap = new HashMap<>();

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
        // 不是预设
        if (!isDefault) {
            int numOfOperations = 0;
            Scanner in = new Scanner(System.in);
            System.out.print("请输入指令数：");
            numOfOperations = in.nextInt();
            System.out.println("请输入页号，单元号及其是否更改（1表示更改，0表示未更改）：");
            for (int i = 0; i < numOfOperations; i++) {
                operations.add(new Command(in.nextInt(), in.nextInt(), in.nextInt()));
            }
            in.close();
        }
        // 预设
        else {
            operations.add(new Command(0, 12, 1));
            operations.add(new Command(1, 23, 0));
            operations.add(new Command(2, 34, 1));
            operations.add(new Command(1, 12, 1));
            operations.add(new Command(3, 45, 0));
            operations.add(new Command(4, 56, 0));
            operations.add(new Command(5, 67, 1));
            operations.add(new Command(6, 78, 1));
        }
    }

    public boolean findPageInMemory(Page page, int unitNumber) {
        System.out.println(operations.get(page.getPageID()));
        // 如果在主存中
        if (page.isInMemory()) {
            System.out.print("Page " + page.getPageID() + " 在主存中，绝对地址：");
            System.out.print(page.getMemoryID() + " * " + sizeOfBlock + " + " + operations.get(page.getPageID()) + " = ");
            System.out.println(page.getMemoryID()*sizeOfBlock + unitNumber);
            return true;
        }
        // 如果不在主存中
        else {
            System.out.println("Page " + page.getPageID() + " 不在主存中，页号：" + page.getPageID());
            return false;
        }
    }

    // 获取可用的主存块号
    private int getFreeMemoryBlock() {
        for (int blockID : memoryBlockPool) {
            if (!pageToBlockMap.containsValue(blockID)) {
                return blockID;
            }
        }
        return -1; // 无可用块
    }

    public void run() {
        init();
        saveOperation();
        for (Command operation : operations) {
            int pageID = operation.getPageID();
            int unitNumber = operation.getUnitNumber();
            boolean isChanged = operation.isChanged();
            pages[pageID].setLocationOfDisk(unitNumber);
            pages[pageID].setChanged(isChanged);
            // 如果在主存中找到，移动到队首
            if (findPageInMemory(pages[pageID], unitNumber)) {
                queue.remove(pages[pageID]);
                queue.addFirst(pages[pageID]);
            }
            // 如果主存中没有
            else {
                // 如果主存已满，需淘汰页
                if (queue.size() >= sizeOfQueue) {
                    Page outputPage = queue.removeLast();
                    if (outputPage.isChanged()) {
                        System.out.println("Page " + outputPage.getPageID() + " has changed. Output Page: " + outputPage.getPageID());
                    } else {
                        System.out.println("Page " + outputPage.getPageID() + " has not changed. Do not output");
                    }
                    int freedBlock = outputPage.getMemoryID();
                    outputPage.setMemoryID(-1);
                    pageToBlockMap.remove(outputPage.getPageID());

                    pages[pageID].setMemoryID(freedBlock);
                    pageToBlockMap.put(pageID, freedBlock);
                    queue.addFirst(pages[pageID]);
                }
                // 如果主存没满，有空闲块
                else {
                    int freeBlock = getFreeMemoryBlock();
                    if (freeBlock != -1) {
                        pages[pageID].setMemoryID(freeBlock);
                        pageToBlockMap.put(pageID, freeBlock);
                        queue.addFirst(pages[pageID]);
                    } else {
                        System.out.println("没有空闲主存块，异常！");
                    }
                }
            }

            // 输出当前队列状态
            for (int i = 0; i < queue.size(); i++) {
                System.out.println(queue.get(i));
            }
            System.out.println();
        }
    }
}
