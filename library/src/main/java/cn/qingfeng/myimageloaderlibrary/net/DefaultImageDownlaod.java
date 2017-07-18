package cn.qingfeng.myimageloaderlibrary.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.qingfeng.myimageloaderlibrary.compress.ImageCompress;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:32
 * @DESC: 默认的图片下载方式
 * @VERSION: V1.0
 */
public class DefaultImageDownlaod implements ImageDownload {
    private static final String TAG = "DefaultImageDownlaod";
    private int corePoolSize = Runtime.getRuntime().availableProcessors();//获取核心线程数
    private int maxPoolSize = 2 * corePoolSize + 1;//最大线程数
    private ThreadPoolExecutor executor;
    private Handler handler;

    public DefaultImageDownlaod() {
        Log.i(TAG, "DefaultImageDownlaod: " + corePoolSize);
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(128));
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getBitmap(final ImageView imageView, final String imagePath, final ImageCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getBitmapFromNet(imageView, imagePath, callback);
            }
        });
    }

    /**
     * 网络中加载图片
     *
     * @param imagePath
     * @param callback
     */
    private void getBitmapFromNet(ImageView imageView, String imagePath, final ImageCallback callback) {
        if (imageView == null || TextUtils.isEmpty(imagePath)) {
            return;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(imagePath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BitmapFactory.Options options = ImageCompress.getCompressBitmap(in, imageView);
                in.close();
                connection.disconnect();

                connection = (HttpURLConnection) url.openConnection();
                in = connection.getInputStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
                in.close();


                if (callback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(bitmap);
                        }
                    });
                }

            } else {
                if (callback != null) {
                    final int errorCode = connection.getResponseCode();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(errorCode);
                        }
                    });
                }
            }


        } catch (final Exception e) {
            if (callback != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }


}
