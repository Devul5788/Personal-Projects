package com.devul.GPAMapper.app.LoginAndRegistration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devul.GPAMapper.app.CustomFonts.MyEditText;
import com.devul.GPAMapper.app.CustomFonts.MyTextView;
import com.devul.GPAMapper.app.Other.DataInitializer;
import com.devul.GPAMapper.app.R;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserLogin extends AppCompatActivity {

    private static final String IMAGE_DIRECTORY = "/myimages";
    public static Boolean imgChanged;
    public static String userN;
    public static Uri resultURI;
    SharedPreferences sb;
    String PTH;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout background;
    MyEditText username;
    MyTextView login;
    CircleImageView userImg;
    Bitmap bitmap;
    private int GALLERY = 1, CAMERA = 2;
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        sb = UserLogin.this.getSharedPreferences("prefs", MODE_PRIVATE);

        username = findViewById(R.id.email_edittext);
        login = findViewById(R.id.login_button);
        coordinatorLayout = findViewById(R.id.coordinatorLayout3);
        userImg = findViewById(R.id.userPhoto);

        PTH = "/data/data/" + getApplicationContext().getPackageName() + "/storid";
        FileOS.setFile(new File(PTH));
        background = findViewById(R.id.content_login);

        AnimationDrawable animationDrawable = (AnimationDrawable) background.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        username.setOnEditorActionListener(editorListner);

        loadValues();

        if (imgChanged != null) {
            if (imgChanged) {
                userImg.setImageURI(resultURI);
            }
        }

        if (sb.contains("userName") && sb.contains("userImg")) {
            startActivity(new Intent(UserLogin.this, DataInitializer.class));
            UserLogin.this.finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (username.getError() == null) {
                        SharedPreferences.Editor editor3 = getSharedPreferences("prefs", MODE_PRIVATE).edit();

                        userN = username.getText().toString();

                        if (!username.getText().toString().trim().toUpperCase().isEmpty() && resultURI != null) {
                            editor3.putString("userName", username.getText().toString());
                            editor3.putString("userImg", resultURI.toString());
                            editor3.apply();

                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Login Successful", Snackbar.LENGTH_SHORT);
                            View snackView = snackbar.getView();
                            TextView textView = snackView.findViewById(R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.greenCustom));
                            snackbar.show();
                            startActivity(new Intent(UserLogin.this, DataInitializer.class));
                            UserLogin.this.finish();
                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Login UnSuccessful. Please make sure you have a profile picture and username!", Snackbar.LENGTH_SHORT);
                            View snackView = snackbar.getView();
                            TextView textView = snackView.findViewById(R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.redCustom));
                            snackbar.show();
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Login UnSuccessful", Snackbar.LENGTH_LONG);
                        View snackView = snackbar.getView();
                        TextView textView = snackView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.redCustom));
                        snackbar.show();
                    }
                } catch (Exception ex) {
                    System.out.println("Error");
                }
            }
        });

        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(UserLogin.this);
            }
        });
    }

    private void validateUsername() {
        String dGrade = username.getText().toString().trim();

        if (dGrade.isEmpty()) {
            username.setError("Field Cant Be Empty!");
        } else {
            username.setError(null);
        }
    }


    public void confirmInputUL(View v) {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateUsername();
            }
        });
    }

    public void loadValues() {
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            imgChanged = false;
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(UserLogin.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    userImg.setImageBitmap(bitmap);
                    UserLogin.imgChanged = true;

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UserLogin.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            userImg.setImageBitmap(bitmap);
            UserLogin.imgChanged = true;
            saveImage(bitmap);
            Toast.makeText(UserLogin.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultURI = result.getUri();

                userImg.setImageURI(resultURI);
                UserLogin.imgChanged = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}