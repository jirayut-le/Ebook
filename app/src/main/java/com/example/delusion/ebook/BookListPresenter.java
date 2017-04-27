package com.example.delusion.ebook;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by macbook on 4/27/2017 AD.
 */

public class BookListPresenter implements Observer {

    private BookListView view;
    private BookRepository repository;

    ArrayList<Book> books;

    public BookListPresenter(BookRepository repository, BookListView view) {
        this.repository = repository;
        this.view = view;
    }

    public void initialize() {
        repository.addObserver(this);
        repository.fetchAllBooks();
    }

    @Override
    public void update(Observable obj, Object arg) {
        if(obj == repository) {
            books = new ArrayList<Book>(repository.getAllBooks());
            view.setBookList(books);
        }
    }

}
