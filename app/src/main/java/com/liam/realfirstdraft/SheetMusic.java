package com.liam.realfirstdraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class SheetMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_music);


        ImageButton backButton3 = (ImageButton) findViewById(R.id.backButton3);

        backButton3.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent g = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(g);
                    }
                }
        );

    }

}
