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
//    private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
//    private static final String AUDIO_RECORDER_FOLDER = "Humposer";
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static final int RECORDER_SAMPLERATE = 44100;
//    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
//    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord recorder = null;
    private int bufferSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;



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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_audio_recorder_page);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.recorderLayout1);
        glowAnimation1 = (AnimationDrawable) relativeLayout.getBackground();

        bufferSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        final EditText beginningText = new EditText(this);

        beginningText.setHint("Enter Name Here");

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
                                stopRecording();
                                glowAnimation1.stop();

                                AlertDialog.Builder builder = new AlertDialog.Builder(AudioRecordPages.this);
                                builder.setTitle("Song Name");

                                final EditText input = new EditText(AudioRecordPages.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

// Set up the buttons
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


        final ImageButton backButton = (ImageButton) findViewById(R.id.backButton3);

        backButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(e);
                    }
                }
        );


        ImageButton musicbutton = (ImageButton) findViewById(R.id.toMusicPage1);

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


    private void startRecording(){
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            System.out.println("External storage is writable");
//        }
//        else {
//            System.out.println("External storage is NOT writable");
//        }
//        File audioDir = new File(Environment.getExternalStorageDirectory(),"Humposer");
//        File audioDir = new File("/data/com.liam/Humposer");
//        if (!audioDir.exists())
//            audioDir.mkdir();

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        tempFile1 = new File(audioDir, "tempAudioFile_"+timeStamp+".wav");

        int i = recorder.getState();
        if(i==1)
            recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                writeAudioDataToFile();
            }
        },"AudioRecorder Thread");

        recordingThread.start();
    }

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
//        copyWaveFile(getTempFilename(),getFilename());
//        deleteTempFile();
    }

    private void writeAudioDataToFile(){
        byte data[] = new byte[bufferSize];
        String filename = getTempFilename();
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
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

    private void deleteTempFile() {
        File file = new File(getTempFilename());

        file.delete();
    }

    private void copyWaveFile(String inFilename,String outFilename){
        FileInputStream in;
        FileOutputStream out;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
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




//    private String getFilename(){
//
//        String filepath = Environment.getExternalStorageDirectory().getPath();
//        File file = new File(filepath,AUDIO_RECORDER_FOLDER);
//
//        if(!file.exists()){
//            file.mkdirs();
//        }
//
//        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + AUDIO_RECORDER_FILE_EXT_WAV);
//    }

    private String getTempFilename(){
        File audioDir = new File(Environment.getExternalStorageDirectory(),"Humposer");
        if (!audioDir.exists())
            audioDir.mkdir();

        File tempFile = new File(Environment.getExternalStorageDirectory(),AUDIO_RECORDER_TEMP_FILE);

        if(tempFile.exists())
            tempFile.delete();

        return (audioDir.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);

//        File audioDir = new File("/data/com.liam/Humposer");
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        tempFile1 = new File(audioDir, "tempAudioFile_"+timeStamp+".wav");

//        return (audioDir.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);
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