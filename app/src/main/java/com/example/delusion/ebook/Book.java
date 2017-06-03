package com.example.delusion.ebook;

public class Book {
    private int id;
    private double price;
    private String title,img_url, pubYear;

    public Book(String title, int id, double price, String pubYear, String img_url){
        this.price = price;
        this.img_url = img_url;
        this.id = id;
        this.title = title;
        this.pubYear = pubYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title + "(" + price + ")";
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }
}
