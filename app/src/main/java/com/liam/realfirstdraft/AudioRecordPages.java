package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AudioRecordPages extends AppCompatActivity {
    String saveToNewFileName;
    File saveToNewFile;
    AnimationDrawable glowAnimation1;
    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static final int RECORDER_SAMPLERATE = 44100;
    private AudioRecord recorder = null;
    private int bufferSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_audio_recorder_page);
        //create layout
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.recorderLayout1);
        glowAnimation1 = (AnimationDrawable) relativeLayout.getBackground();

        bufferSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);

        final EditText beginningText = new EditText(this);
        beginningText.setHint("Enter Name Here");

        //set up recording button
        final ImageButton recordButton = (ImageButton) findViewById(R.id.recordButton1);
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
                                glowAnimation1.start();
                                try {
                                    startRecording();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1: // stop recording
                                recordButton.setBackgroundResource(R.drawable.record);
                                try {
                                    stopRecording();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                glowAnimation1.stop();

                                AlertDialog.Builder builder = new AlertDialog.Builder(AudioRecordPages.this);
                                builder.setTitle("Song Name");

                                final EditText input = new EditText(AudioRecordPages.this);
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

                                // Set up the buttons to set name of song
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        saveToNewFileName = input.getText().toString();
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

        //takes you to the main activity
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton3);
        backButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick(View v) {
                        Intent e = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(e);
                    }
                }
        );

        //take you to the recorded music page
        ImageButton musicbutton = (ImageButton) findViewById(R.id.toMusicPage1);
        musicbutton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick(View v) {
                        Intent o = new Intent(getBaseContext(), MusicPage.class);
                        startActivity(o);
                    }
                }
        );
    }

// begin recording
    private void startRecording(){
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100, AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        int i = recorder.getState();
        if(i==1)
            recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {

            public void run() {
                writeAudioDataToFile();
            }
        },"AudioRecorder Thread");

        recordingThread.start();
    }

    //finish recording
    private void stopRecording(){
        if(null != recorder){
            isRecording = false;

            int i = recorder.getState();
            if(i==1)
                recorder.stop();

            recorder.release();

            recorder = null;
            recordingThread = null;
        }
    }

    //save file method
    protected void saveFile() {
        File extDirectory = new File(Environment.getExternalStorageDirectory(),"Humposer");
        saveToNewFile = new File(extDirectory.getAbsolutePath()+File.separator+saveToNewFileName);
        copyWaveFile(getTempFilename(), saveToNewFile.getAbsolutePath());

        File[] fileList = extDirectory.listFiles();
        for (File aFileList : fileList) {
            System.out.println(aFileList.getAbsoluteFile());
        }
        deleteTempFile();
    }



    //takes audio recording and write its to the file
    private void writeAudioDataToFile(){
        byte data[] = new byte[bufferSize];
        String filename = getTempFilename();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int read;
        if(null != os){
            while(isRecording){
                read = recorder.read(data, 0, bufferSize);

                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                    try {
                        os.write(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //delete temp file
    private void deleteTempFile() {
        File file = new File(getTempFilename());
        file.delete();
    }

    //copy wave file
    private void copyWaveFile(String inFilename,String outFilename){
        FileInputStream in;
        FileOutputStream out;
        long totalAudioLen;
        long totalDataLen;
        long longSampleRate = RECORDER_SAMPLERATE;
        int channels = 2;
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;

        byte[] data = new byte[bufferSize];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);

            while(in.read(data) != -1){
                out.write(data);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write the wavefile header
    private void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException {

        byte[] header = new byte[44];

        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8); // block align
        header[33] = 0;
        header[34] = RECORDER_BPP; // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }


    //getter for temp file name
    private String getTempFilename(){
        File audioDir = new File(Environment.getExternalStorageDirectory(),"Humposer");
        if (!audioDir.exists())
            audioDir.mkdir();

        File tempFile = new File(Environment.getExternalStorageDirectory(),AUDIO_RECORDER_TEMP_FILE);

        if(tempFile.exists())
            tempFile.delete();

        return (audioDir.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);
    }

    //if the system crashes
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