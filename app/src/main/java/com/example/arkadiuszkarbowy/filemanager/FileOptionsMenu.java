package com.example.arkadiuszkarbowy.filemanager;

import android.content.Context;
import android.support.v7.widget.ActionMenuView;
import android.util.AttributeSet;

/**
 * Created by arkadiuszkarbowy on 23/10/15.
 */
public class FileOptionsMenu extends ActionMenuView {

    public FileOptionsMenu(Context context) {
        super(context);
    }

    public FileOptionsMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showItemUncheckedOptions() {
        enableUncheckedGroup(true);
        enableCheckedGroup(false);
        enablePasteGroup(false);
    }

    public void showItemCheckedOptions() {
        enableCheckedGroup(true);
        enableUncheckedGroup(false);
        enablePasteGroup(false);
    }

    public void showItemPasteOptions() {
        enablePasteGroup(true);
        enableUncheckedGroup(false);
        enableCheckedGroup(false);
    }

    private void enableCheckedGroup(boolean enabled) {
        getMenu().setGroupVisible(R.id.checkedState, enabled);
    }

    private void enableUncheckedGroup(boolean enabled) {
        getMenu().setGroupVisible(R.id.uncheckedState, enabled);
    }

    private void enablePasteGroup(boolean enabled) {
        getMenu().setGroupVisible(R.id.pasteState, enabled);
    }
}