package com.cheng.scaleseekbarexample;

import android.app.Activity;
import android.os.Bundle;

import com.cheng.scaleseekbar.ScaleSeekBar;

public class MainActivity extends Activity {

    private ScaleSeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar = (ScaleSeekBar) findViewById(R.id.seek_bar);
        mSeekBar.setProgressListener(new ScaleSeekBar.ProgressListener() {
            @Override
            public void notifyProgressChanged(int progress) {
                //todo what you want to do with progress ratio
            }
        });
    }
}
