package io.github.sanjay555.firebase_ex;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Calendar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class AddNewPurchaseActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;
    Uri filePath;
    ProgressBar progressBar;
    private int uploadProgress = 0;
    EditText editTextEnterAmount;
    Button buttonUploadPurchaseList;
    Button buttonUploadImage;
    Button buttonSubmit,btnClear;
    TextView dateLbl;
    String pid;
    String cid;

    DatabaseReference databaseAddPurchaseDetails;

//    Storage Reference
    StorageReference storageReferencePurchaseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_purchase);

        editTextEnterAmount = (EditText) findViewById(R.id.editTextTotalAmmount);
         dateLbl=(TextView)findViewById(R.id.txtDateLabel);
        buttonUploadPurchaseList = (Button) findViewById(R.id.buttonUploadPurchaseList);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        progressBar = new ProgressBar(this);
//        progressBar.setMesssage()

        final Intent intent = getIntent();

//        Get Id from SingleCustomerActivity Class
        cid = intent.getStringExtra("customer_id");



        String dt;
        Date cal = (Date) Calendar.getInstance().getTime();
        dt = cal.toLocaleString();
        dateLbl.setText(dt.toString());


//        Debugging
//        Log.d(id, "<- Is there ID ?");


//        Get the id from FB-DB
        databaseAddPurchaseDetails = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(cid);
//        databaseAddPurchaseDetails = FirebaseDatabase.getInstance().getReference("purchaseDetails");


//        final String pid = databaseAddPurchaseDetails.getKey();

//        Storage reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReferenceFromUrl("gs://fir-ex-69df4.appspot.com/").child(cid);



//        Add Purchase Details Button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePurchaseDetails(); // function to add details

//                UPLOAD IMAGE
                if(filePath != null && editTextEnterAmount.getText().toString().length() != 0){
                    Toast.makeText(AddNewPurchaseActivity.this, "Uploading..", Toast.LENGTH_SHORT).show();

                    StorageReference storageReference1 = storageReference.child(pid);

//                    Upload the image
                    UploadTask uploadTask = storageReference1.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddNewPurchaseActivity.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewPurchaseActivity.this, "Upload Failed !!", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(AddNewPurchaseActivity.this, "Please Enter amount & Select an Image", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        Button Clear
        btnClear=(Button)findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEnterAmount.getText().clear();
            }
        });

//         Choose Image to Upload Image
        buttonUploadPurchaseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent1, "Select Image"), GALLERY_INTENT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT){
            filePath = data.getData();

            try {
//                Getting Image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public  void savePurchaseDetails() {
//        Now to save Purchase details we need purchase module so lets create class 'PurchaseDetails'
//        Get data from editTextAmount
        String purchaseAmount = editTextEnterAmount.getText().toString();
        //int purchaseAmount2 = Integer.parseInt(purchaseAmount);

//        Validation
       // if(!TextUtils.isEmpty(purchaseAmount)){
            if(editTextEnterAmount.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Please Enter valid Amount", Toast.LENGTH_LONG).show();

            }
            else {

                pid = databaseAddPurchaseDetails.push().getKey();
                String dates = dateLbl.getText().toString();
                PurchaseDetails purchaseDetails = new PurchaseDetails(pid, purchaseAmount,dates, cid);



//            Now Set to DB reference

                databaseAddPurchaseDetails.child(pid).setValue(purchaseDetails);
               // databaseAddPurchaseDetails.child(id).setValue(dates);
                Toast.makeText(this, "Purchase Details Added Successfully", Toast.LENGTH_SHORT).show();
//                editTextEnterAmount.getText().clear();
            }
    }
}
