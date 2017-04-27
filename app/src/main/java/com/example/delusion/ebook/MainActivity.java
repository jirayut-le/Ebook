package com.example.delusion.ebook;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    
//    private ArrayList<Book> books;
    private ArrayAdapter<Book> bookAdapter;
    private ListView listView;
    private EditText searchText;
    BookListPresenter presenter;
    BookRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListView();
    }

    private void initListView() {
        repository = RemoteBookRepository.getInstance();


        bookAdapter = createAdapter(new ArrayList<Book>());
        listView = (ListView) findViewById(R.id.listview_book_list);
        searchText = (EditText) findViewById(R.id.search_bar);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")) {

                } else {
                    searchItems(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setAdapter(bookAdapter);

        presenter = new BookListPresenter(repository, this);
        presenter.initialize();

    }

    private void searchItems(String s) {
        Log.d("None", "search" + s);
        ArrayList<Book> filterBook = new ArrayList<Book>();
        for(Book b : repository.getAllBooks()){
            if(b.getTitle().contains(s)) {
                filterBook.add(b);
                Log.d("search", b.getTitle());
            }
        }
        setBookList(filterBook);
    }

    @Override
    public void setBookList(ArrayList<Book> books) {
        Log.d("None", "setBookList");
        bookAdapter = createAdapter(books);
        listView.setAdapter(bookAdapter);

    }

    private ArrayAdapter<Book> createAdapter(ArrayList<Book> books){
        return new ArrayAdapter<Book>(this,
                android.R.layout.simple_list_item_1,
                books);
    }


}
