package com.soft.big.bigrest.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.soft.big.bigrest.Behaviors.Constants;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.UserService;
import java.sql.Connection;

public class LoginActivity extends AppCompatActivity {

    EditText mUsernameEditText, mPasswordEditText;
    FrameLayout mProgressFrameLayout;

    String mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindActivity();

    }

    private void bindActivity(){

        mUsernameEditText = findViewById(R.id.et_username_login);
        mPasswordEditText = findViewById(R.id.et_password_login);
        mProgressFrameLayout = findViewById(R.id.progress_login);
    }

    public void connect(View view){

        mUsername = mUsernameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();


        AsyncLogin asyncLogin = new AsyncLogin();
        asyncLogin.execute(mUsername, mPassword);
    }

    private void goToTableActivity(String username){
        Intent intent = new Intent(this, TablesActivity.class);
        intent.putExtra(Constants.USER_NAME_EXTRA_MESSAGE, username);
        startActivity(intent);
    }

    /**
     * Connection in Async way to get the user more confeteble
     */

    class AsyncLogin extends AsyncTask<String, String, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            Connection connection = DatabaseAccess.databaseConnection(LoginActivity.this);
            return UserService.login(connection, username, password);
        }

        @Override
        protected void onPostExecute(Boolean login) {
            super.onPostExecute(login);
            mProgressFrameLayout.setVisibility(View.GONE);
            if(login) {
               goToTableActivity(mUsername);
            }else {
                Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();

            }

        }
    }


    public void onConfigClicked(View view){
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }
}
