package com.example.delusion.ebook;

import java.util.ArrayList;

/**
 * Created by macbook on 5/20/2017 AD.
 */

public class User {
    private double fund;
    private ArrayList<Book> ownEbook;
    private ArrayList<Book> lastBought;

    public User(){
        this.fund = 100;
        this.ownEbook = new ArrayList<>();
        this.lastBought = new ArrayList<>();
    }

    public void addFund(String fund){
        this.fund += Integer.parseInt(fund);
    }

    public void buyEbook(ArrayList<Book> buyList){
        for(Book b : buyList){
            this.fund -= b.getPrice();
        }
        ownEbook.addAll(buyList);
        lastBought.addAll(buyList);
    }

    public void refund(){
        for(Book b : lastBought){
            fund += b.getPrice();
        }
        ownEbook.removeAll(lastBought);
        lastBought.clear();
    }


}
