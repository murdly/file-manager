package com.example.arkadiuszkarbowy.filemanager;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
    private ActionMenuView mMenuToolbar;
    private FileManagerPresenter mPresenter;
    private TextView mToolbarTitle;
    private int mFirstVisibleItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new FileManagerPresenterImpl(this, new Filedirs());
        initToolbars();
        initFilesList();
    }

    private void initToolbars() {
        Toolbar top = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(top);
        mToolbarTitle = (TextView) top.findViewById(R.id.toolbar_title);

        Toolbar bottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        mMenuToolbar = (ActionMenuView) bottom.findViewById(R.id.menu_view);
        mMenuToolbar.setOnMenuItemClickListener(mMenuItemListener);
    }

    private void initFilesList() {
        mFiles = (ListView) findViewById(R.id.files);
        mFiles.setOnItemClickListener(mOnFileClickListener);
        mFiles.setEmptyView(findViewById(R.id.emptyFolder));
        mFiles.setOnScrollListener(mOnScrollPositionListener);
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
    public void restore(int position) {
        mFiles.setSelection(position);
    }

    @Override
    public void setToolbarTitle(String title) {
        if (title.length() > 1 && title.substring(0, 2).endsWith(File.separator))
            mToolbarTitle.setText(title.substring(1));
        else
            mToolbarTitle.setText(title);
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void endEdition() {
        mAdapter.uncheck();
        enableItemOptions(false);
    }

    private AdapterView.OnItemClickListener mOnFileClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!isSingleItemChecked()) {
                boolean success = mPresenter.openPath(mAdapter.getItem(position));
                if (!success) Toast.makeText(getContext(), "not a directory", Toast.LENGTH_LONG).show();
                else
                    mPresenter.retainPosition(mFirstVisibleItemPosition);
            } else {
                uncheckIfCorresponding(position);
            }
        }
    };

    private boolean isSingleItemChecked() {
        return mAdapter.hasItemChecked();
    }

    private void uncheckIfCorresponding(int position) {
        if (mAdapter.isChecked(position)) {
            mAdapter.uncheck();
            enableItemOptions(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (isSingleItemChecked()) {
            mAdapter.uncheck();
            enableItemOptions(false);
        }

        mPresenter.goUp();
    }

    private AdapterView.OnItemLongClickListener mOnFileSelectedListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            mAdapter.setItemChecked(position);
            enableItemOptions(true);
            return true;
        }
    };

    private void enableItemOptions(boolean enabled) {
        Menu m = mMenuToolbar.getMenu();
        m.setGroupVisible(R.id.uncheckedState, !enabled);
        m.setGroupVisible(R.id.checkedState, enabled);
    }

    private AbsListView.OnScrollListener mOnScrollPositionListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
                totalItemCount) {
            mFirstVisibleItemPosition = firstVisibleItem;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, mMenuToolbar.getMenu());
        return true;
    }

    private ActionMenuView.OnMenuItemClickListener mMenuItemListener = new ActionMenuView
            .OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_new_dir:
                    mPresenter.createDir();
                    break;
                case R.id.action_delete:
                    String path = mAdapter.getCheckedItemPath();
                    mPresenter.deleteDir(path);
                    break;
            }

            return true;
        }
    };
}