package com.example.arkadiuszkarbowy.filemanager;

import android.os.Environment;
import android.util.Log;

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

    private String mPath = "/data/user/0/com.example.arkadiuszkarbowy.filemanager";//File.separator;
    private List<String> mFilenames;

    public Filedirs() {
        this.mFilenames = new ArrayList<>();
        File file = new File(mPath + File.separator + "files", "lol.txt");

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

    public boolean delete(String filename) {
        File f = new File(getCurrentPath() + File.separator + filename);
        return f.exists() && f.delete();
    }

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

    public void copytest(){
        Log.d("copy", "kopoo");
        File src = new File(mPath + File.separator +"/lol.txt");
        File dst = new File(mPath + File.separator + "/hk"+ "/kopia3.txt");
        try {
            copyFile(src,dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File src, File dst) throws IOException
    {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
}
