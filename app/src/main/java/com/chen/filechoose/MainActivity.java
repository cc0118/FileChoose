package com.chen.filechoose;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {


    private TextView tvFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvFilePath = findViewById(R.id.tv_choose_filename);
    }

    /**
     * 按钮 点击事件
     *
     * @param view
     */
    @NeedsPermission({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    })
    public void MyClick(View view) {
        Intent intent = new Intent(MainActivity.this, ChooseFilesActivity.class);
        switch (view.getId()) {
            case R.id.bt_cho_file:
                // 选择文件
                intent.putExtra("chooseType", 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.bt_cho_folder:
                //选择文件夹

                intent.putExtra("chooseType", 0);
                startActivityForResult(intent, 1);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            tvFilePath.setText(data.getStringExtra("chooseType"));
        }
    }
}