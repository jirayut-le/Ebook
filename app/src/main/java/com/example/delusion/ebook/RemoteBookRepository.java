package com.example.delusion.ebook;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by macbook on 4/27/2017 AD.
 */

public class RemoteBookRepository extends BookRepository {

    private ArrayList<Book> books;

    private static RemoteBookRepository instance = null;

    public static RemoteBookRepository getInstance(){
        if(instance == null){
            instance = new RemoteBookRepository();
        }
        return instance;
    }

    private RemoteBookRepository(){
        books = new ArrayList<Book>();
    }


    @Override
    public void fetchAllBooks() {
        Log.d("none","LOADBOOKS1");
        BookFetcherTask task = new BookFetcherTask();
        Log.d("none","LOADBOOKS2");
        task.execute();
        Log.d("none","LOADBOOKS3");
    }

    @Override
    public ArrayList<Book> getAllBooks() {
        return books;
    }

    @Override
    public ArrayList<Book> searchBooks(String s, String sortBy) {
        ArrayList<Book> result = new ArrayList<Book>();
        for(Book b : books){

            if(b.getTitle().contains(s) || b.getPubYear().contains(s)) {
                result.add(b);
            }
            result = filterSearchBook(result, sortBy);
        }

        return result;
    }

    private ArrayList<Book> filterSearchBook(ArrayList<Book> books, String filterBy){
        if(filterBy.equalsIgnoreCase("Titles"))
            Collections.sort(books, new BookComparatorTitle());
        else if (filterBy.equalsIgnoreCase("Publication years"))
            Collections.sort(books, new BookComparatorPubyear());
        return books;
    }

    @Override
    public ArrayList<Book> filterBook(String filterBy){

        if(filterBy.equalsIgnoreCase("Titles"))
            Collections.sort(books, new BookComparatorTitle());
        else if (filterBy.equalsIgnoreCase("Publication years"))
            Collections.sort(books, new BookComparatorPubyear());

        return books;
    }


    public class BookFetcherTask extends AsyncTask<Void,Void,ArrayList<Book>> {

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            Log.d("none","DOINBACK1");
            String bookListJsonStr = loadBookJson();
            Log.d("none","DO in Background2:" + bookListJsonStr);
            if(bookListJsonStr == null) {
                return null;
            }

            ArrayList<Book> results = new ArrayList<>();
            try {
                JSONArray jsonArr = new JSONArray(bookListJsonStr);

                for(int i=0; i < jsonArr.length(); i++) {
                    JSONObject bookJson = jsonArr.getJSONObject(i);
                    results.add(new Book(bookJson.getString("title"),
                            bookJson.getInt("id"),
                            bookJson.getDouble("price"),
                            bookJson.getString("pub_year"),
                            bookJson.getString("img_url")
                    ));
                    Log.d("none", bookJson.getString("title"));
                }
            }catch(JSONException e) {
                return null;
            }
            return results;
        }

        private String loadBookJson() {
            String result = "";
            try {
                URL bookUrl = new URL("https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json");
                URLConnection bookConn = bookUrl.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        bookConn.getInputStream()));
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                return result;
            } catch(IOException e) {
                Log.d("none","Exception");
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Book> results) {
//            Log.d("none","Here: " + results.size());
//            if(results != null) {
//                bookAdapter.clear();
//                for(Book b : results) {
//                    bookAdapter.add(b);
//                }
//            }
            if(results != null){
                books.clear();
                books.addAll(results);
                setChanged();
                notifyObservers();
            }
        }
    }
}
