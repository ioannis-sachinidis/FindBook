package com.example.findbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


        private TextView regbtn;
        private Button logbtn;
        private DBHelper db;
        private EditText usrnm, psw;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate (savedInstanceState);
            setContentView (R.layout.activity_main);

            db = new DBHelper (this);
            logbtn = (Button) findViewById (R.id.signbtn);
            regbtn = (TextView) findViewById (R.id.regbtn);
            usrnm = (EditText) findViewById (R.id.username);
            psw = (EditText) findViewById (R.id.pass);

            View.OnClickListener onClickListener = new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    if (view.getId ( ) == R.id.signbtn) {
                        if (db.check (usrnm.getText ( ).toString ( ).trim ( ), psw.getText ( ).toString ( ).trim ( ))) {
                            intent = new Intent (getApplicationContext ( ), LoginActivity.class);
                            intent.putExtra ("username", usrnm.getText ( ).toString ( ).trim ( ));
                        } else {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder (MainActivity.this);
                            dlgAlert.setMessage ("wrong password or username");
                            dlgAlert.setTitle ("Error Message...");
                            dlgAlert.setPositiveButton ("OK", null);
                            dlgAlert.setCancelable (true);
                            dlgAlert.create ( ).show ( );
                        }
                    } else if (view.getId ( ) == R.id.regbtn) {
                        intent = new Intent (getApplicationContext ( ), RegisterActivity.class);
                    }
                    if (intent != null) startActivity (intent);
                }
            };
            logbtn.setOnClickListener (onClickListener);
            regbtn.setOnClickListener (onClickListener);
        }

}
