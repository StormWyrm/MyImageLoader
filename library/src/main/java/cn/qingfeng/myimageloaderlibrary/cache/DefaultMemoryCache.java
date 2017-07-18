package cn.qingfeng.myimageloaderlibrary.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import cn.qingfeng.myimageloaderlibrary.util.MD5Util;


/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:28
 * @DESC: 默认的内存缓存方式
 * @VERSION: V1.0
 */
public class DefaultMemoryCache implements ImageCache {
    private LruCache<String, Bitmap> lruCache;

    public DefaultMemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public void putImage(String url, Bitmap bitmap) {
        if (!TextUtils.isEmpty(url) && bitmap != null){
            lruCache.put(MD5Util.getMD5String(url), bitmap);
        }
    }

    @Override
    public Bitmap getImage(String url) {
        if (!TextUtils.isEmpty(url)) {
            return lruCache.get(MD5Util.getMD5String(url));
        } else {
            return null;
        }
    }

    @Override
    public void clearImage(String url) {
        if (!TextUtils.isEmpty(url))
            lruCache.remove(MD5Util.getMD5String(url));
    }

    @Override
    public void clearAll() {
        if(lruCache != null){
            if(lruCache.size() > 0){
                lruCache.evictAll();
            }
        }
    }
}
