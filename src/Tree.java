import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tree {

    public final static int TREE_WIDTH = 1920;
    public final static int TREE_HEIGHT = 1080;

    private Node root;
    private Node NYT;

    private String text;

    private HashMap<Character, Node> map;

    public String getText() {
        return text;
    }

    public Tree(String text) {
        this.text = text;
        map = new HashMap<>();
    }

    public Node getRoot() {
        return root;
    }

    public void code() {

        root = new Node();
        root.setId(256);
        root.setX(TREE_WIDTH / 2);
        root.setY(40);
        NYT = root;

        int i = 0;

        char[] chars = text.toCharArray();

        for (char c : chars) //{
            insert(c);

    }

    private void insert(char c) {

        Node leafToIncrement = null;
        Node p = map.containsKey(c) ? getNode(c) : NYT;

        if (p.isNYT()) {
            Node left = new Node();
            int NYTid = NYT.getId();
            left.setId(NYTid - 2);

            Node right = new Node();
            right.setId(NYTid - 1);
            right.setCharacter(c);

            NYT.setLeft(left);
            NYT.setRight(right);
            NYT = left;

            map.put(c, right);

            p = left.getParent();
            leafToIncrement = right;

        } else {
            Node.swapNodes(p, getLeader(p.isLeaf(), p.getWeight()));

            if (p.isSiblingToNYT()) {
                leafToIncrement = p;
                p = p.getParent();
            }
        }
        while (p != null) {

            Node previousP = p.getParent();

            if (p.isInternal()) {

                Node leader = getLeader(true, p.getWeight() + 1);
                Node.swapNodes(p, leader);
                p.incWeight();
                p = previousP;
            } else {
                Node leader = getLeader(false, p.getWeight());
                Node.swapNodes(p, leader);
                p.incWeight();
                p = p.getParent();
            }
        }

        if (leafToIncrement != null) {

            Node previousP = leafToIncrement.getParent();

            if (leafToIncrement.isInternal()) {
                Node.swapNodes(p, getLeader(true, leafToIncrement.getWeight() + 1));
                leafToIncrement.incWeight();
                leafToIncrement = previousP;
            } else {
                Node.swapNodes(p, getLeader(false, leafToIncrement.getWeight()));
                leafToIncrement.incWeight();
                leafToIncrement = leafToIncrement.getParent();
            }
        }

    }

    private Node getNode(char c) {
        return map.get(c);
    }

    public List<Node> getSymbolNodes() {
        return new ArrayList<>(map.values());
    }

    public List<Node> getAllNodes() {

        List<Node> list = new ArrayList<>();
        return root.getAllNodes(list);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (Node n : getAllNodes())
            builder.append(n.toString()).append("\n");

        return builder.toString();

    }

    private Node getLeader(boolean isLeaf, int weight) {

        int max = -1;

        Node maxNode = null;

        for (Node n : getAllNodes()) {

            if (isLeaf) {
                if (n.isLeaf() && n.getWeight() == weight && n.getId() > max) {
                    max = n.getId();
                    maxNode = n;
                }
            } else {
                if (n.isInternal() && n.getWeight() == weight && n.getId() > max) {
                    max = n.getId();
                    maxNode = n;
                }
            }
        }
        return maxNode;
    }

    private Node getLeader(int weight) {

        int max = -1;

        Node maxNode = null;

        for (Node n : getAllNodes()) {

            if (n.getWeight() == weight && n.getId() > max) {
                max = n.getWeight();
                maxNode = n;
            }
        }
        return maxNode;
    }

    public float getAvgLength() {

        float avg = 0;
        float count = text.length();

        for (Node n : getSymbolNodes()) {
            float p = (float)n.getWeight() / count;
            avg += p * Node.getCode(n).length();
        }

        return avg;
    }

    public float getEntropy(){

        float sum = 0;
        float count = text.length();

        for (Node n : getSymbolNodes()) {
            float weight = n.getWeight();
            float p = weight / count;
            sum += p * log2(1f / p);
        }

        return sum;
    }

    private double log2(float number){
        return Math.log(number) / Math.log(2);
    }
}
