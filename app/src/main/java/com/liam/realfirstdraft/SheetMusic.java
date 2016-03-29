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

public class SheetMusic extends AppCompatActivity {
    ListView sheetMusic;
    File[] fileList;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_music);

        init_musicnote_list();


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

    private void init_musicnote_list() {
        System.gc();
        sheetMusic = (ListView) findViewById(R.id.sheetList);


        File noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
        fileList = noteDir.listFiles();







        ArrayList<String> listViewValues = new ArrayList<>();
        for (File aFileList : fileList) {
            listViewValues.add(aFileList.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_textview,
                listViewValues.toArray(new String[listViewValues.size()]));
        sheetMusic.setAdapter(adapter);

        sheetMusic.setOnItemClickListener(noteListener);
    }







    protected AdapterView.OnItemClickListener noteListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            System.gc();
            Bundle b = new Bundle();
            File selectedFile = fileList[((int) id)];
            fileName = selectedFile.getName();
            b.putString("listItem", fileName);
            Intent g = new Intent(getBaseContext(), NotePage.class);
            g.putExtras(b);
            startActivity(g);

        }
    };
}


