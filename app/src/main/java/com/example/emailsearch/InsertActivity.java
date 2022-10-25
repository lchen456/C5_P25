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
        EditText nameEditText = ( EditText) findViewById( R.id.input_name );
        EditText priceEditText = ( EditText) findViewById( R.id.input_price );
        String name = nameEditText.getText( ).toString( );
        String priceString = priceEditText.getText( ).toString( );

        // insert new candy in database
        try {
            Friend friend= new Friend( 0, "John", "Smith", "johnsmith@gmail.com" );
            dbManager.insert( friend );
            Toast.makeText( this, "Friend added", Toast.LENGTH_SHORT ).show( );
        } catch( Exception e ) {
        }

        // clear data
        nameEditText.setText( "" );
        priceEditText.setText( "" );
    }

    public void goBack( View v ) {
        this.finish( );
    }
}
