package com.example.arkadiuszkarbowy.filemanager;

/**
 * Created by arkadiuszkarbowy on 22/10/15.
 */
public class Clipboard {
    private String mPath = null;
    private static Clipboard mInstance = null;

    private Clipboard() {
    }

    public static Clipboard getInstance() {
        if (mInstance == null)
            mInstance = new Clipboard();
        return mInstance;
    }

    public void save(String path) {
        mPath = path;
    }

    public String get() {
        return mPath;
    }

    public void clear() {
        mPath = null;
    }

    public boolean full() {
        return mPath != null;
    }
}
