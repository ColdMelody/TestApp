package com.demos.testapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by peng on 2016/6/15.
 */
public class HideInputSoft {
    /**
     * 隐藏键盘
     * @param activity
     * @param editText
     */
    private void hideSoftInput(Activity activity, EditText editText) {
        if (activity.getCurrentFocus() != null) {
            ((InputMethodManager) editText.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
