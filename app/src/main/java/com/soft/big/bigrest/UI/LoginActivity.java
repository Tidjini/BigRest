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
    FrameLayout mConnectionErrorFrame;
    String mUsername, mPassword;
    int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindActivity();

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet_land);
        if (tabletSize) {
            // do something
            AsyncConnectionTest asyncConnectionTest = new AsyncConnectionTest();
            asyncConnectionTest.execute("");

        }

    }

    private void bindActivity(){

        mUsernameEditText = findViewById(R.id.et_username_login);
        mPasswordEditText = findViewById(R.id.et_password_login);
        mProgressFrameLayout = findViewById(R.id.progress_login);
        mConnectionErrorFrame = findViewById(R.id.connection_error);
    }

    public void connect(View view){

        mUsername = mUsernameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();


        if(mConnectionErrorFrame.getVisibility() == View.GONE){
            AsyncLogin asyncLogin = new AsyncLogin();
            asyncLogin.execute(mUsername, mPassword);
        }

    }

    private void goToTableActivity(String username, int userId){
        Intent intent = new Intent(this, TablesActivity.class);
        intent.putExtra(Constants.USER_NAME_EXTRA_MESSAGE, username);
        intent.putExtra(Constants.USER_ID_EXTRA_MESSAGE, userId);
        startActivity(intent);
    }

    /**
     * Connection in Async way to get the user more confeteble
     */

    class AsyncLogin extends AsyncTask<String, String, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            Connection connection = DatabaseAccess.databaseConnection(LoginActivity.this);
            if(connection == null) return 0;
            return UserService.login(connection, username, password);
        }

        @Override
        protected void onPostExecute(Integer id) {
            super.onPostExecute(id);
            mProgressFrameLayout.setVisibility(View.GONE);
            if(id == 0)  mConnectionErrorFrame.setVisibility(View.VISIBLE);
            else {
                if(id != 0) {
                    goToTableActivity(mUsername, id);
                }else {
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();

                }
            }


        }
    }


    public void onConfigClicked(View view){
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }

    class AsyncConnectionTest extends AsyncTask<String, String, Connection>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mUsernameEditText.setEnabled(false);
            mPasswordEditText.setEnabled(false);
            mProgressFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Connection doInBackground(String... strings) {

            return DatabaseAccess.databaseConnection(LoginActivity.this);

        }

        @Override
        protected void onPostExecute(Connection connection) {
            super.onPostExecute(connection);
            mProgressFrameLayout.setVisibility(View.GONE);
            if(connection == null) {
                mConnectionErrorFrame.setVisibility(View.VISIBLE);

            }else {
                mConnectionErrorFrame.setVisibility(View.GONE);
                mUsernameEditText.setEnabled(true);
                mPasswordEditText.setEnabled(true);
            }

        }
    }

}
