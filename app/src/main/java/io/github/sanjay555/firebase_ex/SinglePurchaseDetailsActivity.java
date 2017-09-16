package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class SinglePurchaseDetailsActivity extends AppCompatActivity {


    String custId;
    String pid;
    Double purchaseAmount = 0.0;

    TextView textViewPurchasedate;
    TextView textViewPurchaseId;
    TextView textViewPurchaseAmount;

    Button buttonViewAllgivenamountlist;

    DatabaseReference databseaddGivenAmountDetails, databasePurchaseAmount;


    public void displayImage()
    {
        buttonViewAllgivenamountlist = (Button)findViewById(R.id.btnViewPurchaseListImage);

        buttonViewAllgivenamountlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SinglePurchaseDetailsActivity.this, DiplayImage.class);
                startActivity(intent);
            }
        });

    }

//    Storage reference for view purchase image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_purchase_details);

        textViewPurchasedate = (TextView) findViewById(R.id.textViewPurchaseDate);
        textViewPurchaseId = (TextView) findViewById(R.id.textViewPurchaseId);
        textViewPurchaseAmount = (TextView) findViewById(R.id.textViewPurchaseAmount);
        buttonViewAllgivenamountlist = (Button)findViewById(R.id.buttonListOfAllGivenAmount);

        final String dt;
        Date cal = (Date) Calendar.getInstance().getTime();
        dt = cal.toLocaleString();

//        Get intent
        final Intent intent = getIntent();

        String date = intent.getStringExtra(SingleUserAllPurchases.P_DATE);
        pid = intent.getStringExtra(SingleUserAllPurchases.P_ID);

        custId = intent.getStringExtra(SingleUserAllPurchases.C_ID);

        databseaddGivenAmountDetails = FirebaseDatabase.getInstance().getReference("giveamount").child(custId).child(pid);
        databasePurchaseAmount = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(custId).child(pid);

//        Set values to textView
        textViewPurchasedate.setText(date);
//        textViewPurchaseId.setText(pid);

        //Display Image Activity

        displayImage();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePurchaseAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                PurchaseDetails baseAmount = dataSnapshot.getValue(PurchaseDetails.class);
                purchaseAmount = Double.parseDouble(baseAmount.getPurchaseAmount());
                textViewPurchaseAmount.setText(String.valueOf(purchaseAmount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
