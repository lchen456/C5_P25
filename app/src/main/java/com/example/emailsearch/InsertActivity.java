package com.example.emailsearch;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        setContentView( R.layout.activity_insert );
    }

    public void insert( View v ) {
        // Retrieve name and price
        EditText firstnameEditText = ( EditText) findViewById( R.id.input_first_name );
        EditText lastnameEditText = ( EditText) findViewById( R.id.input_last_name );
        EditText emailEditText = ( EditText) findViewById( R.id.input_email );
        String firstname = firstnameEditText.getText( ).toString( );
        String lastname = lastnameEditText.getText( ).toString( );
        String email = emailEditText.getText( ).toString( );

        // insert new friend into database
        try {
            Friend friend= new Friend( 0, firstname, lastname, email );
            dbManager.insert( friend );
            Toast.makeText( this, "Friend added", Toast.LENGTH_SHORT ).show( );
        } catch( Exception e ) {
        }

        // clear data
        firstnameEditText.setText( "" );
        lastnameEditText.setText( "" );
        emailEditText.setText( "" );
    }

    public void goBack( View v ) {
        this.finish( );
    }
}
