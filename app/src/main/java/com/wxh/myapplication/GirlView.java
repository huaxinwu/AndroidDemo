package com.wxh.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 *  自定义View
 * Created by wxh on 2017/12/28.
 */

public class GirlView extends View {

    // 定义相关变量,依次是妹子显示位置的X,Y坐标
    public float bitmapX;
    public float bitmapY;

    /**
     *  初始化妹子坐标
     * @param context
     */
    public GirlView(Context context) {
        super(context);
        // 设置妹子的起始坐标
        bitmapX = 0;
        bitmapY = 200;
    }

    /**
     * //重写View类的onDraw()方法
     *  绘制图
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建一个画笔对象
        Paint paint = new Paint();
        // 根据图片生成位图对象
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.girl);
        // 绘制萌妹子
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);
        // 判断图片是否回收,木有回收的话强制收回图片
        if(bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
