package com.example.arkadiuszkarbowy.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public class Filedir {

    private String mPath = File.separator;
    private List<String> mFilenames;

    public Filedir() {
        this.mFilenames = new ArrayList<>();
    }

    public List<String> collectDirs() {
        if (!mFilenames.isEmpty()) mFilenames.clear();

        File dir = new File(mPath);
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                    if (getCurrentPath().endsWith(File.separator))
                        mFilenames.add(getCurrentPath() + file);
                    else
                        mFilenames.add(getCurrentPath() + File.separator + file);
                }
            }
        }

        Collections.sort(mFilenames);
        return mFilenames;
    }

    public boolean dirCanRead() {
        return new File(mPath).canRead();
    }

    public void setPath(String p) {
        mPath = p;
    }

    public void setPathLevelUp() {
        int index = mPath.lastIndexOf(File.separator);
        if (index != 0)
            mPath = mPath.substring(0, index);
        else
            mPath = File.separator;
    }

    public String getCurrentPath() {
        return mPath;
    }

    public boolean delete(String path) {
        File f = new File(path);
        return f.exists() && f.delete();
    }

    public boolean create(String dirname) {
        File f = new File(getCurrentPath() + File.separator + dirname);
        return !f.exists() && f.mkdir();
    }

    public boolean move(String srcPath, boolean keep) {
        File src = new File(srcPath);
        File dst = new File(getCurrentPath() + File.separator + Utils.parseFileName(srcPath));

        boolean moved = false;
        try {
            moved = copyFile(src, dst);
            if (!keep) delete(srcPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return moved;
    }

    private static boolean copyFile(File src, File dst) throws IOException {
        long copied = 0;

        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            copied = inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            outChannel.close();
        }

        return copied != 0;
    }
}
