package com.liam.realfirstdraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
       // Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.unglow);
       // rl.startAnimation(an2);

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

       ImageButton centerButton = (ImageButton) findViewById(R.id.centerButton);

        centerButton.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        Intent d = new Intent(getBaseContext(), SheetMusic.class);
                        startActivity(d);
                    }
                }
        );

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
