package com.example.findbook;

public class SearchBookItem extends MyBookItem{

    public String email;
    public String phone;
    public String location;

    public SearchBookItem(String email, String phone, String location,int bookid, String title, int quantity, String imagepath) {
        super ( bookid, title, quantity, imagepath );
        this.email = email;
        this.phone = phone;
        this.location = location;
    }
}
