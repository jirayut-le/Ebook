package com.example.delusion.ebook;

import java.util.Comparator;

/**
 * Created by macbook on 5/18/2017 AD.
 */

public class BookComparatorTitle implements Comparator<Book> {
    @Override
    public int compare(Book book, Book t1) {
        return book.getTitle().compareToIgnoreCase(t1.getTitle());
    }
}
