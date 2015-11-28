package com.liam.realfirstdraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.IMediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Intent c = new Intent(getBaseContext(), RecorderPage.class);
                        startActivity(c);
                    }
                }
        );

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
