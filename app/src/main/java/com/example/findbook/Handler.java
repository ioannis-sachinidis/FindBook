package com.example.findbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

public class Handler extends AlertDialog.Builder {
    public Context context;
    public Activity activity;
    private DBHelper db;
    public Handler(Context context, Activity activity)  {
        super(activity);
        this.activity = activity;
        this.context  = context;
        db = new DBHelper(activity.getApplicationContext ());
    }
    public boolean checkReg(String username,String email,String phone,String location)//elegxei ti diadikiasia eggrafis tou xristi
    {
        if(email.isEmpty () && phone.isEmpty () && location.isEmpty ()) {
            this.setMessage ("Απαιτουνται τουλαχιστον ενα απο τα τρια τελευταια πεδια");
            this.create ( ).show ( );
            return false;
        }

        return true;
    }
    public boolean checkwhtspc(String inpt,int flag) //elegxei an iparxei keno sto input tou xristi.
    {
        switch (flag){
            case 1:{
                if(inpt.isEmpty ()){
                    this.setMessage ("Πρεπει να εισαγετε ονομα χρηστη ");
                    this.create ().show ();
                    return false;
                }
            }
            case 2:{
                if(inpt.isEmpty ()){
                    this.setMessage ("Πρεπει να εισαγετε τιτλο βιβλιου");
                    this.create ().show ();
                    return false;

                }
            }
            default:return true;
        }


    }




}
