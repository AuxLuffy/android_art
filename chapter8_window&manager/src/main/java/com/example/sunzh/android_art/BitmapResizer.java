package com.example.sunzh.android_art;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by sunzh on 2018/1/7.
 * 图片压缩工具
 *
 * @author sunzh
 */

public class BitmapResizer {
    /**
     * 加载资源文件的图片
     *
     * @param res       资源
     * @param resId     资源Id
     * @param reqWidth  图片需求宽度
     * @param reqHeight 图片需求高度
     * @return
     */
    public Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        //获取图片边界信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //计算需要的采样率
        options.inSampleSize = calcInSampleSize(options, reqWidth, reqHeight);

        //真正加载图片
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从输入流加载并压缩图片
     *
     * @param fd        可以由InputStream.getFD()方法获取
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calcInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 比较宽高获取采样率
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calcInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeihgt = height / 2;
            final int halfWidth = width / 2;
            while (halfHeihgt / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
