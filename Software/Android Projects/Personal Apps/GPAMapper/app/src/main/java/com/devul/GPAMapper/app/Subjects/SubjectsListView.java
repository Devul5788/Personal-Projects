package com.devul.GPAMapper.app.Subjects;

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

import com.devul.GPAMapper.app.Adapters.SubjectAdapterWithEmotions;
import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.Assignments.AssignmentsViewer;
import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.Other.DataInitializer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.RecyclerView.RecyclerItemTouchHelperSWE;
import com.devul.GPAMapper.app.RecyclerView.RecyclerTouchListener;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Years.Years;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class SubjectsListView extends Fragment implements RecyclerItemTouchHelperSWE.RecyclerItemTouchHelperListener {

    public static String filter, filterS;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton addSubject, emptySubjects, showReactions, setFilters;
    EditText subjectName;
    ArrayList<Subjects> al = new ArrayList<>();
    RecyclerView lst;
    SubjectAdapterWithEmotions my;
    TextView title, message;
    List<Subjects> subjects;
    List<Assignments> assignments;
    List<Categories> categories;
    List<Years> years;
    List<Semesters> semesters;
    List<Semesters> selectedSemesters;
    DatabaseHandler db;
    CoordinatorLayout coordinatorLayout;
    int averageSemesterGradeLG, averageFeelingNumber;
    RecyclerView.LayoutManager layoutManager;
    Spinner filters, filterSeq, year, semester;
    Years yr;
    Semesters sm;
    Subjects slctd;
    SharedPreferences prefs;

    public SubjectsListView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_subjects_list_view, container, false);

        prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        addSubject = myview.findViewById(R.id.addSubjectItem);
        emptySubjects = myview.findViewById(R.id.deleteAllSubjectsItem);
        showReactions = myview.findViewById(R.id.showReactionItem);
        setFilters = myview.findViewById(R.id.setFiltersItem);
        floatingActionMenu = myview.findViewById(R.id.floatingActionMenu);
        lst = myview.findViewById(R.id.showSubjects);
        coordinatorLayout = myview.findViewById(R.id.coordinatorLayout6);
        year = myview.findViewById(R.id.year);
        semester = myview.findViewById(R.id.semester);
        layoutManager = new LinearLayoutManager(getActivity());
        db = new DatabaseHandler(getActivity());

        if (filter == null) {
            filter = "";
        }
        if (filterS == null) {
            filterS = "";
        }

        lst.setItemAnimator(new DefaultItemAnimator());
        lst.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSWE(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(lst);

        floatingActionMenu.setVisibility(View.VISIBLE);
        floatingActionMenu.setEnabled(true);
        floatingActionMenu.close(true);
        averageSemesterGradeLG = R.drawable.no_data_b;
        averageFeelingNumber = 7;

        yr = new Years();
        sm = new Semesters();

        years = db.getAllYears(false, "", "");
        List<String> yearString = new ArrayList<>();

        for (Years yer : years) {
            yearString.add(yer.getYear());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, yearString);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(dataAdapter);

        years = db.getAllYears(false, "", "");
        semesters = db.getAllSemesters(false, "", "");

        showSubjects(db, "", "");

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year.setSelection(i);

                semesters = db.getAllSemesters(false, "", "");
                years = db.getAllYears(false, "", "");

                for (Years yrs : years) {
                    if (yrs.getYear().equals(year.getSelectedItem().toString())) {
                        yr = yrs;
                    }
                }

                selectedSemesters = new ArrayList<>();
                List<String> semestersString = new ArrayList<>();

                for (Semesters sm : semesters) {
                    if (sm.getYearID() == yr.getID()) {
                        semestersString.add(sm.getSemesterName());
                        selectedSemesters.add(sm);
                    }
                }

                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, semestersString);

                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                semester.setAdapter(dataAdapter3);
                semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        semester.setSelection(i);

                        for (Semesters se : selectedSemesters) {
                            if (se.getSemesterName().equals(semester.getSelectedItem().toString())) {
                                sm = se;
                            }
                        }

                        showSubjects(db, "", "");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        showSubjects(db, filter, filterS);

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

        showReactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showReactions.getLabelText().equals("Show Reactions")) {
                    showReactions.setLabelText("Hide Reactions");
                    DataInitializer.emojiState = true;
                    floatingActionMenu.close(true);
                } else if (showReactions.getLabelText().equals("Hide Reactions")) {
                    showReactions.setLabelText("Show Reactions");
                    DataInitializer.emojiState = false;
                    floatingActionMenu.close(true);
                }

                showSubjects(db, filter, filterS);
            }
        });

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_alert_dialog, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);
                subjectName = dialogView.findViewById(R.id.name);
                title = dialogView.findViewById(R.id.title);

                title.setText("Add Subject");

                dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Subjects> subjects = db.getAllSubjects(false, "", "");
                        int num = 0;
                        for (Subjects s : subjects) {
                            if (s.getSubject().toLowerCase().trim().equals(subjectName.getText().toString().toLowerCase().trim()) && s.getSemesterID() == sm.getID()) {
                                num++;
                            }
                        }
                        if (num == 0) {
                            db.addSubject(new Subjects(sm.getID(), subjectName.getText().toString(), 0.0, R.drawable.no_data_b));
                            showSubjects(db, filter, filterS);
                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "A subject with the same name" +
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

                final AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        emptySubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.simpleTitle);
                message = dialogView.findViewById(R.id.message1);

                title.setText("Delete Subjects");
                title.setBackgroundColor(getResources().getColor(R.color.redCustom));

                message.setText("Are you sure you want to delete all subjects?");

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        floatingActionMenu.close(true);

                        subjects = db.getAllSubjects(false, "", "");
                        assignments = db.getAllAssignments(false, "", "");
                        categories = db.getAllCategories();

                        for (Subjects s : subjects) {
                            if (s.getSemesterID() == sm.getID()) {
                                db.deleteSubject(s);
                                for (Assignments a : assignments) {
                                    if (a.getSubjectID() == s.getID()) {
                                        db.deleteAssignment(a);
                                    }
                                }
                                for (Categories c : categories) {
                                    if (c.getSubjectID() == c.getID()) {
                                        db.deleteCategory(c);
                                    }
                                }
                            }
                        }
                        showSubjects(db, filter, filterS);
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

        lst.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), lst, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                floatingActionMenu.close(true);
                Subjects slctd = my.getItem(position);
                Intent i = new Intent(getActivity(), AssignmentsViewer.class);
                i.putExtra("subject", slctd);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                floatingActionMenu.close(true);

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_alert_dialog, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);
                subjectName = dialogView.findViewById(R.id.name);
                title = dialogView.findViewById(R.id.title);
                slctd = my.getItem(position);

                subjectName.setText(slctd.getSubject());
                title.setText("Edit Subject");

                dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        subjects = db.getAllSubjects(false, "", "");
                        for (Subjects s : subjects) {
                            if (s.getID() == slctd.getID()) {
                                Subjects sb = new Subjects(s.getID(), s.getSemesterID(), subjectName.getText().toString().trim(), s.getGradeP(), s.getGrade(), s.getAverageFeelingNumber());
                                db.updateSubject(sb);
                                showSubjects(db, filter, filterS);
                            }
                        }
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Subject update to " + slctd.getSubject(), Snackbar.LENGTH_SHORT);
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
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.filters_setter, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.AlertDialogTitle);
                filters = dialogView.findViewById(R.id.filters);
                filterSeq = dialogView.findViewById(R.id.filtersAD);
                title.setText("Filter Subjects");
                title.setBackgroundColor(getResources().getColor(R.color.purpleCustom));

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.filter, android.R.layout.simple_spinner_item);
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
                        showSubjects(db, filter, filterS);
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

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        final RecyclerView.ViewHolder view = viewHolder;

        if (viewHolder instanceof SubjectAdapterWithEmotions.MyViewHolder) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
            final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
            dialogBuilder.setView(dialogView);

            title = dialogView.findViewById(R.id.simpleTitle);
            message = dialogView.findViewById(R.id.message1);

            List<Subjects> sub = db.getAllSubjects(false, "", "");

            final Subjects deletedItem = sub.get(view.getAdapterPosition());
            final String name = deletedItem.getSubject();

            title.setText("Delete Subject");
            title.setBackgroundColor(getResources().getColor(R.color.redCustom));

            message.setText("Are you sure you want to delete " + name);

            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    db.deleteSubject(deletedItem);
                    List<Assignments> assignments = db.getAllAssignments(false, "", "");


                    for (Assignments a : assignments) {
                        if (a.getSubjectID() == deletedItem.getID()) {
                            db.deleteAssignment(a);
                        }
                    }

                    List<Categories> categories = db.getAllCategories();

                    for (Categories c : categories) {
                        if (c.getSubjectID() == deletedItem.getID()) {
                            db.deleteCategory(c);
                        }
                    }
                    my.removeItem(view.getAdapterPosition());

                    showSubjects(db, filter, filterS);

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
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
        }
    }

    public void showSubjects(DatabaseHandler db, String fName, String filSeq) {
        al.clear();

        List<Subjects> subject = db.getAllSubjects(true, fName, filSeq);
        List<Assignments> assignments = db.getAllAssignments(false, "", "");
        averageFeelingNumber = 0;

        if (subject.size() != 0) {
            for (Subjects s : subject) {
                if (s.getSemesterID() == sm.getID()) {
                    int num = 0;
                    for (Assignments a : assignments) {
                        if (a.getSubjectID() == s.getID() && a.getScore() > 0) {
                            num++;
                            break;
                        }
                    }

                    if (num > 0) {
                        GPACalculations c = new GPACalculations();

                        double averageSubjectGrade = c.getAverageSubjectGrade(s, db, getActivity());

                        if (averageSubjectGrade > 0.0) {
                            averageSemesterGradeLG = c.getAverageGradeLG(averageSubjectGrade, db, getActivity());
                            averageFeelingNumber = c.getAverageFeelingNumber(db, s.getID(), getActivity());

                            Subjects newSubject = new Subjects(s.getID(), s.getSemesterID(), s.getSubject(), averageSubjectGrade,
                                    averageSemesterGradeLG, averageFeelingNumber);

                            db.updateSubject(newSubject);
                            al.add(newSubject);
                        } else {
                            averageFeelingNumber = c.getAverageFeelingNumber(db, s.getID(), getActivity());

                            Subjects newSubject = new Subjects(s.getID(), s.getSemesterID(), s.getSubject(), 0.0,
                                    R.drawable.no_data_b, averageFeelingNumber);

                            db.updateSubject(newSubject);
                            al.add(newSubject);
                        }
                    } else {
                        Subjects newSubject = new Subjects(s.getID(), s.getSemesterID(), s.getSubject(), 0.0,
                                R.drawable.no_data_b, averageFeelingNumber);
                        al.add(newSubject);
                    }
                } else {
                    int num = 0;
                    for (Assignments a : assignments) {
                        if (a.getSubjectID() == s.getID() && a.getScore() > 0) {
                            num++;
                            break;
                        }
                    }

                    if (num > 0) {
                        GPACalculations c = new GPACalculations();

                        double averageSubjectGrade = c.getAverageSubjectGrade(s, db, getActivity());

                        if (averageSubjectGrade > 0.0) {
                            averageSemesterGradeLG = c.getAverageGradeLG(averageSubjectGrade, db, getActivity());
                            averageFeelingNumber = c.getAverageFeelingNumber(db, s.getID(), getActivity());

                            Subjects newSubject = new Subjects(s.getID(), s.getSemesterID(), s.getSubject(), averageSubjectGrade,
                                    averageSemesterGradeLG, averageFeelingNumber);

                            db.updateSubject(newSubject);
                        } else {
                            averageFeelingNumber = c.getAverageFeelingNumber(db, s.getID(), getActivity());

                            Subjects newSubject = new Subjects(s.getID(), s.getSemesterID(), s.getSubject(), 0.0,
                                    R.drawable.no_data_b, averageFeelingNumber);

                            db.updateSubject(newSubject);
                        }
                    }
                }
            }

            if (DataInitializer.emojiState) {
                my = new SubjectAdapterWithEmotions(getActivity(), al, db);
                lst.setLayoutManager(layoutManager);
                lst.setAdapter(my);
                my.notifyDataSetChanged();
            } else {
                my = new SubjectAdapterWithEmotions(getActivity(), al, db);
                lst.setLayoutManager(layoutManager);
                lst.setAdapter(my);
                my.notifyDataSetChanged();
            }
        } else {
            al.clear();
            lst.setAdapter(null);
        }
    }
}
