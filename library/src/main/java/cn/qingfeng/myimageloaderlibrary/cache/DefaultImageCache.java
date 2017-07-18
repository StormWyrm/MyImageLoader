package cn.qingfeng.myimageloaderlibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.IOException;

import cn.qingfeng.myimageloaderlibrary.util.MD5Util;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:32
 * @DESC: 默认的图片缓存方式 采用内存硬盘双缓存策略
 * @VERSION: V1.0
 */
public class DefaultImageCache implements ImageCache {
    private DefaultMemoryCache defaultMemoryCache; //默认的内存缓存
    private DefaultDiskCache defaultDiskCache;//默认的硬盘

    public DefaultImageCache(Context context) {
        defaultMemoryCache = new DefaultMemoryCache();
        defaultDiskCache = new DefaultDiskCache(context);
    }

    @Override
    public void putImage(String url, Bitmap bitmap) throws IOException {
        url = MD5Util.getMD5String(url);
        defaultMemoryCache.putImage(url, bitmap);
        defaultDiskCache.putImage(url, bitmap);
    }

    @Override
    public Bitmap getImage(String url) throws IOException {
        url = MD5Util.getMD5String(url);
        //默认先从内存中获取缓存图片
        Bitmap bitmap = defaultMemoryCache.getImage(url);
        if (bitmap == null) {
            //内存中不存在时，从硬盘中获取
            bitmap = defaultDiskCache.getImage(url);
            if (bitmap != null) {
                //将图片缓存到内存中去
                defaultMemoryCache.putImage(url, bitmap);
//                System.out.println("从硬盘中获取图片");
            }
        }
        return bitmap;
    }

    @Override
    public void clearImage(String url) throws IOException {
        url = MD5Util.getMD5String(url);
        defaultMemoryCache.clearImage(url);
        defaultDiskCache.clearImage(url);
    }

    @Override
    public void clearAll() throws IOException {
        defaultMemoryCache.clearAll();
        defaultDiskCache.clearAll();
    }
}
