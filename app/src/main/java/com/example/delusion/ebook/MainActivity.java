package com.example.delusion.ebook;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        gridView.setOnItemClickListener(onItemClick);

    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.activity_book_detail_dialog, null);

            TextView bookName = (TextView) mView.findViewById(R.id.bookNameDetail);
            bookName.setText(repository.getAllBooks().get(i).getTitle());

            TextView pubYear = (TextView) mView.findViewById(R.id.bookPubDetail);
            pubYear.setText("Publish : " + repository.getAllBooks().get(i).getPubYear());

            TextView price = (TextView) mView.findViewById(R.id.price_detail);
            price.setText(repository.getAllBooks().get(i).getPrize() + " $");

            ImageView imageView = (ImageView) mView.findViewById(R.id.img_detail);
            Picasso.with(MainActivity.this).load(repository.getAllBooks().get(i).getImg_url()).into(imageView);

            mBuilder.setView(mView);
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        repository.searchBooks(newText, "");
        return true;
    }

    @Override
    public void setBookList(ArrayList<Book> books) {
        bookAdapter = new BookAdapter(MainActivity.this, books);
        gridView.setAdapter(bookAdapter);
    }
}
