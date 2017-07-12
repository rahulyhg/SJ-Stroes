package io.github.sanjay555.firebase_ex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sanjayshr on 1/7/17.
 */

public class RegisterCustomer extends AppCompatActivity {

    EditText fName;
    EditText lName;
    EditText phNo;
    EditText address;
    Button submit;
    Button clear;

    //    Create DB reference
    DatabaseReference databaseCustomers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_customer_activity);

        // Get DB reference

        databaseCustomers = FirebaseDatabase.getInstance().getReference("customers");

        fName = (EditText) findViewById(R.id.editTextFName);
        lName = (EditText) findViewById(R.id.editTextLName);
        phNo = (EditText) findViewById(R.id.editTextPhNo);
        address = (EditText) findViewById(R.id.editTextAddress);
        submit = (Button) findViewById(R.id.buttonSubmit);
        clear = (Button) findViewById(R.id.buttonClear);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerCustomer();
            }
        });



    }

    private void registerCustomer(){

        String fname = fName.getText().toString().trim();
        String lname = lName.getText().toString().trim();
        String phno = phNo.getText().toString().trim();
        String Caddress = address.getText().toString().trim();

        if(!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(phno)
                && !TextUtils.isEmpty(Caddress)){

//            Generate FB unique key/id
            String id = databaseCustomers.push().getKey();

//            Create new Customer
            Customers customers = new Customers(id, fname, lname, phno, Caddress);

//            Store data into firebase
            databaseCustomers.child(id).setValue(customers);
            Toast.makeText(this, " Customer Added Successfully", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Error!!! Enter all details", Toast.LENGTH_SHORT).show();
        }
    }
}
