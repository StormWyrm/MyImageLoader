package cn.qingfeng.myimageloaderlibrary.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.reflect.Field;


/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2016/10/26 9:13
 * @DESC: 图片压缩的工具类
 * @VERSION: V1.0
 */
public class ImageCompress {


    public static BitmapFactory.Options getCompressBitmap(InputStream in, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = caculateInSampleSize(in, imageView, options);
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;

        return options;
    }

    /**
     * @NAME: 计算图片压缩的比例
     * @PRAAMS:
     * @RETURN:
     * @Description:
     */
    private static int caculateInSampleSize(InputStream in, ImageView imageView, BitmapFactory.Options options) {
        //获取ImageView的宽和高
        ImageSize imageViewSize = getImageViewSize(imageView);
        int imageViewWidth = imageViewSize.width;
        int imageViewHeight = imageViewSize.height;

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(in, null, options);
        int width = options.outWidth;
        int height = options.outHeight;

        options.inJustDecodeBounds = false;
        int inSampleSize = 1;
        if (width > imageViewWidth || height > imageViewHeight) {
            int widthRadio = Math.round(width * 1.0f / imageViewWidth);
            int heightRadio = Math.round(height * 1.0f / imageViewHeight);
            inSampleSize = Math.min(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    /**
     * @NAME: 获取imageView的宽和高
     * @PRAAMS:
     * @RETURN: ImageSize
     * @Description:
     */
    private static ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

        int width = imageView.getWidth();
        if (width <= 0) {
            width = layoutParams.width;
        }
        if (width <= 0) {
            //width = imageView.getMaxWidth();// 检查最大值
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getWidth();
        if (height <= 0) {
            height = layoutParams.height;
        }
        if (width <= 0) {
            //width = imageView.getMaxWidth();// 检查最大值
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        }
        if (width <= 0) {
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }


    /**
     * 通过反射获取imageview的某个属性值
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
        }
        return value;

    }

    /**
     * @NAME: 图片的宽和高的封装
     * @PRAAMS:
     * @RETURN:
     * @Description:
     */
    private static class ImageSize {
        int width;
        int height;
    }


}
