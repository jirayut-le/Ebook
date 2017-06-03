package com.example.delusion.ebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 5/20/2017 AD.
 */

public class User extends BaseAdapter implements BookGridView{
    private double fund;
    private ArrayList<Book> ownEbook;
    private ArrayList<Book> lastBought;
    private ArrayList<ArrayList<Book>> history;

    private Context context;
    private LayoutInflater inflater;

    private ImageView imgView;
    private TextView bookName;

    public User(Context context){
        this.fund = 100;
        this.context = context;
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

    @Override
    public int getCount() {
        return ownEbook.size();
    }

    @Override
    public Book getItem(int i) {
        return ownEbook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView = view;
        if(view == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.book_layout, null);
        }

        imgView = (ImageView) gridView.findViewById(R.id.img_view);
        bookName = (TextView) gridView.findViewById(R.id.bookName);
        setBookName(i);
        setBookImg(i);

        return gridView;
    }

    @Override
    public void setBookImg(int pos) {
        Picasso.with(context).load(ownEbook.get(pos).getImg_url()).into(this.imgView);
    }

    @Override
    public void setBookName(int pos) {
        bookName.setText(ownEbook.get(pos).getTitle());
    }
}
