package com.example.arkadiuszkarbowy.filemanager;

import java.util.List;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public interface FileManagerView {
    void setItems(List<String> files);

    void setToolbarTitle(String title);

    void notifyAdapter();

    void restoreListPosition(int position);

    void showToast(int resId);

    void uncheckItem();

    void showUncheckedFileOptions();

    void showCheckedFileOptions();

    void showPasteFileOptions();
}
