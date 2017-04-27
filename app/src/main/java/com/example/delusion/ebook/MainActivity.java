package com.example.delusion.ebook;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class MainActivity extends AppCompatActivity {
    
    private ArrayList<Book> books;
    private ArrayAdapter<Book> bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListView();
        loadBooks();
    }

    private void initListView() {
        books = new ArrayList<>();
        bookAdapter = new ArrayAdapter<Book>(this,
                android.R.layout.simple_list_item_1,
                books);
        ListView listView = (ListView) findViewById(R.id.listview_book_list);
        listView.setAdapter(bookAdapter);

    }

    private void loadBooks() {
        Log.d("none","LOADBOOKS1");
        BookFetcherTask task = new BookFetcherTask();
        Log.d("none","LOADBOOKS2");
        task.execute();
        Log.d("none","LOADBOOKS3");
    }

    public void refreshBooks(View view) {
        loadBooks();
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
            Log.d("none","Here: " + results.size());
            if(results != null) {
                bookAdapter.clear();
                for(Book b : results) {
                    bookAdapter.add(b);
                }
            }
        }
    }


}
