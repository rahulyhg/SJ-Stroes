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

    String gId;
    String custId;
    String pid;
    String amount;

     Double purchaseAmount = 0.0;
//    private Double finalRemainingAmount = 0.0;
//    Double totalGivenAmount = 0.0;


    TextView textViewPurchasedate;
    TextView textViewPurchaseId;
    TextView textViewPurchaseAmount;
    TextView textViewRemainingAmount;

    TextView textViewCustomerFName;
    TextView textViewCustomerLName;
    TextView textViewCustomerAddress;
    TextView etxtViewCustomerId;

    EditText editTextGivenToday;
    Button buttonOk;
    Button buttonViewAllgivenamountlist;

    DatabaseReference databseaddGivenAmountDetails, databasePurchaseAmount;

//    Storage reference for view purchase image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_purchase_details);

        textViewPurchasedate = (TextView) findViewById(R.id.textViewPurchaseDate);
        textViewPurchaseId = (TextView) findViewById(R.id.textViewPurchaseId);
        textViewPurchaseAmount = (TextView) findViewById(R.id.textViewPurchaseAmount);
        textViewRemainingAmount = (TextView)findViewById(R.id.textViewRemainingAmount);

        editTextGivenToday = (EditText) findViewById(R.id.editTextGivenToday);
        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonViewAllgivenamountlist = (Button)findViewById(R.id.buttonListOfAllGivenAmount);

        final String dt;
        Date cal = (Date) Calendar.getInstance().getTime();
        dt = cal.toLocaleString();


//        Get intent
        final Intent intent = getIntent();

        String date = intent.getStringExtra(SingleUserAllPurchases.P_DATE);
        pid = intent.getStringExtra(SingleUserAllPurchases.P_ID);
//        amount = intent.getStringExtra(SingleUserAllPurchases.P_AMMOUNT);

        custId = intent.getStringExtra(SingleUserAllPurchases.C_ID);

        databseaddGivenAmountDetails = FirebaseDatabase.getInstance().getReference("giveamount").child(custId).child(pid);
        databasePurchaseAmount = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(custId).child(pid);

//        Set values to textView
        textViewPurchasedate.setText(date);
        textViewPurchaseId.setText(pid);
//        textViewPurchaseAmount.setText(amount);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                get data from editText
                String todayGivenAmount = editTextGivenToday.getText().toString();

                Toast.makeText(SinglePurchaseDetailsActivity.this, " Rs." + todayGivenAmount, Toast.LENGTH_SHORT).show();


//                Validate
                if(editTextGivenToday.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Please Enter valid Amount", Toast.LENGTH_SHORT).show();
                }else {
                    gId = databseaddGivenAmountDetails.push().getKey();
                    String date = dt;
                    GivenAmountDetails givenAmountDetails = new GivenAmountDetails(gId, date, Double.parseDouble(todayGivenAmount));

//                    Now set to FBDB ref
                    databseaddGivenAmountDetails.child(gId).setValue(givenAmountDetails);

                    Toast.makeText(SinglePurchaseDetailsActivity.this, " Given Amount Added Successfully", Toast.LENGTH_SHORT).show();

                }
                editTextGivenToday.setText("");

            }
        });

        buttonViewAllgivenamountlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent
                Intent intent01 = new Intent(SinglePurchaseDetailsActivity.this, SingleUserAllGivenAmountList.class);

//                pass what you wanna display in next activity
//                Cust_ID
                intent01.putExtra("cid",custId);
                intent01.putExtra("pid", pid);
                startActivity(intent01);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePurchaseAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Double purchaseAmount = 0.0;

                PurchaseDetails baseAmount = dataSnapshot.getValue(PurchaseDetails.class);
                purchaseAmount = Double.parseDouble(baseAmount.getPurchaseAmount());
                textViewPurchaseAmount.setText(String.valueOf(purchaseAmount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databseaddGivenAmountDetails.addValueEventListener(new ValueEventListener() {

            Double totalGivenAmount = 0.0;
            Double finalRemainingAmount = 0.0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot givenDataSnapshot : dataSnapshot.getChildren()){
                    GivenAmountDetails givenAmountDetails   = givenDataSnapshot.getValue(GivenAmountDetails.class);

                    totalGivenAmount = totalGivenAmount + givenAmountDetails.getTodayGiveAmount();
                }

                finalRemainingAmount = purchaseAmount - totalGivenAmount;

                textViewRemainingAmount.setText(String.valueOf(finalRemainingAmount));

                Toast.makeText(SinglePurchaseDetailsActivity.this, "BAKI :" + finalRemainingAmount, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
