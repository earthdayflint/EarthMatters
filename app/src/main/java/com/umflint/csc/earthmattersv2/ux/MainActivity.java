package com.umflint.csc.earthmattersv2.ux;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.umflint.csc.earthmattersv2.adaptor.CardViewHolder;
import com.umflint.csc.earthmattersv2.adaptor.ContentAdapter;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.model.EventModel;
import com.umflint.csc.earthmattersv2.utilities.Utilities;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity = this;
    private TextView tipCardTextView;
    private DatabaseReference myref;
    private DatabaseReference tipReference;
    private RecyclerView recyclerView;
    private boolean isAdmin;
    private CardView cardView;
    private FloatingActionButton fab;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Utilities utilities;
    private NavigationView navigationView;
    private Menu nav_Menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myref = database.getReference(getString(R.string.firebaseEvents));
        tipReference = database.getReference("TipText");
        fab = (FloatingActionButton) findViewById(R.id.fab);
        utilities = new Utilities();
        cardView = (CardView) findViewById(R.id.tipCardViewClick);
        tipCardTextView = (TextView) findViewById(R.id.tipCardTextView);


        tipReference.child("text").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipCardTextView.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ContentAdapter contentAdapter = new ContentAdapter(
                EventModel.class,
                R.layout.fragment_card,
                CardViewHolder.class,
                myref,
                this,
                this);

        recyclerView.setAdapter(contentAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateEventActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        checkAuth();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_qrscan) {
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            //integrator.setPrompt(getString(R.string.scanPrompt));
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();


        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_facebook) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL(this);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        } else if (id == R.id.nav_news) {
            Intent newsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.newsURL)));
            startActivity(newsIntent);
        } else if (id == R.id.nav_website) {
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.websiteURL)));
            startActivity(websiteIntent);
        } else if (id == R.id.nav_email){
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.contactUsEmail)});
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } else if(id == R.id.nav_settings){
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(getIntent());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + getString(R.string.facebookURL);
            } else { //older versions of fb app
                return "fb://page/" + getString(R.string.facebookID);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return getString(R.string.facebookURL); //normal web url
        }
    }

    private void checkAuth(){
        if(utilities.checkAuth()){
            isAdmin = true;
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            fab.show();
        }else{
            isAdmin = false;
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            fab.hide();
        }
    }
}
