package com.coolweather.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import com.coolweather.app.R;
import com.coolweather.app.util.LogUtil;




public class SettingsActivity extends Activity {
    private TextView logLevelText;
    private SeekBar logLevelBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_layout);

        logLevelText = (TextView) findViewById(R.id.log_level_txt);
        logLevelBar = (SeekBar) findViewById(R.id.log_level);

        setBarValueAndText(LogUtil.CURRENT_LEVEL);

        logLevelBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtil.d(progress + "");
                setBarValueAndText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int level = seekBar.getProgress();
                if (level == 0) {
                    level = 6;
                }
                LogUtil.CURRENT_LEVEL = level;
            }
        });
    }

    private String[] logLevelDescaArr = {"NOTHING", "VERBOSE", "DEBUG", "INFO", "WARN", "ERROR", "NOTHING"};

    private void setBarValueAndText(int value) {
        logLevelBar.setProgress(value);
        logLevelText.setText(getString(R.string.log_level_desc).replace("?", logLevelDescaArr[value]));
    }
}
