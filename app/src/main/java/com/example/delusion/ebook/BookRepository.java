package com.example.delusion.ebook;

import java.util.List;
import java.util.Observable;

/**
 * Created by macbook on 4/27/2017 AD.
 */

public abstract class BookRepository extends Observable{
    public abstract void fetchAllBooks();
    public abstract List<Book> getAllBooks();
}
