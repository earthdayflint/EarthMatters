package com.umflint.csc.earthmattersv2.ux;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.fragment.AddDateDialogFragment;
import com.umflint.csc.earthmattersv2.model.EventModel;

public class CreateEventActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE_IMAGE = 42;
    private static final int READ_REQUEST_CODE_DOC = 7;
    private DatabaseReference myref;
    FirebaseStorage storage;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private EditText eventNameEditText;
    private EditText eventDescriptionEditText;
    private CheckBox featuredCheckBox;
    private String endDateString;
    private String startDateString;
    private String eventLocation;
    private String coverName;
    private String scheduleName;
    private int featured = 0;
    private Uri imageUri;
    private Uri pdfUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setTitle(getString(R.string.addNewEvent));
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref = database.getReference(getString(R.string.firebaseEvents));
        storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl(getString(R.string.firebaseBucket));
        startTimeTextView = (TextView) findViewById(R.id.timeTextView);
        endTimeTextView = (TextView) findViewById(R.id.endDateTextView);
        eventDescriptionEditText = (EditText) findViewById(R.id.eventDescriptionTextView);
        eventNameEditText = (EditText) findViewById(R.id.eventNameEditText);
        featuredCheckBox = (CheckBox) findViewById(R.id.featuredCheckBox);

        eventNameEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                eventLocation = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {
                Log.i("tag", "An error occurred: " + status);
            }
        });

        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(0);
            }
        });

        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if(requestCode == READ_REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK){
            imageUri = null;
            if(data != null){
                imageUri = data.getData();
                selectImageButton.setText(getDocumentName(imageUri));
            }
        } else if(requestCode == READ_REQUEST_CODE_DOC && resultCode == Activity.RESULT_OK){
            pdfUri = null;
            if(data != null){
                pdfUri = data.getData();
                String uriName = getDocumentName(pdfUri);
                selectScheduleButton.setText(uriName);
                scheduleName = uriName;

            }
        }*/
    }

    public void setStartDateString(String date){
        this.startDateString = date;
        startTimeTextView.setText(startDateString);
    }

    public void setEndDateString(String date){
        this.endDateString = date;
        endTimeTextView.setText(endDateString);
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

    public void setDate(int i){
        AddDateDialogFragment addDateDialogFragment = new AddDateDialogFragment();
        addDateDialogFragment.show(getFragmentManager(), "datePicker");
        addDateDialogFragment.setDateChooser(i);
    }

    public void addEvent(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        EventModel newEvent = new EventModel(eventNameEditText.getText().toString(), eventLocation, eventDescriptionEditText.getText().toString(), startDateString, endDateString, coverName, scheduleName);
        ref.child("Events").child(coverName).setValue(newEvent);
        if (featured ==1){
            ref.child("Featured").child("featured").removeValue();
            ref.child("Featured").child("featured").setValue(newEvent);
        }
    }
}
