package com.example.delusion.ebook;

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
    private MenuItem searchItem;

    private BookPresenter bookPresenter;
    private BookAdapter bookAdapter;

    private GridView gridView;

    private AlertDialog.Builder mBuilder;
    private View mView;
    private BookDetailDialog bookDetailDialog;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

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
        repository.searchBooks(newText, radioButton.getText().toString());
        return true;
    }

    @Override
    public void setBookList(ArrayList<Book> books) {
        bookAdapter = new BookAdapter(MainActivity.this, books);
        gridView.setAdapter(bookAdapter);
    }
}
