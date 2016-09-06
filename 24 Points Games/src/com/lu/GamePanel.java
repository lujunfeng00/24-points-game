package com.lu;

import org.apache.commons.jexl3.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.Date;
import java.util.Random;

/**
 * Created by lu on 2016/5/29.
 */
public class GamePanel extends JPanel implements ActionListener {

    private static final double PER_X = ScreenSize.percentWidth();
    private static final double PER_Y = ScreenSize.percentHeight();
    private static final int SCORE_X = 700;
    private static final int SCORE_Y = 50;
    private static final int SCORE_WIDTH = 200;
    private static final int SCORE_HEIGHT = 100;
    private static final int CARDS_START_X = 100;
    private static final int CARDS_START_Y = 600;
    private static final int CARDS_WIDTH = Card.WIDTH;
    private static final int CARDS_HEIGHT = Card.HEIGHT;
    private static final int CARDS_SPACE = 40;
    private static final int LINES_START_X = 150;
    private static final int LINES_START_Y = 450;
    private static final int LINES_LENGTH = 100;
    private static final int LINES_SPACE = 20;
    private static final int GRID_WIDTH = LINES_LENGTH;
    private static final int GRID_HEIGHT = 40;
    private static final String CARDS_BACKGROUND = "image/background.jpg";
    private static final String CLOSE_IMAGE = "image/close.png";
    private static final int CLOSE_SIZE = 50;
    private static final int BUTTONS_WIDTH = CARDS_WIDTH;
    private static final int BUTTONS_HEIGHT = 60;
    private static final int BUTTONS_START_X = 815;
    private static final int BUTTONS_START_Y = CARDS_START_Y;
    private static final int BUTTONS_SPACE_X = 40;
    private static final int BUTTONS_SPACE_Y = 30;

    private Font font = new Font("Lu", Font.BOLD, 30);
    private Card[] cards;
    private JLabel score;
    private JButton close;
    private JLabel cardBackground;
    private JButton cardButtonOne, cardButtonTwo, cardButtonThree, cardButtonFour;
    private JLabel[] grids;

    private String[] content = {"+", "-", "*", "/", "(", ")", "delete", "next"};
    private JButton[] buttons;
    private JButton submitButton;

    GamePanel() {
        this.setSize(ScreenSize.getWidth(), ScreenSize.getHeight());
        this.setBackground(new Color(0xCCFF66));
        this.setLayout(null);

        initCards();
        initView(this);
        initGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect((int) (CARDS_START_X * PER_X), (int) (CARDS_START_Y * PER_Y),
                (int) ((CARDS_WIDTH + 1) * PER_X), (int) ((CARDS_HEIGHT + 1) * PER_Y));

        g.setFont(font);
        g.drawString("Lu", (int) (100 * PER_X), (int) (100 * PER_Y));

        for (int i = 0; i < grids.length; i++) {
            int startX = (int) ((LINES_START_X + (LINES_LENGTH + LINES_SPACE) * i) * PER_X);
            int startY = (int) (LINES_START_Y * PER_Y);
            g.drawLine(startX, startY, (int) (startX + LINES_LENGTH * PER_X), startY);
        }
    }

    private void initCards() {
        cards = new Card[52];
        for (int i = 0; i < cards.length; i++) {
            String id = "cards_" + (i / 13 + 1) + "_" + (i % 13 + 1);
            String imagePath = "image/" + id + ".jpg";
            cards[i] = new Card(id, (i % 13) + 1, imagePath);
        }
    }

