package com.example.arkadiuszkarbowy.filemanager;

/**
 * Created by arkadiuszkarbowy on 22/10/15.
 */
public class Clipboard {
    private String mPath = null;
    private boolean mKeep = false;
    private static Clipboard mInstance = null;

    private Clipboard() {
    }

    public static Clipboard getInstance() {
        if (mInstance == null)
            mInstance = new Clipboard();
        return mInstance;
    }

    public void save(String path, boolean keep) {
        mPath = path;
        mKeep = keep;
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

    public boolean shouldKeep() { return mKeep;}
}
