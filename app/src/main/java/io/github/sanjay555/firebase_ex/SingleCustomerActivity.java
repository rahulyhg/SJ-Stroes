//Single customer account information/operation

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

import java.util.Calendar;
import java.util.Date;

public class SingleCustomerActivity extends AppCompatActivity {

    String cust_id;
    String Cid;
    String totalGAMT;
    String totalPAMT;
    Double result = 0.0;

    String AllPAmt;

    Double ttGAMT = 0.0, ttPamt = 0.0;

    TextView textViewCustomerId;
    TextView textViewCustomerFName;
    TextView textViewCustomerLName;
    TextView textViewCustomerAddress;
    TextView textViewRemainingAmount;
    TextView txtToatlGivenAmt;
    TextView txtBaki;

    Button addnewPurchase;
    Button viewAllPurchages;
    Button btnok;
    Button buttonViewAllgivenamountlist;
    EditText editTextTodayGivenAMT;
    String gId;

    DatabaseReference databaseGetPId,  databaseGivID, databaseCId, databaseGivenAMT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer);

        textViewCustomerId = (TextView) findViewById(R.id.textViewCustomerId);
        textViewCustomerFName = (TextView) findViewById(R.id.textViewFName);
        textViewCustomerLName = (TextView) findViewById(R.id.textViewLName);
        textViewCustomerAddress = (TextView) findViewById(R.id.textViewAddress);
        txtToatlGivenAmt = (TextView)findViewById(R.id.txtViewTotalBaki);
        txtBaki = (TextView)findViewById(R.id.textViewBaki);

        editTextTodayGivenAMT = (EditText)findViewById(R.id.editTextTodayGivenAMT);

        addnewPurchase = (Button) findViewById(R.id.buttonAddPurchase);
        viewAllPurchages = (Button) findViewById(R.id.buttonListOfAllGivenAmount);
        btnok = (Button)findViewById(R.id.buttonOK);
        buttonViewAllgivenamountlist = (Button)findViewById(R.id.button2);

//        Intent
        Intent intent = getIntent();

        Cid = intent.getStringExtra(ViewCustomersList.CUSTOMER_ID); // C_ID
        String fname = intent.getStringExtra(ViewCustomersList.CUSTOMER_FNAME);
        String lname = intent.getStringExtra(ViewCustomersList.CUSTOMER_LNAME);
        String address = intent.getStringExtra(ViewCustomersList.CUSTOMER_ADDRESS);

//        Databsse reference..

        databaseCId = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(Cid); // CId
//        databaseGetPId = FirebaseDatabase.getInstance().getReference("")
        databaseGivID = FirebaseDatabase.getInstance().getReference("giveamount").child(Cid);
        databaseGivenAMT = FirebaseDatabase.getInstance().getReference("giveamount").child(Cid);

        final String dt;
        Date cal = (Date) Calendar.getInstance().getTime();
        dt = cal.toLocaleString();

        addnewPurchase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cust_id=textViewCustomerId.getText().toString();
                Intent intent = new Intent(SingleCustomerActivity.this, AddNewPurchaseActivity.class);

//                Pass Id to next activity(AddNewPurchaseActivity)
                intent.putExtra("customer_id",cust_id);

                startActivity(intent);
            }
        });

        viewAllPurchages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = textViewCustomerId.getText().toString();
                String fname = textViewCustomerFName.getText().toString();
                String lname = textViewCustomerLName.getText().toString();
                String address = textViewCustomerAddress.getText().toString();

//                Create intent;
                Intent intent = new Intent(SingleCustomerActivity.this, SingleUserAllPurchases.class);

//                Pass all to next activity
                intent.putExtra("id",id);
                intent.putExtra("fname", fname);
                intent.putExtra("lname", lname);
                intent.putExtra("address", address);

                startActivity(intent);

            }
        });

//        Now display in textview
        textViewCustomerId.setText(Cid);
        textViewCustomerFName.setText(fname);
        textViewCustomerLName.setText(lname);
        textViewCustomerAddress.setText(address);


        buttonViewAllgivenamountlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent
                Intent intent1 = new Intent(SingleCustomerActivity.this, SingleUserAllGivenAmountList.class);
                intent1.putExtra("cid", Cid);
                startActivity(intent1);
            }
        });


        databaseCId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



              for(DataSnapshot custSnapshot : dataSnapshot.getChildren()){
                    PurchaseDetails purchaseDetails = custSnapshot.getValue(PurchaseDetails.class);
                    ttPamt += Double.parseDouble(purchaseDetails.getPurchaseAmount());

                }
               totalPAMT = Double.toString(ttPamt);
                txtToatlGivenAmt.setText(totalPAMT);
//                dispTPAMT(ttPAmt);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        databaseGivenAMT.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot givenAMTSnapshot : dataSnapshot.getChildren()){
                    GivenAmountDetails givenAmountDetails = givenAMTSnapshot.getValue(GivenAmountDetails.class);
                    ttGAMT += givenAmountDetails.getTodayGiveAmount();
                }

                totalGAMT = Double.toString(ttGAMT);
//                dispGAMT(totalGAMT);
                ttBaki(totalPAMT, totalGAMT );
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TodayGivenAMT = editTextTodayGivenAMT.getText().toString();
                Double todayGAMT = Double.parseDouble(TodayGivenAMT);
                if(editTextTodayGivenAMT.getText().toString().length() == 0 || result == 0.0 || todayGAMT > result){
                    Toast.makeText(getApplicationContext(), "Please Enter valid Amount", Toast.LENGTH_SHORT).show();
                }else {
                    gId = databaseGivenAMT.push().getKey();
                    String date = dt;
                    GivenAmountDetails givenAmountDetails = new GivenAmountDetails(date,Double.parseDouble(TodayGivenAMT));
                    Toast.makeText(SingleCustomerActivity.this, "Amount Added :" + TodayGivenAMT, Toast.LENGTH_SHORT).show();

                    databaseGivenAMT.child(gId).setValue(givenAmountDetails);
                }
            }
        });
    }

    public void ttBaki(String a, String b){

        Double newa = Double.parseDouble(a);
        Double newb = Double.parseDouble(b);
        result = newa - newb;

        Toast.makeText(this, "BAKI " + result, Toast.LENGTH_SHORT).show();
        String baki = Double.toString(result);
        txtBaki.setText(baki);
    }
}
