package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Created by sanjayshr on 5/7/17.
 * Its a list activity
 */

public class SingleUserAllGivenAmountList extends AppCompatActivity {



//    Used for next intent to show these
    public static final String G_ID = "amountgivenid";
    public static final String G_AMOUNT = "givenamount";
    public static final String G_DATE = "givenamountdate";
    String c_id = null;
    String p_id = null;
    String fname, lname, address;


    DatabaseReference databaseGivemAmountList, databaseCustomer, databaseToatlGivenAmount;

    TextView txtFname,txtLname,txtAddress, txtTotalGivenAmount;
    ListView dateList;
    List<GivenAmountDetails> giveAmountDateList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_user_all_givenamount_list);

        txtFname = (TextView)findViewById(R.id.textViewFName);
        txtLname = (TextView)findViewById(R.id.textViewLName);
        txtAddress = (TextView)findViewById(R.id.textViewAddress);
        txtTotalGivenAmount = (TextView)findViewById(R.id.textViewTotalGivenAmount);

        Intent intent = getIntent();
        c_id = intent.getStringExtra("cid");
        p_id = intent.getStringExtra("pid");


        databaseGivemAmountList = FirebaseDatabase.getInstance().getReference("giveamount").child(c_id);
        databaseCustomer = FirebaseDatabase.getInstance().getReference("customers").child(c_id);
        databaseToatlGivenAmount = FirebaseDatabase.getInstance().getReference("giveamount").child(c_id);

        dateList = (ListView)findViewById(R.id.listViewSingleUserAllGivenAmountList);
        giveAmountDateList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseGivemAmountList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giveAmountDateList.clear();

                for(DataSnapshot givenDataSnapshot : dataSnapshot.getChildren()){
                    GivenAmountDetails givenAmountDetails   = givenDataSnapshot.getValue(GivenAmountDetails.class);

                    giveAmountDateList.add(givenAmountDetails);
                }

//                adapter
                GiveAmountList adapter = new GiveAmountList(SingleUserAllGivenAmountList.this, giveAmountDateList);
                dateList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Get customer name
        databaseCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Customers customers = dataSnapshot.getValue(Customers.class);
                fname = customers.getCustomerFName();
                lname = customers.getCustomerLName();
                address = customers.getCustomerAddress();

                txtFname.setText(fname);
                txtLname.setText(lname);
                txtAddress.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseToatlGivenAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Double totalGivenAmount = 0.0;

                for(DataSnapshot givenDataSnapshot : dataSnapshot.getChildren()){
                    GivenAmountDetails givenAmountDetails   = givenDataSnapshot.getValue(GivenAmountDetails.class);

                    totalGivenAmount += givenAmountDetails.getTodayGiveAmount();
                }

                txtTotalGivenAmount.setText(String.valueOf(totalGivenAmount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
