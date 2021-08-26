package firebasedemo.devul.firebase4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ProductUpload extends AppCompatActivity {


    private ImageView mImageView;
    public String cnt;
    private Button buttonChoose, buttonUpload;
    private EditText title,price;
    private static final int PICK_IMAGE_REQUEST = 234 ;
    private Uri filePath;

    private StorageReference mStorageRefrence;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_upload);
        mImageView = (ImageView) findViewById(R.id.imgView);
        buttonChoose = (Button) findViewById(R.id.btnChoose);
        buttonUpload =(Button) findViewById(R.id.btnUpload);
        title=(EditText)findViewById(R.id.ptitle);
        price=(EditText)findViewById(R.id.pprice);
        mStorageRefrence = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fir-demo-dda93.appspot.com/Upload");
        buttonChoose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }
    private void uploadFile(){
        if (filePath !=null) {
            final ProgressDialog progressDialog = new ProgressDialog(ProductUpload.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            final StorageReference riversRef = mStorageRefrence.child(String.valueOf(Math.random()*5));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            String ur = taskSnapshot.toString();
                            database.child("stock").child("5").child("name").setValue(title.getText().toString());
                            database.child("stock").child("5").child("price").setValue(price.getText().toString());
                            database.child("stock").child("5").child("thumbnail:").setValue("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProductUpload.this,
                                    "ERROR", Toast.LENGTH_SHORT).show();


                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        }
        else {
            Toast.makeText(ProductUpload.this, "Please choose a pic", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null ) {
            filePath = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select and Image"),
                PICK_IMAGE_REQUEST);
    }
}