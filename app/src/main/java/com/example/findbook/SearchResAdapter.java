package com.example.findbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class SearchResAdapter extends ArrayAdapter<SearchBookItem> {
    DBHelper db;
    Context context;
    LayoutInflater inflter;

    public SearchResAdapter(Context context, ArrayList<SearchBookItem> li) {
        super (context,0, li );
        db      = new DBHelper(context);
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate (R.layout.rowfinditem, null, false);

        ImageView icon         = (ImageView) v.findViewById (R.id.icon25);
        final TextView title = (TextView) v.findViewById (R.id.titlesearch);
        TextView copies = (TextView) v.findViewById (R.id.copiessearch);
        TextView email = (TextView) v.findViewById (R.id.emailsearch);
        TextView phone = (TextView) v.findViewById (R.id.phonesearch);
        final TextView location = (TextView) v.findViewById (R.id.locationsearch);
        SearchBookItem book = getItem (position);
        title.setText (book.title);
        email.setText (book.email);
        phone.setText (book.phone);
        location.setText (book.location);
        copies.setText (Integer.toString (book.quantity));

        File imgFile = new  File(book.imagepath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            icon.setImageBitmap(myBitmap);
        }

        location.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Context c = v.getRootView ().getContext ();
                Intent i = new Intent (c, MapsActivity.class);
                i.putExtra ("location", location.getText ().toString ());
                i.putExtra("booktitle",title.getText ().toString());
                c.startActivity (i);
            }
        });
        return v;
    }
}

