package com.valeriipopov.wallety.authorizationActivityPack;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataItemsContract;
import com.valeriipopov.wallety.data.DataItemsDbHelper;
import com.valeriipopov.wallety.data.DataUserContract;
import com.valeriipopov.wallety.data.DataUserDbHelper;

public class NewUserActivity extends AppCompatActivity {

    private EditText mPassCode;
    private Button mAddPassCode;
    private DataUserDbHelper mDataUserDbHelper;
    private SQLiteDatabase mDatabase;
    private String mUserPassCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mDataUserDbHelper = new DataUserDbHelper(getApplicationContext());

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
                    addNewPasscode(mPassCode);
                    NewUserActivity.this.finish();
                }
            }
        });
    }

    private void addNewPasscode(EditText editText) {
        mDatabase = mDataUserDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataUserContract.UserData.COLUMN_PASSCODE, Integer.parseInt(editText.getText().toString()));

        long newRowID = mDatabase.insert(DataUserContract.UserData.TABLE_NAME, null, values);

    }


}
