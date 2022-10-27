package com.example.emailsearch;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        dbManager = new DatabaseManager(this);

        updateView();
    }

    //build view dynamically with list of friends
    public void updateView(){
        ArrayList<Friend> friends = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        GridLayout grid = new GridLayout(this);

        if (friends.size() > 0){
            grid.setRowCount(friends.size());
            grid.setColumnCount(4);

            //create arrays of components
            TextView [] ids = new TextView[friends.size()];
            EditText [][] namesandemail = new EditText[friends.size()][3];
            Button [] btns = new Button[friends.size()];
            ButtonHandler bh = new ButtonHandler();

            //retrieve width of screen
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;

            int i = 0;
            for(Friend friend : friends){
                //create textview for friend's id
                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText("" + friend.getId() );

                //create 3 edittxts for the first, last names and email
                namesandemail[i][0] = new EditText(this);
                namesandemail[i][1] = new EditText(this);
                namesandemail[i][2] = new EditText(this);

                namesandemail[i][0].setText(friend.getFirstName());
                namesandemail[i][1].setText(friend.getLastName());
                namesandemail[i][2].setText(friend.getEmail());

                namesandemail[i][2].setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                namesandemail[i][0].setId( 10 * friend.getId() );
                namesandemail[i][1].setId(10 * friend.getId() + 1);
                namesandemail[i][2].setId(10 * friend.getId() + 2);

                //create button
                btns[i] = new Button(this);
                btns[i].setText("Update");
                btns[i].setId(friend.getId());

                //set up event handling
                btns[i].setOnClickListener(bh);

                //add items to grid
//              grid.addView(ids[i],  (int) (width * 0.05), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesandemail[i][0], (int) (width * 0.2), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesandemail[i][1], (int) (width * 0.2), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesandemail[i][2], (int) (width * 0.35), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(btns[i],  (int) (width * 0.2), ViewGroup.LayoutParams.WRAP_CONTENT);

                i++;
            }

            //go back
            Button backButton = new Button(this);
            backButton.setText(R.string.button_back);
            backButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
//                    UpdateActivity.this.finish();
                    Intent insertIntent = new Intent(UpdateActivity.this, MainActivity.class );
                    startActivity( insertIntent );
                }
            });

            scrollView.addView(grid);
            layout.addView(scrollView);

            //add backButton to layout
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.setMargins(0, 0, 0, 50);
            layout.addView(backButton, params);

            setContentView(layout);
//            setContentView(scrollView);

        }
    }

    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //retrieve names and email
            int friendId = view.getId();
            EditText firstNameET = (EditText) findViewById(10 * friendId);
            EditText lastNameET = (EditText) findViewById(10 * friendId + 1);
            EditText emailET = (EditText) findViewById(10 * friendId + 2);

            String firstName = firstNameET.getText().toString();
            String lastName = lastNameET.getText().toString();
            String email = emailET.getText().toString();

            Log.d("friendId", String.valueOf(friendId));
            Log.d("firstName", firstName);
            //update Friend in database
            try{
                dbManager.updateById(friendId, firstName, lastName, email);
                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_LONG).show();
                updateView();
            }catch (Exception e){
            };
        }
    }

}
