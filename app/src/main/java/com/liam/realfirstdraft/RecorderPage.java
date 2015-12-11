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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.io.IOException;
import android.gesture.GestureLibraries;


public class RecorderPage extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    File tempFile;
    String saveToFileName;

    protected void saveFile() {

        File newFile = new File(tempFile.getParent()+File.separator+saveToFileName);
        tempFile.renameTo(newFile);

        ditchMediaPlayer();
        try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(newFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        // copy tempFile to saveToFileName

        File extDirectory = new File(Environment.getExternalStorageDirectory(),"Humposer");
        File[] fileList = extDirectory.listFiles();
        for (int i=0; i<fileList.length; i++) {
            System.out.println(fileList[i].getAbsoluteFile());
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


                            case 0: // begin recording
                                recordButton.setBackgroundResource(R.drawable.stop);
                                try {
                                    beginRecording();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1: // stop recording
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
                                        saveToFileName = input.getText().toString();
                                        saveFile();
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

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            System.out.println("External storage is writable");
        }
        else {
            System.out.println("External storage is NOT writable");
        }

        File audioDir = new File(Environment.getExternalStorageDirectory(),"Humposer");
//        File audioDir = new File("/data/com.liam/Humposer");
        if (!audioDir.exists())
            audioDir.mkdir();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        tempFile = new File(audioDir, "tempAudioFile_"+timeStamp+".3gpp");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);
        mediaRecorder.setOutputFile(tempFile.toString());
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

    private void ditchMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File getTempFile() {
        return tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }

    public String getSaveToFileName() {
        return saveToFileName;
    }

    public void setSaveToFileName(String saveToFileName) {
        this.saveToFileName = saveToFileName;
    }

    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public void setMediaRecorder(MediaRecorder mediaRecorder) {
        this.mediaRecorder = mediaRecorder;
    }
}