package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sanjayshr on 1/7/17.
 */

public class FirstCustomer extends AppCompatActivity {

    Button addCustomerButton;
    Button viewCustomers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_customer_activity);

        addCustomerButton = (Button) findViewById(R.id.addCustomer);
        viewCustomers = (Button) findViewById(R.id.viewCustomers);


        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirstCustomer.this, RegisterCustomer.class);
                startActivity(intent);
            }
        });

        viewCustomers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstCustomer.this, ViewCustomersList.class);
                startActivity(intent);

            }
        });

    }
}
