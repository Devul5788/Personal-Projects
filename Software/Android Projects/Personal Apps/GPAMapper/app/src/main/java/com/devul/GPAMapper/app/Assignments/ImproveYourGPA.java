package com.devul.GPAMapper.app.Assignments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.devul.GPAMapper.app.Adapters.CategoryListAdapter;
import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.Categories.CategoriesViewer;
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
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ImproveYourGPA extends AppCompatActivity {

    TextView requiredGrade;
    TextInputLayout desiredGrade;
    Button category;
    FloatingActionButton backButton;
    DatabaseHandler db;
    List<Categories> categories;
    ListView cList;
    TextView message, title;
    ArrayList<Categories> names = new ArrayList<>();
    CategoryListAdapter ca;
    int subjectID;
    Subjects subject;
    DrawerLayout dLayout;
    Fragment frag = null;
    ConstraintLayout CL;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_your_gpa);
        desiredGrade = findViewById(R.id.dGPA);
        requiredGrade = findViewById(R.id.requiredGrade);
        category = findViewById(R.id.category_IYG);
        CL = findViewById(R.id.CL);
        backButton = findViewById(R.id.floatingActionMenu7);

        prefs = ImproveYourGPA.this.getSharedPreferences("prefs", MODE_PRIVATE);

        CL.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        db = new DatabaseHandler(ImproveYourGPA.this);
        subject = getIntent().getParcelableExtra("subject");

        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        setNavigationDrawer(); // call method

        categories = db.getAllCategories();
        int f;

        if (categories.size() > 0) {
            for (int k = 0; k < categories.size(); k++) {
                if (categories.get(k).getPercentWeightage() > 0 && categories.get(k).getSubjectID() == subject.getID()) {
                    f = k;
                    Categories ca = categories.get(f);
                    category.setText(ca.getCatName());
                    break;
                }
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ImproveYourGPA.this, AssignmentsViewer.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Categories> cat = db.getAllCategories();
                int num = 0;

                for (Categories c : cat) {
                    if (c.getSubjectID() == subject.getID() && c.getPercentWeightage() > 0) {
                        num++;
                    }
                }

                if (num > 0) {
                    alertDialog1();
                } else {
                    alertDialog2();
                }
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

    private boolean validateDesiredGrade() {
        String dGrade = Objects.requireNonNull(desiredGrade.getEditText()).getText().toString().trim();

        if (dGrade.isEmpty()) {
            desiredGrade.setError("Field Cant Be Empty!");
            return false;
        } else {
            double desiredG = Double.parseDouble(dGrade);
            if ((desiredG < 0) || (desiredG > 100)) {
                desiredGrade.setError("Make sure the value is between 0 and 100!");
                return false;
            } else {
                List<Assignments> assignments = db.getAllAssignments(false, "", "");

                int num = 0;
                for (Assignments as : assignments) {
                    if (as.getSubjectID() == subject.getID()) {
                        num++;
                    }
                }

                if (num == 0) {
                    desiredGrade.setError("Make sure assignments are present in the subject, to do this calculation!");
                    return false;
                } else {
                    desiredGrade.setError(null);
                    return true;
                }
            }
        }
    }

    public void confirmInput(View v) {
        Objects.requireNonNull(desiredGrade.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateDesiredGrade()) {
                    return;
                }
                if (!desiredGrade.getEditText().getText().toString().trim().isEmpty()) {
                    doCalculations();
                }
            }
        });
    }

    public void doCalculations() {
        categories = db.getAllCategories();
        int catPercent = 0;

        for (Categories c : categories) {
            if (c.getSubjectID() == subject.getID() && c.getCatName().equals(category.getText().toString())) {
                catPercent = c.getPercentWeightage();
            }
        }

        double dGrade = Double.parseDouble(Objects.requireNonNull(desiredGrade.getEditText()).getText().toString().trim());
        GPACalculations c = new GPACalculations();
        Double requiredG = c.getScoreRequired(db, subject, dGrade, catPercent, ImproveYourGPA.this);

        String percent = "" + requiredG + "%";
        requiredGrade.setText(percent);
    }

    public void alertDialog1() {
        LayoutInflater inflater = ImproveYourGPA.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.categories_list_view, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ImproveYourGPA.this);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setPositiveButton("Add Category", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ImproveYourGPA.this, CategoriesViewer.class);
                intent.putExtra("subjectID", "" + subjectID);
                startActivity(intent);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        cList = dialogView.findViewById(R.id.mylistview);
        final AlertDialog b = dialogBuilder.create();

        showCategoriesList(db);


        cList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categories c = (Categories) cList.getItemAtPosition(position);
                String catN = c.getCatName();
                category.setText(catN);
                if (!Objects.requireNonNull(desiredGrade.getEditText()).getText().toString().trim().isEmpty()) {
                    doCalculations();
                }
                b.cancel();
            }
        });

        cList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = ImproveYourGPA.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ImproveYourGPA.this);
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.simpleTitle);
                message = dialogView.findViewById(R.id.message1);

                title.setText("Delete Category");

                message.setText("Are you sure you want to delete this item?");

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Categories slctd = (Categories) parent.getItemAtPosition(position);
                        db.deleteCategory(slctd);
                        List<Categories> categories = db.getAllCategories();
                        int num = 0;

                        for (Categories c : categories) {
                            if (c.getSubjectID() == subjectID && c.getPercentWeightage() > 0) {
                                num++;
                            }
                        }

                        if (num == 0) {
                            alertDialog2();
                        } else {
                            showCategoriesList(db);
                        }
                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final android.app.AlertDialog b = dialogBuilder.create();
                b.show();
                return true;
            }
        });
        b.show();
    }

    public void alertDialog2() {
        LayoutInflater inflater = ImproveYourGPA.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ImproveYourGPA.this);
        dialogBuilder.setView(dialogView);

        title = dialogView.findViewById(R.id.simpleTitle);
        message = dialogView.findViewById(R.id.message1);

        title.setText("Add Category");
        title.setBackgroundColor(getResources().getColor(R.color.yellowCustom));

        message.setText("Make sure you have added categories with respective percent weightages to add an assignment");

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ImproveYourGPA.this, CategoriesViewer.class);
                intent.putExtra("subject", subject);
                startActivity(intent);
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

    public void showCategoriesList(DatabaseHandler db) {
        names.clear();
        List<Categories> categories = db.getAllCategories();
        if (categories.size() != 0) {
            for (Categories c : categories) {
                if (c.getPercentWeightage() > 0 && c.getSubjectID() == subject.getID()) {
                    names.add(c);
                }
            }
            ca = new CategoryListAdapter(ImproveYourGPA.this, names);
            cList.setAdapter(ca);
        } else {
            names.clear();
            cList.setAdapter(null);
        }
    }

    private void setNavigationDrawer() {
        dLayout = findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = findViewById(R.id.navigation); // initiate a Navigation View
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open, R.string.close);
        dLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
                int itemId = menuItem.getItemId(); // get selected menu item's id

                if (itemId == R.id.mainPage_item) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    startActivity(new Intent(ImproveYourGPA.this, DataInitializer.class));
                }
                if (itemId == R.id.scores_item) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    startActivity(new Intent(ImproveYourGPA.this, SubjectsViewer.class));
                }
                if (itemId == R.id.yearsAndSemesters_item) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    frag = new YearsViewer();
                }
                if (itemId == R.id.conversionTable) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    frag = new ConversionTablesViewer();
                }
                if (itemId == R.id.scoreConverter) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    frag = new ScoresConvertorViewer();
                }
                if (itemId == R.id.scoresTimeline) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    frag = new TimeLineViewer();
                }
                if (itemId == R.id.settings) {
                    CL.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                    startActivity(new Intent(ImproveYourGPA.this, AppSettings.class));
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
        Intent i = new Intent(this, AssignmentsViewer.class);
        i.putExtra("subject", subject);
        startActivity(i);
    }
}
