package com.valeriipopov.wallety.authorizationActivityPack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

import java.util.ArrayList;

public class AuthorizationActivity extends AppCompatActivity {
    public static final String PASSCODE_WRONG = "wrong";

    private char[] mUserCode = new char[5];
    private int count = 0;
    private String mUserPassCode ;
    private EditText mPassCode;
    private DataBaseDbHelper mDataBaseDbHelper;

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButton0;
    private Button mButtonDelete;
    private Button mButtonCancel;
    private ArrayList <Button> mButtonsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        mButton0 = findViewById(R.id.button_0);
        mButton1 = findViewById(R.id.button_1);
        mButton2 = findViewById(R.id.button_2);
        mButton3 = findViewById(R.id.button_3);
        mButton4 = findViewById(R.id.button_4);
        mButton5 = findViewById(R.id.button_5);
        mButton6 = findViewById(R.id.button_6);
        mButton7 = findViewById(R.id.button_7);
        mButton8 = findViewById(R.id.button_8);
        mButton9 = findViewById(R.id.button_9);

        mButtonsList = new ArrayList<>();
        mButtonsList.add(mButton0);
        mButtonsList.add(mButton1);
        mButtonsList.add(mButton2);
        mButtonsList.add(mButton3);
        mButtonsList.add(mButton4);
        mButtonsList.add(mButton5);
        mButtonsList.add(mButton6);
        mButtonsList.add(mButton7);
        mButtonsList.add(mButton8);
        mButtonsList.add(mButton9);
        addClickListener();

        mPassCode = findViewById(R.id.pass_code);
        mPassCode.setFocusableInTouchMode(false);

        mDataBaseDbHelper = new DataBaseDbHelper(getApplicationContext());
        mUserPassCode = mDataBaseDbHelper.getUserPassCode();

        mButtonDelete = findViewById(R.id.button_delete);
        setClickDeleteButtonListener(mButtonDelete);

        mButtonCancel = findViewById(R.id.button_cancel);
        setClickCancelButtonListener(mButtonCancel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void addClickListener() {
        for (Button btn: mButtonsList) {
            clickButton (btn);
        }
    }

    public void clickButton(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < 4) {
                    char ch = button.getText().charAt(0);
                    mUserCode[count] = ch;
                    mPassCode.setText(mUserCode, 0, count+1);
                    count++;
                }
                else {
                    char ch = button.getText().charAt(0);
                    mUserCode[count] = ch;
                    mPassCode.setText(mUserCode, 0, count+1);

                    if (mUserPassCode.equals(String.copyValueOf(mUserCode))) {
                        Toast toast = Toast.makeText(getApplicationContext(), "COMPLETE", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                        AuthorizationActivity.this.finish();
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                        mButtonCancel.callOnClick();
                    }
                }
            }
        });
    }

    public void setClickCancelButtonListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            count = 0;
            mPassCode.setText("");
            }
        });
    }

    public void setClickDeleteButtonListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    count--;
                    mPassCode.setText(mUserCode, 0, count);
                }
            }
        });
    }


}
