package com.example.findbook;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity {

    public String imagepath = "";
    public ArrayList<String> userinfo;

    private static final int SELECT_PICTURE = 1;

    private ImageButton addBtn;
    private Button      loadingBtn,addfnsbtn,showCurUserBooks;
    private ImageView   img;
    private EditText    titlebk,copies;
    private DBHelper    db;
    private TextView    profile,antitipa;
    private Handler handler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler   = new Handler (getApplicationContext (), LoginActivity.this);
        db = new DBHelper (this);

        String usernameLABEL = getIntent().getExtras().getString("username");

        userinfo = db.getUserinfo (usernameLABEL);

        antitipa = findViewById (R.id.textView9);
        antitipa.setVisibility (View.INVISIBLE);
        profile = findViewById (R.id.labelusername);
        profile.setPaintFlags(profile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        profile.setText (usernameLABEL);

        addBtn = findViewById (R.id.addBtn);
        addBtn.bringToFront ();
        showCurUserBooks = findViewById (R.id.showuserbooksbtn);
        showCurUserBooks.bringToFront ();

        final SearchView findbtn = findViewById (R.id.findbtn);
        final ListView list = findViewById(R.id.bookslistview);

        final android.app.AlertDialog.Builder dbg2  = new android.app.AlertDialog.Builder(LoginActivity.this);
        final StringBuilder sb = new StringBuilder ();

        //String[] mProjection = {UserDictionary.Words.WORD};
        //String[] mSelections = {""};

        //ContentResolver resolver    = getContentResolver ();
        //Cursor          dictCursor  = resolver.query (UserDictionary.Words.CONTENT_URI, mProjection , "", null, null);



        findbtn.setOnQueryTextListener (new SearchView.OnQueryTextListener ( ) { //koumpi anazitisis bibliou
            @Override
            public boolean onQueryTextSubmit(String query) {

                String searchText = findbtn.getQuery ().toString ();

                ArrayList<ArrayList<String>> temp = db.SearchBooksByTitle(searchText);
                ArrayList<SearchBookItem> li2 = new ArrayList<> ();
                antitipa.setVisibility (View.INVISIBLE);
                for (int i = 0;i < temp.size ();i++) {

                    int    bookID       = Integer.parseInt (temp.get (i).get (0));
                    String bookTitle    = temp.get (i).get (1);
                    String bookPath     = temp.get (i).get (2);
                    int    bookQuantity = Integer.parseInt (temp.get (i).get (3));
                    String userName     = temp.get (i).get (4);
                    String email        = temp.get (i).get (5);
                    String phone        = temp.get (i).get (6);
                    String location     = temp.get (i).get (7);



                    SearchBookItem book = new SearchBookItem (email,phone,location,bookID, bookTitle, bookQuantity, bookPath);
                    li2.add (book);
                }

                SearchResAdapter customAdapter = new SearchResAdapter(getApplicationContext (), li2);
                list.setAdapter(customAdapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder nBuilder = new AlertDialog.Builder (LoginActivity.this);
                nBuilder.setTitle ("Προσθηκη Βιβλιου");
                nBuilder.setView (getLayoutInflater ( ).inflate (R.layout.addbook, null));
                final AlertDialog dialog = nBuilder.create ( );
                dialog.show ( );

                titlebk    = dialog.findViewById (R.id.booktitle);
                copies     = dialog.findViewById (R.id.countcopies);
                loadingBtn = dialog.findViewById (R.id.loadimgbtn);
                img        = dialog.findViewById (R.id.imageView);
                addfnsbtn  = dialog.findViewById (R.id.addbtn);

                copies.setText("1");

                loadingBtn.setOnClickListener (new View.OnClickListener (){
                    @Override
                    public void onClick(View v){
                        Intent i = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult (Intent.createChooser (i, "Select Picture"), SELECT_PICTURE);
                    }
                });

                addfnsbtn.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v){
                        String title = titlebk.getText().toString();
                        int userid   = Integer.parseInt (userinfo.get (0));
                        int count    = Integer.parseInt (copies.getText().toString());

                        if(handler.checkwhtspc (title,2)){
                            long err = db.insertBook(title, userid, count, imagepath);
                            handler.setMessage ("Η καταχωρηση ολοκληρωθηκε");
                            handler.create ().show ();
                            dialog.hide ();
                        }

                        //Locale locale = new Locale ("el", "GR");
                        //UserDictionary.Words.addWord(getApplicationContext (), title, 1,"gr", locale);
                        //UserDictionary.Words.addWord (LoginActivity.this,title,"", Locale.forLanguageTag ("el-GR));

                    }
                });
            }
        });


        profile.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                profileIntent.putExtra("id",userinfo.get (0));
                profileIntent.putExtra ("name",userinfo.get (1));
                profileIntent.putExtra ("email",userinfo.get (2));
                profileIntent.putExtra ("phone",userinfo.get(3));
                profileIntent.putExtra ("location",userinfo.get (4));
                startActivity (profileIntent);
            }
        });
        showCurUserBooks.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                antitipa.setVisibility(View.VISIBLE);
                ArrayList<ArrayList<String>> temp = db.getBooksofUserV3 (Integer.parseInt (userinfo.get(0)));
                ArrayList<MyBookItem> li = new ArrayList<> ();

                for (int i = 0;i < temp.size ();i++) {

                    String bookTitle    = temp.get (i).get (0);
                    int    bookQuantity = Integer.parseInt (temp.get (i).get (1));
                    String bookPath     = temp.get (i).get (2);
                    int    bookID       = Integer.parseInt (temp.get (i).get (3));

                    MyBookItem book = new MyBookItem (bookID, bookTitle, bookQuantity, bookPath);
                    li.add (book);
                }
                CustomAdapter customAdapter= new CustomAdapter(LoginActivity.this, li);
                list.setAdapter(customAdapter);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //kanei upload ti fwto apo ti gallery tou xristi.
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            imagepath = picturePath;
            cursor.close();
            img.setImageURI(selectedImage);
        }
    }

    @Override
    protected void onResume() { // ama o xristis figei apo ti login activity kai 3anagirisei de tha emfanistei to label me to username tou xristi,to opoio pernw apo ti bd. to krataw me ti onresume
        super.onResume ( );
        String usernameLABEL = getIntent().getExtras().getString("username");
        userinfo = db.getUserinfo (usernameLABEL);

    }


}
