package com.liam.realfirstdraft;


import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
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
    ArrayList listViewValues;
    String listFile;
    String fileName;
    int v;


    protected void renameFile() {

      File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
      fileList = extDirectory.listFiles();
      for (int i = 0; i < fileList.length; i++) {
          System.out.println(fileList[i].getAbsoluteFile());
      }

  }



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
       // String fileName =  listFile.substring(listFile.lastIndexOf('\\'), listFile.indexOf(' '));
        musiclist = (ListView) findViewById(R.id.listView);


        File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
        fileList = extDirectory.listFiles();







        ArrayList<String> listViewValues = new ArrayList<>();
        for (File aFileList : fileList) {
            System.out.println(aFileList.getAbsoluteFile());
            listViewValues.add(aFileList.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_textview,
                //android.R.layout.simple_list_item_2, android.R.id.text1,
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
            //listFile = String.valueOf(fileList[((int) id)]);
           // fileName = listFile.substring(listFile.lastIndexOf('\\'), listFile.indexOf(' '));
           // String extractedName = listFile.substring(listFile.lastIndexOf('\\'), listFile.indexOf(' '));
          //  b.putString("listItem", String.valueOf(selectedFile));
            b.putString("listItem", listFile);
            //b.putString("listItem", fileName);
            Intent g = new Intent(getBaseContext(), MusicPlayer.class);
            g.putExtras(b);
            startActivity(g);

        }
    };
        }








