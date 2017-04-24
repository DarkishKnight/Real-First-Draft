package com.liam.realfirstdraft;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class NotePage extends AppCompatActivity {
    Map<Integer, Double> positions = new HashMap<>();
    Map<Integer, Object> notes = new HashMap<>();
    Map<Integer, Object> sheets = new HashMap<>();
    ArrayList<Integer> backgroundNoteArray = null;
    ArrayList<Integer>  noteArray = null;
    ImageView[] backgroundImageViews;
    ImageView[] imageViews;
    String name;
    String arrayFile;
    File file;
    FrameLayout[] frames;
    int screenWidth;
    int screenHeight;
    int numberOfNotes = 86;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);


        //get bundle from intent.
        name = getIntent().getExtras().getString("Name");
        if(getIntent()!=null && getIntent().getExtras()!= null){
           backgroundNoteArray = getIntent().getExtras().getIntegerArrayList("Notes");
            noteArray = getIntent().getExtras().getIntegerArrayList("Notes");
            File noteDir = new File(Environment.getExternalStorageDirectory(),"Humposer Notes");
            file = new File(noteDir.getAbsolutePath()+File.separator+name);
        }
//TODO write function to find out what the highest and lowest notes are, then determine which clef should be there

        if (backgroundNoteArray == null) {
            backgroundNoteArray = new ArrayList<>();
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
                        backgroundNoteArray.add(convertToInt);
                        noteArray.add(convertToInt);
                    }
                }
            }
        }



        if (backgroundNoteArray != null) {
            backgroundImageViews = new ImageView[backgroundNoteArray.size()];
            imageViews = new ImageView[backgroundNoteArray.size()];
            frames = new FrameLayout[backgroundNoteArray.size()];
            addingNotesToArray();
        }


        TextView title = (TextView) findViewById(R.id.Title);
        assert title != null;
        title.setText(name);

        //deletes the song file
        ImageButton deleteButton = (ImageButton) findViewById(R.id.trashIcon);
        assert deleteButton != null;
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




        //return to homepage
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        assert homeButton != null;
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


        //go to sheet music page
        ImageButton sheetMusic = (ImageButton) findViewById(R.id.sheetMusicButton);
        assert sheetMusic != null;
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

    //create the sheet music
    //TODO make this more readable
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addingNotesToArray(){
  //      quarternoteDrawables();
        //sets the images of the background
        backgroundImages();
        //sets the individual note images to overlay
        noteImages();
        //array full of coordinates of where bass clef notes go
        BdrawablePositions();
        //array full of coordinates of where treble clef notes go
        TdrawablePositions();
        //displays the backgroundImages array
        displayBackgroundImages();
        //creates copies of image views to put images in.
        copyImageViews();
    }



    private void displayBackgroundImages() {
//creates new framelayout
        createNewLayout();
        int y = 400;
        int x = 73;
        int counter = 0;
        int m = 0;
        int p = 1;
        int c = ViewGroup.LayoutParams.WRAP_CONTENT;
        int l = ViewGroup.LayoutParams.MATCH_PARENT;
        int layoutNumber = 0;
        int q = 0;
//creates new imageViews to add to framelayouts
        calculateScreenSize();
        for (int i = 0; i < backgroundNoteArray.size(); i++) {
            ImageView image = new ImageView(this);
            double imgHeight = screenHeight* 0.80;
            double imgWidth = screenWidth* 0.15;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.topMargin = l + y * q; //was 230 then 25
            params.leftMargin = c + x * m; //was 47 then 67
            image.setMaxHeight((int) imgHeight);
            image.setMaxWidth((int) imgWidth);
            m++;
            //todo add function to set the first image of each line to either treble or bass clef
            backgroundImageViews[counter] = image;
            // Adds the view to the layout
            if (counter != screenWidth/50 * p) { // was 9
                frames[layoutNumber].addView(image, params);
            }
            counter++;
            if (counter == screenWidth/50 * p) { // was 9
                q++;
                p++;
                m = 0;
            }


        }
        int imageNum = 0;
        for (Integer note : backgroundNoteArray) {
            if (imageNum <= backgroundNoteArray.size()) {
                Integer d = (Integer) sheets.get(note); //was notes
                backgroundImageViews[imageNum].setBackgroundResource(d);

                imageNum++;
            }


        }
    }

