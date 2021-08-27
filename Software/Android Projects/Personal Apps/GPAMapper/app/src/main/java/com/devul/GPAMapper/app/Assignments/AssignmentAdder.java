package com.devul.GPAMapper.app.Assignments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.devul.GPAMapper.app.Adapters.CategoryListAdapter;
import com.devul.GPAMapper.app.Adapters.FeelingsAdapter;
import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.Categories.CategoriesViewer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class AssignmentAdder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText assignmentName, scoreRecieved;
    CoordinatorLayout coordinatorLayout;
    TextView title, message;
    Button datePicker, category;
    ImageView feeling, grade;
    ListView cList;
    GridView fGridList;
    CategoryListAdapter ca;
    Categories deleteCat;
    ArrayList<Categories> names = new ArrayList<>();
    int catPercent, feelingNumber;
    List<Assignments> assignments;
    List<Categories> categories;
    AlertDialog b, b2, b4;
    Subjects subject;
    Toolbar toolbar;
    DatabaseHandler db = new DatabaseHandler(AssignmentAdder.this);

    private TextView.OnEditorActionListener editorListner = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_NEXT:
                    break;
                case EditorInfo.IME_ACTION_DONE:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_assignment_adder);

        subject = getIntent().getParcelableExtra("subject");

        toolbar = findViewById(R.id.backToolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Assignment");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AssignmentAdder.this, AssignmentsViewer.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

        assignmentName = findViewById(R.id.assignmentName_E);
        scoreRecieved = findViewById(R.id.scoreRecieved_E);
        category = findViewById(R.id.CATEGORY123);
        datePicker = findViewById(R.id.datePicker123);
        feeling = findViewById(R.id.feeling);
        grade = findViewById(R.id.grdImg);
        coordinatorLayout = findViewById(R.id.coordinatorLayout1);
        feelingNumber = 7;

        feeling.setImageResource(R.drawable.happy);
        assignmentName.setError("Field Cant Be Empty!");
        scoreRecieved.setError("Field Cant Be Empty!");
        assignmentName.setOnEditorActionListener(editorListner);
        scoreRecieved.setOnEditorActionListener(editorListner);
        categories = db.getAllCategories();
        int f;

        if (categories.size() > 0) {
            for (int k = 0; k < categories.size(); k++) {
                if (categories.get(k).getPercentWeightage() >= 0 && categories.get(k).getSubjectID() == subject.getID()) {
                    f = k;
                    Categories ca = categories.get(f);
                    category.setText(ca.getCatName());
                    break;
                }
            }
        }

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        datePicker.setText(date_n);

        assignmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateDesiredName()) {
                }
            }
        });

        scoreRecieved.addTextChangedListener(new TextWatcher() {
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
                doCalculations();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.fragment.app.DialogFragment datepicker = new androidx.fragment.app.DialogFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Categories> cat = db.getAllCategories();
                int num = 0;

                for (Categories c : cat) {
                    if (c.getSubjectID() == subject.getID() && c.getPercentWeightage() >= 0) {
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

        feeling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int[] gridViewImageId = {
                        R.drawable.inlove, R.drawable.happy, R.drawable.smiling, R.drawable.neutral,
                        R.drawable.embarrassed, R.drawable.sad, R.drawable.crying, R.drawable.angry,
                };

                final int[] gridViewImageNumber = {
                        8, 7, 6, 5, 4, 3, 2, 1,
                };

                LayoutInflater inflater = AssignmentAdder.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_images_grid_view, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssignmentAdder.this);
                dialogBuilder.setView(dialogView);

                fGridList = dialogView.findViewById(R.id.images_grid_view);
                title = dialogView.findViewById(R.id.titleG);

                title.setBackgroundColor(getResources().getColor(R.color.redCustom));

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                FeelingsAdapter feelingsAdapter = new FeelingsAdapter(AssignmentAdder.this, gridViewImageId, gridViewImageId);
                fGridList.setNumColumns(4);
                fGridList.setAdapter(feelingsAdapter);
                fGridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int i, long id) {
                        b.cancel();
                        feeling.setImageResource(gridViewImageId[i]);
                        feelingNumber = gridViewImageNumber[i];
                    }
                });


                b = dialogBuilder.create();
                b.show();
            }
        });
    }

    private boolean validateDesiredName() {
        String dGrade = assignmentName.getText().toString().trim();

        if (dGrade.length() > 0) {
            assignmentName.setError(null);
            return true;
        } else {
            assignmentName.setError("Field Cant Be Empty!");
            return false;
        }
    }

    private boolean validateDesiredGrade() {
        String dGrade = scoreRecieved.getText().toString().trim();

        if (dGrade.length() > 0) {
            double desiredG = Double.parseDouble(dGrade);
            if ((desiredG < 0) || (desiredG > 100)) {
                scoreRecieved.setError("Make sure the value is between 0 and 100!");
                return false;
            } else {
                scoreRecieved.setError(null);
                return true;
            }
        } else {
            scoreRecieved.setError("Field Cant Be Empty!");
            return false;
        }
    }

    public void doCalculations() {
        double score = Double.parseDouble(scoreRecieved.getText().toString().trim());

        GPACalculations c = new GPACalculations();
        int scoreLG = c.getAverageGradeLG(score, db, AssignmentAdder.this);
        grade.setImageResource(scoreLG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.backtoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checkButton:
                categories = db.getAllCategories();
                for (Categories c : categories) {
                    if (c.getCatName().contentEquals(category.getText()) && c.getPercentWeightage() >= 0) {
                        catPercent = c.getPercentWeightage();
                    }
                }

                assignments = db.getAllAssignments(false, "", "");

                int num = 0;
                for (Assignments as : assignments) {
                    if (as.getSubjectID() == subject.getID() && as.getAssignmentName().toLowerCase()
                            .trim().equals(assignmentName.getText().toString().toLowerCase().trim())) {
                        num++;
                    }
                }

                if (num == 0) {
                    if (assignmentName.getError() == null && scoreRecieved.getError() == null && !category.getText().toString().isEmpty()) {
                        int imgResource = 0;
                        for (Categories c : categories) {
                            if (category.getText().toString().equals(c.getCatName())) {
                                imgResource = c.getCategoryImg();
                            }
                        }

                        double score = Double.parseDouble(scoreRecieved.getText().toString().trim());

                        GPACalculations gpaCalculations = new GPACalculations();
                        int scoreLG = gpaCalculations.getAverageGradeLG(score, db, AssignmentAdder.this);

                        Assignments a = new Assignments(subject.getID(), assignmentName.getText().toString().trim(),
                                score, catPercent, datePicker.getText().toString(),
                                imgResource, scoreLG, feelingNumber);

                        db.addAssignment(a);

                        assignments = db.getAllAssignments(false, "", "");

                        Intent i = new Intent(AssignmentAdder.this, AssignmentsViewer.class);
                        i.putExtra("subject", subject);
                        startActivity(i);
                    } else {
                        if (assignmentName.getError() != null || scoreRecieved.getError() != null) {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Make sure all errors have been cleared!", Snackbar.LENGTH_LONG);
                            View snackView = snackbar.getView();
                            TextView textView = snackView.findViewById(R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.redCustom));
                            snackbar.show();
                        } else if (category.getText().toString().isEmpty()) {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Make sure you have selected a category!", Snackbar.LENGTH_LONG);
                            View snackView = snackbar.getView();
                            TextView textView = snackView.findViewById(R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.redCustom));
                            snackbar.show();
                        }
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "An assignment with the same name" +
                            " already exists.", Snackbar.LENGTH_SHORT);
                    View snackView = snackbar.getView();
                    TextView textView = snackView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                    snackbar.show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDateSet(DatePicker datePicker1, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        datePicker.setText(currentDateString);
    }

    public void alertDialog1() {
        LayoutInflater inflater = AssignmentAdder.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.categories_list_view, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssignmentAdder.this);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setPositiveButton("Add Category", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AssignmentAdder.this, CategoriesViewer.class);
                intent.putExtra("subject", subject);
                startActivity(intent);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        cList = dialogView.findViewById(R.id.mylistview);
        b2 = dialogBuilder.create();

        showCategoriesList(db);


        cList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categories c = (Categories) cList.getItemAtPosition(position);
                String catN = c.getCatName();
                category.setText(catN);
                b2.cancel();
            }
        });

        cList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = AssignmentAdder.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(AssignmentAdder.this);
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.simpleTitle);
                message = dialogView.findViewById(R.id.message1);

                title.setText("Delete Category");

                message.setText("Are you sure you want to delete this item?");

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Categories slctd = (Categories) parent.getItemAtPosition(position);
                        deleteCat = slctd;
                        db.deleteCategory(slctd);
                        List<Categories> categories = db.getAllCategories();
                        int num = 0;

                        for (Categories c : categories) {
                            if (c.getSubjectID() == subject.getID() && c.getPercentWeightage() >= 0) {
                                num++;
                            }
                        }

                        if (num == 0) {
                            category.setText(null);
                            alertDialog2();
                        } else {
                            showCategoriesList(db);
                            b4.cancel();
                            b2.cancel();
                        }

                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Category was deleted", Snackbar.LENGTH_INDEFINITE)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        db.addCategory(deleteCat);
                                        showCategoriesList(db);
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.redCustom));
                        View snackView = snackbar.getView();
                        TextView textView = snackView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                        snackbar.show();
                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b4 = dialogBuilder.create();
                b4.show();
                return true;
            }
        });
        b2.show();
    }

    public void alertDialog2() {
        LayoutInflater inflater = AssignmentAdder.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(AssignmentAdder.this);
        dialogBuilder.setView(dialogView);

        title = dialogView.findViewById(R.id.simpleTitle);
        message = dialogView.findViewById(R.id.message1);

        title.setText("Add Category");
        title.setBackgroundColor(getResources().getColor(R.color.yellowCustom));

        message.setText("Make sure you have added categories with respective percent weightages to add an assignment");

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AssignmentAdder.this, CategoriesViewer.class);
                intent.putExtra("subject", subject);
                startActivity(intent);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                b2.cancel();
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
                if (c.getPercentWeightage() >= 0 && c.getSubjectID() == subject.getID()) {
                    names.add(c);
                }
            }

            ca = new CategoryListAdapter(AssignmentAdder.this, names);
            cList.setAdapter(ca);
        } else {
            names.clear();
            cList.setAdapter(null);
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(this, AssignmentsViewer.class);
        i.putExtra("subject", subject);
        startActivity(i);
    }
}
