package experiment3.fourth.treefilesystem;

// Since 2025/4/16 by CZ
public class FileLeaf extends Node {
    protected int protectionCode;
    protected int data;

    public FileLeaf(String nodeName, int protectionCode, int data) {
        super(nodeName);
        this.protectionCode = protectionCode;
        this.data = data;
    }
}
