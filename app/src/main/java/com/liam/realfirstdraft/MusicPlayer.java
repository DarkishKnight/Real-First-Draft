package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class MusicPlayer extends AppCompatActivity  {

    String saveToFileName;
    File songFile;
    MediaPlayer mediaPlayer;
    File newFile;
    int[] state;
    int maxStates;


    protected void renameFile() {

        newFile = new File(songFile.getParent() + File.separator + saveToFileName);
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
      songFile = new File(extDirectory + File.separator + id);
      newFile = new File(songFile.getParent() + File.separator + saveToFileName);

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
                      AlertDialog.Builder maker = new AlertDialog.Builder(MusicPlayer.this);
                      maker.setMessage("Delete Song");

                      maker.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              songFile.getAbsoluteFile().delete();
                              Intent x = new Intent(getBaseContext(), MusicPage.class);
                              startActivity(x);
                          }
                      });
                          maker.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
                      maker.create();
                      maker.show();
                  }

              }
      );

      //button

      final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
      state = new int[]{0};
      maxStates = 2;

      final int[] finalState = state;
      final int finalMaxStates = maxStates;
      playButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      if (finalState[0] >= finalMaxStates) finalState[0] = 0;
                      switch (finalState[0]++) {
                          case 0:
                              playButton.setBackgroundResource(R.drawable.pause);
                              ditchMediaPlayer();
                              if (songFile.exists())
                                  try {
                                      mediaPlayer = new MediaPlayer();
                                      mediaPlayer.setDataSource(songFile.getAbsolutePath());
                                      mediaPlayer.prepare();
                                      mediaPlayer.start();
                                  } catch (IOException ioe) {
                                      System.out.println(ioe.getMessage());
                                  }

                              else {
                                  try {
                                      mediaPlayer = new MediaPlayer();
                                      mediaPlayer.setDataSource(newFile.getAbsolutePath());
                                      mediaPlayer.prepare();
                                      mediaPlayer.start();
                                  } catch (IOException ioe) {
                                      System.out.println(ioe.getMessage());
                                  }
                              }
                              break;
                          case 1:
                              playButton.setBackgroundResource(R.drawable.play);
                              pauseRecording();
                              break;
                      }

                  }

              });


//Botton
                      ImageButton renameButton = (ImageButton) findViewById(R.id.renameButton);
                      renameButton.setOnClickListener(
                              new ImageButton.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);
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
    private void pauseRecording() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }
}

