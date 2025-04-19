package experiment3.fourth.treefilesystem;

// Since 2025/4/16 by CZ
public class FileTreeRun {
    public static void main(String[] args) {
        Dir root = new Dir("root");
        Dir user1 = new Dir("user1");
        Dir user2 = new Dir("user2");
        user1.children.add(new FileLeaf("doc.txt", 0, 100));
        Dir project = new Dir("project");
        project.children.add(new FileLeaf("code.java", 1, 200));
        user1.children.add(project);
        root.children.add(user1);

        printTree(root, "");
    }

    private static void printTree(Node node, String indent) {
        if (node instanceof Dir) {
            System.out.println(indent + "📁 " + node.nodeName);
            for (Node child : ((Dir) node).children) {
                printTree(child, indent + "    "); // 每一层增加缩进
            }
        } else if (node instanceof FileLeaf) {
            FileLeaf file = (FileLeaf) node;
            System.out.println(indent + "📄 " + file.nodeName + " [权限: " + file.protectionCode + ", Data: " + file.data + "]");
        }
    }

}
