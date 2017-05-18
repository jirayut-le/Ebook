package com.example.delusion.ebook;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Exchanger;

public class MainActivity extends AppCompatActivity implements BookListView{
    
//    private ArrayList<Book> books;
    private ArrayAdapter<Book> bookAdapter;
    private ListView listView;
    private EditText searchText;
    private BookListPresenter presenter;
    private BookRepository repository;
    private Spinner dropdown;
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

        dropdown = (Spinner)findViewById(R.id.sort_method);
        String[] items = new String[]{"Sort By...","Titles", "Publication years"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        searchText = (EditText) findViewById(R.id.search_bar);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")) {

                } else {
                    Log.d("dropdown",dropdown.getSelectedItem().toString() );
                    searchItems(charSequence.toString(), dropdown.getSelectedItem().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setBookList( repository.filterBook( dropdown.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listView.setAdapter(bookAdapter);

        repository.filterBook(dropdown.getSelectedItem().toString());
        presenter = new BookListPresenter(repository, this);
        presenter.initialize();

    }

    private void searchItems(String s, String sortBy) {
        Log.d("None", "search" + s);

        setBookList((ArrayList<Book>) repository.searchBooks(s,sortBy));
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
