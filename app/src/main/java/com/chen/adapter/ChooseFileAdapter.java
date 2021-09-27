package com.chen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chen.filechoose.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * @author CHEN
 * 选择文件/文件夹适配器
 */
public class ChooseFileAdapter extends RecyclerView.Adapter<ChooseFileAdapter.FileHolder> {
    private List<File> files;
    private Context context;
    private int chooseType;

    public ChooseFileAdapter(Context context, List<File> chooseFileList, int chooseType) {
        this.context = context;
        this.files = chooseFileList;
        this.chooseType = chooseType;
    }

    @NonNull
    @Override
    public ChooseFileAdapter.FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_list, parent, false);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseFileAdapter.FileHolder holder, int position) {
        File file = files.get(position);
        if (file != null) {
            // 文件和文件夹图标分开展示
            if (file.isFile()) {
                holder.ivChooseFile.setImageDrawable(context.getResources().getDrawable(R.mipmap.choose_file));
            } else {
                holder.ivChooseFile.setImageDrawable(context.getResources().getDrawable(R.mipmap.choose_folder));
            }
            holder.tvFileName.setText(file.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return files == null ? 0 : files.size();
    }
    // 点击事件
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class FileHolder extends RecyclerView.ViewHolder {
        ImageView ivChooseFile;
        TextView tvFileName;


        public FileHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivChooseFile = itemView.findViewById(R.id.iv_choose_file);
            tvFileName = itemView.findViewById(R.id.tv_choose_filename);

        }
    }
}
