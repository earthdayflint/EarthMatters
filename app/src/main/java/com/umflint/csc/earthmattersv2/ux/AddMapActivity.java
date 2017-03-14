package com.umflint.csc.earthmattersv2.ux;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.utilities.Utilities;

/**
 * Created by Benjamin on 3/14/2017.
 */

public class AddMapActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE_IMAGE = 42;
    private Uri imageUri;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private Button mapSelectButton;
    private Button addMapButton;
    private String coverName;
    private ImageView mapImage;
    private String mapName;
    private Utilities utilities = new Utilities();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map);
        database = FirebaseDatabase.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl(getString(R.string.firebaseBucket));
        mapSelectButton = (Button) findViewById(R.id.mapSelectButton);
        addMapButton = (Button) findViewById(R.id.addMapButton);
        mapImage = (ImageView) findViewById(R.id.mapImage);

        coverName = getIntent().getExtras().getString(getString(R.string.extrasCoverName));

        mapSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");

                startActivityForResult(intent, READ_REQUEST_CODE_IMAGE);
            }
        });

        addMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null){
                    mapName = Long.toString(System.currentTimeMillis());
                    StorageReference imageRef = storageRef.child(mapName);
                    imageRef.putFile(imageUri);
                }
                finish();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == READ_REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK){
            imageUri = null;
            if(data != null){
                imageUri = data.getData();
                mapSelectButton.setText(getDocumentName(imageUri));
                mapImage.setImageURI(imageUri);
            }
        }
    }

    public String getDocumentName(Uri uri) {

        Cursor cursor = this.getContentResolver()
                .query(uri, null, null, null, null, null);
        String displayName = null;

        try {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            cursor.close();
        }
        return displayName;
    }
}