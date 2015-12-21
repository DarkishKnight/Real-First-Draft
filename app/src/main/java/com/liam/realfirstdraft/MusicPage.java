package com.liam.realfirstdraft;


import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;


public class MusicPage extends AppCompatActivity {
    ListView musiclist;
    File[] fileList;
    ArrayList listViewValues;
    String listFile;
    Cursor musiccursor;
    int music_column_index;
    int count;
    MediaPlayer mMediaPlayer;
    File newFile;
    String fileName;

  protected void renameFile() {

      File oldFile = new File(newFile.getParent() + File.separator + fileName);
      newFile.renameTo(oldFile);
      // copy tempFile to saveToFileName

      File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
      fileList = extDirectory.listFiles();
      for (int i = 0; i < fileList.length; i++) {
          System.out.println(fileList[i].getAbsoluteFile());
      }

  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);
        init_phone_music_grid();
        final ImageButton backButton1 = (ImageButton) findViewById(R.id.backButton1);

        backButton1.setOnClickListener(
                new ImageButton.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(e);
                    }
                }

        );

    }


    private void init_phone_music_grid() {
        System.gc();
//        String[] fileName = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Video.Media.SIZE};
//
//        musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, fileName, null, null, null);
//
//        count = musiccursor.getCount();
        musiclist = (ListView) findViewById(R.id.listView);
//        musiclist.setAdapter(new MusicAdapter(getApplicationContext()));


        File extDirectory = new File(Environment.getExternalStorageDirectory(), "Humposer");
        fileList = extDirectory.listFiles();
        listFile = extDirectory.getAbsoluteFile().getName();

        ArrayList<String> listViewValues = new ArrayList<>();
        for (File aFileList : fileList) {
            System.out.println(aFileList.getAbsoluteFile());
            listViewValues.add(aFileList.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                listViewValues.toArray(new String[listViewValues.size()]));
        musiclist.setAdapter(adapter);

        musiclist.setOnItemClickListener(musicgridlistener);
        //mMediaPlayer = new MediaPlayer();
    }







    protected AdapterView.OnItemClickListener musicgridlistener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            System.gc();
            Bundle b = new Bundle();
            File selectedFile = fileList[((int)id)];
            b.putString("listItem", String.valueOf(selectedFile));
            Intent g = new Intent(getBaseContext(), MusicPlayer.class);
            g.putExtras(b);
            startActivity(g);

        }
    };


    /* music_column_index = musiccursor.getColumnIndexOrThrow("Humposer"+File.separator+);
     musiccursor.moveToPosition(position);
     String fileList = musiccursor.getString(music_column_index);
*/
            /*final AlertDialog.Builder builder = new AlertDialog.Builder(MusicPage.this);
            builder.setTitle(fileName);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            final EditText input = new EditText(MusicPage.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
// Set up the buttons
            builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fileName = input.getText().toString();
                  */
                /*
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNeutralButton("Play", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    File newFile = new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath(), fileName);
                    try {
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(newFile.getAbsolutePath());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }


                }
            });

            builder.show();
           try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.reset();
                }
                mMediaPlayer.setDataSource(fileList);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (Exception e) {

           */
        }








