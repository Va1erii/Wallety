package com.valeriipopov.wallety.authorizationActivityPack;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

/**
 * This class add new password into SQL database
 */

public class NewUserActivity extends AppCompatActivity {

    private EditText mPassCode;
    private Button mAddPassCode;
    private DataBaseDbHelper mDataBaseDbHelper;
    private String mUserPassCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mDataBaseDbHelper = new DataBaseDbHelper(getApplicationContext());
        // DataBaseDbHelper is class which help to connect with database
        // More information in class DataBaseDbHelper

        mPassCode = findViewById(R.id.pass_code);

        mAddPassCode = findViewById(R.id.add_pass_code);
        mAddPassCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserPassCode = mPassCode.getText().toString();
                if (mUserPassCode.length() < 5) {
                    Toast toast = Toast.makeText(NewUserActivity.this, R.string.pass_code_wrong, Toast.LENGTH_SHORT);
                    toast.show();
                    // When we write new password we check password. Password should have 5 numbers
                    // If password's length is short we show Toast about it
                }
                else {
                    mDataBaseDbHelper.addNewPasscode(mPassCode.getText().toString());
                    NewUserActivity.this.finish();
                    // If password is right we add new password into database
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        // If we click back button on phone, we exit from this app, if we not have this method
        // we can use back button and turn to MainActivity, but it is not right
    }
}
