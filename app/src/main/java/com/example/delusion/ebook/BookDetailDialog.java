package com.example.delusion.ebook;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import static com.example.delusion.ebook.MainActivity.cart;

public class BookDetailDialog extends AlertDialog.Builder{

    private View mView;
    private TextView bookName, priceText, pubYear;
    private ImageView imageView;
    private Button addCart;

    private Book book;

    public BookDetailDialog(Context context, View mView) {
        super(context);
        this.mView = mView;
        initView();
    }

    private void initView(){
        bookName = (TextView) mView.findViewById(R.id.bookNameDetail);
        priceText = (TextView) mView.findViewById(R.id.price_detail);
        pubYear = (TextView) mView.findViewById(R.id.bookPubDetail);
        imageView = (ImageView) mView.findViewById(R.id.img_detail);
        addCart = (Button) mView.findViewById(R.id.add_cart);
        setButtonClickListener();
    }

    private void setButtonClickListener() {
        addCart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addToCart(book);
            }
        });
    }

    public void setDetail(Book book){
        this.book = book;
        setBookName(book.getTitle());
        setPrice(book.getPrice());
        setPub(book.getPubYear());
        setImg(book.getImg_url());
    }

    private void setBookName(String name){
        bookName.setText(name);
    }

    private void setPrice(double price){
        priceText.setText(price + " $");
    }

    private void setPub(String pub){
        pubYear.setText("Publish : " + pub);
    }

    private void setImg(String url){
        Picasso.with(super.getContext()).load(url).into(imageView);
    }
}
