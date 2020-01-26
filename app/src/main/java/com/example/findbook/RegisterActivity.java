package com.example.findbook;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {
    private EditText       usrnm, psw, email, phone, location;
    private Button         regbtn;
    private SQLiteDatabase db;
    private DBHelper       mydb;
    public  TextView       showusers;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        handler   = new Handler (getApplicationContext (), RegisterActivity.this);
        mydb      = new DBHelper(this);
        usrnm     = findViewById(R.id.txt1);
        psw       = findViewById(R.id.txt2);
        email     = findViewById(R.id.txt3);
        phone     = findViewById(R.id.txt4);
        regbtn    = findViewById(R.id.btn);
        location  = findViewById(R.id.txt5);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String results = null;

                //results = mydb.getAllCotacts ( );

                if(handler.checkReg (usrnm.getText ().toString (),email.getText ().toString (),phone.getText ().toString (),location.getText().toString ())
                        && handler.checkwhtspc (usrnm.getText ().toString (),1)) {
                    long temp =mydb.insertUser(usrnm.getText().toString(), psw.getText().toString(), email.getText().toString(), phone.getText().toString(), location.getText().toString());
                    if(temp==-1){
                        handler.setMessage ("Η εγγραφη απετυχε, το ονομα χρηστη ηδη υπαρχει");

                    }
                    else{handler.setMessage ("Η διαδικασια εγγραφης ολοκληρωθηκες");}
                    handler.create().show();
                }

                //showusers.append(results);
            }
        });
    }
}
