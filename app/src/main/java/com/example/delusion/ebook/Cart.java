package com.example.delusion.ebook;

import java.util.ArrayList;

/**
 * Created by macbook on 6/2/2017 AD.
 */

public class Cart {

    private ArrayList<Book> cartList;
    private int totalPrice;

    public Cart(){
        cartList = new ArrayList<>();
        totalPrice = 0;
    }

    public void addToCart(Book b){
        cartList.add(b);
        totalPrice += b.getPrice();
    }

    public void clearCartList(){
        cartList.clear();
        totalPrice = 0;
    }

    public ArrayList<Book> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Book> cartList) {
        this.cartList = cartList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
