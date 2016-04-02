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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class MusicPlayer extends AppCompatActivity {
    private String saveToFileName;
    private File songFile;
    private MediaPlayer mediaPlayer;
    private File newFile;
    private int[] state;
    private int maxStates;
    private File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
    private  TextView songNameText;
    private ArrayList<Integer> noteInfo = new ArrayList<>();
    File file;
    ProgressBar progressBar;
    String name;




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



  @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_music_player);
//        activityList();
      progressBar = (ProgressBar) findViewById(R.id.progressBar);
      progressBar.setVisibility(View.INVISIBLE);

      final String id = getIntent().getExtras().getString("listItem");
      songNameText = (TextView) findViewById(R.id.songNameText);
      songNameText.setText(id);
      songFile = new File(extDirectory + File.separator + id);
      newFile = new File(songFile.getParent() + File.separator + saveToFileName);


      ImageButton backButton2 = (ImageButton) findViewById(R.id.backButton2);

      backButton2.setOnClickListener(
              new ImageButton.OnClickListener()

              {
                  @Override
                  public void onClick(View v) {
                      Intent z = new Intent(getBaseContext(), MainActivity.class);
                      startActivity(z);
                  }
              }

      );

      ImageButton musicPage = (ImageButton) findViewById(R.id.torecorderpage);

      musicPage.setOnClickListener(
              new ImageButton.OnClickListener()

              {
                  @Override
                  public void onClick(View v) {
                      Intent q = new Intent(getBaseContext(), AudioRecordPages.class);
                      startActivity(q);
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
                          @Override
                          public void onCompletion(MediaPlayer mp) {
                              playButton.setBackgroundResource(R.drawable.playback);
                          }
                      });

                      }


              });


//Botton
      ImageButton renameButton = (ImageButton) findViewById(R.id.renameButton);
      renameButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(final View v) {
                      AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);
                      builder.setTitle(id);


                      final EditText input = new EditText(MusicPlayer.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                      input.setInputType(InputType.TYPE_CLASS_TEXT);
                      builder.setView(input);
                      input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                          public void onFocusChange(View arg0, boolean arg1) {
                              InputMethodManager inputMgr = (InputMethodManager) v.getContext().
                                      getSystemService(Context.INPUT_METHOD_SERVICE);
                              inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                          }
                      });
// Set up the buttons
                      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
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
                      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
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
      ImageButton fftButton = (ImageButton) findViewById(R.id.fftButton);
      fftButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      progressBar.setVisibility(View.VISIBLE);
                      System.out.println("Selected file = " + songFile.getAbsolutePath());

                      ByteArrayOutputStream out = new ByteArrayOutputStream();
                      BufferedInputStream in = null;
                      try {
                          in = new BufferedInputStream(new FileInputStream(songFile.getAbsolutePath()));
                      } catch (FileNotFoundException e) {
                          e.printStackTrace();
                      }
                      autoCorrelate(in);


                      progressBar.setVisibility(View.INVISIBLE);

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
    private static final double[] FREQUENCIES = { 174.61, 164.81, 155.56, 146.83, 138.59, 130.81, 123.47, 116.54, 110.00, 103.83, 98.00, 92.50, 87.31, 82.41, 77.78};
    private static final String[] NAME = { "F",    "E",    "D#",   "D",    "C#",   "C",    "B",    "A#",   "A",    "G#",   "G",   "F#",  "F",   "E",   "D#"};

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

    public void autoCorrelate(BufferedInputStream in) {


//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);

        File noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
        if (!noteDir.exists()){
            noteDir.mkdir();}

        int FREQ_RANGE = 128;
        float sampleRate = 44100;

        byte[] buffer = new byte[2*9600];
        int[] a = new int[buffer.length/2];

        int n = -1;
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
                        System.out.println("Value1\t" + note);
                        try {noteInfo.add(note);}
                        catch (RuntimeException e){}
                    }
                    else {
                        double nextFreq = FREQUENCIES[note-1];
                        value = (int)(FREQ_RANGE*(frequency-matchFreq)/(nextFreq-matchFreq));
                        System.out.println("Value2\t" + note);
                        try {noteInfo.add(note);}
                        catch (RuntimeException e){}
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
            System.out.println("csvNotes = "+csvNotes);
            write.write(csvNotes+"\n");
            write.close();

            System.out.println("file = "+file);
            System.out.println(noteInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }








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


