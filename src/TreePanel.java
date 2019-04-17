import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {
    final static int NWIDTH = 44;
    final static int NHEIGHT = 32;
    final static int STR1_X = 10;
    final static int STR1_Y = 14;
    final static int STR2_X = 10;
    final static int LEAF_STR2_X = 4;
    final static int STR2_Y = 26;

    final static int OUT_L_X = 8;
    final static int OUT_R_X = 38;
    final static int OUT_Y = 28;
    final static int IN_X = NWIDTH / 2;

    final static int HSPACE = 30;
    final static int VSPACE = 30;

    private Tree tree = null;
    private int height;
    private int center;

    public void drawTree(Tree tree) {
        this.tree = tree;

        height = Tree.TREE_HEIGHT;
        center = Tree.TREE_WIDTH / 2;
        setPreferredSize(new Dimension(2 * center + 10, height * (NHEIGHT + VSPACE) + 50));
        repaint();
    }

    public void paintTree(Graphics g, Node node) {
        paintNode(g, node);
        Node leftChild = node.getLeft();
        Node rightChild = node.getRight();

        boolean areChildrenInternal = leftChild != null && rightChild != null && leftChild.isInternal() && rightChild.isInternal();

        node.calcDepth();

        int depth = node.getDepth();

        int y = 100, x;

        if (depth == 0) {
            x = 200;
        } else {
            x = 50;
        }

        int xBaseValue = x;

        if (leftChild != null) {

            String code = Node.getCode(leftChild);
            if (!code.contains("1") && depth > 0 && leftChild.isInternal())
                x *= 2.5f;
            else if (areChildrenInternal)
                x *= 1.25f;
            else
                x /= 2;

            leftChild.setY(node.getY() + y);
            leftChild.setX(node.getX() - x);

            paintTree(g, leftChild);
        }

        x = xBaseValue;

        if (rightChild != null) {
            String code = Node.getCode(rightChild);
            if (!code.contains("0") && depth > 0 && rightChild.isInternal())
                x *= 2.5f;
            else if (areChildrenInternal)
                x *= 1.25f;
            else
                x /= 2;
            rightChild.setY(node.getY() + y);
            rightChild.setX(node.getX() + x);
            paintTree(g, rightChild);
        }
    }

    private void paintNode(Graphics g, Node node) {
        int x = node.getX();
        int y = node.getY();
        String number = "" + node.getId() + ".";

        if (node.isLeaf()) {
            char symbol = node.getCharacter();
            String sSymbol = Character.toString(symbol);

            String lower = sSymbol + " " + node.getWeight();
            g.drawRect(x, y, NWIDTH, NHEIGHT);
            g.drawString(number, x + STR1_X, y + STR1_Y);
            g.drawString(lower, x + LEAF_STR2_X, y + STR2_Y);
        } else if (node.isNYT()) {
            g.drawRect(x, y, NWIDTH, NHEIGHT);
            g.drawString("NYT", x + STR1_X, y + STR1_Y);

        } else {
            String lower = "" + node.getWeight();
            g.drawOval(x, y, NWIDTH, NHEIGHT);
            g.drawString(number, x + STR1_X, y + STR1_Y);
            g.drawString(lower, x + STR2_X, y + STR2_Y);

            Node leftChild = node.getLeft();
            Node rightChild = node.getRight();
            if (leftChild != null) {
                int x1 = leftChild.getX();
                int y1 = leftChild.getY();
                g.drawLine(x + OUT_L_X, y + OUT_Y, x1 + IN_X, y1);
            }

            if (rightChild != null) {
                int x2 = rightChild.getX();
                int y2 = rightChild.getY();
                g.drawLine(x + OUT_R_X, y + OUT_Y, x2 + IN_X, y2);
            }

        }
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree != null) paintTree(g, tree.getRoot());
    }
}