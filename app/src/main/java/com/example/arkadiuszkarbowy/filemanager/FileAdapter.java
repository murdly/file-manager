package com.example.arkadiuszkarbowy.filemanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
    private int mItemCheckedPosition = -1;

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

        if(mItemCheckedPosition == position)
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        else
            convertView.setBackgroundColor(Color.TRANSPARENT);

        ViewHolder holder = new ViewHolder(convertView);
        String filepath = getItem(position);
        holder.mFileName.setText(Utils.parseFileName(filepath));

        File f = new File(filepath);
        if (f.isFile() || !f.isDirectory())
            holder.mFileIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file));
        else
            holder.mFileIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_folder));

        return convertView;
    }

    public void setItemChecked(int position) {
        mItemCheckedPosition = position;
        notifyDataSetChanged();
    }

    public boolean hasItemChecked() {
        return mItemCheckedPosition != -1;
    }

    public boolean isChecked(int position) {
        return mItemCheckedPosition == position;
    }

    public void uncheck() {
        mItemCheckedPosition = -1;
        notifyDataSetChanged();
    }

    public String getCheckedItemPath() {
        return getItem(mItemCheckedPosition);
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