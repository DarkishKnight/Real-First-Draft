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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;


public class MusicPlayer extends AppCompatActivity {
    private float r;
    private String saveToFileName;
    private Spinner spinnerMenue;
    private File songFile;
    private MediaPlayer mediaPlayer;
    private File newFile;
    private int[] state;
    private int maxStates;
    private File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
    private Complex[] complexData;
    private  TextView songNameText;
    private int[] zums;
    private static final String[] list = {"Music","Sheet Music","Music Recorder","Music Player"};



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
    //protected void saveFile() {

                 //  File newFile = new File(songNameText.getParent()+File.separator+saveToFileName);
               //  songNameText.renameTo(newFile);


  @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_music_player);
//        activityList();

      final String id = getIntent().getExtras().getString("listItem");
      songNameText = (TextView) findViewById(R.id.songNameText);
      songNameText.setText(id);
      songFile = new File(extDirectory + File.separator + id);
      newFile = new File(songFile.getParent() + File.separator + saveToFileName);


      spinnerMenue = (Spinner)findViewById(R.id.spinner1);
      ArrayAdapter adapter = new ArrayAdapter(MusicPlayer.this,
              android.R.layout.simple_spinner_item,list);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerMenue.setAdapter(adapter);
      onItemSelectedSpinner();


//      spinnerMenue = (Spinner) findViewById(R.id.spinner1);
////
//      ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.activity_array, android.R.layout.simple_spinner_item);
//      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//      spinnerMenue.setAdapter(adapter);
//
//      onItemSelectedSpinner();

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
                  }

              });


//Botton
      ImageButton renameButton = (ImageButton) findViewById(R.id.renameButton);
      renameButton.setOnClickListener(
              new ImageButton.OnClickListener() {
                  @Override
                  public void onClick(final View v) {
                      AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayer.this);

                                       /* final ArrayList<String> listViewValues = new ArrayList<>();
-                        for (File aFileList : fileList) {
-                            System.out.println(aFileList.getAbsoluteFile());
-                            listViewValues.add(aFileList.getName());
-                        }*/
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

                      System.out.println("Selected file = " + songFile.getAbsolutePath());

                      ByteArrayOutputStream out = new ByteArrayOutputStream();
                      BufferedInputStream in = null;
                      try {
                          in = new BufferedInputStream(new FileInputStream(songFile.getAbsolutePath()));
                      } catch (FileNotFoundException e) {
                          e.printStackTrace();
                      }

                      if (in != null) {
                          int read;
                          byte[] buff = new byte[1024];
                          try {
                              while ((read = in.read(buff)) > 0) {
                                  out.write(buff, 0, read);
                              }
                              out.flush();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                          byte[] audioBytes = out.toByteArray(); // 587820

                          int audiosize = (audioBytes.length - 44) / 4;  // 146944
                          double[] realData = new double[audiosize];
                          double[] imagData = new double[audiosize];

                          for (int i = 44, r = 0; i < audioBytes.length; i += 4, r++) {
                              byte[] rawbytes = new byte[2];

                              rawbytes[0] = audioBytes[i];
                              rawbytes[1] = audioBytes[i + 1];
//                              for (int  j=2; j<8; j++) {
//                                  rawbytes[j] = 0;
//                              }

                              Short audioValue = ByteBuffer.wrap(rawbytes).getShort();
//                              System.out.println(r+" "+audioValue);
                              realData[r] = audioValue.doubleValue();
                              imagData[r] = 0d;
                          }


//                          double[] realDataL = new double[audiosize / 2]; // 73472
//                          double[] realDataR = new double[audiosize / 2];
//                          for (int i = 0, y = 0; i < realData.length; i += 2, y++) {
//                              realDataL[y] = realData[i];
//                              realDataR[y] = realData[i + 1];
//                          }

                          FFTbase fft = new FFTbase();

                          int windowSize = 1024;
                          int c = 0;
                          while (c < realData.length) {
                              double[] rdata = new double[windowSize];
                              double[] idata = new double[windowSize];

                              for (int i = 0; i < windowSize; i++) {
                                  if (c + i < realData.length) {
                                      rdata[i] = realData[c + i];
                                  } else {
                                      rdata[i] = 0;
                                  }
                                  idata[i] = 0;
                              }

                              double[] newarray = fft.fft(rdata, idata, true);


                              //System.out.println(c);

                              double maxVal = -1;
                              int maxIndex = -1;
                              float sum = 0;
                              float sSum = 0;
                              for (int j = 0; j < newarray.length; j += 2) {
                                  double asd = newarray[j] * newarray[j] + newarray[j + 1] * newarray[j + 1];
                                  if (asd > maxVal) {
                                      maxVal = asd;
                                      maxIndex = j;
                                  }
                                  if (j < 300) {
                                      maxIndex = 0;


                                  }

                              }
                                  float[] r = new float[newarray.length];
                                  for (int i=0;i<newarray.length;i++) {
                                      sum = 0;
                                      for (int j=0;j<newarray.length-i;j++) {
                                          sum+=maxIndex*maxVal;
                                      }
                                      r[i]=sum;
                                  }


                              System.out.println("\t" + c + "\t" + Math.sqrt(maxVal) + "\t" + maxIndex + "\t" + (sum/1.00E12)*100);
                              c += windowSize;

                              }

                          }


                      }


//                      File myFile = new File("/storage/emulated/0/Humposer/record_temp.raw");


//
//                      Complex[] complexData = new Complex[audioData.length];
//                      for (int i = 0; i < complexData.length; i++) {
//                          complexData[i] = new Complex(audioData[i], 0);
//                      }

                  }

      );
  }
    public void onItemSelectedSpinner(){

        spinnerMenue = (Spinner) findViewById(R.id.spinner1);
        spinnerMenue.setSelection(0);
        spinnerMenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch(pos) {
                    case 0:
                        Intent i = new Intent(getBaseContext(), MusicPage.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent h = new Intent(getBaseContext(), SheetMusic.class);
                        startActivity(h);
                        break;
                    case 2:
                        Intent j = new Intent(getBaseContext(), AudioRecordPages.class);
                        startActivity(j);
                        break;
                    case 3:
                        Intent k = new Intent(getBaseContext(), MusicPlayer.class);
                        startActivity(k);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
            }
        });
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
/*File file = new File(extDirectory + File.separator + id);
-
-// File (or directory) with new name
-File file2 = new File(extDirectory + File.separator + String.valueOf(input));
-
-if (file2.exists())
-        try {
-        throw new IOException("file exists");
-        } catch (IOException e) {
-        e.printStackTrace();
-        }
-
-// Rename file (or directory)
-        boolean success = file.renameTo(file2);
-
-        if (!success) {
-        // File was not successfully renamed
-        }
-        */

