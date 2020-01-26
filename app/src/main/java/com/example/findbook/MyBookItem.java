package com.example.findbook;

public class MyBookItem {
    public String imagepath;
    public String title;
    public int    quantity;
    public int    bookid;


    public MyBookItem(int bookid, String title, int quantity, String imagepath) {
        this.bookid    = bookid;
        this.imagepath = imagepath;
        this.title     = title;
        this.quantity  = quantity;
    }
}

