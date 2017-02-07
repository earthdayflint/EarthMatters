package com.umflint.csc.earthmattersv2.ux;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.umflint.csc.earthmattersv2.R;

/**
 * Created by Benjamin on 2/6/2017.
 */

public class AddSubEventActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sub_event);
    }
}
