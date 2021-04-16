import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class RegexEdit extends JFrame {

    private final JTextField tf;
    private final JButton b;
    private final JTextArea ta;

    private final Highlighter hl;
    private final Highlighter.HighlightPainter painter;

    public RegexEdit() {


        tf = new JTextField();
        tf.setPreferredSize(new Dimension(500, 50));
        tf.setEditable(true);
        tf.setFont(new Font(null, Font.BOLD, 18));

        b = new JButton("search");

        ta = new JTextArea();
        ta.setEditable(true);
        ta.setSize(500, 450);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font(null, Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane(ta);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        hl = ta.getHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);

        this.add(tf, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(b, BorderLayout.SOUTH);

        this.setTitle("Reg Editor");
        this.setSize(500, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        runEngine();
    }

    private void runEngine() {

        b.addActionListener(e -> {
            String reg = tf.getText();
            String text = ta.getText();
            RegEng re = new RegEng(reg);
            highlight(re.analyze(text));
        });

        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                hl.removeAllHighlights();
            }
        });

        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                hl.removeAllHighlights();
                if (e.getKeyChar() == '\n') {
                    String reg = tf.getText();
                    String text = ta.getText();
                    RegEng re = new RegEng(reg);
                    highlight(re.analyze(text));
                }
            }
        });
    }

    private void highlight(Map<Integer, Integer> matches) {

        hl.removeAllHighlights();

        try {
            for (Map.Entry<Integer, Integer> match : matches.entrySet()) {
                int p = match.getKey();
                int e = match.getKey() + match.getValue();

                hl.addHighlight(p, e, painter);

            }
        } catch (BadLocationException ignored) {}
    }

    public static void main(String[] args) {
        new RegexEdit();
    }
}
