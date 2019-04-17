import java.util.List;

public class Node {

    private Node left, right, parent;
    private Character ch = null;
    private int weight = 0;
    private int id;
    private int x, y;
    private int depth = 0;

    public void calcDepth() {
        if (parent == null)
            depth = 0;
        else
            depth = getCode(this).length();
    }

    public void incWeight() {
        weight++;
    }

    public static void swapNodes(Node n1, Node n2) {

        if (n1 == null || n2 == null || n1.getParent() == null || n2.getParent() == null)
            return;

        if (n1.getParent() == n2 || n2.getParent() == n1)
            return;

        if (n1 == n2)
            return;

        int id1 = n1.getId();
        int id2 = n2.getId();

        Node parent1 = n1.getParent();
        Node parent2 = n2.getParent();

        boolean isN1left = n1.isLeft();
        boolean isN2left = n2.isLeft();

        n1.setId(id2);
        n2.setId(id1);

        if (isN1left)
            parent1.setLeft(n2);
        else
            parent1.setRight(n2);

        if (isN2left)
            parent2.setLeft(n1);
        else
            parent2.setRight(n1);

    }

    public boolean isLeaf() {
        return ch != null;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append(id).append(" - ");

        if (weight == 0) {
            builder.append("NYT");
            return builder.toString();
        } else builder.append(weight).append(", ");

        if (getCharacter() != null) {
            builder.append(getCharacter());
        }

        builder.append(" | ");
        if (left != null)
            builder.append(left.id).append(" ");
        if (right != null)
            builder.append(right.id);

        return builder.toString();
    }

    public List<Node> getAllNodes(List<Node> nodes) {
        this.calcDepth();
        nodes.add(this);
        if (left != null) nodes = left.getAllNodes(nodes);
        if (right != null) nodes = right.getAllNodes(nodes);
        return nodes;
    }

    private String get01() {

        if (isLeft())
            return "0";
        else if (isRight())
            return "1";

        return "";
    }

    public static String getCode(Node n) {

        StringBuilder code = new StringBuilder(n.get01());

        Node parent = n.getParent();
        code.append(parent.get01());

        while (parent.hasParent()) {
            parent = parent.getParent();
            code.append(parent.get01());
        }

        return code.reverse().toString();
    }

    public boolean isLeft() {
        if (!hasParent())
            return false;

        if (getParent().getLeft() == null)
            return false;

        return (getParent().getLeft().equals(this));
    }

    public boolean isRight() {
        if (!hasParent())
            return false;

        if (getParent().getRight() == null)
            return false;

        return (getParent().getRight().equals(this));
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean isNYT() {
        return weight == 0;
    }

    public int getId() {
        return id;
    }

    public void setCharacter(char character) {
        this.ch = character;
    }

    public void setLeft(Node left) {
        this.left = left;
        left.setParent(this);
    }

    public boolean isInternal() {
        return ch == null;
    }

    public boolean isSiblingToNYT() {
        return !isNYT() && getParent().getLeft().isNYT();
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setRight(Node right) {
        this.right = right;
        right.setParent(this);
    }

    public Character getCharacter() {
        return ch;
    }

    public int getWeight() {
        return weight;
    }

    public Node getLeft() {
        return left;
    }

    public Node getParent() {
        return parent;
    }

    public Node getRight() {
        return right;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDepth() {
        return depth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
