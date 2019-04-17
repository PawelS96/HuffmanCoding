
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

public class Huffman {

    JPanel panel1;
    private JTextArea input;
    private JButton button1;
    private JTextField entropy;
    private JTextField avg1;
    private JTextField entropyTextField, avgLengthTextField;
    private JPanel panel2;
    JTable table;
    private Tree tree;

    public Huffman() {

        Object[][] data = {
        };
        String[] columnNames = {
                "ID",
                "Symbol",
                "Count",
                "Code",
        };

        table.setModel(new DefaultTableModel(data, columnNames));

        button1.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                calcHuffman();
            }
        });
    }

    private void calcHuffman(){
        if (input.getText().isEmpty())
            return;

        tree = new Tree(input.getText());
        tree.code();
        avg1.setText(Float.toString(tree.getAvgLength()));
        entropy.setText(Double.toString(tree.getEntropy()));

        showTable();
        showTree(tree);
    }

    private void showTable() {

        List<Node> nodeList = tree.getSymbolNodes();
        nodeList.sort(Comparator.comparingInt(Node::getWeight).reversed());

        DefaultTableModel model = (DefaultTableModel) (table.getModel());

        model.setRowCount(0);
        model.addRow(new Object[]{"ID", "Symbol", "Count", "Code"});

        for (Node n : nodeList)
            model.addRow(new Object[]{n.getId(), n.getCharacter(), n.getWeight(), Node.getCode(n)});

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Huffman coding");
        JPanel panel = new Huffman().panel1;
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);

    }

    private void showTree(Tree tr) {

        JFrame frame = new JFrame("Tree");
        TreePanel panel = new TreePanel();
        JSlider slider = new JSlider();
        slider.setMaximum(tr.getText().length());
        slider.setMinimum(0);

        panel.add(slider);
        panel.drawTree(tr);
        frame.setContentPane(panel);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        frame.setSize(Tree.TREE_WIDTH, Tree.TREE_HEIGHT);
        frame.pack();
        frame.setVisible(true);

        slider.addChangeListener(e -> {
            int pos = slider.getValue();
            Tree newTree = new Tree(tr.getText().substring(0,pos));
            newTree.code();
            panel.drawTree(newTree);
        });
    }

}
