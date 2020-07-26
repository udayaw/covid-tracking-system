package com.chathu.covidapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chathu.covidapp.R;
import com.chathu.covidapp.api.APICallBack;
import com.chathu.covidapp.api.APIResponse;
import com.chathu.covidapp.api.RestAPIClient;
import com.chathu.covidapp.model.LoggedInUser;
import com.chathu.covidapp.model.User;
import com.chathu.covidapp.util.SessionManager;

import org.json.JSONException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        final Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setEnabled(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final Button btnRegister = findViewById(R.id.btnRegister);




        final EditText txtUserName = findViewById(R.id.editTextUsername);
        final EditText txtPassword = findViewById(R.id.editTextPassword);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(txtUserName.getText().toString().length() > 0 &&
                        txtPassword.getText().toString().length() > 0
                ){
                    btnRegister.setEnabled(true);
                }else{
                    btnRegister.setEnabled(false);
                }

            }
        };

        txtUserName.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);




        final EditText txtFirstname = findViewById(R.id.editTextFirstName);
        final EditText txtLastName = findViewById(R.id.editTextLastName);
        final EditText txtDob = findViewById(R.id.editTextDOB);
        final EditText txtNIC = findViewById(R.id.editTextNIC);
        final EditText txtPhone = findViewById(R.id.editTextPhone);


        final TextView txtError = findViewById(R.id.txtError);






        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                String firstName = txtFirstname.getText().toString();
                String lastName = txtLastName.getText().toString();
                String dob = txtDob.getText().toString();
                String nic = txtNIC.getText().toString();
                String phone = txtPhone.getText().toString();

                User newUser = new User();
                newUser.setUserName(userName);
                newUser.setPassword(password);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setDOB(dob);
                newUser.setLastName(nic);
                newUser.setPhoneNumber(phone);


                try {
                    new RestAPIClient().registerUser(newUser, new APICallBack() {
                        @Override
                        public void onComplete(APIResponse response) {
                            if (response.getStatus() == APIResponse.APIResponseStatus.SUCCESS) {


                                Log.w("Passseddd","Createddddd");
                                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(mainIntent);
                            } else {
                                txtError.setText("Failed to Register!!!");
                            }
                        }
                    });
                } catch (JSONException e) {
                    txtError.setText("Failed to Register!!!");
                }

            }

        });
    }
}