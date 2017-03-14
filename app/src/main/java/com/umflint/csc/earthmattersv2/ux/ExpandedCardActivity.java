package com.umflint.csc.earthmattersv2.ux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;
import com.umflint.csc.earthmattersv2.adaptor.BoothCardContentAdapter;
import com.umflint.csc.earthmattersv2.adaptor.BoothCardViewHolder;
import com.umflint.csc.earthmattersv2.model.BoothModel;
import com.umflint.csc.earthmattersv2.model.ScheduleModel;
import com.umflint.csc.earthmattersv2.utilities.CalendarPopulator;
import com.umflint.csc.earthmattersv2.utilities.EventMap;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.utilities.Utilities;

import java.util.ArrayList;

public class ExpandedCardActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE_IMAGE = 42;
    private ImageView expandedcardImageView;
    private ImageView expandedCardCalendarImageView;
    private String eventName;
    private String location;
    private String coverName;
    private String startDate;
    private String endDate;
    private long calendarStartDate;
    private long calendarEndDate;
    private String eventDescription;
    private TextView expandedCardDateTextView;
    private TextView eventDescriptionTextView;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Utilities utilities;
    private CalendarPopulator calendarPopulator;
    private EventMap eventMap;
    private StorageReference storageRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference boothReference;
    private DatabaseReference scheduleReference;
    private ListView scheduleListView;
    private RecyclerView recyclerView;
    private FloatingActionButton fabBooth;
    private FloatingActionButton fabSchedule;
    private FloatingActionButton fabMaps;
    private boolean isAdmin;
    private ArrayList<String> mapsArrayList;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_expanded);
        toolbar.setTitle(getIntent().getExtras().getString(getString(R.string.extrasEventName)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        utilities = new Utilities();
        calendarPopulator = new CalendarPopulator(this);
        eventMap = new EventMap(this);
        activity = this;
        scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        recyclerView = (RecyclerView) findViewById(R.id.boothRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
        boothReference = database.getReference("Events").
                child(getIntent().getExtras().getString(getString(R.string.extrasCoverName))).child("Booths");
        scheduleReference = database.getReference("Events").
                child(getIntent().getExtras().getString(getString(R.string.extrasCoverName))).child("SubEvents");

        BoothCardContentAdapter boothCardContentAdapter = new BoothCardContentAdapter(
                BoothModel.class,
                R.layout.fragment_booth_card,
                BoothCardViewHolder.class,
                boothReference,
                this,
                this);

        recyclerView.setAdapter(boothCardContentAdapter);

        FirebaseListAdapter<ScheduleModel> firebaseListAdapter = new FirebaseListAdapter<ScheduleModel>(this, ScheduleModel.class,
                R.layout.fragment_schedule_card, scheduleReference ) {
            @Override
            protected void populateView(View v, ScheduleModel model, int position) {
                TextView subEventName = (TextView) v.findViewById(R.id.scheduleCardNameTextView);
                TextView subEventDescription = (TextView) v.findViewById(R.id.scheduleDescriptionTextView);
                TextView subEventDate = (TextView) v.findViewById(R.id.scheduleCardDateTextView);
                TextView subEventEndTime = (TextView) v.findViewById(R.id.scheduleCardEndTimeTextView);
                subEventName.setText(model.getSubEventName());
                subEventDescription.setText(model.getSubEventDescription());
                subEventDate.setText(model.getSubEventDate());
                subEventEndTime.setText(model.getSubEventStartTime() + " - " + model.getSubEventEndTime());
            }
        };

        scheduleListView.setAdapter(firebaseListAdapter);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_description) {
                    vf.setDisplayedChild(0);
                }else if (tabId == R.id.tab_booths){
                    vf.setDisplayedChild(1);
                }else if (tabId == R.id.tab_maps){
                    vf.setDisplayedChild(2);
                }else if (tabId == R.id.tab_schedule) {
                    vf.setDisplayedChild(3);
                }
            }
        });

        expandedcardImageView = (ImageView) findViewById(R.id.expandedCardIMageView);
        expandedcardImageView.setColorFilter(ContextCompat.getColor(this,R.color.cardImageBack), PorterDuff.Mode.MULTIPLY);
        //expandedCardCalendarImageView = (ImageView) findViewById(R.id.expandedCardCalendarImageView);
        eventDescriptionTextView = (TextView) findViewById(R.id.expandedCardDescriptionTextView);
        //expandedCardDateTextView = (TextView) findViewById(R.id.expandedCardDateTextView);

        storageRef = storage.getReferenceFromUrl(getString(R.string.firebaseBucket));
        eventName = getIntent().getExtras().getString(getString(R.string.extrasEventName));
        location = getIntent().getExtras().getString(getString(R.string.extrasLocation));
        coverName = getIntent().getExtras().getString(getString(R.string.extrasCoverName));
        eventDescription = getIntent().getExtras().getString(getString(R.string.extrasDescription));
        mapsArrayList = getIntent().getExtras().getStringArrayList(getString(R.string.maps_array_list));
        startDate = utilities.transFormDateForUser(getIntent().getExtras().getString(getString(R.string.extrasStartDate)));
        endDate = utilities.transFormDateForUser(getIntent().getExtras().getString(getString(R.string.extrasEndDate)));
        calendarStartDate = utilities.tranformDateForCalendar(getIntent().getExtras().getString(getString(R.string.extrasStartDate)));
        calendarEndDate = utilities.tranformDateForCalendar(getIntent().getExtras().getString(getString(R.string.extrasEndDate)));

        FloatingActionButton fabDirections = (FloatingActionButton) findViewById(R.id.fabDirections);
        fabDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventMap.getDirections(location);
            }
        });

        fabBooth = (FloatingActionButton) findViewById(R.id.fabBooth);
        fabBooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBoothActivity.class);
                intent.putExtra(activity.getString(R.string.extrasCoverName), coverName);
                startActivity(intent);
            }
        });

        fabSchedule = (FloatingActionButton) findViewById(R.id.fabSchedule);
        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddSubEventActivity.class);
                intent.putExtra("coverName", coverName);
                startActivity(intent);
            }
        });

        fabMaps = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMapActivity.class);
                intent.putExtra("coverName", coverName);
                intent.putExtra(getString(R.string.maps_array_list),mapsArrayList);
                startActivity(intent);
            }
        });



        checkAuth();
        StorageReference imageRef = storageRef.child(coverName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(expandedcardImageView.getContext()).load(uri.toString()).into(expandedcardImageView);
            }
        });

        eventDescriptionTextView.setText(eventDescription);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expanded_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add_calendar) {
            calendarPopulator.addToCalendar(calendarStartDate,calendarEndDate,eventName,eventDescription,location);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAuth(){
        if(utilities.checkAuth()){
            isAdmin = true;
            fabBooth.show();
            fabSchedule.show();
            fabMaps.show();
        }else{
            isAdmin = false;
            fabBooth.hide();
            fabMaps.hide();
            fabSchedule.hide();
        }
    }
}
