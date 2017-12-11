package com.valeriipopov.wallety.authorizationActivityPack;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

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

        mPassCode = findViewById(R.id.pass_code);

        mAddPassCode = findViewById(R.id.add_pass_code);
        mAddPassCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserPassCode = mPassCode.getText().toString();
                if (mUserPassCode.length() < 5) {
                    Toast toast = Toast.makeText(NewUserActivity.this, R.string.pass_code_wrong, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    mDataBaseDbHelper.addNewPasscode(mPassCode);
                    NewUserActivity.this.finish();
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
    }




}
