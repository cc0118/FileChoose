package com.chen.filechoose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.adapter.ChooseFileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseFilesActivity extends AppCompatActivity {
    private static final String TAG = "ChooseFilesActivity";
    // 文件/文件夹 列表容器
    private RecyclerView rvFileList;

    // 文件 列表
    private List<File> fileList = new ArrayList<>();
    // 选择文件/文件夹 列表适配器
    private ChooseFileAdapter chooseFileAdapter;
    // 当前路径
    private TextView currentPath;
    // 存放上一级目录文件
    private File parentFile;
    // 上一级按钮
    private LinearLayout upOneLevel;
    // 选择标志：1：文件  0：文件夹
    private int chooseType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_files);

        upOneLevel = findViewById(R.id.ll_up_one_level);
        rvFileList = findViewById(R.id.rv_file_list);
        currentPath = findViewById(R.id.tv_current_ath);

        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        // 选择标志
        chooseType = Integer.parseInt(getIntent().getStringExtra("chooseType"));
        Log.v("CHEN", "设置选择文件类型：" + chooseType);
        // 初始化数据
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        getFileList(filePath);
        // 显示当前目录位置: 根目录不显示路径
        currentPath.setText("本地存储设备>");

        // 初始化 容器
        chooseFileAdapter = new ChooseFileAdapter(this, fileList, chooseType);
        rvFileList.setAdapter(chooseFileAdapter);
        // 列表点击事件 这里调用adapter 自己写的点击接口
        chooseFileAdapter.setOnItemClickListener(new ChooseFileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (fileList.get(position).isFile()) {
                    // 选择文件测试
                    // 选择文件 直接点击文件返回路径
                    Log.i(TAG, "选中文件：" + fileList.get(position).getName());
                    String chooseFile = fileList.get(position).getPath();
                    Intent intent = new Intent();
                    intent.putExtra("chooseFile", chooseFile);
                    // 返回标志 1
                    setResult(1, intent);
                    finish();
                } else {
                    // 根目录不显示上一级按钮
                    upOneLevel.setVisibility(View.VISIBLE);
                    // 选择文件夹就进入目录后 点击确认即可选择目录
                    String filePath = fileList.get(position).getPath();
                    parentFile = fileList.get(position);
                    Log.v(TAG, "父目录:" + parentFile.getParent());
                    // 显示当前路径
                    currentPath.setText(filePath.replace("/storage/emulated/0", "本地存储设备").replace("/", ">"));
                    // 执行遍历 目录
                    getFileList(filePath);
                    chooseFileAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 点击事件
     * @param view
     */
    public void MyClick(View view) {
        switch (view.getId()) {
            case R.id.ll_goback:
                //返回按钮
                break;
            case R.id.ll_newfile:
                //新建文件
                break;
            case R.id.ll_bottom:
                //底部 确认
            case R.id.ll_up_one_level:
                //上一级
                break;
        }
    }

    /**
     * 创建文件或文件夹
     */
    private void creatNewFile() {

    }

    /**
     * 获取文件列表
     */
    private void getFileList(String filePath) {
        try {
            // 先清空是因为在遍历子文件夹的时候需要
            fileList.clear();
            // filePath == 手机根目录
            File file = new File(filePath);
            File[] files = file.listFiles();
            // 循环获取就行了
            for (int i = 0; i < files.length; i++) {
                // 根据获取 文件和文件夹 来区分
                if (chooseType == 0) {
                    if (files[i].isDirectory()) {
                        fileList.add(files[i]);
                    }
                } else if (chooseType == 1) {
                    // 获取文件要 所有文件
                    fileList.add(files[i]);
                }
            }
            // 根据字母a-z排序
            Collections.sort(fileList, (a, z) -> a.compareTo(z));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}