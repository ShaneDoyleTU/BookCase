package edu.temple.bookcase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.app.FragmentTransaction;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    //public ArrayList<String> titleList;

    public ArrayList<Book> books = new ArrayList<>();
    /*public ArrayList<String> setUp(){
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
        return titles;
    }*/
    public void setBooks(ArrayList<Book> books){
        this.books = books;

    }
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        ArrayList<Book> titles = books;

        BookDetailsFragment frag = BookDetailsFragment.newInstance(titles.get(i));

        //frag.setSeek();




        //getFragmentManager().beginTransaction().replace(R.id.fragment,frag).commit();
        return frag;
    }

    @Override
    public int getCount() {
        return books.size();
    }


}
