package com.example.arkadiuszkarbowy.filemanager;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public class Filedirs {

    private String mPath = "/data/user/0/com.example.arkadiuszkarbowy.filemanager";//File.separator;
    private List<String> mFilenames;

    public Filedirs() {
        this.mFilenames = new ArrayList<>();
    }

    public boolean dirCanRead() {
        return new File(mPath).canRead();
    }

    public List<String> collectDirs() {
        if (!mFilenames.isEmpty()) mFilenames.clear();

        File dir = new File(mPath);
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                        mFilenames.add(getCurrentPath() + File.separator+ file);
                }
            }
        }

        Collections.sort(mFilenames);
        return mFilenames;
    }

    public void setPath(String p) {
            mPath = p;
    }

    public void setPathLevelUp() {
        int index = mPath.lastIndexOf("/");
        if (index != 0)
            mPath = mPath.substring(0, index);
        else
            mPath = File.separator;
    }

    public String getCurrentPath() {
        return mPath;
    }
}
