package com.devul.GPAMapper.app.LoginAndRegistration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devul.GPAMapper.app.CustomFonts.MyEditText;
import com.devul.GPAMapper.app.CustomFonts.MyTextView;
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

public class RegistrationPage extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/myimages";
    MyEditText username;
    MyTextView register;
    String PTH;
    CircleImageView userPhoto;
    Bitmap bitmap;
    ImageButton backButton;
    CoordinatorLayout coordinatorLayout;
    SharedPreferences prefs;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        username = findViewById(R.id.email_edittext);
        register = findViewById(R.id.signup_button);
        backButton = findViewById(R.id.back_button2);
        userPhoto = findViewById(R.id.photo_button);

        prefs = RegistrationPage.this.getSharedPreferences("prefs", MODE_PRIVATE);

        if (prefs.getString("userImg", null) != null) {
            userPhoto.setImageURI(Uri.parse(prefs.getString("userImg", null)));
        }

        if (prefs.getString("userName", null) != null) {
            username.setText(prefs.getString("userName", null));
        }


        coordinatorLayout = findViewById(R.id.coordinatorLayout4);

        PTH = "/data/data/" + getApplicationContext().getPackageName() + "/storid";
        FileOS.setFile(new File(PTH));

        username.setOnEditorActionListener(editorListner);

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(RegistrationPage.this);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationPage.this, UserLogin.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (username.getError() == null) {
                    SharedPreferences.Editor editor3 = getSharedPreferences("prefs", MODE_PRIVATE).edit();

                    editor3.putString("userName", username.getText().toString());
                    editor3.putString("userImg", UserLogin.resultURI.toString());
                    editor3.apply();

                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Profile Updated Successfully", Snackbar.LENGTH_SHORT);
                    View snackView = snackbar.getView();
                    TextView textView = snackView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.greenCustom));
                    snackbar.show();
                    RegistrationPage.this.finish();
                } else {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Profile Updated Unsuccessful", Snackbar.LENGTH_LONG);
                    View snackView = snackbar.getView();
                    TextView textView = snackView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.redCustom));
                    snackbar.show();
                }
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


    public void confirmInputR(View v) {
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
                    Toast.makeText(RegistrationPage.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    userPhoto.setImageBitmap(bitmap);
                    UserLogin.imgChanged = true;

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegistrationPage.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            userPhoto.setImageBitmap(bitmap);
            UserLogin.imgChanged = true;
            saveImage(bitmap);
            Toast.makeText(RegistrationPage.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                UserLogin.resultURI = result.getUri();

                userPhoto.setImageURI(UserLogin.resultURI);
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
