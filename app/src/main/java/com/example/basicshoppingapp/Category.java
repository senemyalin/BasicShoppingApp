package com.example.basicshoppingapp;

public enum Category {
    A101 (1),
    BIM (2),
    SOK (3),
    YEMEKSEPETI_BANABI (4),
    GETIR (5),
    MIGROS (6);

    private int categorySeq;

    Category(int categorySeq){
        this.categorySeq = categorySeq;
    }

    public int getCategory(){
        return categorySeq;
    }
}
