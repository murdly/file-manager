package com.example.arkadiuszkarbowy.filemanager;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public interface FileManagerPresenter {
    void onResume();

    boolean openPath(String filename);

    void retainPosition(int position);

    void goUp();

    void createDir();

    void delete(String filename);

    void paste();

    void cancel();

    void move(String src, boolean keep);
}
