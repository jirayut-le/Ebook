package com.example.delusion.ebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by macbook on 4/27/2017 AD.
 */

public abstract class BookRepository extends Observable{
    public abstract void fetchAllBooks();
    public abstract ArrayList<Book> getAllBooks();
    public abstract ArrayList<Book> searchBooks(String s, String sortBy);
    public abstract ArrayList<Book> filterBook(String filterBy);

}
