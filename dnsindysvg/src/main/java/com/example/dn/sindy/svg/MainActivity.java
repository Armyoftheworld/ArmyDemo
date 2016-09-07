package com.example.dn.sindy.svg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends ActionBarActivity {

	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.imageView);
	}

	public void shape(View v){
		//切换形状
		int [] resIds = new int[]{
				R.raw.shape_5,
				R.raw.shape_circle_2,
				R.raw.shape_flower,
				R.raw.shape_heart,
				R.raw.shape_star
		};
		//随机形状id
        int resId = resIds[new Random().nextInt(resIds.length)];
      //原始图
      	Bitmap meizi = BitmapFactory.decodeResource(getResources(), R.drawable.half);
	 //合成的形状图4
      	Bitmap bitmap = SvgBitmapUtils.getResourceBitmap(this, meizi, resId);
      	meizi.recycle();
      	iv.setImageBitmap(bitmap);
	}
	
}
