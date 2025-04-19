package experiment3.fourth;

import java.util.ArrayList;

// Since 2025/4/16 by CZ
public class User {
    private String userName;
    private ArrayList<File> userFiles;

    public User(String userName) {
        this.userName = userName;
        this.userFiles = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<File> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(ArrayList<File> userFiles) {
        this.userFiles = userFiles;
    }
}
