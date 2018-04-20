package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MusicPlayer extends AppCompatActivity {
    private String saveToFileName;
    private File songFile;
    private MediaPlayer mediaPlayer;
    private File newFile;
    private File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
    private  TextView songNameText;
    private ArrayList<Integer> noteInfo = new ArrayList<>();
    private TextView hold;
    private static final double[] FREQUENCIES = { 174.61, 164.81, 155.56, 146.83, 138.59, 130.81, 123.47, 116.54, 110.00, 103.83, 98.00, 92.50, 87.31, 82.41, 77.78};
    private static final String[] NAME = { "F",    "E",    "D#",   "D",    "C#",   "C",    "B",    "A#",   "A",    "G#",   "G",   "F#",  "F",   "E",   "D#"};
    File file;
    String name;



    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_music_player);

      hold = (TextView) findViewById(R.id.pleaseHold);
      assert hold != null;
      hold.setVisibility(View.INVISIBLE);

      //get the info sent over from the music page
      final String id = getIntent().getExtras().getString("listItem");
      songNameText = (TextView) findViewById(R.id.songNameText);
      assert songNameText != null;
      songNameText.setText(id);
      songFile = new File(extDirectory + File.separator + id);
      newFile = new File(songFile.getParent() + File.separator + saveToFileName);

      //button to take you back to the main activity
      ImageButton backButton = (ImageButton) findViewById(R.id.backButton2);
      assert backButton != null;
      backButton.setOnClickListener(
              new ImageButton.OnClickListener()
              {
                  public void onClick(View v) {
                      Intent z = new Intent(getBaseContext(), MainActivity.class);
                      startActivity(z);
                  }
              }
      );

      //button to take you to music page
      ImageButton musicPage = (ImageButton) findViewById(R.id.torecorderpage);
      assert musicPage != null;
      musicPage.setOnClickListener(
              new ImageButton.OnClickListener()
              {
                  public void onClick(View v) {
                      Intent q = new Intent(getBaseContext(), AudioRecordPages.class);
                      startActivity(q);
                  }
              }
      );

      //delete button
      ImageButton deleteButton = (ImageButton) findViewById(R.id.deleteButton);
      assert deleteButton != null;
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
                              if (mediaPlayer != null) {
                                  if (mediaPlayer.isPlaying()) {
                                      mediaPlayer.stop();
                                      startActivity(x);
                                  }
                              }
                              else{
                                  startActivity(x);
                              }
                          }
                      });
                      maker.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                          }
                      });
                      maker.create();
                      maker.show();
                  }

              }
      );

      //play button
      final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
      int[] state = new int[]{0};
      int maxStates = 2;

      final int[] finalState = state;
      final int finalMaxStates = maxStates;
      assert playButton != null;
      playButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      if (finalState[0] >= finalMaxStates) finalState[0] = 0;
                      switch (finalState[0]++) {
                          case 0:
                              playButton.setBackgroundResource(R.drawable.pauseicon);
                              ditchMediaPlayer();
                              if (songFile.exists()) {
                                  try {
                                      mediaPlayer = new MediaPlayer();
                                      mediaPlayer.setDataSource(songFile.getAbsolutePath());
                                      mediaPlayer.prepare();
                                      mediaPlayer.start();
                                  } catch (IOException ioe) {
                                      System.out.println(ioe.getMessage());
                                  }
                              } else {
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
                              pauseRecording();
                              playButton.setBackgroundResource(R.drawable.playback);
                              break;

                      }
                      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                          public void onCompletion(MediaPlayer mp) {
                              playButton.setBackgroundResource(R.drawable.playback);
                          }
                      });

                      }
              });

      //Botton to rename song
      ImageButton renameButton = (ImageButton) findViewById(R.id.renameButton);
      assert renameButton != null;
      renameButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  public void onClick(final View v) {
                      AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);
                      builder.setTitle(id);

                      final EditText input = new EditText(MusicPlayer.this);
                      input.setInputType(InputType.TYPE_CLASS_TEXT);
                      builder.setView(input);
                      input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                          public void onFocusChange(View arg0, boolean arg1) {
                              InputMethodManager inputMgr = (InputMethodManager) v.getContext().
                                      getSystemService(Context.INPUT_METHOD_SERVICE);
                              inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                          }
                      });
                      //ok button
                      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              saveToFileName = input.getText().toString();
                              renameFile();
                              // File (or directory) with old name
                              songNameText.setText(input.getText());
                              input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                  public void onFocusChange(View arg0, boolean arg1) {
                                      InputMethodManager inputMgnrs = (InputMethodManager) v.getContext().
                                              getSystemService(Context.INPUT_METHOD_SERVICE);
                                      inputMgnrs.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                  }
                              });
                              dialog.dismiss();
                          }
                      });
                      //cancel button
                      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                              input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                  public void onFocusChange(View arg0, boolean arg1) {
                                      InputMethodManager inputMgnr = (InputMethodManager) v.getContext().
                                              getSystemService(Context.INPUT_METHOD_SERVICE);
                                      inputMgnr.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                  }
                              });
                          }
                      });

                      builder.show();
                  }

              }
      );
      //button to analyze the sound
      ImageButton AutoCorrelateButton = (ImageButton) findViewById(R.id.fftButton);
      assert AutoCorrelateButton != null;
        AutoCorrelateButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  public void onClick(View v) {
                      hold.setVisibility(View.VISIBLE);
                      System.out.println("Selected file = " + songFile.getAbsolutePath());
                      ByteArrayOutputStream out = new ByteArrayOutputStream();
                      BufferedInputStream in = null;
                      try {
                          in = new BufferedInputStream(new FileInputStream(songFile.getAbsolutePath()));
                      } catch (FileNotFoundException e) {
                          e.printStackTrace();
                      }
                      autoCorrelate(in);

                      Bundle b = new Bundle();
                      if(songFile.exists()){
                          name = songFile.getName();
                      }
                      else{
                          name = newFile.getName();
                      }
                      b.putString("Name", name);
                      b.putIntegerArrayList("Notes", noteInfo);
                      Intent p = new Intent(getBaseContext(), NotePage.class);
                      p.putExtras(b);
                      startActivity(p);
                      }
                  }
      );
  }
    //rename the file
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

