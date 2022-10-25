package com.example.emailsearch;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    DatabaseManager dbManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DatabaseManager(this);
        updateView();
    }

    public void updateView(){
        ArrayList<Friend> friends = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup group = new RadioGroup(this);
        for (Friend friend : friends){
            RadioButton rb = new RadioButton(this);
            rb.setId(friend.getId());
            rb.setText(friend.toString());
            group.addView(rb);
        }
        //event handling
        RadioButtonHandler rbh = new RadioButtonHandler();
        group.setOnCheckedChangeListener(rbh);

        //
        Button backButton = new Button(this);
        backButton.setText(R.string.button_back);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                DeleteActivity.this.finish();
            }
        });

        scrollView.addView(group);
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
    }

    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener{
        public void onCheckedChanged(RadioGroup group, int checkedID){
            //delete friend from db
            dbManager.deleteById(checkedID);
            Toast.makeText(DeleteActivity.this, "Friend Deleted", Toast.LENGTH_SHORT).show();

            //update screen
            updateView();
        }
    }
}


