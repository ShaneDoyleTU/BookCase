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
    private String bookAuthor;
    private String bookPub;

    public View view;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK,book);
        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details,container,false);
        view = v;
        TextView title = v.findViewById(R.id.detailTitle);
        TextView author = v.findViewById(R.id.detailAuthor);
        TextView published = v.findViewById(R.id.detailPub);
        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            if (getArguments() != null) {
                Book book = getArguments().getParcelable(ARG_BOOK);
                bookTitle = book.getBookTitle();
                bookAuthor = book.getAuthor();
                int pub = book.getPublished();
                bookPub = String.format("%d",pub);
                title.setText(bookTitle);
                author.setText(bookAuthor);
                published.setText(bookPub);
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
