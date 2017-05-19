package com.example.delusion.ebook;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by macbook on 5/19/2017 AD.
 */

public class BookPresenter implements Observer{

    private RemoteBookRepository remoteBookRepository;
    private BookListView view;

    private ArrayList<Book> books;

    public BookPresenter(RemoteBookRepository remoteBookRepository, BookListView view) {
        this.remoteBookRepository = remoteBookRepository;
        this.view = view;
    }

    public void initialize() {
        remoteBookRepository.addObserver(this);
        remoteBookRepository.fetchAllBooks();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable == remoteBookRepository) {
            books = new ArrayList<>(remoteBookRepository.getAllBooks());
            view.setBookList(books);
        }
    }
}
