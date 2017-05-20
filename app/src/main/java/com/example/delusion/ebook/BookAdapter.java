package com.example.delusion.ebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 5/19/2017 AD.
 */

public class BookAdapter extends BaseAdapter {

    private ArrayList<Book> bookList;
    private Context context;
    private LayoutInflater inflater;

    private ImageView imgView;
    private TextView bookName;

    public BookAdapter(Context context, ArrayList<Book> bookList){
        this.context = context;
        this.bookList = bookList;
    }
    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookList.get(i);
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

    private void setBookImg(int pos){
        Picasso.with(context).load(bookList.get(pos).getImg_url()).into(this.imgView);
    }

    private void setBookName(int pos){
        bookName.setText(bookList.get(pos).getTitle());
    }
}
