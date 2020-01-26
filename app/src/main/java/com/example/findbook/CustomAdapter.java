package com.example.findbook;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<MyBookItem> {
    private DBHelper db;
    private Context context;
    private LayoutInflater inflter;
    public ArrayList<MyBookItem> lista;


    public CustomAdapter(Context context, ArrayList<MyBookItem> li) {
        super (context,0, li);
        this.context = context;
        this.db      = new DBHelper(context);
        inflter = (LayoutInflater.from(context));
        this.lista = li;
    }
    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {

        view = inflter.inflate (R.layout.row_mybooks, null, false);

        ImageView icon         = (ImageView) view.findViewById (R.id.icon25);
        TextView title         = (TextView) view.findViewById (R.id.titlesearch);
        final TextView counter = (TextView) view.findViewById (R.id.finalcountertext);
        Button  increase       = (Button) view.findViewById (R.id.btnincrease);
        Button  decrease       = (Button) view.findViewById (R.id.btndecrease);

        final MyBookItem book  = getItem (position);

        counter.setText (Integer.toString (book.quantity));
        title.setText (book.title);

        final File imgFile = new  File(book.imagepath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            icon.setImageBitmap(myBitmap);
        }


        increase.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                int pcounter = Integer.parseInt ( (counter.getText ().toString ())) + 1;
                counter.setText (Integer.toString (pcounter));
                db.updateBookQuantity (book.bookid, pcounter);
            }
        });

        final android.app.AlertDialog.Builder dialog  = new android.app.AlertDialog.Builder(view.getRootView ().getContext ());

        decrease.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                // delete when counter == 0
                dialog.setMessage ("Ειστε σιγουροι οτι θελετε να διαγραψετε αυτη τη καταχωρηση; " + Integer.toString (position));
                int pcounter = Integer.parseInt ( (counter.getText ().toString ())) - 1;
                if(pcounter == 0) {
                    dialog.setCancelable (true);
                    dialog.setPositiveButton ("Ναι", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteBook(book.bookid);
                            lista.remove(book);
                            CustomAdapter.this.notifyDataSetChanged ();
                        }
                    });
                    dialog.setNegativeButton ("Οχι", new DialogInterface.OnClickListener ( ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setMessage("Ειστε σιγουροι οτι θελετε να διαγρεψετε τη καταχωρηση");
                    dialog.show ();

                } else {
                    counter.setText (Integer.toString (pcounter));
                    db.updateBookQuantity (book.bookid, pcounter);
                }

            }

        });
        return view;
    }








}

