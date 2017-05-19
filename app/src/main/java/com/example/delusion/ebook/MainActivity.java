package com.example.delusion.ebook;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, BookListView{

    private RemoteBookRepository repository;
    private MenuItem searchItem;

    private BookPresenter bookPresenter;
    private BookAdapter bookAdapter;

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    private void initListView() {
        repository = RemoteBookRepository.getInstance();

        gridView = (GridView) findViewById(R.id.grid_view);

        bookPresenter = new BookPresenter(repository, this);
        bookPresenter.initialize();

//
//        BookAdapter bookAdapter = new BookAdapter(MainActivity.this, repository.getAllBooks());
//        gridView.setAdapter(bookAdapter);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Search : " , newText);
        repository.searchBooks(newText, "");
        return true;
    }

    @Override
    public void setBookList(ArrayList<Book> books) {
        bookAdapter = new BookAdapter(MainActivity.this, books);
        gridView.setAdapter(bookAdapter);
        Log.d("setBookList" , "Set after load");
    }
}
