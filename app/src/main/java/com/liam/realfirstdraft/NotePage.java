package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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


public class NotePage extends AppCompatActivity {
    Map<Object, Integer> notes = new HashMap<Object, Integer>(50);
    ArrayList<Integer> noteImage = new ArrayList<>(20);
    ArrayList<Integer> noteArray = new ArrayList<>();
    ArrayList<File> noteArray2 = new ArrayList<>();
    ImageView firstImage;
    ImageView secondImage;
    ImageView thirdImage;
    ImageView fourthImage;
    ImageView fifthImage ;
    ImageView sixthImage;
    ImageView seventhImage;
    File textFile;
    String name;
    String fileToDelete;
    File noteDir;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);

        fileToDelete = getIntent().getExtras().getString("listItem");
        noteArray = getIntent().getExtras().getIntegerArrayList("Notes");
        name = getIntent().getExtras().getString("Name");
        noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
        file = new File(noteDir.getAbsolutePath()+File.separator + fileToDelete);
        textFile = new File(noteDir.getAbsolutePath()+File.separator+name);
        addingQuarterNotesToArray();
//        saveNoteArray();
        if(noteArray == null){
            createArray();
        }

//        addingQuarterNotesToArray();

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
        seventhImage = (ImageView) findViewById(R.id.image7);
        sixthImage = (ImageView) findViewById(R.id.image6);
        fifthImage = (ImageView) findViewById(R.id.image5);
        fourthImage = (ImageView) findViewById(R.id.image4);
        thirdImage = (ImageView) findViewById(R.id.image3);
        secondImage = (ImageView) findViewById(R.id.image2);
        firstImage = (ImageView) findViewById(R.id.image1);

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
    private void addingQuarterNotesToArray(){
        notes.put(R.drawable.dsharp, 14);
        notes.put(R.drawable.e, 13);
        notes.put(R.drawable.f, 12);
        notes.put(R.drawable.fsharp, 11);
        notes.put(R.drawable.g1, 10);
        notes.put(R.drawable.gsharp, 9);
        notes.put(R.drawable.a, 8);
        notes.put(R.drawable.asharp, 7);
        notes.put(R.drawable.b, 6);
        notes.put(R.drawable.c, 5);
        notes.put(R.drawable.csharp, 4);
        notes.put(R.drawable.d0, 3);
        notes.put(R.drawable.d0sharp, 2);
        notes.put(R.drawable.e0, 1);
        notes.put(R.drawable.f0, 0);

        if(notes.get(R.drawable.dsharp) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.dsharp)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.dsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.dsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.dsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.dsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.dsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.dsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.dsharp)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.dsharp);
                    }
                }
        }

        if(notes.get(R.drawable.g1) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.g1)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.g1);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.g1);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.g1);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.g1);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.g1);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.g1);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.g1)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.g1);
                    }
                }
        }
        if (notes.get(R.drawable.d0sharp) != null) {
            for (int i = 0; i < noteArray.size(); i++) {
                if (noteArray.get(i) == notes.get(R.drawable.d0sharp))
//                    noteArray.indexOf(notes.get(R.drawable.d0sharp));
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0sharp)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.d0sharp);
                    }
            }
        }

        if(notes.get(R.drawable.d0) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.d0)== noteArray.get(i))

                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.d0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.d0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.d0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.d0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.d0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.d0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.d0)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.d0);
                    }

            }
        }

        if(notes.get(R.drawable.e) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.e)== noteArray.get(i))

                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.e);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.e);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.e);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.e);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.e);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.e);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.e);
                    }
            }
        }
        if(notes.get(R.drawable.e0) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.e0)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.e0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.e0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.e0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.e0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.e0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.e0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.e0)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.e0);
                    }

            }
        }
        if(notes.get(R.drawable.gsharp) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.gsharp)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.gsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.gsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.gsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.gsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.gsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.gsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.gsharp)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.gsharp);
                    }
                }
        }
        if(notes.get(R.drawable.csharp) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.csharp)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.csharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.csharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.csharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.csharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.csharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.csharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.csharp)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.csharp);
                    }
            }
        }
        if(notes.get(R.drawable.f) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.f)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.f);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.f);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.f);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.f);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.f);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.f);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.f);
                    }
                }
        }
        if(notes.get(R.drawable.f0) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.f0)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.f0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.d0sharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.f0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.f0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.f0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.f0);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.f0)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.f0);
                    }
                }
        }
        if(notes.get(R.drawable.fsharp) != null){
            for(int i = 0; i<noteArray.size(); i++)
                if (notes.get(R.drawable.fsharp) == noteArray.get(i)) {
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.fsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.fsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.fsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.fsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.fsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.fsharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.fsharp)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.fsharp);
                    }
                }
        }
        if(notes.get(R.drawable.c) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.c)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.c);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.c);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.c);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.c);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.c);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.c);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.c)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.c);
                    }
                }
        }
        if(notes.get(R.drawable.a) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.a)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.a);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.a);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.a);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.a);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.a);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.a);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.a)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.a);
                    }
                }
        }
        if(notes.get(R.drawable.asharp) != null){
            for(int i = 0; i<noteArray.size(); i++){
                if(notes.get(R.drawable.asharp)== noteArray.get(i))
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 1) {
                        firstImage.setBackgroundResource(R.drawable.asharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 2) {
                        secondImage.setBackgroundResource(R.drawable.asharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 3) {
                        thirdImage.setBackgroundResource(R.drawable.asharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 4) {
                        fourthImage.setBackgroundResource(R.drawable.asharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 5) {
                        fifthImage.setBackgroundResource(R.drawable.asharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 6) {
                        sixthImage.setBackgroundResource(R.drawable.asharp);
                    }
                    if (noteArray.indexOf(notes.get(R.drawable.asharp)) == 7) {
                        seventhImage.setBackgroundResource(R.drawable.asharp);
                    }
                }
        }

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
        try {
            FileReader fileReader = new FileReader(textFile);
            noteArray2.add(textFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(noteArray2);

    }


}

