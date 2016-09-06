package com.lu;

/**
 * Created by lu on 2016/5/29.
 */
public class ScreenSize {
    public static final int WIDTH_DEFAULT = 1600, HEIGHT_DEFAULT = 900;
    private static int width = WIDTH_DEFAULT, height = HEIGHT_DEFAULT;

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        ScreenSize.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        ScreenSize.height = height;
    }

    public static double percentWidth() {
        return (double) width / WIDTH_DEFAULT;
    }

    public static double percentHeight() {
        return (double) height / HEIGHT_DEFAULT;
    }
}
