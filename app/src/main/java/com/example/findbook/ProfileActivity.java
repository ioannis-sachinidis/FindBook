package com.example.findbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView profid,profname;
    private EditText profemail,profphone,proflocation;
    private Button changebtn;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);
        db = new DBHelper (this);
        profid = (TextView) findViewById (R.id.idinprofile);
        profname= (TextView) findViewById (R.id.nameinprofile);
        profemail = (EditText) findViewById (R.id.emailinprofile);
        profphone = (EditText) findViewById (R.id.phoneinprofile);
        proflocation= (EditText) findViewById (R.id.locationinprofile);
        changebtn = (Button) findViewById (R.id.btninprofile);
        profid.setText (getIntent ().getExtras ().getString ("id"));
        profname.setText (getIntent ().getExtras ().getString ("name"));
        profemail.setText (getIntent ().getExtras ().getString ("email"));
        profphone.setText (getIntent ().getExtras ().getString ("phone"));
        proflocation.setText (getIntent ().getExtras ().getString ("location"));

        final Intent iLogin = new Intent (getApplicationContext(), LoginActivity.class);
        iLogin.putExtra ("username", profname.getText ().toString ());



        changebtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt (profid.getText ().toString ());
                String email = profemail.getText ().toString ();
                String phone = profphone.getText ().toString ();
                String location = proflocation.getText ().toString ();
                db.updateContact (id, email, phone, location);
                startActivity (iLogin);
            }
        });

    }
}
