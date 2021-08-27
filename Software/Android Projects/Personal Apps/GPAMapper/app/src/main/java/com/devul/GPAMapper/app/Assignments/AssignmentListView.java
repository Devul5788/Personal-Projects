package com.devul.GPAMapper.app.Assignments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.devul.GPAMapper.app.Adapters.AssignmentsAdapter;
import com.devul.GPAMapper.app.Categories.CategoriesViewer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.RecyclerView.RecyclerItemTouchHelperA;
import com.devul.GPAMapper.app.RecyclerView.RecyclerTouchListener;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.devul.GPAMapper.app.Subjects.SubjectsViewer;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentListView extends Fragment implements RecyclerItemTouchHelperA.RecyclerItemTouchHelperListener {

    public static String filter, filterS;
    DrawerLayout dLayout;
    Fragment frag = null;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton backButton;
    FloatingActionButton addAssignment, emptyAssignments, launchCategories, improveGPA, setFilters;
    ArrayList<Assignments> al = new ArrayList<>();
    List<Assignments> assignments = new ArrayList<>();
    List<Assignments> assignments2 = new ArrayList<>();
    Assignments deletedAssignment;
    int deletedIndex;
    LinearLayout LL2;
    RecyclerView lst;
    DatabaseHandler db;
    CoordinatorLayout coordinatorLayout;
    TextView title, message;
    AssignmentsAdapter my;
    Subjects subject;
    Spinner filters, filterSeq;
    RecyclerView.LayoutManager layoutManager;

    public AssignmentListView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_assignment_list_view, container, false);

        subject = getActivity().getIntent().getParcelableExtra("subject");

        addAssignment = myView.findViewById(R.id.addAssignmentsItem);
        emptyAssignments = myView.findViewById(R.id.deleteAllAssignmentsItem);
        launchCategories = myView.findViewById(R.id.showCategories);
        improveGPA = myView.findViewById(R.id.improverYourGPAItem);
        setFilters = myView.findViewById(R.id.setFiltersItem2);
        floatingActionMenu = myView.findViewById(R.id.floatingActionMenu2);
        backButton = myView.findViewById(R.id.floatingActionMenu4);
        lst = myView.findViewById(R.id.showAssignments);
        coordinatorLayout = myView.findViewById(R.id.coordinatorLayout10);
        db = new DatabaseHandler(getActivity());
        LL2 = myView.findViewById(R.id.LL2);

        layoutManager = new LinearLayoutManager(getActivity());
        lst.setItemAnimator(new DefaultItemAnimator());
        lst.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        floatingActionMenu.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        LL2.setVisibility(View.VISIBLE);

        floatingActionMenu.setEnabled(true);
        floatingActionMenu.close(true);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperA(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
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

        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(lst);

        showAssignments(db, filter, filterS);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.setVisibility(View.GONE);
                backButton.setVisibility(View.GONE);
                floatingActionMenu.close(true);
                startActivity(new Intent(getActivity(), SubjectsViewer.class));
            }
        });

        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                Intent i = new Intent(getActivity(), AssignmentAdder.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

        emptyAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.simpleTitle);
                message = dialogView.findViewById(R.id.message1);

                title.setText("Delete All Assignment");
                title.setBackgroundColor(getResources().getColor(R.color.orangeCustom));

                message.setText("Are you sure you want to delete all assignments?");

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        assignments = db.getAllAssignments(true, filter, filterS);

                        for (Assignments as : assignments) {
                            if (as.getSubjectID() == subject.getID()) {
                                db.deleteAssignment(as);
                                assignments2.add(as);
                            }
                        }

                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "All Assignments were deleted.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        for (Assignments asgn : assignments2) {
                                            db.addAssignment(asgn);
                                        }
                                        assignments2.clear();
                                        showAssignments(db, filter, filterS);
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.redCustom));
                        View snackView = snackbar.getView();
                        TextView textView = snackView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                        snackbar.show();

                        showAssignments(db, filter, filterS);
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
                Assignments slctd = my.getItem(position);
                ArrayList<Parcelable> objects = new ArrayList<>();
                objects.add(slctd);
                objects.add(subject);
                Intent i = new Intent(getActivity(), AssignmentEditor.class);
                i.putParcelableArrayListExtra("objects", objects);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        launchCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                Intent i = new Intent(getActivity(), CategoriesViewer.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

        improveGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                Intent i = new Intent(getActivity(), ImproveYourGPA.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

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
                title.setText("Filter Assignments");
                title.setBackgroundColor(getResources().getColor(R.color.yellowCustom));

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.filterA, android.R.layout.simple_spinner_item);
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
                        showAssignments(db, filter, filterS);
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

        return myView;
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

    public void showAssignments(DatabaseHandler db, String f, String FS) {
        al.clear();
        List<Assignments> assignments = db.getAllAssignments(true, f, FS);

        if (assignments.size() != 0) {
            for (Assignments a : assignments) {
                if (a.getSubjectID() == subject.getID()) {
                    al.add(a);
                }
            }
            my = new AssignmentsAdapter(getActivity(), al);
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
        if (viewHolder instanceof AssignmentsAdapter.MyViewHolder) {
            List<Assignments> assignments = db.getAllAssignments(false, "", "");
            String name = assignments.get(viewHolder.getAdapterPosition()).getAssignmentName();

            deletedAssignment = my.getItem(position);
            deletedIndex = position;
            db.deleteAssignment(deletedAssignment);

            showAssignments(db, "", "");
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.addAssignment(deletedAssignment);
                    showAssignments(db, "", "");
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
