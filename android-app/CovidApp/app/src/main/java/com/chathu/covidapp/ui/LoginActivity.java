package com.chathu.covidapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chathu.covidapp.R;
import com.chathu.covidapp.api.APICallBack;
import com.chathu.covidapp.api.APIResponse;
import com.chathu.covidapp.api.RestAPIClient;
import com.chathu.covidapp.model.LoggedInUser;
import com.chathu.covidapp.util.SessionManager;

import org.json.JSONException;


public class LoginActivity extends AppCompatActivity {

    //private LoginViewModel loginViewModel;

//    @Override
//    public void onStart(){
//        super.onStart();
//
//        if(SessionManager.getInstance().getLoggedInUser() != null){
//            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
//            LoginActivity.this.startActivity(mainIntent);
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView loginFailedTextView = findViewById(R.id.loginFailed);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(usernameEditText.getText().toString().length() > 0 &&
                passwordEditText.getText().toString().length() > 0
                ){
                    loginButton.setEnabled(true);
                }else{
                    loginButton.setEnabled(false);
                }

            }
        };

        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                String userName = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try {
                    new RestAPIClient().tryLogin(userName, password, new APICallBack() {
                        @Override
                        public void onComplete(APIResponse response) {
                            Toast.makeText(getApplicationContext(),"Login Complete",Toast.LENGTH_LONG);
                            if(response.getStatus() == APIResponse.APIResponseStatus.SUCCESS){
                                SessionManager.getInstance().setLoggedInUser((LoggedInUser) response.getBody());
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                loginFailedTextView.setVisibility(View.INVISIBLE);

                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(mainIntent);
                            }else{
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                loginFailedTextView.setVisibility(View.VISIBLE);

                                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG);
                            }
                        }
                    });
                } catch (JSONException e) {
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    loginFailedTextView.setVisibility(View.VISIBLE);
                }
            }
        });


        final TextView txtRegister = findViewById(R.id.textViewRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(mainIntent);
            }
        });

    }


}