package com.example.basicshoppingapp;

public enum Market {
    A101 (1),
    BIM (2),
    SOK (3),
    YEMEKSEPETI_BANABI (4),
    GETIR (5),
    MIGROS (6);

    private int marketSeq;

    Market(int marketSeq){
        this.marketSeq = marketSeq;
    }

    public int getMarket(){
        return marketSeq;
    }
}
