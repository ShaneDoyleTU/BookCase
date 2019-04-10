package edu.temple.bookcase;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity implements BookListFragment.BookListListener {

    private BookListFragment fragmentA;
    private BookDetailsFragment fragmentB;
    private final String URL_TO_HIT = "https://kamorris.com/lab/audlib/booksearch.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Book> myBooks = new ArrayList<>();
        /*ArrayList<String> titles = new ArrayList<>();
        titles.add("A Tale of Two Cities");
        titles.add("War and Peace");
        titles.add("Lord of the Rings");
        titles.add("Gilgamesh");
        titles.add("The Odyssey");
        titles.add("IT");
        titles.add("The Prince");
        titles.add("Dune");
        titles.add("To Kill a Mockingbird");
        titles.add("Pride and Prejudice");*/
        fragmentA = new BookListFragment();
        fragmentB = new BookDetailsFragment();

        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            ViewPager viewPager = findViewById(R.id.pager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            new JSONTask().execute(URL_TO_HIT);
        }
        else if(getResources().getDisplayMetrics().widthPixels>=600){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment3, fragmentA)
                    .replace(R.id.fragment2, fragmentB)
                    .commit();
        }
        else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment3, fragmentA)
                    .replace(R.id.fragment2, fragmentB)
                    .commit();
        }
        //BookDetailsFragment fragment = BookDetailsFragment.newInstance(titles.get(1));
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }

    public class JSONTask extends AsyncTask<String,String,List<Book>> {

        @Override
        protected List<Book> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");

                List<Book> bookList = new ArrayList<>();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Book book = new Book();
                    book.setAuthor(finalObject.getString("author"));
                    
                    book.setBookTitle(finalObject.getString("title"));
                    book.setCoverURL(finalObject.getString("cover_url"));
                    book.setBookId(finalObject.getInt("book_id"));
                    book.setPublished(finalObject.getInt("published"));
                    bookList.add(book);



                }
                return bookList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection!=null){
                    connection.disconnect();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    public void titleSend(String title){
        fragmentB.displayBook(title);
    }
}
