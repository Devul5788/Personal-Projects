package com.devul.GPAMapper.app.ConversionTables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.devul.GPAMapper.app.Adapters.ConversionTableAdapter;
import com.devul.GPAMapper.app.Adapters.ImagesGridAdapter;
import com.devul.GPAMapper.app.Other.ConversionListView;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Settings.AppSettings;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class ConversionTablesViewer extends Fragment {

    public RadioButton GPA4, GPACustom;
    Button addConversion, deleteConversion;
    ConversionListView GPA4List, GPACustomList;
    LinearLayout LL4, LLC;
    ArrayList<Conversions> al = new ArrayList<>();
    List<Conversions> conversions;
    ConversionTableAdapter cta;
    ImageView grdImg;
    EditText percent2, gpa;
    CoordinatorLayout coordinatorLayout;
    DatabaseHandler db;
    GridView cGridList;
    TextView title, message;
    int imgID;
    Conversions cons2;
    AlertDialog b3;
    SharedPreferences prefs;

    public ConversionTablesViewer() { // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.fragment_conversion_table, container, false);

        GPA4 = myview.findViewById(R.id.GPA4);
        GPACustom = myview.findViewById(R.id.GPACustom);
        addConversion = myview.findViewById(R.id.AddConversion);
        deleteConversion = myview.findViewById(R.id.deleteConversion);
        GPA4List = myview.findViewById(R.id.GPA4List);
        GPACustomList = myview.findViewById(R.id.GPACustomList);
        coordinatorLayout = myview.findViewById(R.id.coordinatorLayout8);
        LL4 = myview.findViewById(R.id.LL4);
        LLC = myview.findViewById(R.id.LLC);
        db = new DatabaseHandler(getActivity());

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Conversion Tables");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        int width = GPACustomList.getWidth() / 2;
        addConversion.setWidth(width);
        deleteConversion.setWidth(width);
        showConversions(db, GPA4List, "4.0");
        showConversions(db, GPACustomList, "Custom");

        prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

        if (Objects.requireNonNull(prefs.getString("gpaTable", null)).equals("4.0")) {
            LL4.setVisibility(View.VISIBLE);
            LLC.setVisibility(View.GONE);
            addConversion.setVisibility(View.GONE);
            deleteConversion.setVisibility(View.GONE);
            GPA4.setChecked(true);
            GPACustom.setChecked(false);
        } else if (prefs.getString("gpaTable", null).equals("Custom")) {
            LLC.setVisibility(View.VISIBLE);
            LL4.setVisibility(View.GONE);
            addConversion.setVisibility(View.VISIBLE);
            deleteConversion.setVisibility(View.VISIBLE);
            GPA4.setChecked(false);
            GPACustom.setChecked(true);
        }

        GPACustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Conversions slctd = (Conversions) parent.getItemAtPosition(position);

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.conversion_row_editor, null);
                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                dialogBuilder.setView(dialogView);
                grdImg = dialogView.findViewById(R.id.grdImg3);
                percent2 = dialogView.findViewById(R.id.percent_editor);
                gpa = dialogView.findViewById(R.id.gpa_editor);

                grdImg.setImageResource(slctd.getLetterGrade());
                percent2.setText(slctd.getPercentage());
                gpa.setText(Double.toString(slctd.getGPA()));

                imgID = slctd.getLetterGrade();

                grdImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int[] gridViewImageId = {
                                R.drawable.a_plus, R.drawable.a, R.drawable.a_minus, R.drawable.b_plus,
                                R.drawable.b, R.drawable.b_minus, R.drawable.c_plus, R.drawable.c,
                                R.drawable.c_minus, R.drawable.d_plus, R.drawable.d, R.drawable.d_minus,
                                R.drawable.f,
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
                                grdImg.setImageResource(gridViewImageId[i]);
                            }
                        });


                        b3 = dialogBuilder.create();
                        b3.show();

                    }
                });

                percent2.addTextChangedListener(new TextWatcher() {
                    int len = 0;

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String str = percent2.getText().toString().trim();
                        len = str.length();
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String str = percent2.getText().toString().trim();
                        if (str.length() == 4 && len < str.length()) {
                            percent2.append(" - ");
                        }
                    }
                });

                dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Conversions c = new Conversions(slctd.getId(), "Custom",
                                imgID, percent2.getText().toString().trim(), Double.parseDouble(gpa.getText().toString().trim()));
                        db.updateConversion(c);

                        showConversions(db, GPACustomList, "Custom");
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
        });

        GPA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL4.setVisibility(LL4.isShown() ? View.GONE : View.VISIBLE);
                LLC.setVisibility(View.GONE);
                addConversion.setVisibility(View.GONE);
                deleteConversion.setVisibility(View.GONE);
                GPACustom.setChecked(false);
                AppSettings.editor.putString("gpaTable", "4.0");
            }
        });

        GPACustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LLC.setVisibility(LLC.isShown() ? View.GONE : View.VISIBLE);
                LL4.setVisibility(View.GONE);
                addConversion.setVisibility(addConversion.isShown() ? View.GONE : View.VISIBLE);
                deleteConversion.setVisibility(deleteConversion.isShown() ? View.GONE : View.VISIBLE);
                GPA4.setChecked(false);
                AppSettings.editor.putString("gpaTable", "Custom");
            }
        });


        deleteConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conversions = db.getAllConversions();

                List<Conversions> cons = new ArrayList<>();

                int size = 0;

                for (Conversions c : conversions) {
                    if (c.getConversionTableName().equals("Custom")) {
                        cons.add(c);
                        size++;
                    }
                }

                if (size > 0) {
                    final Conversions lastConversion = cons.get(size - 1);
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.simple_alert_dialog, null);
                    final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                    dialogBuilder.setView(dialogView);

                    title = dialogView.findViewById(R.id.simpleTitle);
                    message = dialogView.findViewById(R.id.message1);

                    title.setText("Delete Conversion");
                    title.setBackgroundColor(getResources().getColor(R.color.purpleCustom));

                    message.setText("Are you sure you want to delete this item?");

                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteConversion(lastConversion);
                            showConversions(db, GPACustomList, "Custom");
                            cons2 = lastConversion;

                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Conversion was deleted", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            db.addConversion(cons2);
                                            showConversions(db, GPACustomList, "Custom");
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
            }
        });

        addConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Conversions c = new Conversions("Custom", R.drawable.no_data_b, "0.0 - 0.0", 0.0);
                db.addConversion(c);
                showConversions(db, GPACustomList, "Custom");
            }
        });


        return myview;
    }

    public void showConversions(DatabaseHandler db, ListView lst, String tableName) {
        al.clear();
        List<Conversions> conversions = db.getAllConversions();

        if (conversions.size() != 0) {
            for (Conversions c : conversions) {
                if (c.getConversionTableName().equals(tableName)) {
                    al.add(c);
                }
            }

            cta = new ConversionTableAdapter(getActivity(), al);
            lst.setAdapter(cta);
        } else {
            al.clear();
            lst.setAdapter(null);
        }
    }
}