package com.example.arkadiuszkarbowy.filemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class FileManagerActivity extends AppCompatActivity implements FileManagerView {
    private ListView mFiles;
    private FileAdapter mAdapter;
    private FileOptionsMenu mFileMenu;
    private FileManagerPresenter mPresenter;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new FileManagerPresenterImpl(this, new Filedirs(this));
        initToolbars();
        initFilesList();
    }

    private void initToolbars() {
        Toolbar top = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(top);
        mToolbarTitle = (TextView) top.findViewById(R.id.toolbar_title);

        Toolbar bottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        mFileMenu = (FileOptionsMenu) bottom.findViewById(R.id.menu_view);
        getMenuInflater().inflate(R.menu.menu_toolbar, mFileMenu.getMenu());
        mFileMenu.setOnMenuItemClickListener(mMenuItemListener);
    }

    private void initFilesList() {
        mFiles = (ListView) findViewById(R.id.files);
        mFiles.setOnItemClickListener(mOnFileClickListener);
        mFiles.setEmptyView(findViewById(R.id.emptyFolder));
        mFiles.setOnItemLongClickListener(mOnFileSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void setItems(List<String> files) {
        mAdapter = new FileAdapter(this, R.layout.file_item, files);
        mFiles.setAdapter(mAdapter);
    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void restoreListPosition(int position) {
        mFiles.smoothScrollToPosition(position);
    }

    @Override
    public void setToolbarTitle(String title) {
        if (title.length() > 1 && title.substring(0, 2).endsWith(File.separator))
            mToolbarTitle.setText(title.substring(1));
        else
            mToolbarTitle.setText(title);
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uncheckItem() {
        if (isSingleItemChecked()) {
            mAdapter.uncheck();
        }
    }

    private AdapterView.OnItemClickListener mOnFileClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (isSingleItemChecked()) {
                uncheckIfCorresponding(position);
            } else {
                mPresenter.retainListPosition(position);
                mPresenter.openPath(mAdapter.getItem(position));
            }
        }
    };

    private boolean isSingleItemChecked() {
        return mAdapter.hasItemChecked();
    }

    private void uncheckIfCorresponding(int position) {
        if (mAdapter.isChecked(position)) {
            mAdapter.uncheck();
            showUncheckedFileOptions();
        }
    }

    @Override
    public void onBackPressed() {
        mPresenter.goUp();
    }

    private AdapterView.OnItemLongClickListener mOnFileSelectedListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (Clipboard.getInstance().full()) return false;

            mAdapter.setItemChecked(position);
            showCheckedFileOptions();
            return true;
        }
    };

    @Override
    public void showUncheckedFileOptions() {
        mFileMenu.showItemUncheckedOptions();
    }

    @Override
    public void showCheckedFileOptions() {
        mFileMenu.showItemCheckedOptions();
    }

    @Override
    public void showPasteFileOptions() {
        mFileMenu.showItemPasteOptions();
    }

    private ActionMenuView.OnMenuItemClickListener mMenuItemListener = new ActionMenuView
            .OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_new_dir:
                    mPresenter.createDir(getFragmentManager());
                    break;
                case R.id.action_delete:
                    mPresenter.delete(mAdapter.getCheckedItemPath());
                    break;
                case R.id.action_cut:
                    mPresenter.move(mAdapter.getCheckedItemPath(), false);
                    break;
                case R.id.action_copy:
                    mPresenter.move(mAdapter.getCheckedItemPath(), true);
                    break;
                case R.id.action_paste:
                    mPresenter.paste();
                    break;
                case R.id.action_cancel:
                    mPresenter.cancel();
                    break;
            }

            return true;
        }
    };
}