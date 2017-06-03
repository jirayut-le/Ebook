package com.example.delusion.ebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.delusion.ebook.MainActivity.cart;

public class CartView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cart");
        setContentView(R.layout.activity_cart_view);
    }
}
