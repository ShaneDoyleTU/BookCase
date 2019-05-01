package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;


public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK = "argBook";


    //private Book book;
    private String bookTitle;
    private String bookAuthor;
    private String bookPub;
    private String coverURL;
    private int id;
    private int duration;
    protected Button play;
    protected Button pause;
    protected Button stop;
    protected SeekBar seek;
    protected Button download;
    private PlayListener listener;
    //private TextView title;
    //private TextView author;
    //private TextView publish;
    //private TextView identification;
    public View view;
    //private Handler handler;

    public interface PlayListener{
        void bookPlay(int id);
        void bookPause();
        void bookStop();
        void bookSeek(int progress);
        void setId(int id);
        int sendId();
        void download(int id);
    }

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

        //identification = v.findViewById(R.id.identification);
        TextView title = v.findViewById(R.id.detailTitle);
        TextView author = v.findViewById(R.id.detailAuthor);
        TextView published = v.findViewById(R.id.detailPub);
        ImageView cover = v.findViewById(R.id.cover);
        if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                heightPixels) {
            if (getArguments() != null) {
                Book book = getArguments().getParcelable(ARG_BOOK);
                id = book.getBookId();
                //listener.setId(book.getBookId());
                //String id1 = String.format("%d",id);
                duration = book.getDuration();
                bookTitle = book.getBookTitle();
                bookAuthor = book.getAuthor();
                int pub = book.getPublished();
                bookPub = String.format("%d",pub);
                coverURL = book.getCoverURL();
                title.setText(bookTitle);
                author.setText(bookAuthor);
                published.setText(bookPub);
                //identification.setText(id1);
                Picasso.with(getActivity()).load(coverURL).into(cover);

            }
        }

        //title.setText(bookTitle);

        play = (Button) v.findViewById(R.id.button2);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //id = 1;
                if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                        heightPixels) {
                    listener.bookPlay(id);
                }
                else {
                    listener.bookPlay(listener.sendId());
                }
            }
        });
        pause = (Button) v.findViewById(R.id.button3);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.bookPause();
            }
        });
        stop = (Button) v.findViewById(R.id.button4);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.bookStop();
            }
        });
        seek = (SeekBar) v.findViewById(R.id.seekBar);
        seek.setMax(duration);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.bookSeek(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        download = (Button) v.findViewById(R.id.button5);
        File file = new File("/sdcard/somewhere/"+id+".mp3");
        if(file.exists()){
            download.setText("Delete");
        }
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getResources().getDisplayMetrics().widthPixels<getResources().getDisplayMetrics().
                        heightPixels) {
                    File file = new File("/sdcard/somewhere/"+id+".mp3");
                    if(file.exists()){
                        file.delete();
                        download.setText("Download");
                    }
                    else {
                        listener.download(id);
                        download.setText("Delete");
                    }
                }
                else {
                    File file = new File("/sdcard/somewhere/"+listener.sendId()+".mp3");
                    if(file.exists()){
                        file.delete();
                        download.setText("Download");
                    }
                    else {
                        listener.download(listener.sendId());
                        download.setText("Delete");
                    }
                }

            }
        });

        return v;
    }
    public void displayBook(Book title){
        bookTitle = title.getBookTitle();
        bookAuthor = title.getAuthor();
        //id = title.getBookId();
        listener.setId(title.getBookId());
        //String id1 = String.format("%d",id);
        duration = title.getDuration();
        int pub = title.getPublished();
        bookPub = String.format("%d",pub);
        coverURL = title.getCoverURL();
        /*play = (Button) view.findViewById(R.id.button2);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //id = 1;
                listener.bookPlay(id);
            }
        });
        pause = (Button) view.findViewById(R.id.button3);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.bookPause();
            }
        });
        stop = (Button) view.findViewById(R.id.button4);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.bookStop();
            }
        });
        seek = (SeekBar) view.findViewById(R.id.seekBar);
        seek.setMax(duration);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.bookSeek(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        //identification = view.findViewById(R.id.identification);
        ImageView cover = view.findViewById(R.id.cover);
        TextView titleView = view.findViewById(R.id.detailTitle);
        TextView authorView = view.findViewById(R.id.detailAuthor);
        TextView pubView = view.findViewById(R.id.detailPub);
        titleView.setText(bookTitle);
        authorView.setText(bookAuthor);
        pubView.setText(bookPub);
        seek.setMax(duration);
        //identification.setText(id1);
        Picasso.with(getActivity()).load(coverURL).into(cover);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PlayListener) {
            listener = (PlayListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PlayListener");
        }
        //listener = (BookListListener) context;
    }

    public void setSeek(int progress){
        seek.setProgress(progress);
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
