package com.devul.GPAMapper.app.Years;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.devul.GPAMapper.app.Adapters.YearAndSemesterAdapter;
import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.RecyclerView.RecyclerItemTouchHelperYS;
import com.devul.GPAMapper.app.RecyclerView.RecyclerTouchListener;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Semesters.SemestersViewer;
import com.devul.GPAMapper.app.Settings.AppSettings;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class YearsViewer extends Fragment implements RecyclerItemTouchHelperYS.RecyclerItemTouchHelperListener {

    public static String filter, filterS;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton addYear, setFilters;
    EditText yearName;
    ArrayList<Years> al = new ArrayList<>();
    RecyclerView lst;
    YearAndSemesterAdapter my;
    TextView title, message;
    List<Years> years;
    List<Semesters> semesters;
    List<Subjects> subjects;
    List<Assignments> assignments;
    List<Categories> categories;
    DatabaseHandler db;
    CoordinatorLayout coordinatorLayout;
    Spinner filters, filterSeq;
    RecyclerView.LayoutManager layoutManager;
    Years slctd;
    SharedPreferences prefs;

    public YearsViewer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.fragment_years_viewer, container, false);
        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("prefs", MODE_PRIVATE);
        addYear = myview.findViewById(R.id.addYearsItem);
        setFilters = myview.findViewById(R.id.setFiltersItem);
        floatingActionMenu = myview.findViewById(R.id.floatingActionMenu);
        lst = myview.findViewById(R.id.showYears);
        coordinatorLayout = myview.findViewById(R.id.coordinatorLayout6);
        db = new DatabaseHandler(getActivity());

        filter = "";
        filterS = "";

        layoutManager = new LinearLayoutManager(getActivity());
        lst.setItemAnimator(new DefaultItemAnimator());
        lst.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Years");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        floatingActionMenu.setEnabled(true);
        floatingActionMenu.close(true);

        showYears(db, filter, filterS);
        years = db.getAllYears(false, "", "");

        if (years.size() > 1) {
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperYS(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(lst);

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };
            // attaching the touch helper to recycler view
            new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(lst);
        }

        addYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_alert_dialog, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);
                yearName = dialogView.findViewById(R.id.name);
                title = dialogView.findViewById(R.id.title);

                title.setText("Add Year");

                dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        years = db.getAllYears(false, "", "");
                        int num = 0;
                        int num2 = 0;
                        for (Years y : years) {
                            if (y.getYear().toLowerCase().trim().equals(yearName.getText().toString().toLowerCase().trim())) {
                                num++;
                            }
                            num2++;
                        }
                        if (num == 0) {
                            if (num2 == 1) {
                                Years yrs = new Years(yearName.getText().toString(), 0.0);
                                db.addYear(yrs);
                                showYears(db, filter, filterS);
                                AppSettings.editor.putString("yearName", yearName.getText().toString());
                                AppSettings.editor.putInt("year", yrs.getID());

                            } else {
                                db.addYear(new Years(yearName.getText().toString(), 0.0));
                                showYears(db, filter, filterS);
                            }

                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "A year with the same name" +
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

                showYears(db, filter, filterS);
                final AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        lst.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), lst, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                floatingActionMenu.close(true);
                Years slctd = my.getItemY(position);
                Intent i = new Intent(getActivity(), SemestersViewer.class);
                i.putExtra("yearID", slctd.getID());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, final int position) {
                floatingActionMenu.close(true);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_alert_dialog, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);
                yearName = dialogView.findViewById(R.id.name);
                title = dialogView.findViewById(R.id.title);
                slctd = my.getItemY(position);

                yearName.setText(slctd.getYear());
                title.setText("Edit Year");

                dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        years = db.getAllYears(false, "", "");
                        for (Years y : years) {
                            if (y.getID() == slctd.getID()) {
                                Years yr = new Years(y.getID(), yearName.getText().toString().trim(), y.getPercent());
                                db.updateYear(yr);
                                showYears(db, filter, filterS);
                            }
                        }
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Year update to " + slctd.getYear(), Snackbar.LENGTH_SHORT);
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
                LayoutInflater inflater = YearsViewer.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.filters_setter, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.AlertDialogTitle);
                filters = dialogView.findViewById(R.id.filters);
                filterSeq = dialogView.findViewById(R.id.filtersAD);
                title.setText("Filter Years");
                title.setBackgroundColor(getResources().getColor(R.color.darkGreenCustom));

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.filterYS, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                filters.setAdapter(adapter1);

                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.filterSeq, android.R.layout.simple_spinner_item);
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
                        showYears(db, filter, filterS);
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
        });

        return myview;
    }

    public void showYears(DatabaseHandler db, String fName, String filSeq) {
        al.clear();

        years = db.getAllYears(true, fName, filSeq);
        semesters = db.getAllSemesters(false, "", "");

        double averageSemesterGradeP;
        int averageSemesterGradeLG;
        double averageYearGradeP;

        db = new DatabaseHandler(getActivity());
        GPACalculations c = new GPACalculations();

        if (years.size() > 0) {
            for (Years yr : years) {
                for (Semesters s : semesters) {
                    List<Subjects> subjects = db.getAllSubjects(false, "", "");
                    if (subjects.size() > 0) {
                        if (s.getYearID() == yr.getID()) {
                            averageSemesterGradeP = c.getAverageSemesterGrade(s.getID(), subjects, db, getActivity());
                            averageSemesterGradeLG = c.getAverageGradeLG(averageSemesterGradeP, db, getActivity());
                            Semesters sm = new Semesters(s.getID(), s.getYearID(), s.getSemesterName(),
                                    averageSemesterGradeP, averageSemesterGradeLG);
                            db.updateSemester(sm);
                        }
                    }
                }
                averageYearGradeP = c.getAverageYearGrade(yr.getID(), semesters, db, getActivity());
                Years yrs = new Years(yr.getID(), yr.getYear(), GPACalculations.round(averageYearGradeP, prefs.getInt("decimalPlace", 0)));
                db.updateYear(yrs);
                al.add(yrs);
            }

            my = new YearAndSemesterAdapter(getActivity(), true, al, db);
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
        // get the removed item name to display it in snack bar

        final RecyclerView.ViewHolder view = viewHolder;
        final int pos = position;


        years = db.getAllYears(false, "", "");
        semesters = db.getAllSemesters(false, "", "");
        subjects = db.getAllSubjects(false, "", "");
        assignments = db.getAllAssignments(false, "", "");
        categories = db.getAllCategories();

        if (years.size() > 1) {
            final String name = years.get(view.getAdapterPosition()).getYear();

            final Years deletedItem = my.getItemY(pos);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
            final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
            dialogBuilder.setView(dialogView);

            title = dialogView.findViewById(R.id.simpleTitle);
            message = dialogView.findViewById(R.id.message1);

            title.setText("Delete Year");
            title.setBackgroundColor(getResources().getColor(R.color.redCustom));

            message.setText("Are you sure you want to delete " + name);

            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int i = 0;

                    if (deletedItem.getID() == prefs.getInt("year", 0)) {
                        for (Years y : years) {
                            if (y.getID() != prefs.getInt("year", 0)) {
                                AppSettings.editor.putInt("year", y.getID());
                                i = y.getID();
                                break;
                            }
                        }

                        for (Semesters s : semesters) {
                            if (s.getYearID() == i) {
                                AppSettings.editor.putInt("semester", s.getID());
                                break;
                            }
                        }
                    }

                    db.deleteYear(deletedItem);

                    for (Semesters s : semesters) {
                        if (s.getYearID() == deletedItem.getID()) {
                            db.deleteSemester(s);
                            for (Subjects sm : subjects) {
                                if (sm.getSemesterID() == s.getID()) {
                                    db.deleteSubject(sm);
                                    for (Assignments ab : assignments) {
                                        if (ab.getSubjectID() == sm.getID()) {
                                            db.deleteAssignment(ab);
                                        }
                                    }

                                    for (Categories c : categories) {
                                        if (c.getSubjectID() == sm.getID()) {
                                            db.deleteCategory(c);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    showYears(db, "", "");
                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, name + " removed from cart! Please remember to change default semester and year settings!", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            final android.app.AlertDialog b = dialogBuilder.create();
            b.show();
        } else {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Operation Not Successful. Make sure you have 1 or more years!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
