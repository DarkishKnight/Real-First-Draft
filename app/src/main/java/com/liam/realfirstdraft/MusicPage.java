package com.liam.realfirstdraft;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPage extends AppCompatActivity {
    ListView musiclist;
    Cursor musiccursor;
    int music_column_index;
    int count;
    MediaPlayer mMediaPlayer;
    File newFile;
    String fileName;

    protected void renameFile() {

        File oldFile = new File(newFile.getParent()+File.separator+fileName);
        newFile.renameTo(oldFile);
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

        File extDirectory = new File(Environment.getExternalStorageDirectory(),"Humposer");
        File[] fileList = extDirectory.listFiles();

        ArrayList<String> listViewValues = new ArrayList<String>();
        for (int i=0; i<fileList.length; i++) {
            System.out.println(fileList[i].getAbsoluteFile());
            listViewValues.add(fileList[i].getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                listViewValues.toArray(new String[listViewValues.size()]));
        musiclist.setAdapter(adapter);

        musiclist.setOnItemClickListener(musicgridlistener);
        //mMediaPlayer = new MediaPlayer();
    }

    protected AdapterView.OnItemClickListener musicgridlistener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            System.gc();
           /* music_column_index = musiccursor.getColumnIndexOrThrow("Humposer"+File.separator+);
            musiccursor.moveToPosition(position);
            String fileList = musiccursor.getString(music_column_index);
*/
            AlertDialog.Builder builder = new AlertDialog.Builder(MusicPage.this);
            builder.setTitle("Song Name");

            final EditText input = new EditText(MusicPage.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setNeutralButton("Rename", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fileName = input.getText().toString();
                    renameFile();
                }
            });
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
           /* try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.reset();
                }
                mMediaPlayer.setDataSource(fileList);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (Exception e) {

           */
        }
    };



    public class MusicAdapter extends BaseAdapter {
        private Context mContext;

        public MusicAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
//            System.gc();
            TextView tv = new TextView(mContext.getApplicationContext());

//            String id;
//            if (convertView == null) {
//                music_column_index = musiccursor
//                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
//                musiccursor.moveToPosition(position);
//                id = musiccursor.getString(music_column_index);
//                music_column_index = musiccursor
//                        .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
//                musiccursor.moveToPosition(position);
//                id += " Size(KB):" + musiccursor.getString(music_column_index);
//                tv.setText(id);
//            } else
//                tv = (TextView) convertView;
            return tv;
        }
    }
}





