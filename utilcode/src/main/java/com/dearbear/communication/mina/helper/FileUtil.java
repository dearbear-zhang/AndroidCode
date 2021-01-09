package com.dearbear.communication.mina.helper;

import android.text.TextUtils;
import android.util.Log;

import com.dearbear.communication.mina.bean.FilePartMsg;
import com.dearbear.communication.mina.bean.FileTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    public static FileTask getFileTask(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        FileTask task = new FileTask();
        if (file.exists()) {
            task.fileName = file.getName();
            task.filePath = filePath;
            task.length = file.length();
            task.fileSegmentSize = MinaConstans.FILE_SEGMENT_SIZE;
            task.partNum = (int) (task.length / task.fileSegmentSize);
            task.lastSegmentSize = (int) (task.length % (task.partNum * task.fileSegmentSize));
        } else {
            return null;
        }
        return task;
    }

    /***
     * 读取指定位置的文件分段
     * @param fileTask
     * @param pardIdLocal
     */
    public static byte[] randowFileRead(FileTask fileTask, long pardIdLocal) {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(fileTask.filePath, "r");
            int buffSize;
            if (pardIdLocal < fileTask.partNum) {
                buffSize = fileTask.fileSegmentSize;
            } else {
                buffSize = fileTask.lastSegmentSize;
            }
            byte[] buffer = new byte[buffSize];
            randomAccessFile.seek(pardIdLocal * fileTask.fileSegmentSize);
            int availableSize = randomAccessFile.read(buffer);
            Log.d(TAG, "randowFileRead: " + availableSize);
            randomAccessFile.close();
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 文件分段写入指定位置
     * @param fileTask
     * @param filePart
     */
    public static void randowFileWrite(FileTask fileTask, FilePartMsg filePart) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(
                    fileTask.filePath, "rw");
            long beginIndex = fileTask.fileSegmentSize * filePart.getPartId();
            randomAccessFile.seek(beginIndex);
            randomAccessFile.write(filePart.getData());
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
