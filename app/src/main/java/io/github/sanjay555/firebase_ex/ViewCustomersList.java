package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjayshr on 1/7/17.
 */

public class ViewCustomersList extends AppCompatActivity {

    public static final String CUSTOMER_ID = "customerid";
    public static final String CUSTOMER_FNAME = "customerfname";
    public  static final String CUSTOMER_LNAME = "customerlname";
    public static final String CUSTOMER_ADDRESS = "customeraddress";

    //    Create DB reference
    DatabaseReference databaseCustomers;

    ListView listViewCustomers;
    List<Customers> customersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_customers);

        // Get DB reference

        databaseCustomers = FirebaseDatabase.getInstance().getReference("customers");

        listViewCustomers = (ListView) findViewById(R.id.listViewAllCustomers);
        customersList = new ArrayList<>();


        listViewCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Get selected customer for list
                Customers customers = customersList.get(i); // selected position

//                Create new intent
                Intent intent = new Intent(getApplicationContext(), SingleCustomerActivity.class);

                intent.putExtra(CUSTOMER_ID, customers.getCustomerId());
                intent.putExtra(CUSTOMER_FNAME, customers.getCustomerFName());
                intent.putExtra(CUSTOMER_LNAME, customers.getCustomerLName());
                intent.putExtra(CUSTOMER_ADDRESS, customers.getCustomerAddress());

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                customersList.clear();

                for(DataSnapshot customerSnapshot : dataSnapshot.getChildren()){
                    Customers customers = customerSnapshot.getValue(Customers.class);

                    customersList.add(customers);
                }

                CustomerList adapter = new CustomerList(ViewCustomersList.this, customersList);
                listViewCustomers.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
