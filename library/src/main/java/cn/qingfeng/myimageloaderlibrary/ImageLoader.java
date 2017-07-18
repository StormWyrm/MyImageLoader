package cn.qingfeng.myimageloaderlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.IOException;

import cn.qingfeng.myimageloaderlibrary.cache.ImageCache;
import cn.qingfeng.myimageloaderlibrary.config.ImageLoaderConfig;
import cn.qingfeng.myimageloaderlibrary.net.ImageCallback;
import cn.qingfeng.myimageloaderlibrary.net.ImageDownload;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:14
 * @DESC: 通过该类来对图片进行设置
 * @VERSION: V1.0
 */
public class ImageLoader {
    private static ImageLoader mImageLoader;    //ImageLoader实行单例模式

    private ImageLoaderConfig imageLoaderConfig;//ImageLoader的各种配置

    private ImageLoader(Context context) {
        //初始化默认的ImageLoader配置
        imageLoaderConfig = new ImageLoaderConfig.Builder(context).build();
    }

    /***
     * 获取单例的ImageLoader对象
     * @param context
     * @return
     */
    public static ImageLoader getInstance(Context context) {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader(context.getApplicationContext());
                }
            }
        }
        return mImageLoader;
    }

    /***
     * 初始化ImageLoader的配置
     *
     * @param imageLoaderConfig 封装ImageLoader的各种配置
     */
    public void init(ImageLoaderConfig imageLoaderConfig) {
        mImageLoader.imageLoaderConfig = imageLoaderConfig;
    }


    /***
     * 将URL上图片加载到ImageView上
     *
     * @param imageView
     * @param url
     */
    public void displayImage(final ImageView imageView, final String url) {
        int defaultDrawableId = imageLoaderConfig.getDefaultDrawableId();
        final int errorDrawableId = imageLoaderConfig.getErrorDrawableId();
        final ImageCache imageCache = imageLoaderConfig.getImageCache();
        ImageDownload imageDownload = imageLoaderConfig.getImageDownload();

        //防止ListView加载图片出现错乱
        imageView.setTag(url);


        //显示图片加载前的图片
        showDefaultDrawable(imageView, defaultDrawableId);


        //从缓存中获取图片
        try {
            Bitmap myBitmap = imageCache.getImage(url);
            if (myBitmap != null) {
                displayImage(imageView, url, myBitmap);
                return;
            }
        } catch (Exception e) {
            showErrorDrawable(imageView, errorDrawableId);
            e.printStackTrace();
        }


        //网络中下载图片
        imageDownload.getBitmap(imageView, url, new ImageCallback() {
            @Override
            public void onFailure(Exception e) {
                showErrorDrawable(imageView, errorDrawableId);
                e.printStackTrace();
            }

            @Override
            public void onSuccess(Bitmap bitmap) {
                if (bitmap != null) {
                    try {
                        //展示图片
                        displayImage(imageView, url, bitmap);

                        //将图片放入到缓存中
                        imageCache.putImage(url, bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showErrorDrawable(imageView, errorDrawableId);
                }
            }

            @Override
            public void onError(int errorCode) {
                showErrorDrawable(imageView, errorDrawableId);
            }
        });
    }


    //将图片显示到ImageView上
    private void displayImage(ImageView imageView, final String url, Bitmap bitmap) {
        if (imageView.getTag().equals(url)) {
            imageView.setImageBitmap(bitmap);
        }
    }

    //将默认图片显示到ImageView上
    private void showDefaultDrawable(ImageView imageView, int defaultDrawableId) {
        if (defaultDrawableId != 0) {
            imageView.setImageResource(defaultDrawableId);
        }
    }

    //将错误图片显示到ImageView上
    private void showErrorDrawable(ImageView imageView, int errorDrawableId) {
        if (errorDrawableId != 0) {
            imageView.setImageResource(errorDrawableId);
        }
    }
}
