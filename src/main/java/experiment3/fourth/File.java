package experiment3.fourth;

// Since 2025/4/16 by CZ
public class File {
    private String fileName;
    private String fileAddress;
    private int protectionCode;
    private int data;

    File(String fileName, String fileAddress, int protectionCode) {
        this.fileName = fileName;
        this.fileAddress = fileAddress;
        this.protectionCode = protectionCode;
        this.data = 0;
    }

    @Override
    public String toString() {
        return "File Name: " + fileName + " | File Address: " + fileAddress + " | Protection Code: " + protectionCode + " | File Data: " + data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public int getProtectionCode() {
        return protectionCode;
    }

    public void setProtectionCode(int protectionCode) {
        this.protectionCode = protectionCode;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
