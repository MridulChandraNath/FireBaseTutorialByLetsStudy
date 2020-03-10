package com.example.firebasetutorialbyletsstudy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button uploadBtn;
    private StorageReference Folder;
    public static final int ImageBack=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadBtn=findViewById(R.id.uploadBtn);
        Folder= FirebaseStorage.getInstance().getReference();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //After pressing the button then gallery will be showed for selecting deserving image
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,ImageBack);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ImageBack){
            if(resultCode==RESULT_OK){
                final Uri ImageData=data.getData();
                final StorageReference ImageName=Folder.child("image"+ImageData.getLastPathSegment());

                ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Toast.makeText(MainActivity.this, "Image Uploaded Successful", Toast.LENGTH_SHORT).show();
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference imageStore= FirebaseDatabase.getInstance().getReference().child("Image");

                                HashMap<String,String> hashMap=new HashMap<>();
                                imageStore.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Finally success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        }
    }
}







/*      SUMMERY of WORK:(Uploading data in Firebase)// Project are build under (mridulBikiran@gmail.com)
        Step 1: initializing Button
        Step 2: if button is clicked then
                below code need to write
                    Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent,ImageBack);

         Step 3: Make override of onActivityResult method

                @Override
                 protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                  super.onActivityResult(requestCode, resultCode, data);

                    if (requestCode==ImageBack){                //requestCode =1 initialize by final
                        if(resultCode==RESULT_OK){
                            Uri ImageData=data.getData();

                            StorageReference ImageName=Folder.child("image"+ImageData.getLastPathSegment());  step5
                        }
                    }
                }

          Step 4: private StorageReference Folder; are initialize so that image are store in folder;
                    Folder=FirebaseDatabase.getInstance.getReference.child("ImageFolder"); //Data are arranged by using ImageFolder Name

          Step 5:Must create another reference so that data are arranged using a name in above folder

          Step 6: After successfully created StorageReference as ImageName must pass ImageData and must not successful or not
                    ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener)
                    ...........Toast

                    ImageName.getDownloadURL.addOnSuccessListener(new OnSuccessListener{                    //From here are for step 7

                        public void .......(){
                              Toast

                                           ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                    }
                                         })
                        }

                    )
                    })
                    .onFailureListener(){
                    Toast

                    }
          Step 7: After successfully upload must send image to database . Below code are for sending data into Database and please Understand by seeing the code





 */
