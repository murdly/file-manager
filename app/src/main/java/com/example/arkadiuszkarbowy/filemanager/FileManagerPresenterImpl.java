package com.example.arkadiuszkarbowy.filemanager;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by arkadiuszkarbowy on 16/10/15.
 */
public class FileManagerPresenterImpl implements FileManagerPresenter {
    private FileManagerView mView;
    private Filedirs mModel;

    private ArrayList<Integer> mListPositions = new ArrayList();

    public FileManagerPresenterImpl(FileManagerView view, Filedirs model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onResume() {
        if (mModel.dirCanRead())
            mView.setItems(mModel.collectDirs());
        mView.setToolbarTitle(mModel.getCurrentPath());
    }


    @Override
    public boolean openPath(String filename) {
        if (!new File(filename).isDirectory() || !mModel.dirCanRead()) {
            return false;
        } else {
            mModel.appendToPath(filename);
            refresh();
        }

        return true;
    }

    @Override
    public void retainPosition(int position) {
        mListPositions.add(position);
    }

    @Override
    public void goUp() {
        mModel.setPathLevelUp();
        refresh();

        if(!mListPositions.isEmpty())
            mView.restore(mListPositions.remove(mListPositions.size() - 1));
    }

    private void refresh() {
        mModel.collectDirs();
        mView.notifyAdapter();
        mView.setToolbarTitle(mModel.getCurrentPath());
    }
}
