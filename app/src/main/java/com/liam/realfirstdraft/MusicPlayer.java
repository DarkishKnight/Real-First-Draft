package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayer extends AppCompatActivity {

    String saveToFileName;
    File songNameText;
    int getSongPosition;

    //protected void saveFile() {

      //  File newFile = new File(songNameText.getParent()+File.separator+saveToFileName);
      //  songNameText.renameTo(newFile);

     //  File extDirectory = new File(Environment.getExternalStorageDirectory(),"Humposer");
      //  File[] fileList = extDirectory.listFiles();
    //  for (int i=0; i<fileList.length; i++) {
     //       System.out.println(fileList[i].getAbsoluteFile());
    //   }
       // ArrayList<String> fileList; // Initialize all this stuff

        //int getSongPosition(String fileList) {
       //     return fileList.indexOf(category);
       // }
   // }
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        final TextView songNameText = (TextView) findViewById(R.id.songNameText);

        ImageButton renameButton = (ImageButton) findViewById(R.id.renameButton);

        renameButton.setOnClickListener(
                new ImageButton.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);
                        builder.setTitle("Song Title Here");

                        final EditText input = new EditText(MusicPlayer.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

// Set up the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveToFileName = input.getText().toString();
                          //      saveFile();
                                // File (or directory) with old name
                                songNameText.setText(input.getText());

                                File file = new File(String.valueOf((songNameText)));

// File (or directory) with new name
                                File file2 = new File(saveToFileName);

                                if (file2.exists())
                                    try {
                                        throw new IOException("file exists");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

// Rename file (or directory)
                                boolean success = file.renameTo(file2);

                                if (!success) {
                                    // File was not successfully renamed
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    }

                }
        );
    }



}
