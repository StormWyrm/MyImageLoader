package cn.qingfeng.myimageloaderlibrary.config;

import android.content.Context;

import cn.qingfeng.myimageloaderlibrary.cache.DefaultImageCache;
import cn.qingfeng.myimageloaderlibrary.cache.ImageCache;
import cn.qingfeng.myimageloaderlibrary.net.DefaultImageDownlaod;
import cn.qingfeng.myimageloaderlibrary.net.ImageDownload;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/15 15:10
 * @DESC: ImageLoader的配置类 使用Builder模式创建
 * @VERSION: V1.0
 */
public class ImageLoaderConfig {
    private ImageDownload mImageDownload;//图片下载的基类
    private ImageCache mImageCache;//图片缓存的基类
    private int defaultDrawableId;//默认显示图片
    private int errorDrawableId;//加载错误时显示的图片

    //私有化构造器
    private ImageLoaderConfig() {}

    public ImageDownload getImageDownload() {
        return mImageDownload;
    }

    public ImageCache getImageCache() {
        return mImageCache;
    }

    public int getDefaultDrawableId() {
        return defaultDrawableId;
    }

    public int getErrorDrawableId() {
        return errorDrawableId;
    }

    public void setErrorDrawableId(int errorDrawableId) {
        this.errorDrawableId = errorDrawableId;
    }

    public static class Builder {
        private ImageLoaderConfig config;

        public Builder(Context context) {
            config = new ImageLoaderConfig();
            //默认的图片下载
            config.mImageDownload = new DefaultImageDownlaod();
            //默认的图片缓存
            config.mImageCache = new DefaultImageCache(context.getApplicationContext());

        }

        public Builder setImageDownload(ImageDownload imageDownload) {
            config.mImageDownload = imageDownload;
            return this;
        }

        public Builder setImageCache(ImageCache imageCache) {
            config.mImageCache = imageCache;
            return this;
        }

        public Builder setDefaultDrawable(int defaultDrawableId) {
            config.defaultDrawableId = defaultDrawableId;
            return this;
        }

        public Builder setErrorDrawable(int errorDrawableId) {
            config.errorDrawableId = errorDrawableId;
            return this;
        }

        public ImageLoaderConfig build() {
            return config;
        }
    }

}
