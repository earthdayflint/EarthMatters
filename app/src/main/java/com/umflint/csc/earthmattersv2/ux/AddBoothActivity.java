package com.umflint.csc.earthmattersv2.ux;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.model.BoothModel;
import com.umflint.csc.earthmattersv2.model.EventModel;
import com.umflint.csc.earthmattersv2.utilities.Utilities;

/**
 * Created by Benjamin on 1/24/2017.
 */

public class AddBoothActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText boothNumberEditText;
    private EditText boothNameEditText;
    private EditText boothDescriptionEditText;
    private EditText boothWebsiteEditText;
    private String boothId;
    private Button addEventButton;
    private String coverName;
    private Utilities utilities = new Utilities();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booth);
        database = FirebaseDatabase.getInstance();
        boothDescriptionEditText = (EditText) findViewById(R.id.boothDescriptionEditText);
        boothNameEditText = (EditText) findViewById(R.id.boothNameEditText);
        boothNumberEditText = (EditText) findViewById(R.id.boothNumberEditText);
        boothWebsiteEditText = (EditText) findViewById(R.id.boothWebsiteEditText);
        boothId = Long.toString(System.currentTimeMillis());
        addEventButton = (Button) findViewById(R.id.add_booth_button);
        coverName = getIntent().getExtras().getString(getString(R.string.extrasCoverName));
        myRef = database.getReference("Events").
                child(coverName).child("Booths").child(boothId);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
                finish();
            }
        });

    }

    public void addEvent(){
        BoothModel newBooth = new BoothModel(boothNameEditText.getText().toString(),
                Integer.parseInt(boothNumberEditText.getText().toString()),
                boothDescriptionEditText.getText().toString(),
                utilities.formatURL(boothWebsiteEditText.getText().toString()), boothId);
        myRef.setValue(newBooth);

    }
}
