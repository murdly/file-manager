package com.example.arkadiuszkarbowy.filemanager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public class Filedirs {

    private String mPath = "/";
    private List<String> mFilenames;

    public Filedirs(Context c) {
        this.mFilenames = new ArrayList<>();

        File file = new File(mPath + "sdcard/testapp/",  "lol.txt");
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("tekst");
            pw.flush();
            pw.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    mFilenames.add(getCurrentPath() + File.separator + file);
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

//    public boolean delete(String filename) {
//        File f = new File(getCurrentPath() + File.separator + filename);
//        return f.exists() && f.delete();
//    }

    public boolean deletePath(String path) {
        File f = new File(path);
        return f.exists() && f.delete();
    }

    public boolean create(String dirname) {
        File f = new File(getCurrentPath() + File.separator + dirname);
        return !f.exists() && f.mkdir();
    }

    public boolean createPath(String path) {
        File f = new File(path);
        return !f.exists() && f.mkdir();
    }

    public boolean move(String srcPath, boolean keep) {
        File src = new File(srcPath);
        File dst = new File(getCurrentPath() + File.separator + FileAdapter.cutFileName(srcPath));

        boolean moved = false;
        try {
            moved = copyFile(src, dst);
            if (!keep) deletePath(srcPath);
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