    private void initView(Container container) {
        score = new JLabel("分数：0");
        score.setFont(font);
        score.setBounds((int) (SCORE_X * PER_X), (int) (SCORE_Y * PER_Y), (int) (SCORE_WIDTH * PER_X), (int) (SCORE_HEIGHT * PER_Y));
        score.setHorizontalAlignment(JLabel.CENTER);
        container.add(score);

        close = new JButton();
        int closeSize = (int) (CLOSE_SIZE * PER_X);
        close.setBounds(ScreenSize.getWidth() - closeSize - 2, 2, closeSize, closeSize);
        close.setIcon(new ImageIcon(
                new ImageIcon(CLOSE_IMAGE).getImage().getScaledInstance(closeSize, closeSize, Image.SCALE_DEFAULT)));
        close.setBorderPainted(false);
        close.setContentAreaFilled(false);
        close.setActionCommand("close");
        close.addActionListener(this);
        container.add(close);

        cardBackground = new JLabel();
        cardBackground.setBounds(
                (int) (CARDS_START_X * PER_X),
                (int) (CARDS_START_Y * PER_Y),
                (int) (CARDS_WIDTH * PER_X),
                (int) (CARDS_HEIGHT * PER_Y));
        cardBackground.setIcon(new ImageIcon(
                imageAdaptation(new ImageIcon(CARDS_BACKGROUND).getImage(), container)));
        container.add(cardBackground);

        cardButtonOne = new JButton();
        cardButtonOne.setBounds(
                (int) ((CARDS_START_X + CARDS_WIDTH + CARDS_SPACE) * PER_X),
                (int) (CARDS_START_Y * PER_Y),
                (int) (CARDS_WIDTH * PER_X),
                (int) (CARDS_HEIGHT * PER_Y));
        cardButtonOne.setActionCommand("one");
        cardButtonOne.addActionListener(this);
        container.add(cardButtonOne);

        cardButtonTwo = new JButton();
        cardButtonTwo.setBounds(
                cardButtonOne.getX() + (int) ((CARDS_WIDTH + CARDS_SPACE) * PER_X),
                (int) (CARDS_START_Y * PER_Y),
                (int) (CARDS_WIDTH * PER_X),
                (int) (CARDS_HEIGHT * PER_Y));
        cardButtonTwo.setActionCommand("two");
        cardButtonTwo.addActionListener(this);
        container.add(cardButtonTwo);

        cardButtonThree = new JButton();
        cardButtonThree.setBounds(
                cardButtonTwo.getX() + (int) ((CARDS_WIDTH + CARDS_SPACE) * PER_X),
                (int) (CARDS_START_Y * PER_Y),
                (int) (CARDS_WIDTH * PER_X),
                (int) (CARDS_HEIGHT * PER_Y));
        cardButtonThree.setActionCommand("three");
        cardButtonThree.addActionListener(this);
        container.add(cardButtonThree);

        cardButtonFour = new JButton();
        cardButtonFour.setBounds(
                cardButtonThree.getX() + (int) ((CARDS_WIDTH + CARDS_SPACE) * PER_X),
                (int) (CARDS_START_Y * PER_Y),
                (int) (CARDS_WIDTH * PER_X),
                (int) (CARDS_HEIGHT * PER_Y));
        cardButtonFour.setActionCommand("four");
        cardButtonFour.addActionListener(this);
        container.add(cardButtonFour);

        grids = new JLabel[11];
        for (int i = 0; i < grids.length; i++) {
            grids[i] = new JLabel("空");
            int x = (int) ((LINES_START_X + (LINES_LENGTH + LINES_SPACE) * i) * PER_X);
            int y = (int) (LINES_START_Y * PER_Y - GRID_HEIGHT);
            grids[i].setBounds(x, y, (int) (GRID_WIDTH * PER_X), GRID_HEIGHT);
            grids[i].setFont(font);
            grids[i].setHorizontalAlignment(JLabel.CENTER);
            container.add(grids[i]);
        }

        buttons = new JButton[8];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(content[i]);
            buttons[i].setFont(new Font("Lu2", Font.BOLD, (int) (20 * PER_X)));
            buttons[i].setBounds(
                    (int) ((BUTTONS_START_X + (BUTTONS_WIDTH + BUTTONS_SPACE_X) * (i / 2)) * PER_X),
                    (int) ((BUTTONS_START_Y + (BUTTONS_HEIGHT + BUTTONS_SPACE_Y) * (i % 2)) * PER_Y),
                    (int) (BUTTONS_WIDTH * PER_X),
                    (int) (BUTTONS_HEIGHT * PER_Y));
            buttons[i].setActionCommand(content[i]);
            buttons[i].addActionListener(this);
            container.add(buttons[i]);
        }

        submitButton = new JButton("submit");
        submitButton.setFont(new Font("Lu3", Font.BOLD, (int) (20 * PER_X)));
        submitButton.setBounds(
                buttons[buttons.length - 1].getX() + (int) ((BUTTONS_WIDTH + BUTTONS_SPACE_X) * PER_X),
                (int) (BUTTONS_START_Y * PER_Y),
                (int) (CARDS_WIDTH * PER_X),
                (int) ((CARDS_HEIGHT) * PER_Y));
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        container.add(submitButton);

