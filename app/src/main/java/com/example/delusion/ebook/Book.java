package com.example.delusion.ebook;


public class Book {
    private int id;
    private double prize;
    private String title,img_url;

    public Book(double prize,String url,int id,String title) {
        this.prize = prize;
        this.img_url = url;
        this.id = id;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrize() {
        return prize;
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
        return title + "\nPrize : " + prize;
    }
}
