package com.example.delusion.ebook;

import java.util.Comparator;

/**
 * Created by macbook on 5/18/2017 AD.
 */

public class BookComparatorPubyear implements Comparator<Book> {
    @Override
    public int compare(Book book, Book t1) {
        return book.getPubYear().compareTo(t1.getPubYear());
    }
}
