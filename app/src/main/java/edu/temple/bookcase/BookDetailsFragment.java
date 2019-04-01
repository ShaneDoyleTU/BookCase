package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK = "argBook";



    private String bookTitle;

    public View view;

    public static BookDetailsFragment newInstance(String book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOOK,book);
        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details,container,false);
        view = v;
        TextView title = v.findViewById(R.id.detailTitle);
        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            if (getArguments() != null) {
                bookTitle = getArguments().getString(ARG_BOOK);
                title.setText(bookTitle);
            }
        }

        title.setText(bookTitle);

        return v;
    }
    public void displayBook(String title){
        bookTitle = title;
        TextView titleView = view.findViewById(R.id.detailTitle);
        titleView.setText(title);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
