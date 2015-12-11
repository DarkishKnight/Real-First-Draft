package com.liam.realfirstdraft;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {
    private Context mContext;

    public MusicAdapter(Context c) {
        mContext = c;
    }


    @Override
    public int getCount() {
        return 0;
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
