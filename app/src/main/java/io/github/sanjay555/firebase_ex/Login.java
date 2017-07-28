package io.github.sanjay555.firebase_ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sanjayshr on 1/7/17.
 */

public class Login extends AppCompatActivity {


    EditText userName;
    EditText password;
    Button loginButton;
//    TextView
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.LoginButton);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String uname = "";
                String pass = "";
                if(uname.equals(userName.getText().toString()) && pass.equals(password.getText().toString())){
                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, FirstCustomer.class);
                    startActivity(intent);


                }else {
                    Toast.makeText(Login.this, "Entered user-name/password is wrong", Toast.LENGTH_LONG).show();
                }

            }
        });
}

}
