package com.example.emailsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SearchView searchbar;
    DatabaseManager dbManager;
    TextView result;
    AutoCompleteTextView search;
    Button searchButton;
    AutoCompleteTextView autoComplete;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);
        result = (TextView) findViewById(R.id.show_result);
        searchButton = (Button) findViewById(R.id.searchButton);


        //Creating the instance of ArrayAdapter containing list of emails
        ArrayList<Friend> friends = dbManager.selectAll();
        String[] emails = new String[friends.size()];
        for (int i = 0; i < friends.size(); i++) {
            emails[i] = friends.get(i).toEmailString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, emails);
        //Getting the instance of AutoCompleteTextView
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoComplete.setThreshold(1);//will start working from first character
        autoComplete.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                //String query = ((AutoCompleteTextView) view.findViewById(R.id.search)).getText().toString();
                String query = adapterView.getItemAtPosition(i).toString();
                autoComplete.setText(query);
            }
        });

    }

    public void searchForFriend(View view){
        String query = autoComplete.getText().toString();

        try {
            result.setText(dbManager.selectByEmail(query).toNameString());
        } catch (Exception e){
            result.setText("friend not found");
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater( ).inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );
        switch ( id ) {
            case R.id.action_add:
                Intent insertIntent = new Intent( this, InsertActivity.class );
                this.startActivity( insertIntent );
                return true;
            case R.id.action_delete:
                Intent deleteIntent = new Intent( this, DeleteActivity.class );
                this.startActivity( deleteIntent );
                return true;
            case R.id.action_update:
                Intent updateIntent = new Intent( this, UpdateActivity.class );
                this.startActivity( updateIntent );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

}

