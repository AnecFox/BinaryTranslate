package com.anec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindow extends JFrame {

    private final static String VERSION = "0.1.1";

    private final JTextArea textAreaValue = new JTextArea();
    private final JTextArea textAreaResult = new JTextArea();

    private final JScrollPane scrollPaneValue = new JScrollPane(textAreaValue);
    private final JScrollPane scrollPaneResult = new JScrollPane(textAreaResult);

    private final JRadioButton radioButtonTextToBinary = new JRadioButton("Текст в двоичный код");
    private final JRadioButton radioButtonBinaryToText = new JRadioButton("Двоичный код в текст");

    private final JButton buttonTranslate = new JButton("Перевести");
    private final JButton buttonAbout = new JButton("О программе");

    public MainWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("BinaryTranslate");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(512, 256);
        setMinimumSize(new Dimension(256, 128));
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getClassLoader()
                .getResource("com/anec/icon/icon.png")));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 16, 2));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        textAreaValue.setLineWrap(true);
        textAreaValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    buttonTranslate.doClick();
                }
            }
        });
        container.add(scrollPaneValue);

        textAreaResult.setEditable(false);
        textAreaResult.setLineWrap(true);
        container.add(scrollPaneResult);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonTextToBinary);
        group.add(radioButtonBinaryToText);

        radioButtonTextToBinary.setSelected(true);
        container.add(radioButtonTextToBinary);
        container.add(radioButtonBinaryToText);

        buttonTranslate.addActionListener(e -> {
            if (textAreaValue.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Текст не введён, переводить нечего",
                        this.getTitle(), JOptionPane.ERROR_MESSAGE);
                return;
            }

            String result;

            if (radioButtonTextToBinary.isSelected()) {
                result = BinaryTranslate.textToBinary(textAreaValue.getText());
            } else {
                Pattern pattern = Pattern.compile("[^01 ]");
                Matcher matcher = pattern.matcher(textAreaValue.getText());

                if (matcher.find()) {
                    JOptionPane.showMessageDialog(null,
                            "Это не двоичный код, в двоичном коде можно вводить только 0 и 1 :)",
                            this.getTitle(), JOptionPane.ERROR_MESSAGE);
                    return;
                }

                result = BinaryTranslate.binaryToText(textAreaValue.getText());
            }
            textAreaResult.setText(result);
        });
        container.add(buttonTranslate);

        buttonAbout.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Эта программа предназначена для перевода двоичного кода\n" +
                        "                                       в текст и наоборот.\n\n" +
                        "                                            Версия: " + VERSION + "\n\n" +
                        "                                           Created by Anec", "О программе",
                JOptionPane.PLAIN_MESSAGE)
        );
        container.add(buttonAbout);

        for (Component c : this.getComponents()) {
            SwingUtilities.updateComponentTreeUI(c);
        }
    }
}
