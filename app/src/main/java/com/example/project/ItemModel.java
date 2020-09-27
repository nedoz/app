package com.example.project;

public class ItemModel {

    private String itemName;
    private int itemImage;

    public ItemModel(String itemName) {
        this.itemName = itemName;
    }

    public ItemModel(String itemName, int itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemImage() {
        return itemImage;
    }
}
