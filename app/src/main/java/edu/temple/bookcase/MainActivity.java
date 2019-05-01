package edu.temple.bookcase;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.temple.audiobookplayer.AudiobookService;



public class MainActivity extends AppCompatActivity implements BookListFragment.BookListListener, BookDetailsFragment.PlayListener {

    private BookListFragment fragmentA;
    private BookDetailsFragment fragmentB;
    private final String URL_TO_HIT = "https://kamorris.com/lab/audlib/booksearch.php";
    protected Button searchButton;
    private boolean searched;
    public AudiobookService.MediaControlBinder binder;
    private Handler handler = new Handler();
    private int id;
    long queueid;
    DownloadManager dm;
    //public AudiobookService service;

    ArrayList<Book> myBooks = new ArrayList<>();

    AudiobookService audioService;
    boolean connected;

    ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AudiobookService.MediaControlBinder) service;
            //audioService = binder.getService();
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    @Override
    public void onStart(){
        super.onStart();
        Intent serviceIntent = new Intent(this, AudiobookService.class);
        bindService(serviceIntent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop(){
        super.onStop();
        unbindService(myConnection);

    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putBoolean("searched",searched);
        outState.putString("URL_TO_HIT",URL_TO_HIT);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if(savedInstanceState!=null){
            //searched=savedInstanceState.getBoolean("searched");
        }*/

        //binder.setProgressHandler(handler);

        //Message msg = handler.obtainMessage();
        //String msg1 = msg.toString();

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
        /*Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String m = String.valueOf(msg.what);
                int j = Integer.parseInt(m);
                fragmentB.setSeek(j);
                return false;
            }
        });*/

        fragmentA = new BookListFragment();
        fragmentB = new BookDetailsFragment();
        //binder.setProgressHandler(handler);
        searchButton = (Button) findViewById(R.id.button);
        final EditText search = (EditText) findViewById(R.id.editText);
        search.requestFocus();
        /*if(searched){
            String input = search.getText().toString().trim();
            URL_TO_HIT = "https://kamorris.com/lab/audlib/booksearch.php?search="+input;
        }*/


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText search = (EditText) findViewById(R.id.editText);
                setSearched();
                String input = search.getText().toString().trim();
                new JSONTask().execute("https://kamorris.com/lab/audlib/booksearch.php?search="+input);
                //URL_TO_HIT = "https://kamorris.com/lab/audlib/booksearch.php?search="+input;
            }
        });

        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            new JSONTask().execute(URL_TO_HIT);
            //fragmentB.setHandler(handler);
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
        //Toast.makeText(this,"AHHHHHHH",Toast.LENGTH_LONG);
        File file = new File("/sdcard/somewhere/"+id+".mp3");
        if(file.exists()){
            binder.play(file);
        }
        else {
            binder.play(id);
        }
    }
    public void bookPause(){
        binder.pause();
    }
    public void bookStop(){
        binder.stop();
    }
    public void bookSeek(int progress){
        binder.seekTo(progress);
    }
    public void setId(int id){
        this.id = id;
    }
    public int sendId(){
        return id;
    }
    public void download(int id){
        /*dm= (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://kamorris.com/lab/audlib/download.php?id="+String.format("%d",id)));
        queueid = dm.enqueue(request);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(queueid);
            }
        };*/
        new DownloadFile().execute("https://kamorris.com/lab/audlib/download.php?id="+String.format("%d",id),String.format("%d",id));
    }
    private class DownloadFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String...url) {
            int count;
            try {
                URL url1 = new URL(url[0]);
                URLConnection conexion = url1.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url1.openStream());
                OutputStream output = new FileOutputStream("/sdcard/somewhere/"+url[1]+".mp3");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }
    }
}
