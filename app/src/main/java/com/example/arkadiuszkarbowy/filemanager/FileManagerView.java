package com.example.arkadiuszkarbowy.filemanager;

import android.content.Intent;

import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public interface FileManagerView {
    void setToolbarTitle(String title);

    void setItems(List<String> files);

    void notifyAdapter();

    void restoreListPosition(int position);

    void uncheckItem();

    void showUncheckedFileOptions();

    void showCheckedFileOptions();

    void showPasteFileOptions();

    void launchChooser(Intent intent);

    void showToast(int resId);
}
