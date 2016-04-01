package com.liam.realfirstdraft;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.media.IMediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.*;


public class NotePage extends AppCompatActivity {
    Map<Integer, Object> notes = new HashMap<>();
    ArrayList<Integer> noteImage = new ArrayList<>();
    ArrayList<Integer> noteArray = new ArrayList<>();
    ImageView[] imageViews;
    File textFile;
    String name;
    String fileToDelete;
    File file;
    String ret;
    FrameLayout[] frames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);

        fileToDelete = getIntent().getExtras().getString("listItem");
        if(getIntent()!=null && getIntent().getExtras()!= null){
           noteArray = getIntent().getExtras().getIntegerArrayList("Notes");
        }

//        if(getIntent()== null || getIntent().getExtras().getString("Notes")==null){
//            try {
//                getStringFromFile(noteDir.getAbsolutePath()+File.separator + fileToDelete);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
//            file = new File(noteDir.getAbsolutePath()+File.separator + fileToDelete);
//            StringTokenizer st = new StringTokenizer(ret,",");
//            while (st.hasMoreTokens()){
//                int convertToInt = Integer.parseInt(st.nextToken());
//                noteArray.add(convertToInt);
//            }
//        }
        name = getIntent().getExtras().getString("Name");



        imageViews = new ImageView[noteArray.size()];
        frames = new FrameLayout[noteArray.size()];
//        imageViews[6] = (ImageView) findViewById(R.id.image7);
//        imageViews[5] = (ImageView) findViewById(R.id.image6);
//        imageViews[4] = (ImageView) findViewById(R.id.image5);
//        imageViews[3] = (ImageView) findViewById(R.id.image4);
//        imageViews[2] = (ImageView) findViewById(R.id.image3);
//        imageViews[1] = (ImageView) findViewById(R.id.image2);
//        imageViews[0] = (ImageView) findViewById(R.id.image1);

        addingQuarterNotesToArray();
//        saveNoteArray();



        TextView title = (TextView) findViewById(R.id.Title);
        title.setText(name);

        ImageButton deleteButton = (ImageButton) findViewById(R.id.trashIcon);
        deleteButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder maker = new AlertDialog.Builder(NotePage.this);
                        maker.setMessage("Delete Song");

                        maker.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                file.getAbsoluteFile().delete();
                                Intent x = new Intent(getBaseContext(), SheetMusic.class);
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





        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);

       homeButton.setOnClickListener(
               new ImageButton.OnClickListener()

               {
                   @Override
                   public void onClick(View v) {
                       Intent z = new Intent(getBaseContext(), MainActivity.class);
                       startActivity(z);
                   }
               }

       );

        ImageButton sheetMusic = (ImageButton) findViewById(R.id.sheetMusicButton);

        sheetMusic.setOnClickListener(
                new ImageButton.OnClickListener(){
                    @Override
                public void onClick(View v){
                        Intent a = new Intent(getBaseContext(), SheetMusic.class);
                        startActivity(a);
                    }
                }
        );
        noteImage.add(R.drawable.a);
        noteImage.add(R.drawable.b);
        noteImage.add(R.drawable.c);
        noteImage.add(R.drawable.d);
        noteImage.add(R.drawable.e);
        noteImage.add(R.drawable.f);
        noteImage.add(R.drawable.g1);
        noteImage.add(R.drawable.g0);
        noteImage.add(R.drawable.f0);
        noteImage.add(R.drawable.d0);
        noteImage.add(R.drawable.e0);




    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addingQuarterNotesToArray(){
        notes.put(14, R.drawable.dsharp);
        notes.put(13, R.drawable.e);
        notes.put(12, R.drawable.f);
        notes.put(11, R.drawable.fsharp);
        notes.put(10, R.drawable.g1);
        notes.put(9, R.drawable.gsharp);
        notes.put(8, R.drawable.a);
        notes.put(7, R.drawable.asharp);
        notes.put(6, R.drawable.b);
        notes.put(5, R.drawable.c);
        notes.put(4, R.drawable.csharp);
        notes.put(3, R.drawable.d0);
        notes.put(2, R.drawable.d0sharp);
        notes.put(1, R.drawable.e0);
        notes.put(0, R.drawable.f0);

        displayImages();


    }

    private void saveNoteArray(){
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(textFile));
            outputWriter.write(noteArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(textFile);
        }
    }

    private void createArray(){
        File noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
        textFile = new File(noteDir.getAbsolutePath()+File.separator+name);
        StringTokenizer st = new StringTokenizer(textFile.toString(),",");
        while (st.hasMoreTokens()){
            int convertToInt = Integer.parseInt(st.nextToken());
            noteArray.add(convertToInt);
        }

    }

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private String getStringFromFile(String filePath) throws Exception {
        file = new File(filePath);
        FileInputStream fin = new FileInputStream(file);
        ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    private void displayImages() {
//creates new framelayout
        createNewLayout();
        int counter = 0;
        int m = 0;
        int p = 1;
        int c = ViewGroup.LayoutParams.WRAP_CONTENT;
        int l = ViewGroup.LayoutParams.MATCH_PARENT;
        int layoutNumber = 0;
        int q = 0;
//creates new imageViews to add to framelayouts
        for(int i=0;i<noteArray.size();i++)
        {
            ImageView image = new ImageView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.topMargin=l+230*q;
            params.leftMargin=(c+47*m);
            image.setMaxHeight(20);
            image.setMaxWidth(20);
            m++;
            imageViews[counter]= image;
            // Adds the view to the layout
            if (counter != 9*p) {
                frames[layoutNumber].addView(image, params);
            }
            counter++;
            if(counter==9*p){
//                layoutNumber++;
//                frames[layoutNumber].addView(image, params);
                q++;
                p++;
                m=0;
            }



        }
//        int numberOfNotesPerLine = 7;
        int imageNum = 0;
        for (Integer note : noteArray) {
            if (imageNum <= noteArray.size()) {
                Integer d = (Integer) notes.get(note);
                imageViews[imageNum].setBackgroundResource(d);

                imageNum++;
            }



        }

    }
    private void createNewLayout(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        int x =  0;
        int counter1 = 0;
        for(int i = 0; i<=1; i++) {
            FrameLayout frameLayout = new FrameLayout(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params1.topMargin = 1 + 100 * x;
            frames[counter1] = frameLayout;
            linearLayout.addView(frameLayout, params1);
            x++;
            counter1++;
        }
    }
}