//Todo fill with code for imageviews for the notes
    private void copyImageViews() {
        int counter = 0;
        // y will be set to whatever number
        double y = 0;
        int x = 73;
        int m = 0;
        int p = 1;
        int c = ViewGroup.LayoutParams.MATCH_PARENT;
        int layoutNumber = 0;
        int imageNum = 0;
//creates new imageViews to add over the other image views
        for (int i = 0; i < noteArray.size(); i++) {
                if (imageNum <= noteArray.size()) {
                    y = positions.get(i); //gets weight of where imageView should lie
                    imageNum++;
                }
                ImageView noteImage = new ImageView(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, (int) y);
                params.gravity = (int) y;
                params.leftMargin = c + x * m;
                m++;

                imageViews[counter] = noteImage;
                // Adds the view to the layout
                if (counter != screenWidth / 50 * p) {
                    frames[layoutNumber].addView(noteImage, params);
                }
                counter++;
                if (counter == screenWidth / 50 * p) {
                    p++;
                    m = 0;
                }
            displayNoteImages();


            }
//        int imageNum = 0;
//        for (Integer note : noteArray) {
//            if (imageNum <= noteArray.size()) {
//                Double d = positions.get(note); //gets weight of where imageView should lie
//                imageViews[imageNum].getLayoutParams();
//
//
//
//                imageNum++;
//            }
//
//
//        }
        }



