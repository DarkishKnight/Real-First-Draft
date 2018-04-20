package com.liam.realfirstdraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button to send to recorded music page
       ImageButton bottomButton = (ImageButton) findViewById(R.id.buttomButton);
        bottomButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent b = new Intent(getBaseContext(), MusicPage.class);
                        startActivity(b);
                    }
                }
        );

       //button to send to sheet music page
       ImageButton centerButton = (ImageButton) findViewById(R.id.centerButton);
        centerButton.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        Intent d = new Intent(getBaseContext(), SheetMusic.class);
                        startActivity(d);
                    }
                }
        );

        //button to take you to recorder page
        ImageButton topButton = (ImageButton) findViewById(R.id.topButton);
        topButton.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v) {
                        Intent c = new Intent(getBaseContext(), AudioRecordPages.class);
                        startActivity(c);
                    }
                }
        );

    }


    //encase stuff goes south
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.mainLayout));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

}
