package com.devul.GPAMapper.app.Assignments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.devul.GPAMapper.app.Feelings.Feelings;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class AssignmentEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText assignmentName, scoreRecieved;
    TextView categoryAlertDialogName, title, message;
    Button datePicker, category;
    ImageView feeling, grade;
    ListView cList;
    GridView fGridList;
    CoordinatorLayout coordinatorLayout;
    CategoryListAdapter ca;
    ArrayList<Feelings> feelings = new ArrayList<>();
    ArrayList<Categories> names = new ArrayList<>();
    List<Assignments> assignments;
    List<Categories> categories;
    int catPercent, feelingNumber, feelingImg;
    AlertDialog b;
    DatabaseHandler db = new DatabaseHandler(AssignmentEditor.this);
    Assignments as;
    Categories catgs;
    AlertDialog b2, b4;
    Subjects subject;
    Toolbar toolbar;
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
        setContentView(R.layout.activity_assignment_editor);

        ArrayList<Parcelable> p = getIntent().getParcelableArrayListExtra("objects");
        as = (Assignments) p.get(0);
        subject = (Subjects) p.get(1);

        assignmentName = findViewById(R.id.assignmentName123);
        scoreRecieved = findViewById(R.id.scoreRecieved123);
        category = findViewById(R.id.CATEGORY1234);
        coordinatorLayout = findViewById(R.id.coordinatorLayout2);
        datePicker = findViewById(R.id.datePicker1234);
        feeling = findViewById(R.id.feeling2);
        grade = findViewById(R.id.grdImg2);

        assignmentName.setOnEditorActionListener(editorListner);
        scoreRecieved.setOnEditorActionListener(editorListner);

        toolbar = findViewById(R.id.backToolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Assignment");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AssignmentEditor.this, AssignmentsViewer.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

        if (as != null) {
            assignmentName.setText(as.getAssignmentName());
            scoreRecieved.setText(Double.toString(as.getScore()));
            grade.setImageResource(as.getGradeImg());
            categories = db.getAllCategories();

            int f;
            for (int k = 0; k < categories.size(); k++) {
                if (categories.get(k).getSubjectID() == as.getSubjectID() && categories.get(k).getPercentWeightage() == as.getPercentWeightage()) {
                    f = k;
                    Categories c = categories.get(f);
                    category.setText(c.getCatName());
                    break;
                }
            }

            feelings = db.getAllFeelings();

            for (Feelings fl : feelings) {
                if (fl.getNum() == as.getFeelingNumber()) {
                    feelingNumber = fl.getNum();
                    feelingImg = fl.getImg();
                    break;
                }
            }

            feeling.setImageResource(feelingImg);
            datePicker.setText(as.getDate());


        }

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
                    return;
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
                    if (c.getSubjectID() == as.getSubjectID() && c.getPercentWeightage() >= 0) {
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

                LayoutInflater inflater = AssignmentEditor.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_images_grid_view, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssignmentEditor.this);
                dialogBuilder.setView(dialogView);

                fGridList = dialogView.findViewById(R.id.images_grid_view);
                title = dialogView.findViewById(R.id.titleG);

                title.setBackgroundColor(getResources().getColor(R.color.redCustom));

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                FeelingsAdapter feelingsAdapter = new FeelingsAdapter(AssignmentEditor.this, gridViewImageId, gridViewImageId);
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
            //assignmentName.setErrorEnabled(false);
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
                //desiredGrade.setErrorEnabled(false);
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
        int scoreLG = c.getAverageGradeLG(score, db, AssignmentEditor.this);
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
                if (assignmentName.getError() == null && scoreRecieved.getError() == null && !category.getText().toString().isEmpty()) {
                    List<Categories> categories = db.getAllCategories();
                    int imgResource = 0;
                    for (Categories c : categories) {
                        if (category.getText().toString().equals(c.getCatName())) {
                            imgResource = c.getCategoryImg();
                        }
                    }
                    for (Categories c : categories) {
                        if (c.getCatName().contentEquals(category.getText()) && c.getSubjectID() == as.getSubjectID()) {
                            catPercent = c.getPercentWeightage();
                        }
                    }

                    assignments = db.getAllAssignments(false, "", "");

                    double score = Double.parseDouble(scoreRecieved.getText().toString().trim());

                    GPACalculations gpaCalculations = new GPACalculations();
                    int scoreLG = gpaCalculations.getAverageGradeLG(score, db, AssignmentEditor.this);

                    Assignments a = new Assignments(as.getID(), as.getSubjectID(), assignmentName.getText().toString().trim(),
                            score, catPercent, datePicker.getText().toString(), imgResource, scoreLG, feelingNumber);
                    db.updateAssignment(a);

                    Intent i = new Intent(AssignmentEditor.this, AssignmentsViewer.class);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker1, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        datePicker.setText(currentDateString);
    }

    public void alertDialog1() {
        LayoutInflater inflater = AssignmentEditor.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.categories_list_view, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssignmentEditor.this);
        dialogBuilder.setView(dialogView);
        categoryAlertDialogName = dialogView.findViewById(R.id.titleG);

        categoryAlertDialogName.setText("Choose A Category");

        dialogBuilder.setPositiveButton("Add Category", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AssignmentEditor.this, CategoriesViewer.class);
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
                LayoutInflater inflater = AssignmentEditor.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(AssignmentEditor.this);
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
                        catgs = slctd;
                        List<Categories> categories = db.getAllCategories();
                        int num = 0;

                        for (Categories c : categories) {
                            if (c.getSubjectID() == as.getSubjectID() && c.getPercentWeightage() >= 0) {
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

                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Category was deleted.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        db.addCategory(catgs);
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
        LayoutInflater inflater = AssignmentEditor.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(AssignmentEditor.this);
        dialogBuilder.setView(dialogView);

        title = dialogView.findViewById(R.id.simpleTitle);
        message = dialogView.findViewById(R.id.message1);

        title.setText("Add Category");
        title.setBackgroundColor(getResources().getColor(R.color.yellowCustom));

        message.setText("Make sure you have added categories with respective percent weightages to add an assignment");

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AssignmentEditor.this, CategoriesViewer.class);
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
        categories = db.getAllCategories();

        if (categories.size() != 0) {
            for (Categories c : categories) {
                if (c.getPercentWeightage() >= 0 && c.getSubjectID() == as.getSubjectID()) {
                    names.add(c);
                }
            }

            ca = new CategoryListAdapter(AssignmentEditor.this, names);
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
