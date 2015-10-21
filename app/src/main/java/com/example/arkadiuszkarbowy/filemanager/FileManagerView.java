package com.example.arkadiuszkarbowy.filemanager;

import android.content.Context;

import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public interface FileManagerView {
    void setItems(List<String> files);
    void setToolbarTitle(String title);
    void notifyAdapter();
    void restore(int position);
    Context getContext();
}
