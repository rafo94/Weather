package com.example.lland;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LLandActivity extends AppCompatActivity {
    LLand mLand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lland);
        mLand =  findViewById(R.id.world);
        mLand.setScoreField((TextView) findViewById(R.id.score));
        mLand.setSplash(findViewById(R.id.welcome));
    }

    @Override
    public void onPause() {
        mLand.stop();
        super.onPause();
    }
}
