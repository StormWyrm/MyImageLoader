package cn.qingfeng.myimageloaderlibrary.net;

import android.widget.ImageView;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/9 18:22
 * @DESC: 网络图片下载的接口
 * @VERSION: V1.0
 */
public interface ImageDownload {
    /***
     * 从网络中下载图片
     *
     * @param url 图片的地址
     */
    void getBitmap(ImageView imageView, String url, ImageCallback callback);
}
