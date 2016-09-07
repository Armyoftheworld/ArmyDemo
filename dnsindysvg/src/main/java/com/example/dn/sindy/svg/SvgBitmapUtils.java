package com.example.dn.sindy.svg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.svgandroid.SVG;
import com.svgandroid.SVGParser;

public class SvgBitmapUtils {
	
	/***
	 * 通过svg文件生成Picture
	 * @param mContext
	 * @param width
	 * @param height
	 * @param resouceId
	 * @return
	 */
	public static Bitmap getSvgBitmap(Context mContext,int width,int height,int resouceId){
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		if(resouceId>0){
			SVG svg = SVGParser.getSVGFromInputStream(mContext.getResources().openRawResource(resouceId), width, height);
			canvas.drawPicture(svg.getPicture());
		}else{
			canvas.drawRect(new Rect(0, 0, width, height), paint);
		}
		
		
		return bitmap;
	}
	
	//
	/***
	 * 把svg和原图片合成目标图片
	 * @param mContext
	 * @param fg
	 * @param resourceId
	 * @return
	 */
	public static Bitmap getResourceBitmap(Context mContext,Bitmap fg,int resourceId){
		int size = Math.min(fg.getWidth(), fg.getHeight());
		int x = (fg.getWidth()-size)/2;
		int y = (fg.getHeight()-size)/2;
		//先把svg的图片加载进来
		Bitmap svgBitmap = getSvgBitmap(mContext, size, size, resourceId);
		Bitmap bitmap = Bitmap.createBitmap(size,size,Config.ARGB_8888);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Canvas canvas = new Canvas(bitmap);
		//晓敏老师在上还是在下--------上下都可以
		//Xformode 模式
		canvas.drawBitmap(svgBitmap, new Matrix(), paint);
		//设置Xformode模式
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
		//再去绘制fg
		canvas.drawBitmap(Bitmap.createBitmap(fg, x, y, size, size), new Matrix(), paint);
		
		return bitmap;
	}

}
