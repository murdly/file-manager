package com.example.arkadiuszkarbowy.filemanager;

import android.app.FragmentManager;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public interface FileManagerPresenter {
    void onResume();

    boolean openPath(String path);

    void retainListPosition(int position);

    void goUp();

    void createDir(FragmentManager fm);

    void delete(String path);

    void move(String src, boolean keep);

    void paste();

    void cancel();
}
