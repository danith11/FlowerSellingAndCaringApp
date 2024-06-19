package com.s22010334.finalproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.s22010334.finalproject.R;

public class UploadActivity extends AppCompatActivity {

    private EditText editTextUploadName, editTextUploadPrice, editTextQuantity, editTextUploadDescription, editTextUploadAddress, editTextUploadPhoneNumber;
    private ImageView imageViewUploadImage;
    private AppCompatButton buttonUploadSave;
    private Uri imageUri;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Initialize Firebase Database and Storage references
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        // Initialize UI elements
        editTextUploadName = findViewById(R.id.editTextUploadName);
        editTextUploadPrice = findViewById(R.id.editTextUploadPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextUploadDescription = findViewById(R.id.editTextUploadDescription);
        editTextUploadAddress = findViewById(R.id.editTextUploadAddress);
        imageViewUploadImage = findViewById(R.id.imageViewUploadImage);
        editTextUploadPhoneNumber = findViewById(R.id.editTextUploadPhoneNumber);
        buttonUploadSave = findViewById(R.id.buttonUploadSave);

        imageViewUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        buttonUploadSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewUploadImage.setImageURI(imageUri);
        }
    }

    private void uploadData() {
        String name = editTextUploadName.getText().toString().trim();
        String priceStr = editTextUploadPrice.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String description = editTextUploadDescription.getText().toString().trim();
        String address = editTextUploadAddress.getText().toString().trim();
        String phoneNumberStr = editTextUploadPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(quantityStr) ||
                TextUtils.isEmpty(description) || TextUtils.isEmpty(address) || imageUri == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int quantity;
        Long phoneNumber;

        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
            phoneNumber = Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Price and Quantity must be valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");

        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String uploadId = databaseReference.push().getKey();
                                if (uploadId != null) {
                                    Upload upload = new Upload(userId, name, price, quantity, description, address, uri.toString(), phoneNumber);
                                    databaseReference.child(uploadId).setValue(upload);
                                    Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UploadActivity.this, "Failed to generate upload ID", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static class Upload {
        public String userId;
        public String name;
        public double price;
        public int quantity;
        public String description;
        public String address;
        public String imageUrl;
        public Long phoneNumber;

        public Upload() {
            // Default constructor required for calls to DataSnapshot.getValue(Upload.class)
        }

        public Upload(String userId, String name, double price, int quantity, String description, String address, String imageUrl, Long phoneNumber) {
            this.userId = userId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.description = description;
            this.address = address;
            this.imageUrl = imageUrl;
            this.phoneNumber = phoneNumber;
        }

        // Getters and setters (if needed)
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Long getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

}


//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.PopupMenu;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.s22010334.finalproject.Adapter.AdapterImagesPicked;
//import com.s22010334.finalproject.Helper.DataClass;
//
//import com.s22010334.finalproject.R;
//import com.s22010334.finalproject.databinding.ActivityUploadBinding;
//import com.s22010334.finalproject.fragments.ProfileFragment;
//
//import java.util.ArrayList;
//
//public class UploadActivity extends AppCompatActivity {
//
//    Button uploadBtn;
//    EditText uploadName , uploadPrice , uploadDescription, uploadAddress, uploadQuanity;
//    ImageView uploadImage,backToProfileBtn;
//    private Uri ImageUri;
//    ActivityUploadBinding binding;
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DatabaseOfPlants");
//    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//    DatabaseReference myRef;
//    FirebaseDatabase database;
//    private static final String TAG = "UploadActivity";
//    private ArrayList<ModelImagePicked> imagePickedArrayList;
//    private AdapterImagesPicked adapterImagesPicked;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload);
//
//        uploadBtn = findViewById(R.id.buttonUploadSave);
//        uploadName = findViewById(R.id.editTextUploadName);
//        uploadPrice = findViewById(R.id.editTextUploadPrice);
//        uploadDescription = findViewById(R.id.editTextUploadDescription);
//        uploadAddress = findViewById(R.id.editTextUploadAddress);
//        uploadImage = findViewById(R.id.imageViewUploadImage);
//        //backToProfileBtn = findViewById(R.id.imageViewbacktoProfile);
//        uploadQuanity = findViewById(R.id.editTextQuantity);
//
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK){
//                            Intent data = result.getData();
//                            ImageUri = data.getData();
//                            uploadImage.setImageURI(ImageUri);
//                        }
//                        else {
//                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//
////        backToProfileBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(getApplicationContext(),ProfileFragment.class);
////                startActivity(intent);
////                finish();
////            }
////        });
//
//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final StorageReference imageReference = storageReference.child(System.currentTimeMillis()+ ","+getFieldExtension(ImageUri));
//                imageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//                                String name = uploadName.getText().toString();
//                                double price = uploadPrice.getText().length();
//                                String description = uploadDescription.getText().toString();
//                                String address = uploadAddress.getText().toString();
//                                String quantity = uploadQuanity.getText().toString().trim();
//
//                                DataClass dataClass = new DataClass(name,price,description,address,uri.toString(),quantity);
//                                String key = databaseReference.push().getKey();
//                                databaseReference.child(name).setValue(dataClass);
//
//                                Toast.makeText(UploadActivity.this, "Uploaded Item Succesfully", Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(UploadActivity.this, ProfileFragment.class);
////                                startActivity(intent);
////                                startActivity(intent);
////                                finish();
//                            }
//                        });
//                    }
//                });
//
//            }
//            private String getFieldExtension(Uri fileUri){
//                ContentResolver contentResolver = getContentResolver();
//                MimeTypeMap mime = MimeTypeMap.getSingleton();
//                return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
//            }
//        });
//
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database = FirebaseDatabase.getInstance();
//                myRef = database.getReference("Users");
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });
//    };
//}
////                Log.d(TAG,"showImagePickOption: ");
////
////                PopupMenu popupMenu = new PopupMenu(UploadActivity.this,uploadImage);
////
////                popupMenu.getMenu().add(Menu.NONE,1,1,"Camera");
////                popupMenu.getMenu().add(Menu.NONE,2,2,"Gallery");
////
////                popupMenu.show();
////
////                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
////                    @Override
////                    public boolean onMenuItemClick(MenuItem item) {
////                        int itemId = item.getItemId();
////                        if (itemId == 1){
////
////                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
////                                String[] cameraPermission = new String[]{android.Manifest.permission.CAMERA};
////                                requestCameraPermission.launch(cameraPermission);
////
////                            }else {
////                                String[] cameraPermission = new String[]{Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
////                                requestCameraPermission.launch(cameraPermission);
////                            }
////                        } else if (itemId == 2) {
////
////                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
////                                pickImageGallery();
////
////                            }else {
////                                String storagePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
////                                requestStoragePermission.launch(storagePermission);
////                            }
////                        }
////                        return true;
////                    }
////                });
////            }
//
//
//
//
//
//
////                final StorageReference imageReference = storageReference.child(System.currentTimeMillis()+ ","+getFieldExtension(ImageUri));
////                imageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                    @Override
////                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                            @Override
////                            public void onSuccess(Uri uri) {
////
////                                String name = uploadName.getText().toString();
////                                String price = uploadPrice.getText().toString();
////                                String description = uploadDescription.getText().toString();
////                                String address = uploadAddress.getText().toString();
////
////                                DataClass dataClass = new DataClass(name,price,description,address,uri.toString());
////                                String key = databaseReference.push().getKey();
////                                databaseReference.child(name).setValue(dataClass);
////
////                                Toast.makeText(UploadActivity.this, "Uploaded Item Succesfully", Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(UploadActivity.this,MainActivity.class);
////                                startActivity(intent);
////                                finish();
////
////                            }
////                        });
////                    }
////                });
//
//
////    private void loadImages(){
////        Log.d(TAG, "LoadImages");
////        adapterImagesPicked = new AdapterImagesPicked(this,imagePickedArrayList);
////        binding.images.setAdapter(adapterImagesPicked);
////    }
////
////
////    private ActivityResultLauncher<String> requestStoragePermission = registerForActivityResult(
////            new ActivityResultContracts.RequestPermission(),
////            new ActivityResultCallback<Boolean>() {
////                @Override
////                public void onActivityResult(Boolean isGranted) {
////                    Log.d(TAG,"onActivityResult: isGranted: "+ isGranted);
////
////                    if (isGranted){
////                        pickImageGallery();
////                    }else {
////                        Toast.makeText(UploadActivity.this, "Storage Permission Failed", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////    );
////    private ActivityResultLauncher<String[]> requestCameraPermission = registerForActivityResult(
////            new ActivityResultContracts.RequestMultiplePermissions(),
////            new ActivityResultCallback<Map<String, Boolean>>() {
////                @Override
////                public void onActivityResult(Map<String, Boolean> result) {
////                    Log.d(TAG,"onActivityResult: ");
////                    Log.d(TAG,"onActivityResult: "+ result.toString());
////
////                    boolean areAllGranted = true;
////                    for (Boolean isGranted : result.values()){
////                        areAllGranted = areAllGranted && isGranted;
////                    }
////                    if (areAllGranted){
////                        pickImageCamera();
////                    }else {
////                        Toast.makeText(UploadActivity.this, "Camera or Storage or both permission denied...", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////    );
////
////    private void pickImageGallery(){
////        Log.d(TAG,"pickImageGallery: ");
////
////        Intent intent = new Intent(Intent.ACTION_PICK);
////
////        intent.setType("image/*");
////        galleryActivityResultLauncher.launch(intent);
////    }
////
////    private void pickImageCamera(){
////        Log.d(TAG,"pickImageCamera: ");
////
////        ContentValues contentValues= new ContentValues();
////        contentValues.put(MediaStore.Images.Media.TITLE,"TEMPORARY_IMAGE");
////        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"TEMPORARY_IMAGE_DESCRIPTION");
////
////        imageUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
////
////        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
////        cameraActivityResultLauncher.launch(intent);
////    }
////
////    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
////            new ActivityResultContracts.StartActivityForResult(),
////            new ActivityResultCallback<ActivityResult>() {
////                @Override
////                public void onActivityResult(ActivityResult result) {
////                    Log.d(TAG,"onActivityResult: ");
////                    if (result.getResultCode() == Activity.RESULT_OK){
////                        Intent data = result.getData();
////                        imageUri = data.getData();
////                        Log.d(TAG,"onActivityResult: imageUri: "+ imageUri);
////                        String timeStamp = "" + Utils.getTimestamp();
////
////                        ModelImagePicked modelImagePicked = new ModelImagePicked(timeStamp,imageUri,null,false);
////                        imagePickedArrayList.add(modelImagePicked);
////                        loadImages();
////
////                    }else {
////                        Toast.makeText(UploadActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////    );
////
////    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
////            new ActivityResultContracts.StartActivityForResult(),
////            new ActivityResultCallback<ActivityResult>() {
////                @Override
////                public void onActivityResult(ActivityResult result) {
////                    Log.d(TAG,"onActivityResult: ");
////                    if (result.getResultCode() == Activity.RESULT_OK){
////
////                        Log.d(TAG,"onActivityResult: imageUri: "+ imageUri);
////                        String timeStamp = "" + Utils.getTimestamp();
////
////                        ModelImagePicked modelImagePicked = new ModelImagePicked(timeStamp,imageUri,null,false);
////                        imagePickedArrayList.add(modelImagePicked);
////                        loadImages();
////
////                    }else {
////                        Toast.makeText(UploadActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////    );


