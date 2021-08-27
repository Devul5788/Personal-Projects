package com.devul.GPAMapper.app.Semesters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devul.GPAMapper.app.Adapters.YearAndSemesterAdapter;
import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.ConversionTables.ConversionTablesViewer;
import com.devul.GPAMapper.app.Other.DataInitializer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.RecyclerView.RecyclerItemTouchHelperYS;
import com.devul.GPAMapper.app.RecyclerView.RecyclerTouchListener;
import com.devul.GPAMapper.app.ScoresConvertor.ScoresConvertorViewer;
import com.devul.GPAMapper.app.Settings.AppSettings;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.devul.GPAMapper.app.Subjects.SubjectsViewer;
import com.devul.GPAMapper.app.Timeline.utils.TimeLineViewer;
import com.devul.GPAMapper.app.Years.Years;
import com.devul.GPAMapper.app.Years.YearsViewer;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SemestersViewer extends AppCompatActivity implements RecyclerItemTouchHelperYS.RecyclerItemTouchHelperListener {

    public static String filter, filterS;
    DrawerLayout dLayout;
    Fragment frag = null;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton addSemester, setFilters;
    EditText semesterName;
    ArrayList<Semesters> al = new ArrayList<>();
    RecyclerView lst;
    YearAndSemesterAdapter my;
    TextView title, message;
    EditText semesterName2;
    List<Years> years;
    List<Semesters> semesters;
    List<Subjects> subjects;
    List<Assignments> assignments;
    List<Categories> categories;
    DatabaseHandler db;
    CoordinatorLayout coordinatorLayout;
    Spinner filters, filterSeq;
    RecyclerView.LayoutManager layoutManager;
    int yearID;
    LinearLayout LL2;
    FloatingActionButton backButton;
    Semesters slctd;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters_viewer);

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        yearID = prefs.getInt("year", 0);

        yearID = getIntent().getExtras().getInt("yearID");

        addSemester = findViewById(R.id.addSemesterItem);
        setFilters = findViewById(R.id.setFiltersItem);
        floatingActionMenu = findViewById(R.id.floatingActionMenu);
        lst = findViewById(R.id.showSemesters);
        coordinatorLayout = findViewById(R.id.coordinatorLayout10);
        db = new DatabaseHandler(SemestersViewer.this);
        LL2 = findViewById(R.id.LL2);
        backButton = findViewById(R.id.floatingActionMenu4);

        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        setNavigationDrawer(); // call method

        filter = "";
        filterS = "";

        layoutManager = new LinearLayoutManager(SemestersViewer.this);
        lst.setItemAnimator(new DefaultItemAnimator());
        lst.addItemDecoration(new DividerItemDecoration(SemestersViewer.this, LinearLayoutManager.VERTICAL));

        floatingActionMenu.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        LL2.setVisibility(View.VISIBLE);
        floatingActionMenu.setEnabled(true);
        floatingActionMenu.close(true);

        showSemesters(db, filter, filterS);
        semesters = db.getAllSemesters(false, "", "");

        if (semesters.size() > 1) {
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperYS(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(lst);
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    // Row is swiped from recycler view
                    // remove it from adapter
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };

            // attaching the touch helper to recycler view
            new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(lst);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.setVisibility(View.GONE);
                backButton.setVisibility(View.GONE);
                floatingActionMenu.close(true);
                LL2.setVisibility(View.GONE);
                frag = new YearsViewer();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame2, frag); // replace a Fragment with Frame Layout
                transaction.commit(); // commit the changes
            }
        });

        addSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                LayoutInflater inflater = SemestersViewer.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_alert_dialog, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SemestersViewer.this);
                dialogBuilder.setView(dialogView);
                semesterName = dialogView.findViewById(R.id.name);
                title = dialogView.findViewById(R.id.title);

                title.setText("Add Semester");

                dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        years = db.getAllYears(false, "", "");
                        int num = 0;
                        int num2 = 2;
                        for (Semesters s : semesters) {
                            if (s.getSemesterName().toLowerCase().trim().equals(semesterName.getText().toString().toLowerCase().trim()) && s.getYearID() == yearID) {
                                num++;
                            }
                            if (s.getYearID() == yearID) {
                                num2++;
                            }
                        }
                        if (num == 0) {
                            if (num2 == 1) {
                                Semesters sms = new Semesters(yearID, semesterName.getText().toString(), 0.0, R.drawable.no_data_b);
                                db.addSemester(sms);
                                finish();
                                startActivity(getIntent());
                                showSemesters(db, filter, filterS);
                                AppSettings.editor.putString("semName", semesterName.getText().toString());
                                AppSettings.editor.putInt("semester", sms.getID());

                            } else {
                                db.addSemester(new Semesters(yearID, semesterName.getText().toString(), 0.0, R.drawable.no_data_b));
                                finish();
                                startActivity(getIntent());
                                showSemesters(db, filter, filterS);
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "A Semester with the same name" +
                                    " already exists.", Snackbar.LENGTH_SHORT);
                            View snackView = snackbar.getView();
                            TextView textView = snackView.findViewById(R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                            snackbar.show();
                        }


                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                showSemesters(db, filter, filterS);
                final AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        lst.addOnItemTouchListener(new RecyclerTouchListener(SemestersViewer.this, lst, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, final int position) {
                floatingActionMenu.close(true);

                LayoutInflater inflater = SemestersViewer.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_alert_dialog, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SemestersViewer.this);
                dialogBuilder.setView(dialogView);
                semesterName2 = dialogView.findViewById(R.id.name);
                title = dialogView.findViewById(R.id.title);
                slctd = my.getItemS(position);

                semesterName2.setText(slctd.getSemesterName());
                title.setText("Edit Semester");

                dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        semesters = db.getAllSemesters(false, "", "");
                        for (Semesters s : semesters) {
                            if (s.getID() == slctd.getID()) {
                                Semesters sm = new Semesters(s.getID(), s.getYearID(), semesterName2.getText().toString().trim(), s.getPercent(), s.getGrade());
                                db.updateSemester(sm);
                                showSemesters(db, filter, filterS);
                            }
                        }
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Semester update to " + slctd.getSemesterName(), Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        TextView textView = snackView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                        snackbar.show();
                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                final AlertDialog b = dialogBuilder.create();
                b.show();
            }
        }));

        setFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = SemestersViewer.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.filters_setter, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SemestersViewer.this);
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.AlertDialogTitle);
                filters = dialogView.findViewById(R.id.filters);
                filterSeq = dialogView.findViewById(R.id.filtersAD);
                title.setText("Filter Semesters");
                title.setBackgroundColor(getResources().getColor(R.color.darkGreenCustom));

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(SemestersViewer.this, R.array.filterYS, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                filters.setAdapter(adapter1);

                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(SemestersViewer.this, R.array.filterSeq, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                filterSeq.setAdapter(adapter2);

                filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        filter = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                filterSeq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        filterS = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dialogBuilder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        floatingActionMenu.close(true);
                        showSemesters(db, filter, filterS);
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final AlertDialog b = dialogBuilder.create();
                b.show();
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

    public void showSemesters(DatabaseHandler db, String fName, String filSeq) {
        al.clear();

        semesters = db.getAllSemesters(true, fName, filSeq);

        double averageSemesterGradeP;
        int averageSemesterGradeLG;

        db = new DatabaseHandler(SemestersViewer.this);
        GPACalculations c = new GPACalculations();

        if (semesters.size() > 0) {
            for (Semesters s : semesters) {
                if (s.getYearID() == yearID) {
                    List<Subjects> subjects = db.getAllSubjects(false, "", "");
                    if (subjects.size() > 0) {
                        averageSemesterGradeP = c.getAverageSemesterGrade(s.getID(), subjects, db, SemestersViewer.this);
                        averageSemesterGradeLG = c.getAverageGradeLG(averageSemesterGradeP, db, SemestersViewer.this);
                        Semesters sm = new Semesters(s.getID(), s.getYearID(), s.getSemesterName(),
                                averageSemesterGradeP, averageSemesterGradeLG);
                        db.updateSemester(sm);
                        al.add(sm);
                    } else {
                        al.add(s);
                    }
                }
            }

            my = new YearAndSemesterAdapter(SemestersViewer.this, "", false, al, db);
            lst.setLayoutManager(layoutManager);
            lst.setAdapter(my);
            my.notifyDataSetChanged();
        } else {
            al.clear();
            lst.setAdapter(null);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        semesters = db.getAllSemesters(false, "", "");
        subjects = db.getAllSubjects(false, "", "");
        assignments = db.getAllAssignments(false, "", "");
        categories = db.getAllCategories();

        final String name = semesters.get(viewHolder.getAdapterPosition()).getSemesterName();

        final Semesters deletedItem = my.getItemS(position);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        title = dialogView.findViewById(R.id.simpleTitle);
        message = dialogView.findViewById(R.id.message1);

        title.setText("Delete Semester");
        title.setBackgroundColor(getResources().getColor(R.color.redCustom));

        message.setText("Are you sure you want to delete " + name);

        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (semesters.size() > 1) {
                    if (deletedItem.getID() == prefs.getInt("year", 0)) {
                        for (Semesters s : semesters) {
                            if (s.getYearID() != yearID) {
                                AppSettings.semester = s.getID();
                                break;
                            }
                        }
                    }

                    db.deleteSemester(deletedItem);

                    if (subjects.size() > 0) {
                        for (Subjects s : subjects) {
                            if (s.getSemesterID() == deletedItem.getID()) {
                                db.deleteSubject(s);
                                for (Assignments ab : assignments) {
                                    if (ab.getSubjectID() == s.getID()) {
                                        db.deleteAssignment(ab);
                                    }
                                }

                                for (Categories c : categories) {
                                    if (c.getSubjectID() == s.getID()) {
                                        db.deleteCategory(c);
                                    }
                                }
                            }
                        }
                    }

                    showSemesters(db, "", "");

                    Toast.makeText(SemestersViewer.this, name + " removed from cart! Please remember to change default semester and year settings!", Toast.LENGTH_LONG).show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Operation Not Successful. Make sure you have 1 or more semesters!", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }

                finish();
                startActivity(getIntent());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final android.app.AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void setNavigationDrawer() {
        dLayout = findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = findViewById(R.id.navigation); // initiate a Navigation View
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open, R.string.close);
        dLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Semesters");
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

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
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    startActivity(new Intent(SemestersViewer.this, DataInitializer.class));
                }
                if (itemId == R.id.yearsAndSemesters_item) {
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    frag = new YearsViewer();
                }
                if (itemId == R.id.scores_item) {
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    startActivity(new Intent(SemestersViewer.this, SubjectsViewer.class));
                }
                if (itemId == R.id.conversionTable) {
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    frag = new ConversionTablesViewer();
                }
                if (itemId == R.id.scoreConverter) {
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    frag = new ScoresConvertorViewer();
                }
                if (itemId == R.id.scoresTimeline) {
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    frag = new TimeLineViewer();
                }
                if (itemId == R.id.settings) {
                    floatingActionMenu.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                    LL2.setVisibility(View.GONE);
                    startActivity(new Intent(SemestersViewer.this, AppSettings.class));
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
}
