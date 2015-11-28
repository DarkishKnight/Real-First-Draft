package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.io.IOException;
import android.gesture.GestureLibraries;


public class RecorderPage extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private File SDCardpath;
    private String  OUTPUT_FILE;
    private String fileName = "";
    private File audioFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SDCardpath = getFilesDir();
        File myDataPath = new File(SDCardpath.getAbsolutePath()
                + "/.My Recordings");

        if (!myDataPath.exists())
            myDataPath.mkdir();



        audioFile = new File(myDataPath + "/" + fileName);
        final EditText beginningText = new EditText(this);

        beginningText.setHint("Enter Name Here");

        final ImageButton recordButton = (ImageButton) findViewById(R.id.recordButton);
        int[] state = {0};
        int maxStates = 2;

        final int[] finalState1 = state;
        final int finalMaxStates1 = maxStates;

        recordButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick(View v) {
                        if (finalState1[0] >= finalMaxStates1) finalState1[0] = 0;
                        switch (finalState1[0]++) {


                            case 0:
                                recordButton.setBackgroundResource(R.drawable.stop);
                                try {
                                    beginRecording();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1:
                                recordButton.setBackgroundResource(R.drawable.record);
                                stopRecording();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RecorderPage.this);
                                builder.setTitle("Song Name");

                                final EditText input = new EditText(RecorderPage.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

// Set up the buttons
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        fileName = input.getText().toString();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                builder.show();
                                break;
                        }
                    }
                }

        );
        final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        state = new int[]{0};
        maxStates = 2;

        final int[] finalState = state;
        final int finalMaxStates = maxStates;
        playButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick (View v){
                        if (finalState[0] >= finalMaxStates) finalState[0] = 0;
                        switch (finalState[0]++) {
                            case 0:
                                playButton.setBackgroundResource(R.drawable.play);
                                break;
                            case 1:
                                playButton.setBackgroundResource(R.drawable.pause);
                                break;
                        }
                    }
                }

        );




        final ImageButton backButton = (ImageButton) findViewById(R.id.backButton);

                        backButton.setOnClickListener(
                                new ImageButton.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent e = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(e);
                                    }
                                }
                        );

                    }



    private void beginRecording() throws Exception {
        ditchMediaRecorder();
        File outFile = new File(OUTPUT_FILE);
        if (outFile.exists())
            outFile.mkdir();

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);
        mediaRecorder.setOutputFile(OUTPUT_FILE);
        mediaRecorder.prepare();
        mediaRecorder.start();
                    }


    private void stopRecording() {
        if (mediaRecorder != null)
            mediaRecorder.stop();
    }
    private void pauseRecording() {
        if (mediaRecorder != null)
            mediaRecorder.stop();
    }
    private void playRecording() {
        if (mediaRecorder == null) {
            assert mediaRecorder != null;
            mediaRecorder.start();
        }
    }
    private void ditchMediaRecorder() {
        if (mediaRecorder != null)
            mediaRecorder.release();

    }

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

    public void setOUTPUT_FILE(String OUTPUT_FILE) {
        this.OUTPUT_FILE = OUTPUT_FILE;
    }
}