// Todo  fill with code for displaying the notes
    private void displayNoteImages() {
        int count = 0;
        for (Integer note : noteArray) {
            if (count <= noteArray.size()) {
                Integer d = (Integer) notes.get(note);
                imageViews[count].setBackgroundResource(d);
                count++;
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
            params1.topMargin = 1 + 100 * x; //was 100
            frames[counter1] = frameLayout;
            assert linearLayout != null;
            linearLayout.addView(frameLayout, params1);
            x++;
            counter1++;
        }
    }


//calculates screen size for positioning and sizing of images
    private void calculateScreenSize(){

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }


// the note plus where it should be positioned in relation to the imageview
    //TODO split into drawablePositionsBassClef and drawablePositionsTrebleClef
    private void BdrawablePositions(){
        calculateScreenSize();
        positions.put(0, .05);
        positions.put(1, .07);
        positions.put(2, .09);
        positions.put(3, .11);
        positions.put(4, .13);
        positions.put(5, .15);
        positions.put(6, .17);
        positions.put(7, .19);
        positions.put(8, .21);
        positions.put(9, .23);
        positions.put(10, .25);
        positions.put(11, .27);
        positions.put(12, .29);
        positions.put(13, .31);
        positions.put(14, .33);
        positions.put(15, .35);
        positions.put(16, .37);
        positions.put(17, .39);
        positions.put(18, .41);
        positions.put(19, .43);
        positions.put(20, .45);
        positions.put(21, .47);
        positions.put(22, .49);
        positions.put(23, .51);
        positions.put(24, .53);
        positions.put(25, .55);
        positions.put(26, .57);
        positions.put(27, .59);
        positions.put(28, .61);
        positions.put(29, .63);
        positions.put(30, .65);
        positions.put(31, .67);
        positions.put(32, .69);
        positions.put(33, .71);
        positions.put(34, .73);
        positions.put(35, .75);
        positions.put(36, .77);
        positions.put(37, .79);
        positions.put(38, .81);
        positions.put(39, .83);
        positions.put(40, .85);
        positions.put(41, .87);
        positions.put(42, .89);
        positions.put(43, .91);//done to here
        //todo change drawable background to fit with how I spaced the notes

    }

    private void TdrawablePositions(){
        positions.put(39, .83);
        positions.put(40, .85);
        positions.put(41, .87);
        positions.put(42, .89);
        positions.put(43, .91);
        positions.put(44, .11);
        positions.put(45, .95);
        positions.put(46, .97);
        positions.put(47, .99);
        positions.put(48, 1.01);
        positions.put(49, 1.03);
        positions.put(50, 1.05);
        positions.put(51, .383);
        positions.put(52, .403);
        positions.put(53, .423);
        positions.put(54, .443);
        positions.put(55, .463);
        positions.put(56, .483);
        positions.put(57, .423);
        positions.put(58, .423);
        positions.put(59, .423);
        positions.put(60, .363);
        positions.put(61, .383);
        positions.put(62, .403);
        positions.put(63, .423);
        positions.put(64, .443);
        positions.put(65, .463);
        positions.put(66, .483);
        positions.put(67, .423);
        positions.put(68, .423);
        positions.put(69, .423);
        positions.put(70, .363);
        positions.put(71, .383);
        positions.put(72, .403);
        positions.put(73, .423);
        positions.put(74, .443);
        positions.put(75, .463);
        positions.put(76, .483);
        positions.put(77, .423);
        positions.put(78, .423);
        positions.put(79, .423);
        positions.put(80, .363);
        positions.put(81, .383);
        positions.put(82, .403);
        positions.put(83, .423);
        positions.put(84, .443);
        positions.put(85, .463);
        positions.put(86, .483);
        positions.put(87, .423);
    }




// sets every note to a set of blank sheet music, so that the background is set to the correct
// size
    private void backgroundImages(){
        int count = 0;
        for(int i = 0; i<=numberOfNotes; i++) {
            sheets.put(count, R.drawable.sheetmusicbackground);
            count++;
        }
    }

    //add the note images to an array
    private void noteImages(){
        int count = 0;
        for(int i = 0; i<numberOfNotes; i++){
            notes.put(count, R.drawable.note);
            count++;
        }
    }


//   private void quarternoteDrawables(){
//        notes.put(14, R.drawable.dsharp);
//        notes.put(13, R.drawable.e);
//        notes.put(12, R.drawable.f);
//        notes.put(11, R.drawable.fsharp);
//        notes.put(10, R.drawable.g0);
//        notes.put(9, R.drawable.g0sharp);
//        notes.put(8, R.drawable.a);
//        notes.put(7, R.drawable.asharp);
//        notes.put(6, R.drawable.b);
//        notes.put(5, R.drawable.c);
//        notes.put(4, R.drawable.csharp);
//        notes.put(3, R.drawable.d0);
//        notes.put(2, R.drawable.d0sharp);
//        notes.put(1, R.drawable.e0);
//        notes.put(0, R.drawable.f0);
//    }

//    private void halfnoteDrawables(){
//        notes.put(14, R.drawable.dsharphalf);
//        notes.put(13, R.drawable.ehalf);
//        notes.put(12, R.drawable.fhalf);
//        notes.put(11, R.drawable.fsharp);
//        notes.put(10, R.drawable.g0half);
//        notes.put(9, R.drawable.g0sharphalf);
//        notes.put(8, R.drawable.ahalf);
//        notes.put(7, R.drawable.asharphalf);
//        notes.put(6, R.drawable.bhalf);
//        notes.put(5, R.drawable.chalf);
//        notes.put(4, R.drawable.csharphalf);
//        notes.put(3, R.drawable.d0half);
//        notes.put(2, R.drawable.d0sharphalf);
//        notes.put(1, R.drawable.e0half);
//        notes.put(0, R.drawable.f0half);
//    }
//    private void wholenoteDrawable(){
//        notes.put(14, R.drawable.dsharpwhole);
//        notes.put(13, R.drawable.ewhole);
//        notes.put(12, R.drawable.fwhole);
//        notes.put(11, R.drawable.fsharpwhole);
//        notes.put(10, R.drawable.g0whole);
//        notes.put(9, R.drawable.g0sharpwhole);
//        notes.put(8, R.drawable.awhole);
//        notes.put(7, R.drawable.asharpwhole);
//        notes.put(6, R.drawable.bwhole);
//        notes.put(5, R.drawable.cwhole);
//        notes.put(4, R.drawable.csharpwhole);
//        notes.put(3, R.drawable.d0whole);
//        notes.put(2, R.drawable.d0sharpwhole);
//        notes.put(1, R.drawable.e0whole);
//        notes.put(0, R.drawable.f0whole);
//    }
}

