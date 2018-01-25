package com.example.twapp.base;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


public abstract class BaseFragment extends Fragment {
    private View conteView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        conteView = inflater.inflate(getViewID(), container, false);
        initView(conteView);
        setListener();
        return conteView;
    }


    /**
     * b布局id传入
     *
     * @return
     */
    protected abstract int getViewID();

    /**
     * 初始化view
     *
     * @param conteView
     */
    protected abstract void initView(View conteView);

    /**
     * 监听事件
     */
    protected abstract void setListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create();
    }

    protected abstract void create();

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        resuem();
        
    }

    protected abstract void resuem();

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
        psuse();
    }

    protected abstract void psuse();

    protected void copyDBToSDcrad() {
        String DATABASE_NAME = "twdb";

        String oldPath = "data/data/com.example.twapp/databases/" + DATABASE_NAME;
        String newPath = Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME;

        if (copyFile(oldPath, newPath)) {
            Toast.makeText(getActivity(), "导出成功 ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "导出失败 ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径
     * @param newPath String 复制后路径
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if (!newfile.exists()) {
                newfile.createNewFile();
            }
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            return false;

        }
        return true;
    }
}
