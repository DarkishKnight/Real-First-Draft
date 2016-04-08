package com.liam.realfirstdraft;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class NotePage extends AppCompatActivity {
    Map<Integer, Object> notes = new HashMap<>();
    ArrayList<Integer> noteArray = null;
    ImageView[] imageViews;
    String name;
    String arrayFile;
    File file;
    FrameLayout[] frames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);

        name = getIntent().getExtras().getString("Name");

        if(getIntent()!=null && getIntent().getExtras()!= null){
           noteArray = getIntent().getExtras().getIntegerArrayList("Notes");
            File noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
            file = new File(noteDir.getAbsolutePath()+File.separator+name);
        }

        if (noteArray == null) {
            noteArray = new ArrayList<>();

            arrayFile = getIntent().getExtras().getString("listItem");

            if (arrayFile != null) {
                File noteDir = new File(Environment.getExternalStorageDirectory(), "Humposer Notes");
                file = new File(noteDir.getAbsolutePath() + File.separator + arrayFile);

                String line = null;

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    line = reader.readLine();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (line != null) {
                    StringTokenizer st = new StringTokenizer(line, ",");
                    while (st.hasMoreTokens()) {
                        int convertToInt = Integer.parseInt(st.nextToken());
                        noteArray.add(convertToInt);
                    }
                }
            }
        }



        if (noteArray != null) {
            imageViews = new ImageView[noteArray.size()];
            frames = new FrameLayout[noteArray.size()];

            addingQuarterNotesToArray();
        }


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
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(getBaseContext(), SheetMusic.class);
                        startActivity(a);
                    }
                }
        );





    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addingQuarterNotesToArray(){
        quarternoteDrawables();
        displayImages();
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
        for (int i = 0; i < noteArray.size(); i++) {
            ImageView image = new ImageView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.topMargin = l + 230 * q;
            params.leftMargin = (c + 47 * m);
            image.setMaxHeight(20);
            image.setMaxWidth(20);
            m++;
            imageViews[counter] = image;
            // Adds the view to the layout
            if (counter != 9 * p) {
                frames[layoutNumber].addView(image, params);
            }
            counter++;
            if (counter == 9 * p) {
//                layoutNumber++;
//                frames[layoutNumber].addView(image, params);
                q++;
                p++;
                m = 0;
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
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params1.topMargin = 1 + 100 * x;
            frames[counter1] = frameLayout;
            linearLayout.addView(frameLayout, params1);
            x++;
            counter1++;
        }
    }

    private void quarternoteDrawables(){
        notes.put(14, R.drawable.dsharp);
        notes.put(13, R.drawable.e);
        notes.put(12, R.drawable.f);
        notes.put(11, R.drawable.fsharp);
        notes.put(10, R.drawable.g0);
        notes.put(9, R.drawable.g0sharp);
        notes.put(8, R.drawable.a);
        notes.put(7, R.drawable.asharp);
        notes.put(6, R.drawable.b);
        notes.put(5, R.drawable.c);
        notes.put(4, R.drawable.csharp);
        notes.put(3, R.drawable.d0);
        notes.put(2, R.drawable.d0sharp);
        notes.put(1, R.drawable.e0);
        notes.put(0, R.drawable.f0);
    }

    private void halfnoteDrawables(){
        notes.put(14, R.drawable.dsharphalf);
        notes.put(13, R.drawable.ehalf);
        notes.put(12, R.drawable.fhalf);
        notes.put(11, R.drawable.fsharp);
        notes.put(10, R.drawable.g0half);
        notes.put(9, R.drawable.g0sharphalf);
        notes.put(8, R.drawable.ahalf);
        notes.put(7, R.drawable.asharphalf);
        notes.put(6, R.drawable.bhalf);
        notes.put(5, R.drawable.chalf);
        notes.put(4, R.drawable.csharphalf);
        notes.put(3, R.drawable.d0half);
        notes.put(2, R.drawable.d0sharphalf);
        notes.put(1, R.drawable.e0half);
        notes.put(0, R.drawable.f0half);
    }
    private void wholenoteDrawable(){
        notes.put(14, R.drawable.dsharpwhole);
        notes.put(13, R.drawable.ewhole);
        notes.put(12, R.drawable.fwhole);
        notes.put(11, R.drawable.fsharpwhole);
        notes.put(10, R.drawable.g0whole);
        notes.put(9, R.drawable.g0sharpwhole);
        notes.put(8, R.drawable.awhole);
        notes.put(7, R.drawable.asharpwhole);
        notes.put(6, R.drawable.bwhole);
        notes.put(5, R.drawable.cwhole);
        notes.put(4, R.drawable.csharpwhole);
        notes.put(3, R.drawable.d0whole);
        notes.put(2, R.drawable.d0sharpwhole);
        notes.put(1, R.drawable.e0whole);
        notes.put(0, R.drawable.f0whole);
    }
}

