package experiment3.fourth.treefilesystem;

import java.util.ArrayList;

// Since 2025/4/16 by CZ
public class Dir extends Node {
    protected ArrayList<Node> children;

    public Dir(String nodeName) {
        super(nodeName);
        this.children = new ArrayList<>();
    }
}