//    private static final double[] FREQUENCIES =
//        {29.14, 30.87,
//        32.70, 34.65, 36.71, 38.89, 41.20, 43.65, 46.25, 49.00, 51.91, 55.00, 58.27, 61.74,
//        65.41, 69.30, 73.42, 77.78, 82.41, 87.31, 92.50, 98.00, 103.83, 110.00, 116.54, 123.47,
//        130.81, 138.59, 146.83, 155.56, 164.81, 174.61, 185.00, 196.00, 207.65, 220.00, 233.08, 246.94,
//        261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392.00, 415.30, 440.00, 466.16, 493.88,
//        523.25, 554.37, 587.33, 622.25, 659.25, 698.46, 739.99, 783.99, 830.61, 880.00, 932.33, 987.77,
//        1046.50, 1108.73, 1174.66, 1244.51, 1318.51, 1396.91, 1479.98, 1567.98, 1661.22, 1760.00, 1864.66, 1975.53,
//        2093.00, 2217.46, 2349.32, 2489.02, 2637.02, 2793.83, 2959.96, 3135.96, 3322.44, 3520.00, 3729.31, 3951.07,
//        4186.01};
//
//
//    private static final String[] NAME =
//            {"A0#","B0",
//            "C1","C1#","D1","D1#","E1","F1","F1#","G1","G1#","A1","A1#","B1",
//            "C2","C2#","D2","D2#","E2","F2","F2#","G2","G2#","A2","A2#","B2",
//            "C3","C3#","D3","D3#","E3","F3","F3#","G3","G3#","A3","A3#","B3",
//            "C4","C4#","D4","D4#","E4","F4","F4#","G4","G4#","A4","A4#","B4",
//            "C5","C5#","D5","D5#","E5","F5","F5#","G5","G5#","A5","A5#","B5",
//            "C6","C6#","D6","D6#","E6","F6","F6#","G6","G6#","A6","A6#","B6",
//            "C7","C7#","D7","D7#","E7","F7","F7#","G7","G7#","A7","A7#","B7",
//            "C8"};

    //TODO change the normaliseFreq so that it either doesn't normalize at all, or it only normalises super high or low freq
    private static double normaliseFreq(double hz) {
        // get hz into a standard range to make things easier to deal with
        while ( hz < 82.41 ) {
            hz = 2*hz;
        }
        while ( hz > 164.81 ) {
            hz = 0.5*hz;
        }
        return hz;
    }

    //find the closest note to the frequency so that we don't end up with half tones.
    private static int closestNote(double hz) {
        double minDist = Double.MAX_VALUE;
        int minFreq = -1;
        for ( int i = 0; i < FREQUENCIES.length; i++ ) {
            double dist = Math.abs(FREQUENCIES[i]-hz);
            if ( dist < minDist ) {
                minDist=dist;
                minFreq=i;
            }
        }
        return minFreq;
    }

    //analyse recording frequency
    public void autoCorrelate(BufferedInputStream in) {
        File noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
        if (!noteDir.exists()){
            noteDir.mkdir();}

        int FREQ_RANGE = 128;
        float sampleRate = 44100;

        byte[] buffer = new byte[2*4800];
        int[] a = new int[buffer.length/2];

        int n;
        try {
            while ((n = in.read(buffer)) > 0) {

                for ( int i = 0; i < n; i+=2 ) {
                    int value = (short)((buffer[i]&0xFF) | ((buffer[i+1]&0xFF) << 8));
                    a[i >> 1] = value;
                }

                double prevDiff = 0;
                double prevDx = 0;
                double maxDiff = 0;
                int sampleLen = 0;

                int len = a.length/4;
//            this is original
                for ( int i = 0; i < len; i++ ) {
//                for ( int i = 0; i < len; i+=4 ) {
                    double diff = 0;
                    for ( int j = 0; j < len; j++ ) {
                        diff += Math.abs(a[j]-a[i+j]);
                    }

                    double dx = prevDiff-diff;


                    if ( dx < 0 && prevDx > 0 ) {
                        // only look for troughs that drop to less than 10% of peak
                        if ( diff < (0.1*maxDiff) ) {
                            if ( sampleLen == 0 ) {
                                sampleLen=i-1;
                            }
                        }
                    }

                    prevDx = dx;
                    prevDiff=diff;
                    maxDiff=Math.max(diff,maxDiff);
                }

                if ( sampleLen > 0 ) {
                    double frequency = (sampleRate/sampleLen);

                    frequency = normaliseFreq(frequency);
                    int note = closestNote(frequency);
                    int value = 0;
                    double matchFreq = FREQUENCIES[note];
                    if ( frequency < matchFreq ) {
                        double prevFreq = FREQUENCIES[note+1];
                        value = (int)(-FREQ_RANGE*(frequency-matchFreq)/(prevFreq-matchFreq));
                        try {noteInfo.add(note);}
                        catch (RuntimeException ignored){}
                    }
                    else {
                        double nextFreq = FREQUENCIES[note-1];
                        value = (int)(FREQ_RANGE*(frequency-matchFreq)/(nextFreq-matchFreq));
                        try {noteInfo.add(note);}
                        catch (RuntimeException ignored){}
                    }
                }


            }

            if(songFile.exists()){
                file = new File(noteDir.getAbsolutePath()+File.separator +songFile.getName()+"notes");
            }
            else {
                file = new File(noteDir.getAbsolutePath()+File.separator +newFile.getName()+"notes");
            }

            FileWriter write = new FileWriter(file);
            String csvNotes = android.text.TextUtils.join(",",noteInfo);
            write.write(csvNotes + "\n");
            write.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //stop the media player
    private void ditchMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //pause recording
    private void pauseRecording() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }
}


