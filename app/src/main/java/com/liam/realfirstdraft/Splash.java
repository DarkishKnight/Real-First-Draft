package com.liam.realfirstdraft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;


public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.imageView);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.glow);

        rl.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}

            public void onAnimationEnd(Animation animation) {
                finish();
                Intent a = new Intent(getBaseContext(), MainActivity.class);
                startActivity(a);

            }

            public void onAnimationRepeat(Animation animation) {}
        });
    }

}
