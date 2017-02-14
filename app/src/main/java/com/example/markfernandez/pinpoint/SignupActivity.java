package com.example.markfernandez.pinpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.markfernandez.pinpoint.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton ibSelectImage;
    private EditText etFullname,etEmailAddress,etPassword;
    private Button btnCreateAccount;
    private TextInputLayout txtTIL1,txtTIL2,txtTIL3;

    private ProgressDialog progressDialog;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase,mDatabaseRefUser;
    private String mUserId;

    private String fname;
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseRefUser = FirebaseDatabase.getInstance().getReference().child("users");

        ibSelectImage = (ImageButton) findViewById(R.id.imageSelect);
        txtTIL1 = (TextInputLayout)findViewById(R.id.txt_input1);
        txtTIL2 = (TextInputLayout)findViewById(R.id.txt_input2);
        txtTIL3 = (TextInputLayout)findViewById(R.id.txt_input3);
        etFullname = (EditText)findViewById(R.id.editTextFullName);
        etEmailAddress = (EditText)findViewById(R.id.editTextEmail);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        btnCreateAccount = (Button)findViewById(R.id.buttonCreateAccount);

        btnCreateAccount.setOnClickListener(this);
        ibSelectImage.setOnClickListener(this);

    }

    private void saveImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    private void registerUser() {
        boolean isValid = true;

        fname = etFullname.getText().toString().trim();
        String email = etEmailAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //PROFIE IMAGE VALIDATION
        if(mImageUri == null){
            Toast.makeText(SignupActivity.this, "Profile image required!", Toast.LENGTH_LONG).show();
            return;
        }

        //FULLNAME VALIDATION
        if(TextUtils.isEmpty(fname)){
            etFullname.setError("Full Name required!");
            isValid = false;
        }else{txtTIL1.setErrorEnabled(false);}
        //EMAIL VALIDATION
        if(TextUtils.isEmpty(email)){
            etEmailAddress.setError("Email is required!");
            isValid = false;
        }else{txtTIL1.setErrorEnabled(false);}
        //PASSWORD VALIDATION
        if( TextUtils.isEmpty(password)){
            etPassword.setError("Password is required!");
            isValid = false;
        }else if(password.length() <= 5){
            etPassword.setError("Minimum of 6 characters!");
        }else{txtTIL1.setErrorEnabled(false);}

        if(isValid && mImageUri != null){
            progressDialog.setMessage("Registering account...");
            progressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                saveProfile();
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(SignupActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(SignupActivity.this, "Could not register, Please try again.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }


    }

    private void saveProfile()
    {
        // Initialize Firebase Auth and Database Reference
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        StorageReference filePath = mStorage.child("UserPic").child(mImageUri.getLastPathSegment());
        //UserProfile userProfile = new UserProfile();
        filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mDatabase.child("users").child(mUserId).child("userName").setValue(fname);
                mDatabase.child("users").child(mUserId).child("userImage").setValue(downloadUrl.toString());
            }
        });

    }

    public void onClick(View v) {
        if(v == btnCreateAccount) {
            registerUser();
        }
        else if(v == ibSelectImage){
            saveImage();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            try {
                mImageUri = data.getData();
                CropImage.activity(mImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

            }catch (Exception e){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                e = result.getError();
                Log.e("MARK log","crop error1: " + e);
            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                ibSelectImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("MARK log","crop erro2: " + error);
            }
        }
    }
}
