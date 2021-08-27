package com.devul.GPAMapper.app.Categories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devul.GPAMapper.app.Adapters.CategoriesAdapter;
import com.devul.GPAMapper.app.Adapters.CategoryListAdapter;
import com.devul.GPAMapper.app.Adapters.ImagesGridAdapter;
import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.Assignments.AssignmentsViewer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.RecyclerView.RecyclerItemTouchHelperC;
import com.devul.GPAMapper.app.RecyclerView.RecyclerTouchListener;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListView extends Fragment implements RecyclerItemTouchHelperC.RecyclerItemTouchHelperListener {

    public int imgID, imgAdderViewer, num;
    DatabaseHandler db;
    TextView category1, title, message;
    String categoryName;
    EditText percent;
    Categories selectCategory;
    List<Categories> categories;
    List<Categories> categories2;
    List<Assignments> assignments;
    Categories deleteCat;
    FloatingActionMenu floatingActionMenu;
    com.github.clans.fab.FloatingActionButton addCategory, deleteAllCategories;
    FloatingActionButton backButton;
    TextInputEditText catNAME;
    EditText categoryNameEditor;
    ArrayList<Categories> al = new ArrayList<>();
    ArrayList<Categories> names = new ArrayList<>();
    ListView cList;
    RecyclerView lst;
    RecyclerView.LayoutManager layoutManager;
    GridView cGridList;
    CategoriesAdapter my;
    CategoryListAdapter ca;
    LinearLayout LL;
    ImageView catImage, imgAddViewer;
    AlertDialog b2, b3, b4, b5;
    Subjects subject;
    CoordinatorLayout coordinatorLayout;

    public CategoryListView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.fragment_category_list_view, container, false);

        db = new DatabaseHandler(getActivity());

        addCategory = myview.findViewById(R.id.addCategoryItem);
        deleteAllCategories = myview.findViewById(R.id.deleteAllCategoriesItem);
        floatingActionMenu = myview.findViewById(R.id.floatingActionMenu3);
        backButton = myview.findViewById(R.id.floatingActionMenu5);
        lst = myview.findViewById(R.id.showAllCategories);
        LL = myview.findViewById(R.id.LL1);
        coordinatorLayout = myview.findViewById(R.id.coordinatorLayout7);
        imgAdderViewer = R.drawable.major;
        imgID = R.drawable.major;
        num = 0;

        layoutManager = new LinearLayoutManager(getActivity());
        lst.setItemAnimator(new DefaultItemAnimator());
        lst.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        subject = getActivity().getIntent().getParcelableExtra("subject");

        floatingActionMenu.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        floatingActionMenu.setEnabled(true);
        LL.setVisibility(View.VISIBLE);
        showCategories(db);
        floatingActionMenu.close(true);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperC(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AssignmentsViewer.class);
                i.putExtra("subject", subject);
                startActivity(i);
            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog3();
            }
        });

        deleteAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categories = db.getAllCategories();
                categories2 = new ArrayList<>();
                assignments = db.getAllAssignments(false, "", "");

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);

                title = dialogView.findViewById(R.id.simpleTitle);
                message = dialogView.findViewById(R.id.message1);

                title.setText("Delete Assignment");
                title.setBackgroundColor(getResources().getColor(R.color.yellowCustom));

                message.setText("Are you sure you want to delete all categories? You may need to change Assignment Categories accordingly.");

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Categories c : categories) {
                            if (c.getSubjectID() == subject.getID() && c.getPercentWeightage() >= 0) {
                                db.deleteCategory(c);
                                categories2.add(c);
                                for (Assignments a : assignments) {
                                    if (a.getPercentWeightage() == c.getPercentWeightage() && a.getSubjectID() == subject.getID()) {
                                        Assignments as = new Assignments(a.getID(), a.getSubjectID(), a.getAssignmentName(),
                                                a.getScore(), 0, a.getDate(), R.drawable.no_data_b,
                                                a.getGradeImg(), a.getFeelingNumber());
                                        db.updateAssignment(as);
                                    }
                                }
                            }
                        }

                        showCategories(db);

                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "All Categories were deleted.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getActivity(), "catSize: " + categories2.size(), Toast.LENGTH_SHORT).show();
                                        for (Categories cats : categories2) {
                                            db.addCategory(cats);
                                        }
                                        categories2.clear();
                                        showCategories(db);
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
                final android.app.AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        lst.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), lst, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                floatingActionMenu.close(true);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_category_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);
                imgAddViewer = dialogView.findViewById(R.id.category_adder_image);
                category1 = dialogView.findViewById(R.id.categoryName_adder);
                categoryNameEditor = dialogView.findViewById(R.id.categoryName_editor);
                percent = dialogView.findViewById(R.id.percent_picker);
                title = dialogView.findViewById(R.id.title123);

                category1.setVisibility(View.GONE);
                categoryNameEditor.setVisibility(View.VISIBLE);
                title.setText("Edit Category?");
                final Categories slctd = my.getItem(position);

                imgAddViewer.setImageResource(slctd.getCategoryImg());
                categoryNameEditor.setText(slctd.getCatName());
                percent.setText("" + slctd.getPercentWeightage());
                title.setText("Edit Category");
                title.setBackgroundColor(getResources().getColor(R.color.greenCustom));
                imgID = slctd.getCategoryImg();

                imgAddViewer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int[] gridViewImageId = {
                                R.drawable.alarm_clock, R.drawable.basketball,
                                R.drawable.certificate, R.drawable.classwork, R.drawable.colloboration,
                                R.drawable.ebook, R.drawable.exam, R.drawable.homework, R.drawable.labs, R.drawable.laptop,
                                R.drawable.management, R.drawable.math, R.drawable.minor, R.drawable.notebook, R.drawable.notepad,
                                R.drawable.notepad, R.drawable.presentation, R.drawable.project, R.drawable.quiz, R.drawable.reading,
                                R.drawable.research, R.drawable.scholarship, R.drawable.school, R.drawable.studying, R.drawable.major,
                                R.drawable.test, R.drawable.thinking,
                        };

                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.custom_images_grid_view, null);
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        dialogBuilder.setView(dialogView);

                        cGridList = dialogView.findViewById(R.id.images_grid_view);
                        title = dialogView.findViewById(R.id.titleG);

                        title.setBackgroundColor(getResources().getColor(R.color.purpleCustom));

                        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });

                        ImagesGridAdapter adapterViewAndroid = new ImagesGridAdapter(getActivity(), gridViewImageId);
                        cGridList.setNumColumns(3);
                        cGridList.setAdapter(adapterViewAndroid);
                        cGridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int i, long id) {
                                imgID = gridViewImageId[i];
                                b3.cancel();
                                imgAddViewer.setImageResource(gridViewImageId[i]);
                            }
                        });

                        b3 = dialogBuilder.create();
                        b3.show();

                    }
                });

                dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Categories c = new Categories(slctd.getID(), subject.getID(), categoryNameEditor.getText().toString(),
                                Integer.parseInt(percent.getText().toString().trim()), imgID);
                        db.updateCategory(c);

                        List<Assignments> assignments = db.getAllAssignments(false, "", "");

                        for (Assignments a : assignments) {
                            if (a.getPercentWeightage() == slctd.getPercentWeightage() && a.getSubjectID() == subject.getID()) {
                                Assignments as = new Assignments(a.getID(), a.getSubjectID(), a.getAssignmentName(),
                                        a.getScore(), Integer.parseInt(percent.getText().toString().trim()), a.getDate(), c.getCategoryImg(), a.getGradeImg(),
                                        a.getFeelingNumber());
                                db.updateAssignment(as);
                            }
                        }
                        showCategories(db);
                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                final android.app.AlertDialog b = dialogBuilder.create();
                b.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return myview;
    }

    public void alertDialog1() {
        b4.cancel();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.categories_list_view, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                alertDialog2();
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
                selectCategory = new Categories(c.getID(), c.getSubjectID(), c.getCatName(),
                        c.getPercentWeightage(), c.getCategoryImg());
                imgAdderViewer = c.getCategoryImg();
                b.cancel();
                alertDialog3();
            }
        });

        cList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
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
                        showCategoriesList(db);
                        b4.cancel();
                        b5.cancel();
                        b.cancel();

                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "A category was deleted", Snackbar.LENGTH_INDEFINITE)
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
                b5 = dialogBuilder.create();
                b5.show();
                return true;
            }
        });

        b.show();
    }

    public void alertDialog2() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_category_alert_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(dialogView);

        catNAME = dialogView.findViewById(R.id.catNAME);
        catImage = dialogView.findViewById(R.id.catImage);

        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] gridViewImageId = {
                        R.drawable.alarm_clock, R.drawable.basketball,
                        R.drawable.certificate, R.drawable.classwork, R.drawable.colloboration,
                        R.drawable.ebook, R.drawable.exam, R.drawable.homework, R.drawable.labs, R.drawable.laptop,
                        R.drawable.management, R.drawable.math, R.drawable.minor, R.drawable.notebook, R.drawable.notepad,
                        R.drawable.notepad, R.drawable.presentation, R.drawable.project, R.drawable.quiz, R.drawable.reading,
                        R.drawable.research, R.drawable.scholarship, R.drawable.school, R.drawable.studying, R.drawable.major,
                        R.drawable.test, R.drawable.thinking,
                };

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_images_grid_view, null);
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                catImage.setImageResource(R.drawable.colloboration);

                ImagesGridAdapter adapterViewAndroid = new ImagesGridAdapter(getActivity(), gridViewImageId);
                cGridList = dialogView.findViewById(R.id.images_grid_view);
                cGridList.setNumColumns(3);
                cGridList.setAdapter(adapterViewAndroid);
                cGridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int i, long id) {
                        catImage.setImageResource(gridViewImageId[+i]);
                        imgID = gridViewImageId[+i];
                        num++;
                        b3.cancel();
                    }
                });


                b3 = dialogBuilder.create();
                b3.show();
            }
        });

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                List<Categories> categories = db.getAllCategories();
                int num = 0;
                for (Categories c : categories) {
                    if (catNAME.getText().toString().toLowerCase().trim().equals(c.getCatName().toLowerCase().trim())) {
                        num++;
                    }
                }
                if (num == 0) {
                    Categories c = new Categories(subject.getID(), catNAME.getText().toString(), -1, imgID);
                    db.addCategory(c);
                    b2.cancel();
                    alertDialog1();
                } else {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "A category with the same name" +
                            " already exists.", Snackbar.LENGTH_SHORT);
                    View snackView = snackbar.getView();
                    TextView textView = snackView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                    snackbar.show();
                }
                b2.cancel();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                alertDialog1();
                b2.cancel();
            }
        });
        b2 = dialogBuilder.create();
        b2.show();
    }

    public void alertDialog3() {
        floatingActionMenu.close(true);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_category_alert_dialog, null);
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        dialogBuilder.setView(dialogView);
        imgAddViewer = dialogView.findViewById(R.id.category_adder_image);
        categoryNameEditor = dialogView.findViewById(R.id.categoryName_editor);
        category1 = dialogView.findViewById(R.id.categoryName_adder);
        percent = dialogView.findViewById(R.id.percent_picker);
        title = dialogView.findViewById(R.id.title123);

        category1.setVisibility(View.VISIBLE);
        categoryNameEditor.setVisibility(View.GONE);

        percent.setText("75");
        title.setText("Add Category");

        percent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateDesiredGrade()) {
                }
            }
        });

        if (selectCategory != null) {
            category1.setText(selectCategory.getCatName());
            imgAddViewer.setImageResource(selectCategory.getCategoryImg());
        } else {
            categories = db.getAllCategories();
            if (categories.size() > 0) {
                Categories c = categories.get(0);
                category1.setText(c.getCatName());
            }
            categoryName = category1.getText().toString();
        }

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (percent.getError() == null) {
                    List<Categories> categories = db.getAllCategories();
                    int num = 0;
                    int total = 0;
                    for (Categories c : categories) {
                        if (c.getSubjectID() == subject.getID() && category1.getText().toString().equals(c.getCatName()) && c.getPercentWeightage() > 0) {
                            num++;
                        }
                        if (c.getSubjectID() == subject.getID()) {
                            total += c.getPercentWeightage();
                        }
                    }
                    total += Integer.parseInt(percent.getText().toString().trim());
                    if (num == 0 && total <= 100) {
                        Categories cat = new Categories(subject.getID(), category1.getText().toString(), Integer.parseInt(percent.getText().toString().trim()), imgAdderViewer);
                        db.addCategory(cat);
                        showCategories(db);
                        b4.cancel();
                    }
                    if (num > 0) {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "A category with the same name" +
                                " already exists.", Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        TextView textView = snackView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                        snackbar.show();
                        b4.cancel();
                    } else if (total > 100) {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "All categories must add up to a maximum of 100 percent!", Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        TextView textView = snackView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.yellowCustom));
                        snackbar.show();
                        b4.cancel();
                    }
                }
            }
        });

        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1();
            }
        });

        imgAddViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        b4 = dialogBuilder.create();
        b4.show();
    }

    private boolean validateDesiredGrade() {
        String dGrade = percent.getText().toString().trim();

        if (dGrade.length() > 0) {
            double desiredG = Double.parseDouble(dGrade);
            if ((desiredG < 0) || (desiredG > 100)) {
                percent.setError("Make sure the value is between 0 and 100!");
                return false;
            } else {
                percent.setError(null);
                return true;
            }
        } else {
            percent.setError("Field Cant Be Empty!");
            return false;
        }
    }

    public void showCategories(DatabaseHandler db) {
        al.clear();
        List<Categories> categories = db.getAllCategories();

        if (categories.size() != 0) {
            for (Categories c : categories) {
                if (c.getSubjectID() == subject.getID() && c.getPercentWeightage() >= 0) {
                    al.add(c);
                }
            }
            my = new CategoriesAdapter(getActivity(), al);
            lst.setLayoutManager(layoutManager);
            lst.setAdapter(my);
            my.notifyDataSetChanged();
        } else {
            al.clear();
            lst.setAdapter(null);
        }
    }

    public void showCategoriesList(DatabaseHandler db) {
        names.clear();
        List<Categories> categories = db.getAllCategories();

        if (categories.size() != 0) {
            for (Categories c : categories) {
                if (c.getPercentWeightage() == -1) {
                    names.add(c);
                }
            }
            ca = new CategoryListAdapter(getActivity(), names);
            cList.setAdapter(ca);
        } else {
            names.clear();
            cList.setAdapter(null);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CategoriesAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar

            List<Categories> cat123 = new ArrayList<Categories>();
            List<Categories> cat1234 = db.getAllCategories();

            for (Categories cat : cat1234) {
                if (cat.getSubjectID() == subject.getID() && cat.getPercentWeightage() > 0) {
                    cat123.add(cat);
                }
            }

            String name = cat123.get(viewHolder.getAdapterPosition()).getCatName();

            // backup of removed item for undo purpose
            final Categories deletedItem = cat123.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            my.removeItem(viewHolder.getAdapterPosition());
            db.deleteCategory(deletedItem);

            //showCategories(db);

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    my.restoreItem(deletedItem, deletedIndex);
                    db.addCategory(deletedItem);
                    showCategories(db);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
