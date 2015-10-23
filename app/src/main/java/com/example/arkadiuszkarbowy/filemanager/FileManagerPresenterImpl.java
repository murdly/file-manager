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
    public boolean openPath(String path) {
        if (!new File(path).isDirectory() || !mModel.dirCanRead()) {
            return false;
        } else {
            mModel.setPath(path);
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

        if (!mListPositions.isEmpty())
            mView.restore(mListPositions.remove(mListPositions.size() - 1));
    }





    @Override
    public void createDir() {
        CreateDirListener listener = new CreateDirListener();
        DirNameDialog.newInstance(listener).show(mView.getContext().getFragmentManager(), "create");
    }

    @Override
    public void delete(String path) {
        if (mModel.deletePath(path)) refresh();
        else mView.showToast(R.string.must_be_empty);

        mView.showUncheckedFileOptions();
    }

    @Override
    public void cancel() {
        Clipboard.getInstance().clear();
        mView.showUncheckedFileOptions();
    }

    @Override
    public void move(String src, boolean keep) {
        if (new File(src).isDirectory()) {
            mView.showToast(R.string.not_provided);
            mView.showUncheckedFileOptions();
            return;
        }

        mView.showPasteFileOptions();
        Clipboard.getInstance().save(src, keep);

        if (keep) mView.showToast(R.string.choose_destination);
        else mView.showToast(R.string.copied_to_clipboard);
    }

    @Override
    public void paste() {
        Clipboard clipboard = Clipboard.getInstance();

        boolean moved = mModel.move(clipboard.get(), clipboard.shouldKeep());
        clipboard.clear();

        if (moved) refresh();
        else mView.showToast(R.string.cant_create);

        mView.showUncheckedFileOptions();
    }

    private void refresh() {
        mModel.collectDirs();
        mView.notifyAdapter();
        mView.setToolbarTitle(mModel.getCurrentPath());
    }

    private class CreateDirListener implements DirNameDialog.DirNameListener {
        boolean created = false;

        @Override
        public void onResult(String dirname) {
            created = mModel.create(dirname);
            if (created) refresh();
            else mView.showToast(R.string.cant_create);
        }
    }
}