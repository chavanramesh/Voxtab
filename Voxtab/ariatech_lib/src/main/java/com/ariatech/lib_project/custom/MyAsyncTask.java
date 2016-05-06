package com.ariatech.lib_project.custom;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;

public abstract class MyAsyncTask<T, V, Q> extends AsyncTask<T, V, Q> {
    protected TransparentProgressDialog pd;
    protected View view;

    public MyAsyncTask(View view) {
        this.view = view;
    }

    public MyAsyncTask() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void executeContent(T... content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
        } else {
            this.execute(content);
        }
    }

    @Override
    protected void onPostExecute(Q q) {
        super.onPostExecute(q);
        if (view != null)
            view.setClickable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (view != null)
            view.setClickable(false);
    }
}