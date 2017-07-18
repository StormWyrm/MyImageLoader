package cn.qingfeng.myimageloaderlibrary.cache;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:24
 * @DESC: 图片缓存接口
 * @VERSION: V1.0
 */
public interface ImageCache {
    void putImage(String url, Bitmap bitmap) throws IOException;

    Bitmap getImage(String url) throws IOException;

    void clearImage(String url) throws IOException;

    void clearAll() throws IOException;
}
