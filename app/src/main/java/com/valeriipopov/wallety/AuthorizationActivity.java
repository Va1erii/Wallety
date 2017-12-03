package com.valeriipopov.wallety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AuthorizationActivity extends AppCompatActivity {
    private static final String PASSCODE = "12345";
    private char[] mPassChecker = new char[5];
    private char[] mGhostPassChecker = new char[5];
    private int count = 0;
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

    private TextView mPassCode;

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
        addClickListener(mButtonsList);

        mPassCode = findViewById(R.id.pass_code);

        mButtonDelete = findViewById(R.id.button_delete);
        setClickDeleteButtonListener(mButtonDelete);

        mButtonCancel = findViewById(R.id.button_cancel);
        setClickCancelButtonListener(mButtonCancel);
    }

    public void addClickListener(ArrayList <Button> buttons) {
        for (Button btn: mButtonsList) {
            clickButton (btn);
        }
    }

    public void clickButton(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                char ch = button.getText().charAt(0);
                if (count < PASSCODE.length() - 1) {
                    mPassChecker[count] = ch;
                    mGhostPassChecker[count] = '*';
                    mPassCode.setText(mGhostPassChecker,0, mGhostPassChecker.length);
                    count++;
                }
                else {
                    mPassChecker[count] = ch;
                    mGhostPassChecker[count] = '*';
                    mPassCode.setText(mGhostPassChecker,0, mGhostPassChecker.length);
                    if (String.valueOf(mPassChecker).equals(PASSCODE)){
                        Toast toast = Toast.makeText(AuthorizationActivity.this, "COMPLETE", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(AuthorizationActivity.this, "WRONG", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();

                        for (int i = 0; i < mPassChecker.length; i++) {
                            mPassChecker[i] = ' ';
                            mGhostPassChecker[i] = ' ';
                        }
                        count = 0;
                        mPassCode.setText(mGhostPassChecker, 0, mPassChecker.length);
                    }
                }
            }
        });
    }

    public void setClickCancelButtonListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mPassChecker.length; i++) {
                    mPassChecker[i] = ' ';
                    mGhostPassChecker[i] = ' ';
                }
                count = 0;
                mPassCode.setText(mGhostPassChecker, 0, 5);
            }
        });
    }

    public void setClickDeleteButtonListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count != 0)
                    count--;
                mPassChecker[count] = ' ';
                mGhostPassChecker[count] = ' ';
                mPassCode.setText(mGhostPassChecker, 0, 5);
            }
        });
    }
}
