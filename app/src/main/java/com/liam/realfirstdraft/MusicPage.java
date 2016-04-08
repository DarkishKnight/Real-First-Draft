package com.liam.realfirstdraft;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;


public class MusicPage extends AppCompatActivity {
    ListView musiclist;
    File[] fileList;
    String listFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);
        init_phone_music_grid();
        final ImageButton backButton1 = (ImageButton) findViewById(R.id.backButton1);

        backButton1.setOnClickListener(
                new ImageButton.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(e);
                    }
                }

        );

    }


    private void init_phone_music_grid() {
        System.gc();
        musiclist = (ListView) findViewById(R.id.listView);


        File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
        fileList = extDirectory.listFiles();







        ArrayList<String> listViewValues = new ArrayList<>();
        for (File aFileList : fileList) {
            listViewValues.add(aFileList.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_textview,
                listViewValues.toArray(new String[listViewValues.size()]));
        musiclist.setAdapter(adapter);

        musiclist.setOnItemClickListener(musicgridlistener);
    }







    protected AdapterView.OnItemClickListener musicgridlistener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            System.gc();
            Bundle b = new Bundle();
            File selectedFile = fileList[((int) id)];
            listFile = selectedFile.getName();
            b.putString("listItem", listFile);
            Intent g = new Intent(getBaseContext(), MusicPlayer.class);
            g.putExtras(b);
            startActivity(g);

        }
    };
        }








