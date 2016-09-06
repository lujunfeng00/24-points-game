package com.lu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lu on 2016/5/29.
 */
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        ScreenSize.setWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
        ScreenSize.setHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
        this.setSize(ScreenSize.getWidth(), ScreenSize.getHeight());
        //设置无边框
        this.setUndecorated(true);
        // 设置图标
        this.setIconImage(new ImageIcon("image/icon.png").getImage());

        this.add(new GamePanel());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
    }
}
