package com.example.delusion.ebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.delusion.ebook.MainActivity.cart;

public class CartView extends AppCompatActivity {

    private ListView cartList;
    public static TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cart");
        setContentView(R.layout.activity_cart_view);

        cartList = (ListView) findViewById(R.id.cartList);
        cartList.setAdapter(cart);

        totalPrice = (TextView) findViewById(R.id.total_price);
        setTotalPriceText();

    }

    public static void setTotalPriceText(){
        totalPrice.setText(Double.toString(cart.getTotalPrice()));
    }
}
