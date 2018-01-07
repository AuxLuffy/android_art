package com.example.sunzh.android_art;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final int OVERLAY_PERM_REQ_CODE = 100;
    private Button mFloatingBtn;
    private LayoutParams lp;
    private WindowManager wm;
    private boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn :
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                    Toast.makeText(MainActivity.this, "can not DrawOverlays", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), OVERLAY_PERM_REQ_CODE);
                } else {
                    showWindow();
                }
                break;
            case R.id.btn1:

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERM_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(MainActivity.this, "permission denied by user. please recheck it in settings.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "permission granted!", Toast.LENGTH_SHORT).show();
                    showWindow();
                }
            }
        }
    }

    private void showWindow() {
        if (!isAdded) {
            mFloatingBtn = new Button(this);
            mFloatingBtn.setText("button");
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ALERT, 0, PixelFormat.TRANSPARENT);
            lp.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            lp.gravity = Gravity.LEFT | Gravity.TOP;
            lp.x = 100;
            lp.y = 300;
            wm.addView(mFloatingBtn, lp);
            mFloatingBtn.setOnTouchListener(this);
            isAdded = true;
        } else {
            Toast.makeText(MainActivity.this, "the window has showed yet", Toast.LENGTH_SHORT).show();
        }
    }

    private int lastX;
    private int lastY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                lp.x += (rawX - lastX);
                lp.y += (rawY - lastY);
                wm.updateViewLayout(mFloatingBtn, lp);
                break;
            default:
                break;
        }
        lastX = rawX;
        lastY = rawY;
        return false;
    }

    private AsyncTask downloadTask = new AsyncTask<URL,Integer,Long>(){
        @Override
        protected Long doInBackground(URL... urls) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    };
}
