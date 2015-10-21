package com.example.arkadiuszkarbowy.filemanager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.support.v7.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FileManagerActivity extends AppCompatActivity implements FileManagerView {

    private ListView mFiles;
    private FileAdapter mAdapter;
    private ActionMenuView mMenuToolbar;
    private FileManagerPresenter mPresenter;

    private int mFirstVisibleItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new FileManagerPresenterImpl(this, new Filedirs());

        mFiles = (ListView) findViewById(R.id.files);
        mFiles.setOnItemClickListener(mOnFileClickListener);
        mFiles.setEmptyView(findViewById(R.id.emptyFolder));
        mFiles.setOnScrollListener(mOnScrollPositionListener);

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bottom);
        mMenuToolbar = (ActionMenuView) toolbar.findViewById(R.id.menu_view);
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
        setTitle(title);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private AdapterView.OnItemClickListener mOnFileClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            boolean success = mPresenter.openPath(mAdapter.getItem(position));
            if (!success) Toast.makeText(getContext(), "not a directory", Toast.LENGTH_LONG).show();
            else
                mPresenter.retainPosition(mFirstVisibleItemPosition);
        }
    };

    @Override
    public void onBackPressed() {
        mPresenter.goUp();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_dir:
                break;
        }

        return true;
    }
}
