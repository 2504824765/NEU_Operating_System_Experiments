package experiment3.fourth;

// Since 2025/4/16 by CZ
public class OpenedFile {
    private String fileName;
    private int protectionCode;
    private int readWritePointer;

    public OpenedFile(String fileName, int protectionCode, int readWritePointer) {
        this.fileName = fileName;
        this.protectionCode = protectionCode;
        this.readWritePointer = readWritePointer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getProtectionCode() {
        return protectionCode;
    }

    public void setProtectionCode(int protectionCode) {
        this.protectionCode = protectionCode;
    }

    public int getReadWritePointer() {
        return readWritePointer;
    }

    public void setReadWritePointer(int readWritePointer) {
        this.readWritePointer = readWritePointer;
    }
}
