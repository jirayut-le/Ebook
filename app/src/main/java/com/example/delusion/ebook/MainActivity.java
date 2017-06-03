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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, BookListView{

    private RemoteBookRepository repository;
    private MenuItem searchItem, balanceItem, cartItem;

    private BookPresenter bookPresenter;
    private BookAdapter bookAdapter;

    private GridView gridView;

    private AlertDialog.Builder mBuilder;
    private View mView;
    private BookDetailDialog bookDetailDialog;
    public static AlertDialog alertDialog;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    public static User user;
    public static Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User(MainActivity.this);
        cart = new Cart(MainActivity.this);
        initListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);

        balanceItem = menu.findItem(R.id.action_balance);
        searchItem = menu.findItem(R.id.action_search);
        cartItem = menu.findItem(R.id.action_cart);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        balanceItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, UserView.class);
                startActivity(intent);
                return true;
            }
        });

        cartItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, CartView.class);
                startActivity(intent);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void initListView() {

        repository = RemoteBookRepository.getInstance();

        gridView = (GridView) findViewById(R.id.grid_view);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        bookPresenter = new BookPresenter(repository, this);
        bookPresenter.initialize();

        gridView.setOnItemClickListener(onItemClick);

    }

    public void sortClick(View v){
        radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        repository.sort(radioButton.getText().toString());
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            mBuilder = new AlertDialog.Builder(MainActivity.this);
            mView = getLayoutInflater().inflate(R.layout.activity_book_detail_dialog, null);
            bookDetailDialog = new BookDetailDialog(MainActivity.this, mView);

            bookDetailDialog.setDetail(repository.getAllBooks().get(i));

            mBuilder.setView(mView);
            alertDialog = mBuilder.create();
            alertDialog.show();
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        repository.searchBooks(query, radioButton.getText().toString());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        repository.searchBooks(newText, radioButton.getText().toString());
        return true;
    }

    @Override
    public void setBookList(ArrayList<Book> books) {
        bookAdapter = new BookAdapter(MainActivity.this, books);
        gridView.setAdapter(bookAdapter);
    }

}
