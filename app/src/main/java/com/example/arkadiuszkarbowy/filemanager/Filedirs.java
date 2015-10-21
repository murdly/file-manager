package com.example.arkadiuszkarbowy.filemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public class Filedirs {

    private String mPath = File.separator;
    private List<String> mFilenames;

    public Filedirs() {
        this.mFilenames = new ArrayList<>();
    }

    public boolean dirCanRead(){
        return new File(mPath).canRead();
    }
    public List<String> collectDirs() {
        if (!mFilenames.isEmpty()) mFilenames.clear();

        File dir = new File(mPath);

        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                    mFilenames.add(file);
                }
            }
        }

        Collections.sort(mFilenames);
        return mFilenames;
    }

    public void appendToPath(String filename) {
        if (mPath.endsWith(File.separator))
            mPath += filename;
        else
            mPath += File.separator + filename;
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
