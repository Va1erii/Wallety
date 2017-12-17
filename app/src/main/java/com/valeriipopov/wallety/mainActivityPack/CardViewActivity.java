package com.valeriipopov.wallety.mainActivityPack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.valeriipopov.wallety.R;

/**
 * CardView is our item's card
 */

public class CardViewActivity extends AppCompatActivity {
    CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardView = findViewById(R.id.card_view);
        setContentView(mCardView);
    }
}
