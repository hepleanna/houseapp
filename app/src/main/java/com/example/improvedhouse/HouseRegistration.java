package com.example.improvedhouse;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class HouseRegistration extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference databaseReference;
    StorageReference storageReference ;
    EditText apartmentname_tv,typeofhouse_tv,address_tv,Rentamount_tv,leaserentsale_tv,youremail_tv;
    Button save;
    ImageView imageview;
    String newKey;
    Uri FilePathUri;
    Bitmap bit=null;

    String Storage_Path = "All_Image_Uploads/";


    int Image_Request_Code = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_registration);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        apartmentname_tv = (EditText) findViewById(R.id.apartmentname);
        typeofhouse_tv = (EditText) findViewById(R.id.typeofhouse);
        address_tv = (EditText) findViewById(R.id.address);
        Rentamount_tv = (EditText) findViewById(R.id.Rentamount);
        leaserentsale_tv = (EditText) findViewById(R.id.leaserentsale);
        youremail_tv = (EditText) findViewById(R.id.youremail);
        imageview = (ImageView) findViewById(R.id.shop_image);
        save = (Button) findViewById(R.id.save);

        //set onclick for selecting image
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });
        //end of selecting image onclick

        //set onclick for save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Upload();

            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                bit=bitmap;
                // Setting up bitmap selected image into ImageView.
                imageview.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void Upload(){


        final String apartmentname = apartmentname_tv.getText().toString().trim();
        final String typeofhouse = typeofhouse_tv.getText().toString().trim();
        final String address = address_tv.getText().toString().trim();
        final String Rentamount = Rentamount_tv.getText().toString().trim();
        final String leaserentsale = leaserentsale_tv.getText().toString().trim();
        final String youremail = youremail_tv.getText().toString().trim();

        //Request focus
        if (apartmentname.isEmpty())
        {
            apartmentname_tv.setError("Empty");
            apartmentname_tv.requestFocus();
        }
        else if (typeofhouse.isEmpty())
        {
            typeofhouse_tv.setError("Empty");
            typeofhouse_tv.requestFocus();
        }
        else if (address.isEmpty())
        {
            address_tv.setError("Empty");
            address_tv.requestFocus();
        }
        else if (Rentamount.isEmpty())
        {
            Rentamount_tv.setError("Empty");
            Rentamount_tv.requestFocus();
        }
        else if (leaserentsale.isEmpty())
        {
            leaserentsale_tv.setError("Empty");
            leaserentsale_tv.requestFocus();
        }
        else if (youremail.isEmpty())
        {
            youremail_tv.setError("Empty");
            youremail_tv.requestFocus();
        }

        else{

            if (bit==null){
                Toast.makeText(HouseRegistration.this, "Please Choose a Photo", Toast.LENGTH_SHORT).show();
            }
            else{

                String StoragePath = youremail+"_"+address;
                final StorageReference storageRef = storage.getReferenceFromUrl("gs://improvedhouse.appspot.com");
                StorageReference filepath = storageRef.child(StoragePath);


                filepath.putFile(FilePathUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                String TempImageName = apartmentname;
                                String imageUploadInfo = taskSnapshot.getDownloadUrl().toString();
                                Houselist_Beenclass Houselist = new Houselist_Beenclass(apartmentname, typeofhouse, address, Rentamount, leaserentsale,youremail,imageUploadInfo);
                                newKey=databaseReference.child("HousesList").push().getKey();
                                databaseReference.child("HousesList").child(newKey).setValue(Houselist);
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                                mDatabase.child(UserDetails.username).child("Houses").child(newKey).setValue(Houselist);
//                                databaseReference.child("Users").child(UserDetails.username).
//                                        child(newKey).setValue(Houselist);
                                String name=UserDetails.username;
                                Toast.makeText(HouseRegistration.this,"House added succesfully", Toast.LENGTH_LONG).show();
                                Log.d("Name",name);
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(HouseRegistration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
//
//                Toast.makeText(HouseRegistration.this, "Details Saved Successfull", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(HouseRegistration.this, Houses_list.class);
//                        startActivity(i);
//                        finish();
            }

        }




    }



}
