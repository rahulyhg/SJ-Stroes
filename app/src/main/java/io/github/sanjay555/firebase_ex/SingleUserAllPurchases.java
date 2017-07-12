package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.sanjay555.firebase_ex.R;

/**
 * Created by sanjayshr on 3/7/17.
 * Its list activity
 * This module for onclick open new activity. and values are passed from this module
 */
public class SingleUserAllPurchases extends AppCompatActivity {

    public static final String P_ID = "purchaseid";
    public  static final String P_AMMOUNT = "purchaseammount";
    public static final String P_DATE = "purchasedate";
    public  static final String C_ID = "customerid";



    DatabaseReference databaseCustomers;

    TextView txtFname,txtLname,txtAddress, txtCId;
    ListView dateList;
    List<PurchaseDetails> purchaseDateList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_user_all_purchase_list);




        txtFname=(TextView)findViewById(R.id.txtFname);
        txtLname=(TextView)findViewById(R.id.txtLname);
        txtAddress=(TextView)findViewById(R.id.txtAddress);

        Intent intent = getIntent();
        String c_id = intent.getStringExtra("id");
        String first_name=intent.getStringExtra("fname");
        String Last_name=intent.getStringExtra("lname");
        String address_=intent.getStringExtra("address");


        databaseCustomers = FirebaseDatabase.getInstance().getReference("purchaseDetails").child(c_id);

        txtFname.setText(first_name);
        txtLname.setText(Last_name);
        txtAddress.setText(address_);


        dateList=(ListView)findViewById(R.id.listViewsingleUserAllPurchaseDateList);
        purchaseDateList = new ArrayList<>();

        dateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Get select purchase details from date list
                PurchaseDetails purchaseDetails = purchaseDateList.get(i); // selected position

//                Create new Intent
                Intent intent = new Intent(getApplicationContext(), SinglePurchaseDetailsActivity.class);

//                Pass data to view on next intent(deatails)
                intent.putExtra(P_DATE, purchaseDetails.getDates());
                intent.putExtra(P_ID, purchaseDetails.getPurchaseId());
                intent.putExtra(P_AMMOUNT, purchaseDetails.getPurchaseAmount());
                intent.putExtra(C_ID, purchaseDetails.getCustid());

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

//       Try to validate : if purchase list is empty , toast : purchase list is empty
//            Toast.makeText(this, "Not Purchases No Transactions", Toast.LENGTH_SHORT).show();

            databaseCustomers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    purchaseDateList.clear();
                    for (DataSnapshot purchaseDateSnapShot : dataSnapshot.getChildren()) {
                        PurchaseDetails purchaseDetails = purchaseDateSnapShot.getValue(PurchaseDetails.class);

                        purchaseDateList.add(purchaseDetails);
                    }

                    PurchaseDataListClass adapter = new PurchaseDataListClass(SingleUserAllPurchases.this, purchaseDateList);
                    dateList.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
}