        cardButtonOne.setEnabled(true);
        cardButtonTwo.setEnabled(true);
        cardButtonThree.setEnabled(true);
        cardButtonFour.setEnabled(true);
    }

    private int cardsToShow[];
    private int isBeUsed[];
    private String[] texts;
    private int position;
    private int progress = 0;

    private void initGame() {
        isBeUsed = new int[52];
        for (int i = 0; i < isBeUsed.length; i++) {
            isBeUsed[i] = 0;
        }
        texts = new String[grids.length];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = "";
            grids[i].setText(texts[i]);
        }
        position = 0;

        cardsToShow = new int[4];
        cardsToShow = randomFourNumbers();

        progress++;
        cardButtonSetIcon(cardButtonOne, cardsToShow[0], this);
        cardButtonSetIcon(cardButtonTwo, cardsToShow[1], this);
        cardButtonSetIcon(cardButtonThree, cardsToShow[2], this);
        cardButtonSetIcon(cardButtonFour, cardsToShow[3], this);
    }

    private int[] randomFourNumbers() {
        int[] temps = new int[4];
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < temps.length; i++) {
            int temp;
            do {
                temp = random.nextInt(isBeUsed.length);
            } while (isBeUsed[temp] == 1);
            isBeUsed[temp] = 1;
            temps[i] = temp;
        }
        return temps;
    }


    private void cardButtonSetIcon(JButton button, int postion, ImageObserver observer) {
        button.setIcon(new ImageIcon(
                imageAdaptation(new ImageIcon(cards[postion].getPath()).getImage(), observer)));
    }

    private Image imageAdaptation(Image sourceImage, ImageObserver observer) {
        int newWidth = (int) (sourceImage.getWidth(observer) * ScreenSize.percentWidth());
        int newHeight = (int) (sourceImage.getHeight(observer) * ScreenSize.percentHeight());
        return sourceImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
    }

    private boolean needNumber = true;
    private int[] order = {0, 0, 0, 0};
    private int orderPosition = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("one".equals(command) && needNumber) {
            if (position < texts.length) {
                texts[position] = "" + cards[cardsToShow[0]].getValue();
                grids[position].setText(texts[position]);
                position++;
                needNumber = false;
                order[orderPosition++] = 1;
                cardButtonOne.setEnabled(false);
            }
        } else if ("two".equals(command) && needNumber) {
            if (position < texts.length) {
                texts[position] = "" + cards[cardsToShow[1]].getValue();
                grids[position].setText(texts[position]);
                position++;
                needNumber = false;
                order[orderPosition++] = 2;
                cardButtonTwo.setEnabled(false);
            }
        } else if ("three".equals(command) && needNumber) {
            if (position < texts.length) {
                texts[position] = "" + cards[cardsToShow[2]].getValue();
                grids[position].setText(texts[position]);
                position++;
                needNumber = false;
                order[orderPosition++] = 3;
                cardButtonThree.setEnabled(false);
            }
        } else if ("four".equals(command) && needNumber) {
            if (position < texts.length) {
                texts[position] = "" + cards[cardsToShow[3]].getValue();
                grids[position].setText(texts[position]);
                position++;
                needNumber = false;
                order[orderPosition++] = 4;
                cardButtonFour.setEnabled(false);
            }
        } else if ("+-*/()".contains(command)) {
            if (position < texts.length) {
                texts[position] = command;
                grids[position].setText(texts[position]);
                position++;
                needNumber = true;
            }
        } else if ("close".equals(command)) {
            System.exit(0);
        } else if ("delete".equals(command)) {
            if (position > 0) {
                if ("+-*/()".contains(texts[position - 1])) {
                    texts[--position] = "";
                    grids[position].setText(texts[position]);
                } else {
                    int num = order[--orderPosition];
                    needNumber = true;
                    if (num == 1) {
                        texts[--position] = "";
                        grids[position].setText(texts[position]);
                        cardButtonOne.setEnabled(true);
                    } else if (num == 2) {
                        texts[--position] = "";
                        grids[position].setText(texts[position]);
                        cardButtonTwo.setEnabled(true);
                    } else if (num == 3) {
                        texts[--position] = "";
                        grids[position].setText(texts[position]);
                        cardButtonThree.setEnabled(true);
                    } else if (num == 4) {
                        texts[--position] = "";
                        grids[position].setText(texts[position]);
                        cardButtonFour.setEnabled(true);
                    }
                }
            }
        } else if ("next".equals(command)) {
            position = 0;
            for (int i = 0; i < texts.length; i++) {
                texts[i] = "";
                grids[i].setText(texts[i]);
            }
            needNumber = true;
            for (int i = 0; i < order.length; i++) {
                order[i] = 0;
            }

            if (progress < 13) {
                cardsToShow = randomFourNumbers();

                progress++;
                orderPosition = 0;
                cardButtonSetIcon(cardButtonOne, cardsToShow[0], this);
                cardButtonSetIcon(cardButtonTwo, cardsToShow[1], this);
                cardButtonSetIcon(cardButtonThree, cardsToShow[2], this);
                cardButtonSetIcon(cardButtonFour, cardsToShow[3], this);
                cardButtonOne.setEnabled(true);
                cardButtonTwo.setEnabled(true);
                cardButtonThree.setEnabled(true);
                cardButtonFour.setEnabled(true);
            } else {
                String aa = score.getText();
                String bb[] = aa.split("：");
                JOptionPane.showMessageDialog(null, "Game Over!!!\n你的得分为：" + bb[1], "24点", JOptionPane.PLAIN_MESSAGE);
                close.doClick();
            }
        } else if ("submit".equals(command)) {
            String exp = "";
            for (String text : texts) {
                exp += text;
            }
            try {
                JexlEngine jexlEngine = new JexlBuilder().create();
                JexlExpression expression = jexlEngine.createExpression(exp);
                JexlContext jexlContext = new MapContext();
                Object result = expression.evaluate(jexlContext);

                if ("24".equals(result.toString()) && !cardButtonOne.isEnabled() && !cardButtonTwo.isEnabled()
                        && !cardButtonThree.isEnabled() && !cardButtonFour.isEnabled()) {
                    String aa = score.getText();
                    String bb[] = aa.split("：");
                    score.setText(bb[0] + "：" + (Integer.valueOf(bb[1]) + 100));
                    buttons[buttons.length - 1].doClick();
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "输入的表达式有错！！！", "24点", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
