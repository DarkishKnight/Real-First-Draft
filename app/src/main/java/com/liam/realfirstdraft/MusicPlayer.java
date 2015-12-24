package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;


public class MusicPlayer extends AppCompatActivity {

    String saveToFileName;
    File songFile;
    MediaPlayer mediaPlayer;


    //protected void saveFile() {

      //  File newFile = new File(songNameText.getParent()+File.separator+saveToFileName);
      //  songNameText.renameTo(newFile);

    protected void renameFile() {

        File newFile = new File(songFile.getParent() + File.separator + saveToFileName);
        songFile.renameTo(newFile);
        // copy tempFile to saveToFileName

        File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
        File[] fileList = extDirectory.listFiles();
        for (File aFileList : fileList) {
            System.out.println(aFileList.getAbsoluteFile());
        }

    }
       // ArrayList<String> fileList; // Initialize all this stuff

        //int getSongPosition(String fileList) {
       //     return fileList.indexOf(category);
       // }
   // }


    public static TextView songNameText;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

      final String id = getIntent().getExtras().getString("listItem");
      songNameText= (TextView) findViewById(R.id.songNameText);
      songNameText.setText(id);
      songFile = new File("id");

      ImageButton backButton2 = (ImageButton) findViewById(R.id.backButton2);

      backButton2.setOnClickListener(
              new ImageButton.OnClickListener()

              {
                  @Override
                  public void onClick(View v) {
                      Intent z = new Intent(getBaseContext(), MusicPage.class);
                      startActivity(z);
                  }
              }

      );


//Button
              ImageButton deleteButton = (ImageButton) findViewById(R.id.deleteButton);
      deleteButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);
                      builder.setMessage("Delete Song");

                      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      });
                          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }


                          });
                  }
              }
      );

      ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
      playButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      ditchMediaPlayer();
                      try {
                          mediaPlayer = new MediaPlayer();
                          mediaPlayer.setDataSource(songFile.getAbsolutePath());
                          mediaPlayer.prepare();
                          mediaPlayer.start();
                      } catch (IOException ioe) {
                          System.out.println(ioe.getMessage());
                      }
                  }
              });


//Botton
                      ImageButton renameButton = (ImageButton) findViewById(R.id.renameButton);
                      final File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");



                      renameButton.setOnClickListener(
                              new ImageButton.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);
                       /* final ArrayList<String> listViewValues = new ArrayList<>();
                        for (File aFileList : fileList) {
                            System.out.println(aFileList.getAbsoluteFile());
                            listViewValues.add(aFileList.getName());
                        }*/
                                      builder.setTitle(id);


                                      final EditText input = new EditText(MusicPlayer.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                      input.setInputType(InputType.TYPE_CLASS_TEXT);
                                      builder.setView(input);

// Set up the buttons
                                      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              saveToFileName = input.getText().toString();
                                              renameFile();
                                              // File (or directory) with old name
                                              songNameText.setText(input.getText());


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
    File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");



    private void ditchMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/*File file = new File(extDirectory + File.separator + id);

// File (or directory) with new name
File file2 = new File(extDirectory + File.separator + String.valueOf(input));

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
        */