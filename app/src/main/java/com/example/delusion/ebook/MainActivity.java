package com.example.delusion.ebook;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class MainActivity extends AppCompatActivity implements BookListView{
    
    private ArrayList<Book> books;
    private ArrayAdapter<Book> bookAdapter;
    private ListView listView;
    BookListPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListView();
    }

    private void initListView() {
        BookRepository repository = RemoteBookRepository.getInstance();


        bookAdapter = createAdapter(new ArrayList<Book>());
        listView = (ListView) findViewById(R.id.listview_book_list);
        listView.setAdapter(bookAdapter);

        presenter = new BookListPresenter(repository, this);
        presenter.initialize();

    }


    public void refreshBooks(View view) {
        
    }

    @Override
    public void setBookList(ArrayList<Book> books) {
        bookAdapter = createAdapter(books);
        listView.setAdapter(bookAdapter);

    }

    private ArrayAdapter<Book> createAdapter(ArrayList<Book> books){
        return new ArrayAdapter<Book>(this,
                android.R.layout.simple_list_item_1,
                books);
    }


}
