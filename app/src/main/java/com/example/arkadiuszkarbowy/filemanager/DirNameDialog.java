package com.example.arkadiuszkarbowy.filemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by arkadiuszkarbowy on 22/10/15.
 */
public class DirNameDialog extends DialogFragment {

    private DirNameListener mOnNameSetListener;
    private EditText mInput;
    private Button mPositiveButton;

    public interface DirNameListener {
        void onResult(String dirname);
    }

    static DirNameDialog newInstance(DirNameListener onClickListener) {
        DirNameDialog dialog = new DirNameDialog();
        dialog.setOnTitleSetListener(onClickListener);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.set_filename));
        builder.setView(createInputLayout());
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        builder.setPositiveButton(getResources().getString(R.string.create), mPositiveButtonListener);

        AlertDialog dialog = builder.show();
        mPositiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        mPositiveButton.setEnabled(false);
        mInput.addTextChangedListener(mInputWatcher);

        return dialog;
    }

    private LinearLayout createInputLayout() {
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int mpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                .getDisplayMetrics());
        params.setMargins(mpx, 0, mpx, 0);
        mInput = new EditText(getActivity());
        layout.addView(mInput, params);
        return layout;
    }

    private DialogInterface.OnClickListener mPositiveButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mOnNameSetListener.onResult(mInput.getText().toString());
        }
    };

    private TextWatcher mInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mInput.getText().length() > 0)
                mPositiveButton.setEnabled(true);
            else
                mPositiveButton.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setOnTitleSetListener(DirNameListener onClickListener) {
        mOnNameSetListener = onClickListener;
    }
}