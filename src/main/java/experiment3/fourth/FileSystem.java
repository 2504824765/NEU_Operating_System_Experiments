package experiment3.fourth;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

// Since 2025/4/16 by CZ
public class FileSystem {
    private HashMap<String, User> users; // 系统注册的所有用户
    private HashMap<String, ArrayList<File>> openedFiles4EachUser; // 当前用户打开的所有文件
    private User currentUser;

    public FileSystem() {
        users = new HashMap<>();
        users.put("meyer", new User("meyer"));
        users.put("admin", new User("admin"));
        openedFiles4EachUser = new HashMap<>();
        currentUser = null;
    }

    public void init() {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter user name: ");
        String inputUserName = in.nextLine();
        // 如果用户没有注册
        if (!users.containsKey(inputUserName)) {
            System.out.println("User not found");
        } else {
            openedFiles4EachUser.putIfAbsent(inputUserName, new ArrayList<>());
            currentUser = users.get(inputUserName);
        }
    }

    public void dir() {
        if (currentUser.getUserFiles().isEmpty()) {
            System.out.println("No files found");
        } else {
            for (File file : currentUser.getUserFiles()) {
                System.out.println(file);
            }
        }
    }

    public String generateAddress() {
        Random rand = new Random();
        return rand.nextInt() + "";
    }

    public void create(String newFileName, int protectionCode) {
        // 如果文件数量超上线
        if (currentUser.getUserFiles().size() >= 10) {
            System.out.println("The number of files reach to maximum 10");
        } else {
            File newFile = new File(newFileName, generateAddress(), protectionCode);
            currentUser.getUserFiles().add(newFile);
            System.out.println("File '" + newFileName + "' created");
        }
    }

    public void delete(String fileName) {
        for (File file : currentUser.getUserFiles()) {
            if (file.getFileName().equals(fileName)) {
                currentUser.getUserFiles().remove(file);
                System.out.println("Deleted file '" + fileName + "'");
                break;
            }
        }
    }

    public void open(String fileName) {
        for (File file : currentUser.getUserFiles()) {
            if (file.getFileName().equals(fileName)) {
                // If the number of opened file is greater than 5
                if (openedFiles4EachUser.get(currentUser.getUserName()).size() >= 5) {
                    System.out.println("The number of opened files reach to maximum 5");
                } else {
                    // If file hasn't opened
                    if (!openedFiles4EachUser.get(currentUser.getUserName()).contains(file)) {
                        openedFiles4EachUser.get(currentUser.getUserName()).add(file);
                        System.out.println("File '" + fileName + "' opened");
                    } else {
                        System.out.println("File '" + fileName + "' already opened");
                    }
                }
                return;
            }
        }
    }

    public void close(String fileName) {
        for (File file : openedFiles4EachUser.get(currentUser.getUserName())) {
            if (file.getFileName().equals(fileName)) {
                openedFiles4EachUser.get(currentUser.getUserName()).remove(file);
                System.out.println("Close file '" + fileName + "'");
            }
            break;
        }
    }

    public int read(String fileName) {
        // Search target file in opened files
        for (File file : openedFiles4EachUser.get(currentUser.getUserName())) {
            if (file.getFileName().equals(fileName)) {
                if (file.getProtectionCode() == 0) {
                    System.out.println("The file '" + fileName + "' is protected");
                    return 0;
                } else {
                    System.out.println("The file '" + fileName + "' is READ. Data: " + file.getData());
                    return file.getData();
                }
            }
        }
        System.out.println("The file '" + fileName + "' is not opened");
        return 0;
    }

    public void write(String fileName, int data) {
        for (File file : openedFiles4EachUser.get(currentUser.getUserName())) {
            if (file.getFileName().equals(fileName)) {
                if (file.getProtectionCode() == 0) {
                    System.out.println("The file '" + fileName + "' is protected");
                } else {
                    System.out.println("The file '" + fileName + "' is WRITTEN with data: " + data);
                    file.setData(data);
                }
                return;
            }
        }
        System.out.println("The file '" + fileName + "' is not opened");
    }

    public void run() {
        init();
        if (currentUser == null) {
            return;
        }
        System.out.println("Current user: '" + currentUser.getUserName() + "'");
        Scanner in = new Scanner(System.in);
        int choice = 0;
        welcome();
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = in.nextInt();
            String targetFileName = "";
            switch (choice) {
                case 1:
                    System.out.print("Please enter file name and protection code: ");
                    String newFileName = in.next();
                    int protectionCode = in.nextInt();
                    create(newFileName, protectionCode);
                    break;
                case 2:
                    System.out.print("Please enter file name: ");
                    targetFileName = in.next();
                    open(targetFileName);
                    break;
                case 3:
                    System.out.print("Please enter file name: ");
                    targetFileName = in.next();
                    close(targetFileName);
                    break;
                case 4:
                    System.out.print("Please enter file name: ");
                    targetFileName = in.next();
                    read(targetFileName);
                    break;
                case 5:
                    System.out.print("Please enter file name and data: ");
                    targetFileName = in.next();
                    int writeData = in.nextInt();
                    write(targetFileName, writeData);
                    break;
                case 6:
                    System.out.print("Please enter file name: ");
                    targetFileName = in.next();
                    delete(targetFileName);
                    break;
                case 7:
                    dir();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    private void welcome() {
        System.out.println(
                "---------File System---------\n" +
                "1. Create File\n" +
                "2. Open File\n" +
                "3. Close File\n" +
                "4. Read File\n" +
                "5. Write File\n" +
                "6. Delete File\n" +
                "7. Check Dir\n" +
                "0. Exit System\n" +
                "----------------------------");
    }
}
