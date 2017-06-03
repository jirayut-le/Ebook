package com.example.delusion.ebook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 6/2/2017 AD.
 */

public class Cart extends BaseAdapter{

    private ArrayList<Book> cartList;
    private double totalPrice;
    private Context context;

    private TextView bookName, bookPrice;
    private ImageView bookImg;
    private Button deleteBtn;

    public Cart(Context context){
        this.context = context;
        cartList = new ArrayList<>();
        totalPrice = 0;
    }

    public void addToCart(Book b){
        if (checkInCart(b)) {
            cartList.add(b);
            totalPrice += b.getPrice();
        }
        notifyDataSetChanged();
    }

    public void delete(Book b){
        totalPrice -= b.getPrice();
        cartList.remove(b);
        notifyDataSetChanged();
    }

    public void checkOut(){
        cartList.clear();
        totalPrice = 0;
        notifyDataSetChanged();
    }

    private boolean checkInCart(Book newBook){
        for(Book b : cartList){
            if(b.getTitle().equalsIgnoreCase(newBook.getTitle()))
                return false;
        }
        return true;
    }

    public ArrayList<Book> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Book> cartList) {
        this.cartList = cartList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Book getItem(int i) {
        return cartList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.book_cart_layout, null);
        bookName = (TextView) v.findViewById(R.id.book_name_cart);
        bookPrice = (TextView) v.findViewById(R.id.price_cart);
        bookImg = (ImageView) v.findViewById(R.id.img_cart);
        deleteBtn = (Button) v.findViewById(R.id.deleteBookBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete complete.", Toast.LENGTH_SHORT).show();
                delete(cartList.get(i));
                CartView.setTotalPriceText();
            }
        });
        setBookImg(i);
        setBookName(i);
        setBookPrice(i);
        return v;
    }

    private void setBookName(int i){
        bookName.setText(cartList.get(i).getTitle());
    }

    private void setBookPrice(int i){
        bookPrice.setText("Price : " + Double.toString(cartList.get(i).getPrice()));
    }

    private void setBookImg(int i){
        Picasso.with(context).load(cartList.get(i).getImg_url()).into(this.bookImg);
    }
}
