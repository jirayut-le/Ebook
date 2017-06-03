package com.example.delusion.ebook;

import android.os.AsyncTask;

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
import java.util.Observable;

/**
 * Created by macbook on 4/27/2017 AD.
 */

public class RemoteBookRepository extends Observable{

    private ArrayList<Book> books;
    private ArrayList<Book> result;

    private static RemoteBookRepository instance = null;

    public static RemoteBookRepository getInstance(){
        if(instance == null){
            instance = new RemoteBookRepository();
        }
        return instance;
    }

    private RemoteBookRepository(){
        books = new ArrayList<>();
        result = new ArrayList<>();
    }

    public void fetchAllBooks() {
        BookFetcherTask task = new BookFetcherTask();
        task.execute();
    }

    public ArrayList<Book> getAllBooks() {
        return result;
    }

    public ArrayList<Book> searchBooks(String s, String sortBy) {
        result.clear();
        for(Book b : books){
            if(b.getTitle().toLowerCase().contains(s.toLowerCase())) {
                result.add(b);
            }
        }
        sort(sortBy);
        return result;
    }

    public void sort(String sortBy){
        if(sortBy.equalsIgnoreCase("titles"))
            sortByTitle();
        else
            sortByPub();
        setChanged();
        notifyObservers();
    }

    private void sortByTitle(){
        Collections.sort(result, new BookComparatorTitle());
    }

    private void sortByPub(){
        Collections.sort(result, new BookComparatorPubyear());
    }

    public class BookFetcherTask extends AsyncTask<Void,Void,ArrayList<Book>> {

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            String bookListJsonStr = loadBookJson();
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
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Book> results) {
            if(results != null){
                result.clear();
                result.addAll(results);
                books.clear();
                books.addAll(results);
                sortByTitle();
                setChanged();
                notifyObservers();
            }
        }
    }
}
