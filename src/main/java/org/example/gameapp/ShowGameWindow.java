package org.example.gameapp;

import org.example.Move;
import org.example.Winner;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ShowGameWindow {
    private static Move move = new Move();
    private static JTextField playerBoard;
    private static JTextField computerBoard;
    private static JButton skipButton;
    private static JButton moveButton;
    private static JButton surrenderButton;

    public static void show() {
        JFrame gameFrame = new JFrame("City Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(600, 400);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - gameFrame.getWidth()) / 2;
        int y = (screenSize.height - gameFrame.getHeight()) / 2;
        gameFrame.setLocation(x,y);

//        JLabel label = new JLabel();
//        label.setText("computer count: " + "");
        playerBoard = new JTextField();
        computerBoard = new JTextField();

        moveButton = new JButton("Move");
        skipButton = new JButton("Skip");
        surrenderButton = new JButton("Surrender");

        computerBoard.setEditable(false);

        Dimension textFieldSize = new Dimension(200, 30);
        computerBoard.setPreferredSize(textFieldSize);
        playerBoard.setPreferredSize(textFieldSize);

        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        playerBoard.setFont(inputFont);
        computerBoard.setFont(inputFont);

        ((AbstractDocument) playerBoard.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int currentLength = fb.getDocument().getLength();
                int insertLength = text.length();
                if (currentLength + insertLength <= 26) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        moveButton.addActionListener(e -> processPlayerMove());

        playerBoard.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        playerBoard.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPlayerMove();
            }
        });

        skipButton.addActionListener(e -> {
            computerBoard.setText("");
            String computerMove = move.skip(); // редагував код Віталія!!
            computerBoard.setText(computerBoard.getText() + computerMove + "\n"); // редагував код Віталія
        });


        surrenderButton.addActionListener(e -> {
            playerBoard.setText("");
            computerBoard.setText("");

            gameFrame.dispose();
            ShowInitialWindow.show();
        });

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(playerBoard);
        topPanel.add(computerBoard);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(moveButton);
        buttonPanel.add(skipButton);
        buttonPanel.add(surrenderButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.setBackground(Color.GRAY);

        gameFrame.getContentPane().add(mainPanel);
        gameFrame.setVisible(true);
    }

    private static void processPlayerMove() {

        String input = playerBoard.getText().trim();
        playerBoard.setText("");

        String str = new Winner().userHasGivenUp(input);
        computerBoard.setText(str);


        if (move.isUserMoveValid(input)) {
            move.playGame(input);

            playerBoard.setText(""); // Очистити поле вводу
            String computerMove = move.getComputerMove();
            computerBoard.setText(computerMove);
        } else {
            // Вивести повідомлення про помилку
            JOptionPane.showMessageDialog(null, "The entered city is invalid or does not comply with the rules. Please enter another city.", "Error", JOptionPane.ERROR_MESSAGE);
            playerBoard.setText(""); // Очистити поле вводу
        }







    }


}


