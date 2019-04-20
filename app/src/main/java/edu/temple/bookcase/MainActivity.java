package edu.temple.bookcase;

import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import edu.temple.audiobookplayer.AudiobookService;

//import static android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.play;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListListener {

    private BookListFragment fragmentA;
    private BookDetailsFragment fragmentB;
    private final String URL_TO_HIT = "https://kamorris.com/lab/audlib/booksearch.php";
    protected Button searchButton;
    private boolean searched;

    ArrayList<Book> myBooks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditText search = (EditText) findViewById(R.id.editText);
        //ArrayList<Book> myBooks = new ArrayList<>();
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
        searchButton = (Button) findViewById(R.id.button);
        final EditText search = (EditText) findViewById(R.id.editText);
        search.requestFocus();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText search = (EditText) findViewById(R.id.editText);
                setSearched();
                String input = search.getText().toString().trim();
                new JSONTask().execute("https://kamorris.com/lab/audlib/booksearch.php?search="+input);
            }
        });

        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            new JSONTask().execute(URL_TO_HIT);
            /*ViewPager viewPager = findViewById(R.id.pager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.setBooks(myBooks);
            viewPager.setAdapter(adapter);*/

            //new JSONTask().doInBackground(URL_TO_HIT);
            //myBooks = new JSONTask().doInBackground(URL_TO_HIT);
        }
        else if(getResources().getDisplayMetrics().widthPixels>=600){
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment3, fragmentA)
                    .replace(R.id.fragment2, fragmentB)
                    .commit();*/
            new JSONTask().execute(URL_TO_HIT);
        }
        else{
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment3, fragmentA)
                    .replace(R.id.fragment2, fragmentB)
                    .commit();*/
            new JSONTask().execute(URL_TO_HIT);
        }


        //BookDetailsFragment fragment = BookDetailsFragment.newInstance(titles.get(1));
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }

    public void setBooks(ArrayList<Book> list){
        myBooks = list;

        //Looper.prepare();
        //Toast.makeText(this,myBooks.get(0).getAuthor(),Toast.LENGTH_LONG);
    }
    public void setSearched(){
        searched = true;
    }



    public class JSONTask extends AsyncTask<String,String,List<Book>> {

        @Override
        protected ArrayList<Book> doInBackground(String... params) {
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
                //JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = new JSONArray(finalJson);

                ArrayList<Book> bookList = new ArrayList<>();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Book book = new Book();
                    book.setAuthor(finalObject.getString("author"));

                    //Toast.makeText(MainActivity.this, book.getAuthor(), Toast.LENGTH_SHORT).show();
                    book.setBookTitle(finalObject.getString("title"));
                    book.setCoverURL(finalObject.getString("cover_url"));
                    book.setBookId(finalObject.getInt("book_id"));
                    book.setPublished(finalObject.getInt("published"));
                    book.setDuration(finalObject.getInt("duration"));
                    bookList.add(book);



                }
                setBooks(bookList);
                fragmentA.setBooks(bookList);
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
        @Override
        protected void onPostExecute(final List<Book> result) {
            super.onPostExecute(result);
            /*dialog.dismiss();
            if(result != null) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row, result);
                lvMovies.setAdapter(adapter);
                lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MovieModel movieModel = result.get(position); // getting the model
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieModel", new Gson().toJson(movieModel)); // converting model json into string type and sending it via intent
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }*/
            if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                    heightPixels) {
                ViewPager viewPager = findViewById(R.id.pager);
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.setBooks(myBooks);
                viewPager.setAdapter(adapter);
            }
            else{
                if(searched){
                    searched = false;
                    fragmentA.setAdapter();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment3, fragmentA)
                        .replace(R.id.fragment2, fragmentB)
                        .commit();
            }
        }

    }

    public void titleSend(Book title){
        fragmentB.displayBook(title);
    }

    public void bookPlay(int id){
        //AudiobookService.play(id);
    }
}
