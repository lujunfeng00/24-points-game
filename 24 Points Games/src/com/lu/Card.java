package com.lu;

/**
 * Created by lu on 2016/5/29.
 */
public class Card {

    public static final int WIDTH = 105, HEIGHT = 150;

    private String id;
    private int value;
    private String path;

    /**
     * 扑克牌
     *
     * @param id    扑克牌的编号
     * @param value 扑克牌的值
     * @param path  扑克牌的图片
     */
    public Card(String id, int value, String path) {
        this.id = id;
        this.value = value;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}