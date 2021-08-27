package com.devul.GPAMapper.app.Other;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devul.GPAMapper.app.Assignments.AssignmentListView;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.ConversionTables.ConversionTablesViewer;
import com.devul.GPAMapper.app.ConversionTables.Conversions;
import com.devul.GPAMapper.app.Feelings.Feelings;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.ScoresConvertor.ScoresConvertorViewer;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Settings.AppSettings;
import com.devul.GPAMapper.app.Subjects.SubjectsListView;
import com.devul.GPAMapper.app.Subjects.SubjectsViewer;
import com.devul.GPAMapper.app.Timeline.utils.TimeLineViewer;
import com.devul.GPAMapper.app.Years.Years;
import com.devul.GPAMapper.app.Years.YearsViewer;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DataInitializer extends AppCompatActivity {

    public static boolean emojiState = true;
    final DatabaseHandler db = new DatabaseHandler(DataInitializer.this);
    DrawerLayout dLayout;
    Fragment frag;
    Fragment conversionViewer;
    SharedPreferences prefs;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_initilizer);
        conversionViewer = new ConversionTablesViewer();
        simpleFrameLayout = findViewById(R.id.frame1);
        tabLayout = findViewById(R.id.simpleTabLayout);

        tabLayout.setVisibility(View.VISIBLE);

        prefs = DataInitializer.this.getSharedPreferences("prefs", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Main Page");
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        setNavigationDrawer(); // call method
        loadValues();

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Main Activity"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.list); // set an icon for the

        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Graphs"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.bar_chart); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame1, new MainActivity());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MainActivity();
                        break;
                    case 1:
                        fragment = new MainActivityGraphs();
                        break;
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame1, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // THIS IS YOUR DRAWER/HAMBURGER BUTTON
            case android.R.id.home:
                dLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationDrawer() {
        dLayout = findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = findViewById(R.id.navigation); // initiate a Navigation View

        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open, R.string.close);
        dLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        prefs = DataInitializer.this.getSharedPreferences("prefs", MODE_PRIVATE);

        if (prefs.getString("userImg", null) != null) {
            ImageView img = navView.getHeaderView(0).findViewById(R.id.profile_image);
            img.setImageURI(Uri.parse(prefs.getString("userImg", null)));
        }

        if (prefs.getString("userName", null) != null) {
            TextView usrName = navView.getHeaderView(0).findViewById(R.id.username);
            usrName.setText(prefs.getString("userName", null));
        }

        // implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
                // check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.mainPage_item) {
                    tabLayout.setVisibility(View.GONE);
                    startActivity(new Intent(DataInitializer.this, DataInitializer.class));
                }
                if (itemId == R.id.scores_item) {
                    tabLayout.setVisibility(View.GONE);
                    startActivity(new Intent(DataInitializer.this, SubjectsViewer.class));
                }
                if (itemId == R.id.yearsAndSemesters_item) {
                    tabLayout.setVisibility(View.GONE);
                    frag = new YearsViewer();
                }
                if (itemId == R.id.conversionTable) {
                    tabLayout.setVisibility(View.GONE);
                    frag = new ConversionTablesViewer();
                }
                if (itemId == R.id.scoreConverter) {
                    tabLayout.setVisibility(View.GONE);
                    frag = new ScoresConvertorViewer();
                }
                if (itemId == R.id.scoresTimeline) {
                    tabLayout.setVisibility(View.GONE);
                    frag = new TimeLineViewer();
                }
                if (itemId == R.id.settings) {
                    tabLayout.setVisibility(View.GONE);
                    startActivity(new Intent(DataInitializer.this, AppSettings.class));
                }
                // display a toast message with menu item's title
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame1, frag); // replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                return false;
            }
        });
    }

    public void loadValues() {
        AppSettings.editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        SubjectsListView.filter = "";
        SubjectsListView.filterS = "";
        AssignmentListView.filter = "";
        AssignmentListView.filterS = "";

        SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(DataInitializer.this);
        if (!prefs2.getBoolean("firstTime", false)) {
            SubjectsListView.filter = "";
            SubjectsListView.filterS = "";
            AssignmentListView.filter = "";
            AssignmentListView.filterS = "";
            emojiState = true;
            AppSettings.year = 1;
            AppSettings.semester = 1;
            AppSettings.decimalPlaces = 1;
            AppSettings.yrName = "";
            AppSettings.semName = "";
            AppSettings.gpaTable = "4.0";

            SharedPreferences.Editor editor3 = getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor3.putString("yearName", "Year 1");
            editor3.putInt("year", 1);
            editor3.putString("semName", "1");
            editor3.putInt("semester", 1);
            editor3.putString("gpaTable", "4.0");
            editor3.putInt("decimalPlace", 1);
            editor3.putBoolean("emojiState", true);
            editor3.apply();

            db.addYear(new Years(1, "Year 1", 0.0));
            db.addSemester(new Semesters(1, 1, "1", 0.0, R.drawable.no_data_b));

            db.addFeelings(new Feelings(R.drawable.inlove, 8));
            db.addFeelings(new Feelings(R.drawable.happy, 7));
            db.addFeelings(new Feelings(R.drawable.smiling, 6));
            db.addFeelings(new Feelings(R.drawable.neutral, 5));
            db.addFeelings(new Feelings(R.drawable.embarrassed, 4));
            db.addFeelings(new Feelings(R.drawable.sad, 3));
            db.addFeelings(new Feelings(R.drawable.crying, 2));
            db.addFeelings(new Feelings(R.drawable.angry, 1));

            db.addCategory(new Categories("Major", -1, R.drawable.major));
            db.addCategory(new Categories("Minor", -1, R.drawable.minor));
            db.addCategory(new Categories("Labs", -1, R.drawable.labs));
            db.addCategory(new Categories("Quiz", -1, R.drawable.quiz));
            db.addCategory(new Categories("Test", -1, R.drawable.test));
            db.addCategory(new Categories("Classwork", -1, R.drawable.classwork));
            db.addCategory(new Categories("Presentation", -1, R.drawable.presentation));
            db.addCategory(new Categories("Research Essay", -1, R.drawable.research));
            db.addCategory(new Categories("Project", -1, R.drawable.colloboration));
            db.addCategory(new Categories("Homework", -1, R.drawable.homework));
            db.addCategory(new Categories("Exam", -1, R.drawable.exam));

            db.addConversion(new Conversions("4.0", R.drawable.a_plus, "97.5 - 100.0", 4.0));
            db.addConversion(new Conversions("4.0", R.drawable.a, "93.0 - 97.5", 4.0));
            db.addConversion(new Conversions("4.0", R.drawable.a_minus, "89.5 - 93.0", 3.7));
            db.addConversion(new Conversions("4.0", R.drawable.b_plus, "87.5 - 89.5", 3.3));
            db.addConversion(new Conversions("4.0", R.drawable.b, "82.5 - 87.5", 3.0));
            db.addConversion(new Conversions("4.0", R.drawable.b_minus, "79.5 - 82.5", 2.7));
            db.addConversion(new Conversions("4.0", R.drawable.c_plus, "77.5 - 79.5", 2.3));
            db.addConversion(new Conversions("4.0", R.drawable.c, "72.5 - 77.5", 2.0));
            db.addConversion(new Conversions("4.0", R.drawable.c_minus, "69.5 - 72.5", 1.7));
            db.addConversion(new Conversions("4.0", R.drawable.d_plus, "67.5 - 69.5", 1.3));
            db.addConversion(new Conversions("4.0", R.drawable.d, "62.5 - 67.5", 1.0));
            db.addConversion(new Conversions("4.0", R.drawable.d_minus, "59.5 - 62.5", 0.5));
            db.addConversion(new Conversions("4.0", R.drawable.f, "0.0 - 59.5", 0.0));

            db.addConversion(new Conversions("Custom", R.drawable.a_plus, "97.5 - 100.0", 4.0));
            db.addConversion(new Conversions("Custom", R.drawable.a, "93.0 - 97.5", 4.0));
            db.addConversion(new Conversions("Custom", R.drawable.a_minus, "89.5 - 93.0", 3.7));
            db.addConversion(new Conversions("Custom", R.drawable.b_plus, "87.5 - 89.5", 3.3));
            db.addConversion(new Conversions("Custom", R.drawable.b, "82.5 - 87.5", 3.0));
            db.addConversion(new Conversions("Custom", R.drawable.b_minus, "79.5 - 82.5", 2.7));
            db.addConversion(new Conversions("Custom", R.drawable.c_plus, "77.5 - 79.5", 2.3));
            db.addConversion(new Conversions("Custom", R.drawable.c, "72.5 - 77.5", 2.0));
            db.addConversion(new Conversions("Custom", R.drawable.c_minus, "69.5 - 72.5", 1.7));
            db.addConversion(new Conversions("Custom", R.drawable.d_plus, "67.5 - 69.5", 1.3));
            db.addConversion(new Conversions("Custom", R.drawable.d, "62.5 - 67.5", 1.0));
            db.addConversion(new Conversions("Custom", R.drawable.d_minus, "59.5 - 62.5", 0.5));
            db.addConversion(new Conversions("Custom", R.drawable.f, "0.0 - 59.5", 0.0));

            emojiState = true;
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putBoolean("firstTime", true);
            editor2.apply();
        }
    }
}