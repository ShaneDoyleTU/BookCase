package edu.temple.bookcase;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("A Tale of Two Cities");
        titles.add("War and Peace");
        titles.add("Lord of the Rings");
        titles.add("Gilgamesh");
        titles.add("The Odyssey");
        titles.add("IT");
        titles.add("The Prince");
        titles.add("Dune");
        titles.add("To Kill a Mockingbird");
        titles.add("Pride and Prejudice");
        ViewPager viewPager = findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),titles);
        //BookDetailsFragment fragment = BookDetailsFragment.newInstance(titles.get(1));
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }
}
