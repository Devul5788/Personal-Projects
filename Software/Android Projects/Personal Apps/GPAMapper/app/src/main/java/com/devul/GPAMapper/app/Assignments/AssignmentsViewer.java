package com.devul.GPAMapper.app.Assignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devul.GPAMapper.app.ConversionTables.ConversionTablesViewer;
import com.devul.GPAMapper.app.Other.DataInitializer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.ScoresConvertor.ScoresConvertorViewer;
import com.devul.GPAMapper.app.Settings.AppSettings;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.devul.GPAMapper.app.Subjects.SubjectsViewer;
import com.devul.GPAMapper.app.Timeline.utils.TimeLineViewer;
import com.devul.GPAMapper.app.Years.YearsViewer;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
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

@SuppressWarnings("serial")
public class AssignmentsViewer extends AppCompatActivity {

    DrawerLayout dLayout;
    androidx.fragment.app.Fragment frag = null;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    SharedPreferences prefs;
    Subjects subject;
    int subjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_viewer);

        simpleFrameLayout = findViewById(R.id.frame2);
        tabLayout = findViewById(R.id.simpleTabLayout);
        prefs = AssignmentsViewer.this.getSharedPreferences("prefs", MODE_PRIVATE);

        subject = getIntent().getParcelableExtra("subject");
        subjectID = subject.getID();

        tabLayout.setVisibility(View.VISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        setNavigationDrawer(); // call method

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("List"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.list); // set an icon for the

        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Graph"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.bar_chart); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame2, new AssignmentListView());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new AssignmentListView();
                        break;
                    case 1:
                        fragment = new AssignmentGraphView();
                        break;
                }

                DatabaseHandler db = new DatabaseHandler(AssignmentsViewer.this);

                List<Assignments> assignmentsList = db.getAllAssignments(false, "", "");

                int n = 0;

                for (Assignments as : assignmentsList) {
                    if (as.getSubjectID() == subjectID) {
                        n++;
                    }
                }

                if (n > 0) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame2, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
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

        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Your Assignments");
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        if (prefs.getString("userImg", null) != null) {
            ImageView img = navView.getHeaderView(0).findViewById(R.id.profile_image);
            img.setImageURI(Uri.parse(prefs.getString("userImg", null)));
        }

        if (prefs.getString("userName", null) != null) {
            TextView usrName = navView.getHeaderView(0).findViewById(R.id.username);
            usrName.setText(prefs.getString("userName", null));
        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                // check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.mainPage_item) {
                    tabLayout.setVisibility(View.GONE);
                    startActivity(new Intent(AssignmentsViewer.this, DataInitializer.class));
                }
                if (itemId == R.id.scores_item) {
                    tabLayout.setVisibility(View.GONE);
                    startActivity(new Intent(AssignmentsViewer.this, SubjectsViewer.class));
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
                    startActivity(new Intent(AssignmentsViewer.this, AppSettings.class));
                }
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame2, frag); // replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                return false;
            }
        });
    }

    public void onBackPressed() {
        tabLayout.setVisibility(View.GONE);
        startActivity(new Intent(AssignmentsViewer.this, SubjectsViewer.class));
    }
}