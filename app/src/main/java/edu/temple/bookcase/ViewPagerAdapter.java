package edu.temple.bookcase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.app.FragmentTransaction;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ArrayList<String> titleList;
    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> titles) {
        super(fm);
        titleList = titles;
    }

    @Override
    public Fragment getItem(int i) {
        BookDetailsFragment frag = BookDetailsFragment.newInstance(titleList.get(i));
        i++;
        //getFragmentManager().beginTransaction().replace(R.id.fragment,frag).commit();
        return frag;
    }

    @Override
    public int getCount() {
        return 10;
    }


}
