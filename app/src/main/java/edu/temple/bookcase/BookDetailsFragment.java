package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK = "argBook";


    //private Book book;
    private String bookTitle;
    private String bookAuthor;
    private String bookPub;
    private String coverURL;

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
        ImageView cover = v.findViewById(R.id.cover);
        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            if (getArguments() != null) {
                Book book = getArguments().getParcelable(ARG_BOOK);
                bookTitle = book.getBookTitle();
                bookAuthor = book.getAuthor();
                int pub = book.getPublished();
                bookPub = String.format("%d",pub);
                coverURL = book.getCoverURL();
                title.setText(bookTitle);
                author.setText(bookAuthor);
                published.setText(bookPub);
                Picasso.with(getActivity()).load(coverURL).into(cover);
            }
        }

        title.setText(bookTitle);

        return v;
    }
    public void displayBook(Book title){
        bookTitle = title.getBookTitle();
        bookAuthor = title.getAuthor();
        int pub = title.getPublished();
        bookPub = String.format("%d",pub);
        coverURL = title.getCoverURL();
        ImageView cover = view.findViewById(R.id.cover);
        TextView titleView = view.findViewById(R.id.detailTitle);
        TextView authorView = view.findViewById(R.id.detailAuthor);
        TextView pubView = view.findViewById(R.id.detailPub);
        titleView.setText(bookTitle);
        authorView.setText(bookAuthor);
        pubView.setText(bookPub);
        Picasso.with(getActivity()).load(coverURL).into(cover);

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
