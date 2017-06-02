package com.example.delusion.ebook;

import java.util.ArrayList;

/**
 * Created by macbook on 5/20/2017 AD.
 */

public class User {
    private double fund;
    private ArrayList<Book> ownEbook;
    private ArrayList<Book> lastBought;
    private ArrayList<ArrayList<Book>> history;

    public User(){
        this.fund = 100;
        this.ownEbook = new ArrayList<>();
        this.lastBought = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public void addFund(String fund){
        this.fund += Integer.parseInt(fund);
    }


    public void buyEbook(Cart cart){
        for(Book b : cart.getCartList()){
            this.fund -= b.getPrice();
        }
        ownEbook.addAll(cart.getCartList());
        lastBought.clear();
        lastBought.addAll(cart.getCartList());
    }

    public void refund(){
        for(Book b : lastBought){
            fund += b.getPrice();
        }
        ownEbook.removeAll(lastBought);
        lastBought.clear();
    }

    public double getFund() {
        return fund;
    }

    public void setFund(double fund) {
        this.fund = fund;
    }

    public ArrayList<Book> getOwnEbook() {
        return ownEbook;
    }

    public void setOwnEbook(ArrayList<Book> ownEbook) {
        this.ownEbook = ownEbook;
    }

    public ArrayList<Book> getLastBought() {
        return lastBought;
    }

    public void setLastBought(ArrayList<Book> lastBought) {
        this.lastBought = lastBought;
    }

    public ArrayList<ArrayList<Book>> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<ArrayList<Book>> history) {
        this.history = history;
    }
}
