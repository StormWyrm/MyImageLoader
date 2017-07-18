package cn.qingfeng.myimageloaderlibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.qingfeng.myimageloaderlibrary.util.DiskLruCache;
import cn.qingfeng.myimageloaderlibrary.util.MD5Util;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:29
 * @DESC: 默认的硬盘缓存方式 使用LruDiskCache
 * @VERSION: V1.0
 */
public class DefaultDiskCache implements ImageCache {
    private DiskLruCache diskLruCache;//硬盘缓存

    private File defaultDiskCacheDir;//默认的缓存目录
    private long defaultDiskCacheSize;//默认的缓存大小

    public DefaultDiskCache(Context context) {
        try {
            defaultDiskCacheDir = getDiskCacheDir(context, "cache");
            defaultDiskCacheSize = 100 * 1024 * 1024;
            diskLruCache = DiskLruCache.open(defaultDiskCacheDir, 1, 1, defaultDiskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /****
     * 设置缓存目录
     *
     * @param defaultDiskCacheDir
     */
    public void setDefaultDiskCacheDir(File defaultDiskCacheDir) {
        this.defaultDiskCacheDir = defaultDiskCacheDir;
        try {
            diskLruCache = DiskLruCache.open(defaultDiskCacheDir, 1, 1, defaultDiskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 设置缓存大小
     *
     * @param defaultDiskCacheSize
     */
    public void setDefaultDiskCacheSize(long defaultDiskCacheSize) {
        this.defaultDiskCacheSize = defaultDiskCacheSize;
        try {
            diskLruCache = DiskLruCache.open(defaultDiskCacheDir, 1, 1, defaultDiskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putImage(String url, Bitmap bitmap) throws IOException {
        DiskLruCache.Editor editor = diskLruCache.edit(MD5Util.getMD5String(url));
        OutputStream os = null;
        try {
            os = editor.newOutputStream(0);
            if (bitmap != null) {
                boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                if (b) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }

        } finally {
            os.close();
        }

    }

    @Override
    public Bitmap getImage(String url) throws IOException {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(url)) {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(MD5Util.getMD5String(url));
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
            return null;
        }
        return null;
    }

    @Override
    public void clearImage(String url) throws IOException {
        if (!TextUtils.isEmpty(url)) {
            diskLruCache.remove(MD5Util.getMD5String(url));
        }
    }

    @Override
    public void clearAll() throws IOException {
        if (diskLruCache != null) {
            if (diskLruCache.size() > 0) {
                diskLruCache.delete();
            }
        }
    }

    /****
     * 获取默认的缓存目录
     *
     * @param context
     * @param cacheDir 缓存目录
     * @return
     */
    private File getDiskCacheDir(Context context, String cacheDir) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath() + "/" + cacheDir;
        } else {
            cachePath = context.getCacheDir().getPath() + "/" + cacheDir;
        }
        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
