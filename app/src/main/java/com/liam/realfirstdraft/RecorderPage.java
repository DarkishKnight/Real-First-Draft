package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.RelativeLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



public class RecorderPage extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
   // private MediaPlayer mediaPlayer;
    File tempFile;
    String saveToFileName;
    File newFile;
    AnimationDrawable glowAnimation;

    protected void saveFile() {

        newFile = new File(tempFile.getParent()+File.separator+saveToFileName);
        tempFile.renameTo(newFile);


        // copy tempFile to saveToFileName

        File extDirectory = new File(Environment.getExternalStorageDirectory(),"Humposer");
        File[] fileList = extDirectory.listFiles();
        for (File aFileList : fileList) {
            System.out.println(aFileList.getAbsoluteFile());
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder_page);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.recorderLayout);
        glowAnimation = (AnimationDrawable) relativeLayout.getBackground();


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
                                glowAnimation.start();
                                try {
                                    beginRecording();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1: // stop recording
                                recordButton.setBackgroundResource(R.drawable.record);
                                stopRecording();
                                glowAnimation.stop();

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


        ImageButton musicbutton = (ImageButton) findViewById(R.id.toMusicPage);

        musicbutton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent o = new Intent(getBaseContext(), MusicPage.class);
                        startActivity(o);
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

    private void ditchMediaRecorder() {
        if (mediaRecorder != null)
            mediaRecorder.release();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.recorderLayout));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

}