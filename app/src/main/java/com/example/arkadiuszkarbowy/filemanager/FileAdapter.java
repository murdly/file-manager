package com.example.arkadiuszkarbowy.filemanager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public class FileAdapter extends ArrayAdapter<String> {
    private Context mContext;

    public FileAdapter(Context context, int row, List<String> filenames) {
        super(context, row, filenames);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.file_item, parent, false);
        }

        ViewHolder holder = new ViewHolder(convertView);
        String filename = getItem(position);
        holder.mFileName.setText(filename);

        File f = new File(filename);

        if (f.isFile() || !f.isDirectory())
            holder.mFileIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file));
        else
            holder.mFileIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_folder));

        return convertView;
    }

    public static class ViewHolder {
        public ImageView mFileIcon;
        private TextView mFileName;

        public ViewHolder(View v) {
            mFileIcon = (ImageView) v.findViewById(R.id.fileicon);
            mFileName = (TextView) v.findViewById(R.id.filename);
        }
    }
}
