package com.example.delusion.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.delusion.ebook.MainActivity.cart;
import static com.example.delusion.ebook.MainActivity.user;

public class CartView extends AppCompatActivity {

    private ListView cartList;
    private Button checkOutBtn;
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

        checkOutBtn = (Button) findViewById(R.id.checkout_btn);
        setOnClickCheckOutBtn();

    }

    public static void setTotalPriceText(){
        totalPrice.setText(Double.toString(cart.getTotalPrice()));
    }

    private void setOnClickCheckOutBtn(){
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getFund() >= cart.getTotalPrice() && cart.getCount() > 0){
                    user.buyEbook(cart);
                    cart.checkOut();
                    setTotalPriceText();
                    Toast.makeText(CartView.this, "Bought complete you can check your book list.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CartView.this, UserView.class);
                    startActivity(intent);
                } else if (cart.getCount() == 0) {
                    Toast.makeText(CartView.this, "Not have any book in cart, You should add book to cart first.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartView.this, "You haven't enough money. Please add money or remove some book in your cart.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
