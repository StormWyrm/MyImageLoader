package cn.qingfeng.myimageloaderlibrary.net;

import android.graphics.Bitmap;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/2/10 21:21
 * @DESC: 当图片下载成功的回调函数
 * @VERSION: V1.0
 */
public interface ImageCallback {
    /***
     * 当出现地址，服务器等错误时调
     *
     * @param e
     */
    void onFailure(Exception e);

    /**
     * 图片加载成功时回调
     *
     * @param bitmap
     */
    void onSuccess(Bitmap bitmap);

    /***
     * 当服务器返回错误码时回调
     *
     * @param errorCode
     */
    void onError(int errorCode);
}
