//Single customer account information/operation

package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SingleCustomerActivity extends AppCompatActivity {

    String cust_id;
    String id;
//    String p_id;

    Double totalGiveamt = 0.0;

    TextView textViewCustomerId;
    TextView textViewCustomerFName;
    TextView textViewCustomerLName;
    TextView textViewCustomerAddress;
    TextView textViewRemainingAmount;

    Button addnewPurchase;
    Button viewAllPurchages;


    DatabaseReference databaseGetPId, databaseGivenAmount, databaseGetPTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer);

        textViewCustomerId = (TextView) findViewById(R.id.textViewCustomerId);
        textViewCustomerFName = (TextView) findViewById(R.id.textViewFName);
        textViewCustomerLName = (TextView) findViewById(R.id.textViewLName);
        textViewCustomerAddress = (TextView) findViewById(R.id.textViewAddress);

        addnewPurchase = (Button) findViewById(R.id.buttonAddPurchase);
        viewAllPurchages = (Button) findViewById(R.id.buttonViewAllPurchases);


//        Intent
        Intent intent = getIntent();

        id = intent.getStringExtra(ViewCustomersList.CUSTOMER_ID);
        String fname = intent.getStringExtra(ViewCustomersList.CUSTOMER_FNAME);
        String lname = intent.getStringExtra(ViewCustomersList.CUSTOMER_LNAME);
        String address = intent.getStringExtra(ViewCustomersList.CUSTOMER_ADDRESS);

//        Databsse reference..

        databaseGetPId = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(id);

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
        textViewCustomerId.setText(id);
        textViewCustomerFName.setText(fname);
        textViewCustomerLName.setText(lname);
        textViewCustomerAddress.setText(address);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        Get total purchased amount
        databaseGetPId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Double totalPurchaseAmount = 0.0;

                for(DataSnapshot getP_Id : dataSnapshot.getChildren()){
                    PurchaseDetails purchaseDetails =  getP_Id.getValue(PurchaseDetails.class);
//                    totalPurchaseAmount = Double.parseDouble( purchaseDetails.getPurchaseAmount());
                    String p_id;
                    p_id = purchaseDetails.getPurchaseId();

//                    Get p_amount

                    databaseGetPTotalAmount = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(id).child(p_id);

                    databaseGetPTotalAmount.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Double totalPurchaseAmount = 0.0;
                            for (DataSnapshot totalPAmountDataSnapshot : dataSnapshot.getChildren() ){
                                PurchaseDetails purchaseDetail = totalPAmountDataSnapshot.getValue(PurchaseDetails.class);
                                totalPurchaseAmount += Double.parseDouble( purchaseDetail.getPurchaseAmount());
                            }

                            Toast.makeText(SingleCustomerActivity.this, "ttPamt :" + totalPurchaseAmount, Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

















//
//        databaseGetPId.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                for(DataSnapshot purchaseIdDataSnapshot : dataSnapshot.getChildren()){
//                    //        Inside P_ID(PurchaseID)
//                    PurchaseDetails purchaseDetails = purchaseIdDataSnapshot.getValue(PurchaseDetails.class);
//                    p_id =purchaseDetails.getPurchaseId();
//
//                    Toast.makeText(SingleCustomerActivity.this, "pid" + p_id, Toast.LENGTH_SHORT).show();
//
//
//                    databaseGivenAmount = FirebaseDatabase.getInstance().getReference("giveamount").child(id).child(p_id);
//                    databaseGivenAmount.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            Toast.makeText(SingleCustomerActivity.this, "B-totalGiveamt :" + totalGiveamt, Toast.LENGTH_SHORT).show();
//
//
//                            for (DataSnapshot todayGivenDataSnapshot : dataSnapshot.getChildren()){
////                                Inside G_ID(GiveID)
//                                GivenAmountDetails giverAmount = todayGivenDataSnapshot.getValue(GivenAmountDetails.class);
////                                Add all given amount of a one(2,3,..,n) purchase
//                                totalGiveamt += giverAmount.getTodayGiveAmount();
//                            }
//                            Toast.makeText(SingleCustomerActivity.this, "A-totalGiveamt :" + totalGiveamt, Toast.LENGTH_SHORT).show();
//
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
                }
}
